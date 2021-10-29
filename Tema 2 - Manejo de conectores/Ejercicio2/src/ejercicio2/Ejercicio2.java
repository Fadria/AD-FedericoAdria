package ejercicio2;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;

import java.util.Scanner;

public class Ejercicio2
{
	public static void main(String[] args)
	{
		try {
			// Variable que va a contener todos los datos que se muestren por la aplicación
			String datos = "";
			
			// Creamos la conexión con nuestra base de datos
			Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/starwars?user=star&password=wars");
			
			// Obtenemos los metadatos
			DatabaseMetaData dbmd = con.getMetaData();
			ResultSet resul = dbmd.getTables(null, "starwars", null, null);
			
			// Mientras tengamos tablas realizaremos las siguientes acciones
			while(resul.next())
			{
				// Obtenemos y mostramos el nombre de la tabla
				String nombreTabla = resul.getString("TABLE_NAME");
				datos += "Tabla " + nombreTabla + "\n\n";

				
				
				// Mostramos las claves primarias de la tabla
				ResultSet primaryKeys = dbmd.getPrimaryKeys(resul.getString("TABLE_CAT"), resul.getString("TABLE_SCHEM"), resul.getString("TABLE_NAME"));

				datos += "Clave primaria: ";
				String clavesPrimarias = "";
				while (primaryKeys.next()) {
	                clavesPrimarias += primaryKeys.getString("COLUMN_NAME") + " - ";
	            }				
				
				// Eliminamos los carácteres sobrantes del último elemento
				clavesPrimarias = clavesPrimarias.substring(0, clavesPrimarias.length() - 3);
	            datos += clavesPrimarias + "\n";

	            
	            
	            // Mostramos las diferentes columnas de una tabla
				ResultSet columnas = dbmd.getColumns(null, "starwars", nombreTabla, null);
				
				datos += "Columnas:\n";
				while(columnas.next())
				{
					datos += columnas.getString("COLUMN_NAME") + " - " + columnas.getString("TYPE_NAME") + "\n";
				}	            
				datos += "\n";
			}
			
			// Preguntamos al usuario como quiere visualizar sus datos			
			String opcion;
			do
			{
				Scanner sc = new Scanner(System.in);
				System.out.println("¿Cómo desea visualizar el resultado de la ejecución?\n-Consola\n-Fichero");
				opcion = sc.nextLine();				
			}while(!opcion.toLowerCase().equals("consola") && !opcion.toLowerCase().equals("fichero"));

			System.out.print("\n\n\n\n");
			if(opcion.toLowerCase().equals("consola")) // Mostramos los datos por consola
			{
				System.out.println(datos);
			}else {
				try (PrintWriter out = new PrintWriter("filename.txt")) // Guardamos el fichero en la carpeta del proyecto
				{
				    out.println(datos);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}				
			}
			
			System.out.println("OPERACIÓN COMPLETADA: Fichero guardado en la raíz del proyecto");
			
			// Cerramos la conexión con la base de datos
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}