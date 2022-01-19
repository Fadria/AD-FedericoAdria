package paquete;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.OID;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.And;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

public class Ejercicio {
	public static void main(String[] args) {
		// Descomentar para comprobar su ejecuci�n

		// EJERCICIO 1
		//migrarRealms();
		//migrarCharacters();
		//migrarMarriage();
		//migrarBooks();
		//migrarChapters();
		//migrarMovies();
		//migrarBooksMovies();
		//migrarDialogs();
		
		// EJERCICIO 2
		//consulta1();
		//consulta2();
		//consulta3();
		//consulta4();
		//consulta5();
		consulta6();
	}

	private static void consulta6() {
		// Realizamos la creaci�n/apertura de la base de datos
		ODB odb = ODBFactory.open("lotr_pruebas");
		
		ICriterion criterio = Where.equal("title", "The Return Of The King");
		IQuery consulta = new CriteriaQuery(Book.class, criterio);
		
		// Obtenemos el libro The Return Of The King
		Book libro = (Book) odb.getObjects(consulta).getFirst();

		// Obtenemos las pel�culas que adaptan el libro
		Set<Movie> peliculas = libro.getMovies();

		// Obtenemos todos los reinos
		consulta = new CriteriaQuery(Realm.class);
		List<Realm> reinos = (List)odb.getObjects(consulta);
		
		// Set con el nombre de los reinos
		Set<String> nombresReinos = new HashSet<String>();

		// Obtenemos el nombre de todos los reinos
		reinos.forEach(reino ->{
			nombresReinos.add(reino.getName());
		});
		
		// Por cada pel�cula ejecutaremos las siguientes acciones
		peliculas.forEach(pelicula -> {
			ICriterion criterio2 = Where.equal("movie.id", pelicula.getId());
			IQuery consulta2 = new CriteriaQuery(Dialog.class, criterio2);
			
			List<Dialog> dialogos = (List)odb.getObjects(consulta2);
			
			// Por cada di�logo comprobaremos si este contiene el nombre de alguno de todos los reinos
			dialogos.forEach(dialogo -> {
				nombresReinos.forEach(nombreReino -> {
					if(dialogo.getDialog().indexOf(nombreReino) != -1) {
						// Mostramos el reino y el di�logo donde aparece
						System.out.println("\nEl reino " + nombreReino + " aparece en el siguiente di�logo:\n" + dialogo.getDialog());
					}
				});
			});
		});

	}

	private static void consulta5() {
		// Realizamos la creaci�n/apertura de la base de datos
		ODB odb = ODBFactory.open("lotr_pruebas");
		
		ICriterion criterio = Where.equal("title", "The Hobbit");
		IQuery consulta = new CriteriaQuery(Book.class, criterio);
		
		// Obtenemos el libro de The Hobbit
		Book elHobbit = (Book) odb.getObjects(consulta).getFirst();
		
		// Obtenemos las pel�culas del libro
		Set<Movie> peliculas = elHobbit.getMovies();
		
		System.out.println("Pel�culas relacionadas con The Hobbit");
		
		// Mostramos el t�tulo de cada pel�cula
		peliculas.forEach(pelicula ->{
			System.out.println(pelicula.getName());
		});
		
		odb.close();
	}

	private static void consulta4() {
		System.out.println("Resultado del apartado 1");
		consulta4_1();
		System.out.println("Resultado del apartado 2");
		consulta4_2();
	}

	private static void consulta4_2() {
		// Realizamos la creaci�n/apertura de la base de datos
		ODB odb = ODBFactory.open("lotr_pruebas");
		
		ICriterion criterio = Where.equal("name", "Valinor");
		IQuery consulta = new CriteriaQuery(Realm.class, criterio);
	
		// Obtenemos el reino de Valinor
		Realm valinor = (Realm) odb.getObjects(consulta).getFirst();
		
		// Obtenemos el listado de personajes cuyo reino es igual al objeto anterior
		criterio = Where.equal("realm.id", valinor.getId());
		consulta = new CriteriaQuery(Character.class, criterio);
		List<Character> lista = (List)odb.getObjects(consulta);
		
		System.out.println("Personajes de Valinor");
		lista.forEach((personaje) -> {
			System.out.println(personaje.getName());		
		});
	}

	private static void consulta4_1() {
		// Realizamos la creaci�n/apertura de la base de datos
		ODB odb = ODBFactory.open("lotr_pruebas");
		
		ICriterion criterio = Where.equal("name", "Valinor");
		IQuery consulta = new CriteriaQuery(Realm.class, criterio);
		
		Realm valinor = (Realm) odb.getObjects(consulta).getFirst();
		
		System.out.println("Personajes de Valinor");
		valinor.getCharacters().forEach((personaje) -> {
			System.out.println(personaje.getName());		
		});
		
		odb.close();
	}

	private static void consulta3() {
		// Realizamos la creaci�n/apertura de la base de datos
		ODB odb = ODBFactory.open("lotr_pruebas");
		
		ICriterion criterio = new And().add(Where.gt("budgetInMillions", 100)).add(Where.lt("rottenTomatoesScore", 70));
		IQuery consulta = new CriteriaQuery(Movie.class, criterio);
		
		// Obtenemos el listado de pel�culas que cumplen los requisitos
		List<Movie> lista = (List)odb.getObjects(consulta);
		
		// Mostramos todos los datos de ellas
		System.out.println("Lista de pel�culas que cumplen los requisitos:");
		lista.forEach((pelicula) -> {
			System.out.println("\nID: " + pelicula.getId() + "\nName: " + pelicula.getName() + "\nRuntime In Minutes: " + pelicula.getRuntimeInMinutes() + 
					"\nBudget In Millions: " + pelicula.getBudgetInMillions() + "\nBox Office Revenue In Millions: " + pelicula.getBoxOfficeRevenueInMillions() + 
					"\nAcademy Award Nominations: " + pelicula.getAcademyAwardNominations() + "\nAcademy Award Wins: " + pelicula.getAcademyAwardWins() + 
					"\nRotten Tomatoes Score: " + pelicula.getRottenTomatoesScore());
		});
		
		odb.close();
	}

	private static void consulta2() {
		// Realizamos la creaci�n/apertura de la base de datos
		ODB odb = ODBFactory.open("lotr_pruebas");
		
		ICriterion criterio = Where.like("name", "Baggins");
		IQuery consulta = new CriteriaQuery(Character.class, criterio);
		
		System.out.println("Miembros del clan Baggins:");
		
		// Obtenemos los personajes que cumplen los requisitos
		List<Character> lista = (List)odb.getObjects(consulta);
		
		// Mostramos el nombre de cada personaje
		lista.forEach((personaje) -> {
			System.out.println(personaje.getName());
		});
		
		// Mostramos el total de personajes encontrados
		System.out.println("Tenemos un total de " + odb.getObjects(consulta).size() + " personajes del clan Baggins.");
		
		odb.close();
	}

	private static void consulta1() {
		// Realizamos la creaci�n/apertura de la base de datos
		ODB odb = ODBFactory.open("lotr_pruebas");
		
		ICriterion criterio = Where.isNotNull("spouse");
		IQuery consulta = new CriteriaQuery(Character.class, criterio);
		
		// Mostramos el total de personajes encontrados en nuestra consulta
		System.out.println("Tenemos un total de " + odb.getObjects(consulta).size() + " personajes casados.");

		odb.close();
	}

	private static void migrarDialogs() {
		try {
			// Realizamos la creaci�n/apertura de la base de datos
			ODB odb = ODBFactory.open("lotr_pruebas");

			// Abrimos la conexi�n con la base de datos MySQL
			Connection conexion = DriverManager
					.getConnection("jdbc:mariadb://localhost:3306/lotr?user=star&password=wars");

			// Creamos una sentencia para obtener todos los di�logos
			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("select * from dialogs");

			// Variable que contendr� los diferentes di�logos a crear en la base de datos de
			// Neodatis
			Dialog myDialog;

			// Por cada di�logo crearemos uno en Neodatis
			while (rs.next()) {
				// Obtenemos la pel�cula
				ICriterion criterio = Where.equal("id", rs.getString(3));
				IQuery consulta = new CriteriaQuery(Movie.class, criterio);
				Movie movie = (Movie) odb.getObjects(consulta).getFirst();

				// Obtenemos el personaje
				criterio = Where.equal("id", rs.getString(4));
				consulta = new CriteriaQuery(Character.class, criterio);
				Character character = (Character)odb.getObjects(consulta).getFirst();

				myDialog = new Dialog(rs.getString(1), rs.getString(2), movie, character);
				
				OID oid_dialog= odb.store(myDialog);
			}

			odb.commit(); // Guardamos los cambios en la BBDD
			odb.close(); // Cerramos la conexi�n con la base de datos

		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	private static void migrarBooksMovies() {
		try {
			// Realizamos la creaci�n/apertura de la base de datos
			ODB odb = ODBFactory.open("lotr_pruebas");

			// Abrimos la conexi�n con la base de datos MySQL
			Connection conexion = DriverManager
					.getConnection("jdbc:mariadb://localhost:3306/lotr?user=star&password=wars");

			// Creamos una sentencia para obtener todas las pel�culas
			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("select * from books_movies");

			// Variables que usaremos para contener los libros y pel�culas
			Movie movie;
			Book book;

			// Por cada pel�cula crearemos una en Neodatis
			while (rs.next()) {
				int idLibro = rs.getInt(3);
				String idMovie = rs.getString(2);
				
				// Obtenemos el libro
				ICriterion criterio = Where.equal("id", idLibro);
				IQuery consulta = new CriteriaQuery(Book.class, criterio);
				book = (Book) odb.getObjects(consulta).getFirst();
				
				// Obtenemos la pel�cula
				criterio = Where.equal("id", idMovie);
				consulta = new CriteriaQuery(Movie.class, criterio);
				movie = (Movie) odb.getObjects(consulta).getFirst();
				
				// PASO 1: A�adimos la pel�cula al libro
				Set<Movie> peliculasLibro;

				// Obtenemos las pel�culas del libro
				peliculasLibro = book.getMovies();

				// Si el libro no tiene pel�culas nos crearemos un nuevo HashSet, ya que si no
				// tendr�amos el valor Null y provocar�amos una excepci�n
				if (peliculasLibro == null) {
					peliculasLibro = new HashSet<Movie>();
				}

				// A�adimos la pel�cula a nuestra variable que contiene el listado de
				// pel�culas
				peliculasLibro.add(movie);

				// Lo a�adimos al libro y sobreescribimos el libro
				book.setMovies(peliculasLibro);
				odb.store(book);

				
				// PASO 2: A�adimos el libro a la pel�cula
				Set<Book> librosPelicula;

				// Obtenemos los libros de la pel�cula
				librosPelicula = movie.getBooks();

				// Si la pel�cula no tiene libros nos crearemos un nuevo HashSet, ya que si no
				// tendr�amos el valor Null y provocar�amos una excepci�n
				if (librosPelicula == null) {
					librosPelicula = new HashSet<Book>();
				}

				// A�adimos el libro a nuestra variable que contiene el listado de
				// pel�culas
				librosPelicula.add(book);

				// Lo a�adimos al libro y sobreescribimos el libro
				movie.setBooks(librosPelicula);
				odb.store(movie);
			}

			odb.commit(); // Guardamos los cambios en la BBDD
			odb.close(); // Cerramos la conexi�n con la base de datos

		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	private static void migrarMovies() {
		try {
			// Realizamos la creaci�n/apertura de la base de datos
			ODB odb = ODBFactory.open("lotr_pruebas");

			// Abrimos la conexi�n con la base de datos MySQL
			Connection conexion = DriverManager
					.getConnection("jdbc:mariadb://localhost:3306/lotr?user=star&password=wars");

			// Creamos una sentencia para obtener todas las pel�culas
			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("select * from movies");

			// Variable que contendr� las diferentes pel�culas a crear en la base de datos de
			// Neodatis
			Movie myMovie;

			// Por cada pel�cula crearemos una en Neodatis
			while (rs.next()) {
				myMovie = new Movie(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), 
						rs.getInt(8), null, null);
				
				OID oid_movie= odb.store(myMovie);
			}

			odb.commit(); // Guardamos los cambios en la BBDD
			odb.close(); // Cerramos la conexi�n con la base de datos

		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	private static void migrarChapters() {
		try {
			// Realizamos la creaci�n/apertura de la base de datos
			ODB odb = ODBFactory.open("lotr_pruebas");

			// Abrimos la conexi�n con la base de datos MySQL
			Connection conexion = DriverManager
					.getConnection("jdbc:mariadb://localhost:3306/lotr?user=star&password=wars");

			// Creamos una sentencia para obtener los cap�tulos
			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("select * from chapters");

			// Variable que contendr� los diferentes personajes a insertar en la base de
			// datos de Neodatis
			Chapter myChapter;

			// Por cada personaje insertaremos uno en la base de datos y adem�s lo
			// a�adiremos al Set de personajes de cada reino
			while (rs.next()) {
				// A�adimos un personaje a la base de datos
				myChapter = new Chapter(rs.getString(1), rs.getString(2), null);

				// Lo a�adimos a su libro, si tiene
				if ((Object) rs.getString(3) != null) {
					// Obtenemos el libro correspondiente a la ID del libro del cap�tulo
					ICriterion criterio = Where.equal("id", rs.getInt(3));
					IQuery consulta = new CriteriaQuery(Book.class, criterio);
					Book libroCapitulo = (Book) odb.getObjects(consulta).getFirst();

					// A�adimos el cap�tulo al libro
					Set<Chapter> capitulosLibro;

					// Obtenemos los cap�tulos del libro
					capitulosLibro = libroCapitulo.getChapters();

					// Si el libro no tiene cap�tulos nos crearemos un nuevo HashSet, ya que si no
					// tendr�amos el valor Null y provocar�amos una excepci�n
					if (capitulosLibro == null) {
						capitulosLibro = new HashSet<Chapter>();
					}

					// A�adimos el cap�tulo a nuestra variable que contiene el listado de
					// cap�tulos
					capitulosLibro.add(myChapter);

					// Lo a�adimos al libro y sobreescribimos el libro
					libroCapitulo.setChapters(capitulosLibro);
					odb.store(libroCapitulo);

					// A�adimos el libro al cap�tulo
					myChapter.setBook(libroCapitulo);
					odb.commit(); // Confirmamos los cambios

					odb.store(myChapter);
				} else { // Si no tuviese libro simplemente a�adiremos el cap�tulo sin este
					odb.store(myChapter);
				}
			}

			odb.commit(); // Confirmamos los cambios
			odb.close(); // Cerramos la conexi�n con la base de datos

		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	private static void migrarBooks() {
		try {
			// Realizamos la creaci�n/apertura de la base de datos
			ODB odb = ODBFactory.open("lotr_pruebas");

			// Abrimos la conexi�n con la base de datos MySQL
			Connection conexion = DriverManager
					.getConnection("jdbc:mariadb://localhost:3306/lotr?user=star&password=wars");

			// Creamos una sentencia para obtener todos los libros
			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("select * from books");

			// Variable que contendr� los diferentes libros a crear en la base de datos de
			// Neodatis
			Book myBook;

			// Por cada libro crearemos uno en Neodatis
			while (rs.next()) {
				myBook = new Book(rs.getInt(1), rs.getString(2), null, null);
				OID oid_book= odb.store(myBook);
			}

			odb.commit(); // Guardamos los cambios en la BBDD
			odb.close(); // Cerramos la conexi�n con la base de datos

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void migrarMarriage() {
		try {
			// Realizamos la creaci�n/apertura de la base de datos
			ODB odb = ODBFactory.open("lotr_pruebas");

			// Abrimos la conexi�n con la base de datos MySQL
			Connection conexion = DriverManager
					.getConnection("jdbc:mariadb://localhost:3306/lotr?user=star&password=wars");

			// Creamos una sentencia para obtener los personajes
			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("select * from marriage");

			// En primer lugar a�adimos un personaje para quien est� casado con una pareja
			// desconocida
			Character parejaDesconocida = new Character("-1", "Unknown person", null, null, null, null, null, null,
					null, null, null);
			odb.store(parejaDesconocida);
			odb.commit();

			// Mientras tengamos personas casadas realizaremos las siguientes acciones
			while (rs.next()) {
				// Variables que contienen la id del personaje y la id de su pareja
				String idPersonaje = rs.getString(1);
				String idPareja = rs.getString(2);

				// Obtenemos el objeto del personaje
				ICriterion criterio = Where.equal("id", idPersonaje);
				IQuery consulta = new CriteriaQuery(Character.class, criterio);
				Character personaje = (Character) odb.getObjects(consulta).getFirst();

				if (idPareja == null) { // Si el personaje est� casado con alguien desconocido ejecutaremos las
										// siguientes acciones

					// Le a�adimos la pareja desconocida como pareja
					personaje.setSpouse(parejaDesconocida);
					odb.store(personaje);
					odb.commit();
				} else { // Si conocemos el personaje con el que est� casado realizaremos las siguientes
							// acciones

					// Obtendremos su pareja mediante una consulta
					criterio = Where.equal("id", idPareja);
					consulta = new CriteriaQuery(Character.class, criterio);
					Character pareja = (Character) odb.getObjects(consulta).getFirst();

					// Comprobamos que nadie est� casada con su pareja
					criterio = Where.equal("spouse", pareja);
					consulta = new CriteriaQuery(Character.class, criterio);
					boolean posiblePareja = odb.getObjects(consulta).isEmpty(); // True si no hay elementos
					
					if(posiblePareja == true) {
						personaje.setSpouse(pareja);
						odb.store(personaje);
						odb.commit();						
					}
				}

			}

			odb.commit(); // Confirmamos los cambios
			odb.close(); // Cerramos la conexi�n con la base de datos

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void migrarCharacters() {
		try {
			// Realizamos la creaci�n/apertura de la base de datos
			ODB odb = ODBFactory.open("lotr_pruebas");

			// Abrimos la conexi�n con la base de datos MySQL
			Connection conexion = DriverManager
					.getConnection("jdbc:mariadb://localhost:3306/lotr?user=star&password=wars");

			// Creamos una sentencia para obtener los personajes
			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("select * from characters");

			// Variable que contendr� los diferentes personajes a insertar en la base de
			// datos de Neodatis
			Character myCharacter;

			// Por cada personaje insertaremos uno en la base de datos y adem�s lo
			// a�adiremos al Set de personajes de cada reino
			while (rs.next()) {
				// A�adimos un personaje a la base de datos
				myCharacter = new Character(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), null,
						null);

				// Lo a�adimos a su reino, si tiene
				if ((Object) rs.getString(10) != null) {
					// Obtenemos el reino correspondiente a la ID del reino del personaje
					ICriterion criterio = Where.equal("id", rs.getInt(10));
					IQuery consulta = new CriteriaQuery(Realm.class, criterio);
					Realm reinoPersonaje = (Realm) odb.getObjects(consulta).getFirst();

					// A�adimos el personaje al reino
					Set<Character> personajesReino;

					// Obtenemos los personajes del reino
					personajesReino = reinoPersonaje.getCharacters();

					// Si el reino no tiene personajes nos crearemos un nuevo HashSet, ya que si no
					// tendr�amos el valor Null y provocar�amos una excepci�n
					if (reinoPersonaje.getCharacters() == null) {
						personajesReino = new HashSet<Character>();
					}

					// A�adimos el personaje a nuestra variable que contiene el listado de
					// personajes
					personajesReino.add(myCharacter);

					// Lo a�adimos al reino y sobreescribimos el reino
					reinoPersonaje.setCharacters(personajesReino);
					odb.store(reinoPersonaje);

					odb.commit(); // Confirmamos los cambios

					// Si el personaje tiene un reino se lo a�adiremos
					myCharacter.setRealm(reinoPersonaje);
					odb.store(myCharacter);
				} else { // Si no tuviese reino simplemente a�adiremos el personaje sin este
					odb.store(myCharacter);
				}
			}

			odb.commit(); // Confirmamos los cambios
			odb.close(); // Cerramos la conexi�n con la base de datos

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void migrarRealms() {
		try {
			// Realizamos la creaci�n/apertura de la base de datos
			ODB odb = ODBFactory.open("lotr_pruebas");

			// Abrimos la conexi�n con la base de datos MySQL
			Connection conexion = DriverManager
					.getConnection("jdbc:mariadb://localhost:3306/lotr?user=star&password=wars");

			// Creamos una sentencia para obtener todos los reinos
			Statement s = conexion.createStatement();
			ResultSet rs = s.executeQuery("select * from realms");

			// Variable que contendr� los diferentes reinos a crear en la base de datos de
			// Neodatis
			Realm myRealm;

			// Por cada reino crearemos uno en Neodatis
			while (rs.next()) {
				myRealm = new Realm(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), null);
				OID oid_realm = odb.store(myRealm);
			}

			odb.commit(); // Guardamos los cambios en la BBDD
			odb.close(); // Cerramos la conexi�n con la base de datos

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
