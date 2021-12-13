package paquete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class Ejercicio {
	public static void main(String[] args)
	{
		// Inicializamos el entorno de Hibernate
		Configuration cfg = new Configuration().configure();
		
		// Crear el ejemplar de SessionFactory
		SessionFactory sessionFactoria = cfg.buildSessionFactory(
				new StandardServiceRegistryBuilder().configure().build());
		
		// Abrir la sesión
		Session sesion = sessionFactoria.openSession();
		
		// Comentad y descomentad las llamadas a las funciones para una mejor visualización de los resultados
		
		//ejercicio1(sesion);
		
		//ejercicio2(sesion);
		
		//ejercicio3(sesion);
		
		ejercicio4(sesion);
		
		// Cerrar la sesión
		sesion.close();
	}

	private static void ejercicio4(Session sesion) {
		// APARTADO 1: Consulta no asociada sin fetch		
		
		// Obtenemos los alumnos de Gryffindor que cursan Potions
		ScrollableResults sc = sesion.createQuery("select alu.firstName, alu.lastName, c.name, prof.firstName, prof.lastName\r\n" + 
				"from Course c \r\n" + 
				"inner join c.persons alu \r\n" + 
				"inner join c.person prof\r\n" + 
				"where c.name = 'Potions' and alu.house.name = 'Gryffindor'").scroll();		
		
		System.out.println("Ejercicio 4.1:");
		while(sc.next()) {
			System.out.println(sc.get(0) + " " + sc.get(1) + " estudia " + sc.get(2) + " con " + sc.get(3) + " " + sc.get(4));
		}
		
		// Obtenemos el total de alumnos
		long totalAlumnos = (long)sesion.createQuery("select count(alu.id)\r\n" + 
				"from Course c \r\n" + 
				"inner join c.persons alu \r\n" + 
				"inner join c.person prof\r\n" + 
				"where c.name = 'Potions' and alu.house.name = 'Gryffindor'").uniqueResult();
		System.out.println("Total estudiantes: " + totalAlumnos + "\n\n");
		
		
		// APARTADO 2: Consulta asociada con fetch

		System.out.println("Ejercicio 4.2:");
		
		// Obtenemos los datos del curso de Potions
		Course c = (Course) sesion.createQuery("select c\r\n" + 
				"from Course c \r\n" + 
				"inner join fetch c.persons alu\r\n" + 
				"inner join fetch c.person prof\r\n" + 
				"where c.name = 'Potions' and alu.house.name = 'Gryffindor'").uniqueResult();

		// Instanciamos un set con todos los alumnos de Potions y Gryffindor
		Set<Person> setAlumnos = c.getPersons();
		
		// Mostramos el nombre completo de cada alumno seguido de la asignatura y el profesor de pociones
		for(Person alumno: setAlumnos)
		{
			System.out.println(alumno.getFirstName() + " " + alumno.getLastName() + " estudia " + 
					c.getName() + " con " + c.getPerson().getFirstName() + " " + c.getPerson().getLastName());
		}
		
		// Mostramos el total de estudiantes
		System.out.println("Total estudiantes: " + setAlumnos.size() + "\n\n");
	}

	private static void ejercicio3(Session sesion) {
		
		// Lista de IDs que usaremos para la última sentencia de la función
		List<Integer> ids = new ArrayList<Integer>();
		
		// Lista de alumnos de los que obtendremos los puntos
		String[][] alumnos = {{"Harry", "Potter"}, {"Ron", "Weasley"}, {"Hermione", "Granger"}};
		
		// Bucle ejecutado por cada alumno
		for(String[] alumno: alumnos)
		{
			// Obtenemos la lista de puntos del alumno
			List<HousePoints> hpl = sesion.createQuery("from HousePoints hp where hp.personByReceiver.firstName = ?1 and hp.personByReceiver.lastName = ?2")
					.setParameter(1, alumno[0]).setParameter(2, alumno[1]).getResultList();		

			// Recorremos la lista de puntos que le han sido otorgados y mostramos la información
			for(HousePoints hp: hpl) 
			{
				// Mostramos la persona que da o resta puntos, la cifra de estos, y la persona a la que se les asigna
				System.out.println(hp.getPersonByGiver().getFirstName() + " " + hp.getPersonByGiver().getLastName() + " -> "
						+ hp.getPoints() + " puntos para " + hp.getPersonByReceiver().getFirstName() + " " + hp.getPersonByReceiver().getLastName());				
				
				ids.add(hp.getPersonByReceiver().getId()); // Añadimos la ID del alumno
			}			
		}
				
		// Obtenemos el balance de puntos de los alumnos indicados
		long totalAlumnos = (long)sesion.createQuery("select sum(hp.points) from HousePoints hp where hp.personByReceiver.id in (:listaIds)")
				.setParameter("listaIds", ids).uniqueResult();
		
		System.out.println("Puntos totales: " + totalAlumnos); // Mostramos los puntos totales entre los alumnos de la lista

	}

	private static void ejercicio2(Session sesion) {		
		
		// Parte 1: Obtenemos los alumnos matrículados y los mostramos
		ScrollableResults sc = sesion.createQuery("select p.firstName, p.lastName from Person p join p.courses_1 c group by p.id order by p.lastName").scroll();		
		System.out.println("Ejercicio 2:");
		while(sc.next()) {
			System.out.println(sc.get(1) + ", " + sc.get(0));
		}
		
		// Parte 2: Obtenemos el total de alumnos matrículados y lo mostramos
		long totalAlumnos = (long)sesion.createQuery("select count(distinct p.id) from Person p join p.courses_1 c").uniqueResult();
		System.out.println("Número total de estudiantes: " + totalAlumnos);		
	}

	private static void ejercicio1(Session sesion) {
		// Sentencia a usar
		Query q = sesion.createQuery("from Person where last_name='Potter'");
		
		// Lista de resultados
		List<Person> cl = q.getResultList();
		
		// Mostramos los resultados empleando un foreach y accediendo a los campos deseados
		for(Person c:cl)System.out.println(c.getFirstName() + " " + c.getLastName() + " - " + c.getHouse().getName());		
	}
}