package ejercicio1;

import java.sql.*;

public class Ejercicio1 
{
	public static void main(String[] args)
	{
		try {
			// Creamos la conexión con nuestra base de datos
			Connection con = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\fadri\\Downloads\\sqlite-tools-win32-x86-3360000\\veterinaria.db");
			
			// Obtenemos los metadatos
			DatabaseMetaData dbmd = con.getMetaData();
			ResultSet resul = dbmd.getTables(null, "veterinaria", null, null);

			// Mostramos los diferentes metadatos de cada tabla
			while(resul.next())
			{
				String catalogo = resul.getString("TABLE_CAT");
				String esquema = resul.getString("TABLE_SCHEM");
				String tabla  = resul.getString("TABLE_NAME");
				String tipo = resul.getString("TABLE_TYPE");
				
				System.out.println("Catálogo " + catalogo + " - Esquema: " + esquema + " - Tipo: " + tipo + " - Nombre tabla: " + tabla);
			}
			
			// Cerramos la conexión con la base de datos
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}