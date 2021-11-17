package ejercicios;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Ejercicio5 {
	
	public static void main(String[] args) 
	{
		// Creamos la conexión con nuestra base de datos
		try {
			Connection conSqlite = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\fadri\\Downloads\\sqlite-tools-win32-x86-3360000\\veterinaria.db");
		
			Connection conMariaDb = DriverManager
					.getConnection("jdbc:mariadb://localhost:3306/veterinaria?user=star&password=wars");

			String sentencias = ejercicio1(conSqlite); // Obtenemos las sentencias create table
			
			ejercicio2(sentencias, conMariaDb); // Ejecutamos las sentencias que crearán las tablas en la base de datos mariadb
			
			ejercicio3(conSqlite, conMariaDb); // Insertamos los registros de la antigua base de datos en la nueva
			
			
			// Cerramos las conexiones con las bases de datos
			conSqlite.close();
			conMariaDb.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	private static void ejercicio3(Connection conSqlite, Connection conMariaDb) 
	{
		String insertsString = getTablesInserts(conSqlite);
		
		String[] sentenciasInsert = insertsString.split("SEPARADOR");

		Statement sentencia;
		try {
			sentencia = conMariaDb.createStatement();

			for(String sentenciaInsert:sentenciasInsert)
			{
				System.out.println("Sentencia ejecutada:\n" + sentenciaInsert);
				int rs = sentencia.executeUpdate(sentenciaInsert); // Obtenemos las filas afectadas
				System.out.println("Filas afectadas: " + rs);
			}

			String sentenciaIntegridad = "SET FOREIGN_KEY_CHECKS=1;";

			sentencia.executeUpdate(sentenciaIntegridad);
			
			sentencia = conMariaDb.createStatement();
			sentencia.executeUpdate(sentenciaIntegridad);

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		
		// Activamos las restricciones de las claves ajenas una vez finalizamos la creación de las tablas
	}

	private static String getTablesInserts(Connection conSqlite) {
		// Array bidimensional que va a contener el select necesario para obtener los valores de la tabla
		String sentenciasSelect[] = {"SELECT * from mascota;", "SELECT * from propietario;"};
			
		// String que contendrá cada fila de la base de datos
		String sentenciaInsert = "";		

		// Obtenemos los datos de cada sentencia
		for(String select:sentenciasSelect)
		{			
			// Ejecutamos nuestra sentencia y mostramos un error en el caso de que suceda
			Statement sentencia;
			
			try {
				sentencia = conSqlite.createStatement();

				// Obtenemos los datos para ser mostrados y los metadatos para organizar nuestra información
				ResultSet resultSet = sentencia.executeQuery(select);
				ResultSetMetaData rsmd = resultSet.getMetaData();
				
				// Número de columnas
				int numeroColumnas = rsmd.getColumnCount();
				
				// Recorremos cada línea
				while (resultSet.next()) 
				{
					// Comenzamos a formar nuestra sentencia INSERT
					sentenciaInsert += "INSERT INTO " + rsmd.getTableName(1) + " VALUES (";
					// Mostramos cada columna
					for (int i = 1; i <= numeroColumnas; i++) {
						if (i > 1)
							sentenciaInsert += ",  ";
						
						// Si nos encontramos en la columna fecha de la tabla propietario realizaremos el siguiente tratamiento
						if(rsmd.getTableName(1).equals("propietario") && i==4)
						{
							// Convertimos la cadena a una fecha que puede ser usada en un INSERT
							SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
							
						    try {
						        java.util.Date utilDate = format.parse(resultSet.getString(i));
						        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
								sentenciaInsert += '"' + sqlDate.toString() + '"';
						    } catch (ParseException e) {
						        e.printStackTrace();
						    }							
							
						}else {							
							sentenciaInsert += '"' + resultSet.getString(i) + '"'; // Valor de la columna
						}
						
					}
					
					sentenciaInsert += ");SEPARADOR"; // Añadimos la cadena SEPARADOR para separar cada línea con un split()
				}
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}
				
		return sentenciaInsert;
	}

	private static void ejercicio2(String sentencias, Connection conMariaDb) 
{			
		String[] arraySentencias = sentencias.split("\n");
		
		// Desactivamos las restricciones de las claves ajenas mientras cargamos la base de datos
		// Las volveremos a activar una vez finalicemos la inserción de datos en la base de datos en el ejercicio 3
		String sentenciaIntegridad = "SET FOREIGN_KEY_CHECKS=0;";
		Statement sentencia;
		try {
			sentencia = conMariaDb.createStatement();

			sentencia.executeUpdate(sentenciaIntegridad);
			
			for(int i = 0; i < arraySentencias.length; i++)
			{
				try {					
					System.out.println("Sentencia ejecutada: " + arraySentencias[i]);
					int rs = sentencia.executeUpdate(arraySentencias[i]); // Obtenemos las filas afectadas
					System.out.println("Filas afectadas: " + rs);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private static String ejercicio1(Connection con) {
		String sentenciaCreate = "";

		try {
			// Obtenemos los metadatos
			DatabaseMetaData dbmd = con.getMetaData();
			ResultSet resul = dbmd.getTables(null, "veterinaria", null, null);

			// Mostramos los diferentes metadatos de cada tabla
			while (resul.next()) {
				sentenciaCreate += "CREATE TABLE IF NOT EXISTS "; // Inicializamos la variable que contendrá la sentencia CREATE

				String nombreTabla = resul.getString("TABLE_NAME");
				sentenciaCreate += nombreTabla + " (";

				// Añadimos las diferentes columnas de una tabla
				ResultSet columnas = dbmd.getColumns(null, "veterinaria", nombreTabla, null);
				while (columnas.next()) {
					String nombreColumna = columnas.getString("COLUMN_NAME");
					String tipoColumna = columnas.getString("TYPE_NAME");

					sentenciaCreate += nombreColumna + " " + tipoColumna + ", ";
				}

				sentenciaCreate = sentenciaCreate.substring(0, sentenciaCreate.length() - 1); // Eliminamos los
																								// carácteres sobrantes
				sentenciaCreate += " PRIMARY KEY (";

				// Añadimos las claves primarias a nuestro create
				ResultSet primaryKeys = dbmd.getPrimaryKeys(resul.getString("TABLE_CAT"),
						resul.getString("TABLE_SCHEM"), resul.getString("TABLE_NAME"));
				String clavesPrimarias = "";
				while (primaryKeys.next()) {
					clavesPrimarias += primaryKeys.getString("COLUMN_NAME") + ", ";
				}
				sentenciaCreate += clavesPrimarias;
				sentenciaCreate = sentenciaCreate.substring(0, sentenciaCreate.length() - 2); // Eliminamos los
																								// carácteres sobrantes
				sentenciaCreate += "), ";

				// Añadimos las claves foráneas a nuestro create
				ResultSet foreignKeys = dbmd.getImportedKeys(resul.getString("TABLE_CAT"),
						resul.getString("TABLE_SCHEM"), resul.getString("TABLE_NAME"));

				while (foreignKeys.next()) {
					String nombreColumnaClaveAjena = foreignKeys.getString("FKCOLUMN_NAME");
					String nombreTablaAjena = foreignKeys.getString("PKTABLE_NAME");
					String nombreColumnaTablaAjena = foreignKeys.getString("PKCOLUMN_NAME");

					sentenciaCreate += " foreign key(" + nombreColumnaClaveAjena + ") references " + nombreTablaAjena
							+ "(" + nombreColumnaTablaAjena + "), ";
				}

				sentenciaCreate = sentenciaCreate.substring(0, sentenciaCreate.length() - 2); // Eliminamos los
																								// carácteres sobrantes
				sentenciaCreate += ");";

				// Añadimos las claves primarias a la sentencia
				sentenciaCreate += "\n";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sentenciaCreate;
	}
}