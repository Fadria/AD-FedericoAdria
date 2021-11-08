package ejercicio4;

import java.sql.*;

import java.util.Scanner;

public class Ejercicio4 {
	
	public static void main(String[] args) {		
		try {
			// Creamos la conexión con nuestra base de datos
			Connection con = DriverManager
					.getConnection("jdbc:mariadb://localhost:3306/starwars?user=star&password=wars");

			// Apartado 1
			apartado1(con);
			
			// Apartado 2
			apartado2(con);
			
			// Apartado 3
			apartado3(con);
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void apartado1(Connection con) 
	{		
		Scanner sc = new Scanner(System.in);

		int valor1, valor2; // Variables para el diametro de los planetas
		
		for(int i = 0; i < 3; i++)
		{
			// Indicamos la ejecución en la que nos encontramos
			System.out.println("Ejecución " + (i+1) + ": ");
			
			// Pedimos los datos al usuario
			System.out.println("Introduzca el diámetro menor: ");
			valor1 = sc.nextInt();			
			System.out.println("Introduzca el diámetro mayor: ");
			valor2 = sc.nextInt();
			
			// Preparamos la consulta, cada interrogante será un valor indicado por el usuario a continuación
			String consulta = "SELECT * FROM planets p WHERE p.diameter between ? and ?; ";
			try {
				// Creamos la PreparedStatement y le asignamos los valores
				PreparedStatement sentencia= con.prepareStatement(consulta);
				sentencia.setInt(1, valor1);
				sentencia.setInt(2, valor2);
				
				// Obtenemos los datos para ser mostrados y los metadatos para organizar nuestra información
				ResultSet rs = sentencia.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				
				// Número de columnas
				int numeroColumnas = rsmd.getColumnCount();
				
				// Mostramos cada línea
				while (rs.next()) 
				{
					// Mostramos cada columna
					for (int j = 1; j <= numeroColumnas; j++) {
						if (j > 1)
							System.out.print(",  ");
						String valorColumna = rs.getString(j);
						System.out.print(rsmd.getColumnName(j) + ": " + valorColumna);
					}
					
					System.out.println();
				}
				
				rs.close();				
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
	}

	public static void apartado2(Connection con) 
	{
		int idPlaneta = -1; // Variable usada para almacenar la ID del planeta
		int idPersonaje = -1; // Variable usada para almacenar la ID del personaje
		Timestamp fechaActual = new Timestamp(System.currentTimeMillis()); // Obtenemos la fecha actual

		// Preparamos la sentencia que nos permitirá insertar un personaje
		String consulta = "INSERT INTO characters (id, name, height, mass, hair_color, skin_color, eye_color, birth_year, gender, planet_id, created_date, updated_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		// Creamos la PreparedStatement y le asignamos los valores
		try {
			PreparedStatement sentencia= con.prepareStatement(consulta);

			for(int i = 0; i < 3; i++)
			{
				// Insertamos a Rey
				if(i == 0)
				{
					idPlaneta = getIdFromPlanet("Jakku", con); // Obtenemos la ID de su planeta
					idPersonaje = getNextCharacterId(con); // Obtenemos la siguiente ID de personaje disponible
					
					// Indicamos todos los datos a usar en la sentencia
					sentencia.setInt(1, idPersonaje);
					sentencia.setString(2, "Rey");
					sentencia.setInt(3, 170);
					sentencia.setInt(4, 54);
					sentencia.setString(5, "black");
					sentencia.setString(6, "white");
					sentencia.setString(7, "brown");
					sentencia.setString(8, "15DBY");
					sentencia.setString(9, "female");
					sentencia.setInt(10, idPlaneta);
					sentencia.setTimestamp(11, fechaActual);
					sentencia.setTimestamp(12, fechaActual);
					
					// Realizamos el INSERT
					int rs = sentencia.executeUpdate();
					
					// Mostramos las filas afectadas por esta operación
					System.out.println("Filas afectadas: " + rs);
				}else {
					// Insertamos a Finn
					if(i == 1)
					{
						idPlaneta = getIdFromPlanet("Kamino", con); // Obtenemos la ID de su planeta
						idPersonaje = getNextCharacterId(con); // Obtenemos la siguiente ID de personaje disponible
						
						// Indicamos todos los datos a usar en la sentencia
						sentencia.setInt(1, idPersonaje);
						sentencia.setString(2, "Finn");
						sentencia.setInt(3, 178);
						sentencia.setInt(4, 73);
						sentencia.setString(5, "black");
						sentencia.setString(6, "dark");
						sentencia.setString(7, "dark");
						sentencia.setString(8, "11DBY");
						sentencia.setString(9, "male");
						sentencia.setInt(10, idPlaneta);
						sentencia.setTimestamp(11, fechaActual);
						sentencia.setTimestamp(12, fechaActual);
						
						// Realizamos el INSERT
						int rs = sentencia.executeUpdate();
						
						// Mostramos las filas afectadas por esta operación
						System.out.println("Filas afectadas: " + rs);
					}else { // Insertamos a Kylo Ren
						idPlaneta = getIdFromPlanet("Chandrila", con); // Obtenemos la ID de su planeta
						idPersonaje = getNextCharacterId(con); // Obtenemos la siguiente ID de personaje disponible
						
						// Indicamos todos los datos a usar en la sentencia
						sentencia.setInt(1, idPersonaje);
						sentencia.setString(2, "Kylo Ren");
						sentencia.setInt(3, 189);
						sentencia.setInt(4, 89);
						sentencia.setString(5, "black");
						sentencia.setString(6, "white");
						sentencia.setString(7, "brown");
						sentencia.setString(8, "5DBY");
						sentencia.setString(9, "male");
						sentencia.setInt(10, idPlaneta);
						sentencia.setTimestamp(11, fechaActual);
						sentencia.setTimestamp(12, fechaActual);
						
						// Realizamos el INSERT
						int rs = sentencia.executeUpdate();
						
						// Mostramos las filas afectadas por esta operación
						System.out.println("Filas afectadas: " + rs);
					}
				}
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static int getNextCharacterId(Connection con) {
		Statement sentencia;

		int idPersonaje = -1; // Variable que contendrá la siguiente ID de personaje libre

		try {			
			sentencia = con.createStatement();

			String sql = "SELECT max(id+1) FROM characters;";
			
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
					idPersonaje = resultSet.getInt(i);
				}
				
				System.out.println();
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return idPersonaje; // Devolvemos la primera id disponible para ser asignada
	}

	private static int getIdFromPlanet(String nombrePlaneta, Connection con) {
		// Consulta para obtener la id del planeta de los personajes
		String consulta = "SELECT id FROM `planets` WHERE name=?;";
		int idPlaneta = -1; // Inicializamos la variable
		
		try {
			PreparedStatement sentencia= con.prepareStatement(consulta);
			sentencia.setString(1, nombrePlaneta);
			ResultSet rs = sentencia.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();

			// Número de columnas
			int numeroColumnas = rsmd.getColumnCount();
			
			// Mostramos cada línea
			while (rs.next()) 
			{
				// Mostramos cada columna
				for (int j = 1; j <= numeroColumnas; j++)
					idPlaneta = rs.getInt(j);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return idPlaneta; // Devolvemos la id del planeta
	}

	public static void apartado3(Connection con) 
	{
		String consulta = "SELECT ch.name, (SELECT ch1.name from characters ch1 WHERE ch1.id = de.id_killer) as nameKiller FROM `deaths` de join films fi on fi.id = de.id_film join characters ch on ch.id = de.id_character where fi.title = ?;";
		String tituloPelicula = "";
		
		// Bucle que ejecutará acciones para cada una de las 9 películas
		for(int i = 1; i <= 9; i++)
		{
			tituloPelicula = getMovieTitle(i, con);
			
			System.out.println("Personaje y Asesino de la película: " + tituloPelicula);
			
			PreparedStatement sentencia;
			try {
				sentencia = con.prepareStatement(consulta);
				sentencia.setString(1, tituloPelicula);
				ResultSet rs = sentencia.executeQuery();
				
				ResultSetMetaData rsmd = rs.getMetaData();
				
				// Número de columnas
				int numeroColumnas = rsmd.getColumnCount();
				
				// Mostramos cada línea
				while (rs.next()) 
				{
					// Mostramos cada columna
					for (int j = 1; j <= numeroColumnas; j++) {
						if (j > 1)
							System.out.print(",  ");
						String valorColumna = rs.getString(j);
						System.out.print(rsmd.getColumnName(j) + ": " + valorColumna);
					}
					
					System.out.println();
				}
				
				System.out.println("\n");
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static String getMovieTitle(int idPelicula, Connection con) 
	{
		// Consulta para obtener la id del planeta de los personajes
		String consulta = "SELECT title FROM films WHERE id = ?;";
		String tituloPelicula = ""; // Inicializamos la variable
		
		try {
			PreparedStatement sentencia= con.prepareStatement(consulta);
			sentencia.setInt(1, idPelicula);
			ResultSet rs = sentencia.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();

			// Número de columnas
			int numeroColumnas = rsmd.getColumnCount();
			
			// Mostramos cada línea
			while (rs.next()) 
			{
				// Mostramos cada columna
				for (int j = 1; j <= numeroColumnas; j++)
					tituloPelicula = rs.getString(j);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tituloPelicula;
	}

}