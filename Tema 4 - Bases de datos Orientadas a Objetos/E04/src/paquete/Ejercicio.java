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
import org.neodatis.odb.ODBRuntimeException;
import org.neodatis.odb.OID;
import org.neodatis.odb.ObjectValues;
import org.neodatis.odb.Values;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.And;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import org.neodatis.odb.impl.core.query.values.ValuesCriteriaQuery;

public class Ejercicio {
	public static void main(String[] args) {
		ejercicio1();
		ejercicio2();
		ejercicio3();
	}

	private static void ejercicio3() {
		// Realizamos la creación/apertura de la base de datos
		ODB odb = ODBFactory.open("lotr_pruebas");

		// Obtenemos todos los personajes cuya raza es Hobbit
		ICriterion criterio = Where.like("race", "%Hobbit%");
		Values valores = odb.getValues(new ValuesCriteriaQuery(Character.class, criterio).field("name"));

		try {
			while (valores.hasNext()) {
				// Obtenemos el nombre de este personaje en concreto
				ObjectValues valorObjetos = valores.nextValues();
				String nombrePersonaje = valorObjetos.getByAlias("name").toString();
				
				// Obtenemos los diálogos que contienen el nombre de este personaje
				ICriterion criterio2 = Where.like("dialog", "%" + nombrePersonaje + "%");
				Values dialogos = odb.getValues(new ValuesCriteriaQuery(Dialog.class, criterio2).field("dialog").field("character.name"));
				
				// Mostraremos el diálogo si tiene el nombre del personaje de esta itineración
				while(dialogos.hasNext()) {
					// Obtenemos el diálogo y lo mostramos
					ObjectValues valorDialogos = dialogos.nextValues();
					String dialogo = valorDialogos.getByAlias("dialog").toString();
					String personajeQueLoDice = valorDialogos.getByAlias("character.name").toString();
					System.out.println(personajeQueLoDice + ": " + dialogo);
				}
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
		odb.close();
	}

	private static void ejercicio2() {
		//consulta1();
		//consulta2();
		//consulta3();
		//consulta4();
	}

	private static void consulta4() {
		// Realizamos la creación/apertura de la base de datos
		ODB odb = ODBFactory.open("lotr_pruebas");

		// Sacamos la media de la primera trilogía
		ICriterion criterio = Where.like("name", "%The Lord of the Ring%");
		Values valores = odb.getValues(new ValuesCriteriaQuery(Movie.class, criterio).avg("rottenTomatoesScore"));

		try {
			while (valores.hasNext()) {
				ObjectValues valorObjetos = valores.nextValues();
				String valor = valorObjetos.getByAlias("rottenTomatoesScore").toString();
				System.out.println("La nota media de la trilogía The Lord of the Ring es de : " + valor);
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
		
		// Sacamos la media de la segunda trilogía
		ICriterion criterio2 = Where.like("name", "%The Hobbit%");
		Values valores2 = odb.getValues(new ValuesCriteriaQuery(Movie.class, criterio2).sum("rottenTomatoesScore").count("rottenTomatoesScore"));

		try {
			// En este caso al necesitar redondear necesitamos obtener tanto la suma como el contador y hacerlo desde Java
			int suma = Integer.parseInt(valores2.getFirst().getByIndex(0).toString());
			int contador = Integer.parseInt(valores2.getFirst().getByIndex(1).toString());
			System.out.println(suma / contador);
		} catch (IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
		
		odb.close();
	}

	private static void consulta3() {
		// Realizamos la creación/apertura de la base de datos
		ODB odb = ODBFactory.open("lotr_pruebas");

		Values valores = odb.getValues(new ValuesCriteriaQuery(Chapter.class).field("book.title").count("id").groupBy("book.title"));

		try {
			while (valores.hasNext()) {
				ObjectValues valorObjetos = valores.nextValues();
				String libro = valorObjetos.getByIndex(0).toString();
				String totalCapitulos = valorObjetos.getByIndex(1).toString();
				System.out.println("Título: " + libro + " | Total capítulos: " + totalCapitulos);
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
		odb.close();
	}

	private static void consulta2() {
		// Realizamos la creación/apertura de la base de datos
		ODB odb = ODBFactory.open("lotr_pruebas");

		ICriterion criterio = Where.like("name", "%The Lord of the Ring%");
		Values valores = odb.getValues(new ValuesCriteriaQuery(Movie.class, criterio).sum("runtimeInMinutes"));

		try {
			while (valores.hasNext()) {
				ObjectValues valorObjetos = valores.nextValues();
				String valor = valorObjetos.getByAlias("runtimeInMinutes").toString();
				System.out.println("El total de minutos de la trilogía es de : " + valor);
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
		odb.close();
	}

	private static void consulta1() {
		// Realizamos la creación/apertura de la base de datos
		ODB odb = ODBFactory.open("lotr_pruebas");

		// Creamos la consulta para buscar al personaje llamado Aragorn II
		ICriterion criterio = Where.like("name", "%Aragorn II%");
		IQuery consulta = new CriteriaQuery(Character.class, criterio);

		// Obtenemos el personaje buscado
		Character personaje = (Character) odb.getObjects(consulta).getFirst();

		// Mostramos su nombre
		System.out.println(personaje.getName());

		// Cerramos la base de datos
		odb.close();
	}

	private static void ejercicio1() {
		// NOTA: Comentar y descomentar según la necesidad

		// Creamos nuestro objeto y con él generamos todos los índices
		NeoDatisDB_LOTR_Management n1 = new NeoDatisDB_LOTR_Management();
		n1.generateIndexes();

		// Realizamos la creación/apertura de la base de datos
		ODB odb = ODBFactory.open("lotr_pruebas");

		IQuery consulta = new CriteriaQuery(Book.class);
		Book librillo = (Book) odb.getObjects(consulta).getFirst();

		// Intentamos almacenar un personaje con una id nueva y otro con una id ya en
		// uso
		Character personajeIdNueva = new Character("111111111", "Francisco José", null, null, null, null, null, null,
				null, null, null);
		Character personajeIdRepetida = new Character("2940848", "Francisco José", null, null, null, null, null, null,
				null, null, null);
		// n1.guardarObjetoSegunClase(odb, personajeIdNueva);
		// n1.guardarObjetoSegunClase(odb, personajeIdRepetida);

		// Intentamos almacenar una película con una id nueva y otra con una id ya en
		// uso
		Movie peliculaIdNueva = new Movie("idnueva11", "La nueva película", 0, 0, 0, 0, 0, 0, null, null);
		Movie peliculaIdRepetida = new Movie("bccde58", "La nueva película", 0, 0, 0, 0, 0, 0, null, null);
		// n1.guardarObjetoSegunClase(odb, peliculaIdNueva);
		// n1.guardarObjetoSegunClase(odb, peliculaIdRepetida);

		// ID existente y nombre existente
		// RDO: Se queja de que la ID ya existe
		/*
		 * Realm r1 = new Realm(5000019, "Himlad", 0, 0, null);
		 * n1.guardarObjetoSegunClase(odb, r1);
		 */

		// ID nueva y nombre existente
		// RDO: Se queja de que el nombre ya existe
		/*
		 * Realm r2 = new Realm(5165, "Himlad", 0, 0, null);
		 * n1.guardarObjetoSegunClase(odb, r2);
		 */

		// ID existente y nombre nuevo
		// RDO: Se queja de que la id ya existe
		/*
		 * Realm r3 = new Realm(5000019, "El reino inventado", 0, 0, null);
		 * n1.guardarObjetoSegunClase(odb, r3);
		 */

		// ID existente y nombre nuevo
		// RDO: Creamos el objeto sin ningún problema
		/*
		 * Realm r4 = new Realm(651651, "El reino inventado", 0, 0, null);
		 * n1.guardarObjetoSegunClase(odb, r4);
		 */

		odb.close();
	}
}
