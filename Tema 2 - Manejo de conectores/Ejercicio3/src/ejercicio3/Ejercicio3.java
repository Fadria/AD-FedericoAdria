package ejercicio3;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;

import java.util.Scanner;

public class Ejercicio3 {
	public static void main(String[] args) {
		try {
			// Variable que va a contener todos los datos que se muestren por la aplicación
			String datos = "";

			// Creamos la conexión con nuestra base de datos
			Connection con = DriverManager
					.getConnection("jdbc:mariadb://localhost:3306/starwars?user=star&password=wars");

			// Sentencia 1. Lista la información de todos los planetas (planets)
			apartado1(con);

			// Sentencia 2. Inserta  tres  nuevos  registros  en  la  tabla  films  con  los  datos  de  los episodios VII, VIII y IX 
			apartado2(con);

			// Sentencia 3. Lista  la  información de  los  personajes  (characters)  que  pertenecen  a  la Orden Jedi
			apartado3(con);

			// Sentencia 4. Lista el nombre de los personajes que mueren en el episodio III junto al nombre de su asesino.
			apartado4(con);
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void apartado1(Connection con) 
	{
		String sql = "SELECT * from planets;"; // Sentencia usada

		// Ejecutamos nuestra sentencia y mostramos un error en el caso de que suceda
		Statement sentencia;
		try {
			sentencia = con.createStatement();

			// Obtenemos los datos para ser mostrados y los metadatos para organizar nuestra información
			ResultSet resultSet = sentencia.executeQuery(sql);
			ResultSetMetaData rsmd = resultSet.getMetaData();
			
			// Número de columnas
			int numeroColumnas = rsmd.getColumnCount();
			
			// Mostramos cada línea
			while (resultSet.next()) 
			{
				// Mostramos cada columna
				for (int i = 1; i <= numeroColumnas; i++) {
					if (i > 1)
						System.out.print(",  ");
					String valorColumna = resultSet.getString(i);
					System.out.print(rsmd.getColumnName(i) + ": " + valorColumna);
				}
				
				System.out.println();
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void apartado2(Connection con) 
	{
		String sql1 = "INSERT INTO films VALUES (7, 'Episode VII', 'The Force Awakens');"; // Insert 1
		String sql2 = "INSERT INTO films VALUES (8, 'Episode VIII', 'The Last Jedi');"; // Insert 2
		String sql3 = "INSERT INTO films VALUES (9, 'Episode IX', 'The Rise of Skywalker');"; // Insert 3

		// Ejecutamos nuestras sentencias y mostramos un error en el caso de que suceda
		Statement sentencia;
		try {
			sentencia = con.createStatement();

			// Insert 1
			int rs = sentencia.executeUpdate(sql1); // Obtenemos las filas afectadas
			System.out.println("Filas afectadas: " + rs);

			// Insert 2
			rs = sentencia.executeUpdate(sql2); // Obtenemos las filas afectadas
			System.out.println("Filas afectadas: " + rs);

			// Insert 3
			rs = sentencia.executeUpdate(sql3); // Obtenemos las filas afectadas
			System.out.println("Filas afectadas: " + rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void apartado3(Connection con) 
	{
		String sql = "SELECT * FROM characters ch join character_affiliations caff on caff.id_character = ch.id join affiliations aff on aff.id = caff.id_affiliation where aff.affiliation = 'Jedi Order';";

		// Ejecutamos nuestras sentencias y mostramos un error en el caso de que suceda
		Statement sentencia;
		try {
			sentencia = con.createStatement();
			
			// Obtenemos los datos para ser mostrados y los metadatos para organizar nuestra información
			ResultSet resultSet = sentencia.executeQuery(sql);
			ResultSetMetaData rsmd = resultSet.getMetaData();
			
			// Número de columnas
			int numeroColumnas = rsmd.getColumnCount();
			
			// Mostramos cada línea
			while (resultSet.next()) 
			{
				// Mostramos cada columna
				for (int i = 1; i <= numeroColumnas; i++) {
					if (i > 1)
						System.out.print(",  ");
					String valorColumna = resultSet.getString(i);
					System.out.print(rsmd.getColumnName(i) + ": " + valorColumna);
				}
				
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void apartado4(Connection con) 
	{
		String sql = "SELECT ch.name, (SELECT ch1.name from characters ch1 WHERE ch1.id = de.id_killer) as nameKiller FROM `deaths` de join films fi on fi.id = de.id_film join characters ch on ch.id = de.id_character where fi.episode = 'Episode III';";
		
		// Ejecutamos nuestras sentencias y mostramos un error en el caso de que suceda
		Statement sentencia;
		try {
			sentencia = con.createStatement();
			
			// Obtenemos los datos para ser mostrados y los metadatos para organizar nuestra información
			ResultSet resultSet = sentencia.executeQuery(sql);
			ResultSetMetaData rsmd = resultSet.getMetaData();
			
			// Número de columnas
			int numeroColumnas = rsmd.getColumnCount();
			
			// Mostramos cada línea
			while (resultSet.next()) 
			{
				// Mostramos cada columna
				for (int i = 1; i <= numeroColumnas; i++) {
					if (i > 1)
						System.out.print(",  ");
					String valorColumna = resultSet.getString(i);
					System.out.print(rsmd.getColumnName(i) + ": " + valorColumna);
				}
				
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}