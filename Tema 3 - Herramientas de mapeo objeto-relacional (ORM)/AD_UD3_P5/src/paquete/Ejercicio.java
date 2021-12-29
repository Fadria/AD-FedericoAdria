package paquete;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
		
		ejercicio4(sessionFactoria);
		
		// Cerrar la sesión
		sesion.close();
	}

	private static void ejercicio4(SessionFactory sessionFactoria) {
		Session sesion = sessionFactoria.openSession();
		
		// Obtenemos el total de alumnos matriculados en Flying
	    long totalAlumnosFlying = (long) sesion.createQuery("select count(c.person)\r\n" + 
	    		"from Course c\r\n" + 
	    		"inner join c.persons p\r\n" + 
	    		"where c.name = 'Flying'").uniqueResult();
	    System.out.println("Alumnos matriculados en Flying: " + totalAlumnosFlying);
	    
	    
	    // Listado de alumnos
	    List <Person> alumnos = sesion.createQuery("from Person").getResultList();

	    // Listado de ids de los alumnos de Transfiguration
	    List <Integer> idsAlumnosTransfiguration = sesion.createQuery("select p.id from Person p inner join p.courses_1 c where c.name = 'Transfiguration'").getResultList();
	    
	    // Id del curso de Flying
	    int idFlying = (int) sesion.createQuery("select id from Course where name='Flying'").uniqueResult();
	    
		// Recorremos todos los alumnos
	    for(Person alumno: alumnos) {
	    	// Recorremos las ids de los alumnos de Transfiguration
	    	for(int id:idsAlumnosTransfiguration) {
	    		// Si la ID del alumno coincide con la ID de un alumno de Transfiguration lo añadiremos a la clase de Flying
	    		if(alumno.getId()==id) {
	    			// Abrimos la sesión para poder insertar los datos
	    			sesion = sessionFactoria.openSession();
	    			
	    		    // Iniciamos la  transacción para insertar datos
	    			Transaction tx = sesion.beginTransaction();

	    			try {
		    			String query = "INSERT INTO enrollment(person_enrollment,course_enrollment) VALUES (?1, ?2)";	    			
		    			sesion.createNativeQuery(query).setParameter(1, alumno).setParameter(2, idFlying).executeUpdate();	    				
	    			}catch(Exception e){
	    				System.out.println("No se ha podido insertar el alumno en la asignatura. Razón: " + e.getCause());
	    			}
	    			
	    		    // Guardamos los cambios
	    		    tx.commit();

	    		    // Cerramos la sesión
	    		    sesion.close();
	    		}
	    	}
	    }


	    sesion = sessionFactoria.openSession();
	    
		// Obtenemos el total de alumnos matriculados en Flyings una vez finalizamos la inserción de alumnos
	    totalAlumnosFlying = (long) sesion.createQuery("select count(c.person)\r\n" + 
	    		"from Course c\r\n" + 
	    		"inner join c.persons p\r\n" + 
	    		"where c.name = 'Flying'").uniqueResult();
	    System.out.println(totalAlumnosFlying);	
	    
	    sesion.close();
	}

	private static void ejercicio3(Session sesion) {
		// Obtenemos el total de alumnos matriculados en Muggle Studies
	    long totalAlumnosMuggle = (long) sesion.createQuery("select count(c.person)\r\n" + 
	    		"from Course c\r\n" + 
	    		"inner join c.persons p\r\n" + 
	    		"where c.name = 'Muggle Studies'").uniqueResult();
	    System.out.println("Alumnos matriculados en Muggle Studies: " + totalAlumnosMuggle);
	    
	    
	    // Listado de alumnos
	    List <Person> alumnos = sesion.createQuery("from Person").getResultList();

	    // Listado de ids de los alumnos de pociones
	    List <Integer> idsAlumnosPotions = sesion.createQuery("select p.id from Person p inner join p.courses_1 c where c.name = 'Potions'").getResultList();
	    
	    // Id del curso de Muggle Studies
	    int idMuggleStudies = (int) sesion.createQuery("select id from Course where name='Muggle Studies'").uniqueResult();
	    
	    // Iniciamos la  transacción para insertar datos
		Transaction tx = sesion.beginTransaction();

		// Recorremos todos los alumnos
	    for(Person alumno: alumnos) {
	    	// Recorremos las ids de los alumnos de Potions
	    	for(int id:idsAlumnosPotions) {
	    		// Si la ID del alumno coincide con la ID de un alumno de Potions lo añadiremos a la clase de Muggle Studies
	    		if(alumno.getId()==id) {
	    			String query = "INSERT INTO enrollment(person_enrollment,course_enrollment) VALUES (?1, ?2)";	    			
	    			sesion.createNativeQuery(query).setParameter(1, alumno).setParameter(2, idMuggleStudies).executeUpdate();
	    		}
	    	}
	    }

	    // Guardamos los cambios
	    tx.commit();

		// Obtenemos el total de alumnos matriculados en Muggle Studies una vez finalizamos la inserción de alumnos
	    totalAlumnosMuggle = (long) sesion.createQuery("select count(c.person)\r\n" + 
	    		"from Course c\r\n" + 
	    		"inner join c.persons p\r\n" + 
	    		"where c.name = 'Muggle Studies'").uniqueResult();
	    System.out.println("Alumnos matriculados en Muggle Studies: " + totalAlumnosMuggle);	
	}

	private static void ejercicio2(Session sesion) {
		mostrarPuntos(sesion); // Mostramos el total antes de duplicar las entradas
		
		// Array bidimensional de alumnos a los que queremos doblar los puntos recibidos por Severus Snape
		String[][] alumnos = {{"Harry", "Potter"}, {"Ron", "Weasley"}};

		Transaction tx = sesion.beginTransaction();
		String hqlInsert = "";
		
		for(String[] alumno: alumnos) {			
			hqlInsert = "insert into HousePoints (personByGiver, personByReceiver, points) " + 
					"select hp.personByGiver, hp.personByReceiver, points " +  
					"from HousePoints hp " + 
					"where hp.personByGiver.firstName = 'Severus' and hp.personByGiver.lastName = 'Snape' " + 
					"and hp.personByReceiver.firstName = ?1 and hp.personByReceiver.lastName = ?2";
			
			sesion.createQuery(hqlInsert).setParameter(1, alumno[0]).setParameter(2, alumno[1]).executeUpdate();			
		}
		
		tx.commit();
		
		mostrarPuntos(sesion); // Mostramos el total después de duplicar las entradas
	}

	private static void mostrarPuntos(Session sesion) {
		// Obtenemos el total de puntos sumados por los dos alumnos
		long puntosQuitados = (long)sesion.createQuery("select sum(hp.points) from HousePoints hp where hp.personByGiver.firstName = 'Severus' and hp.personByGiver.lastName = 'Snape'\r\n" + 
				"and (hp.personByReceiver.firstName = ?1 and hp.personByReceiver.lastName = ?2) or\r\n" + 
				"(hp.personByReceiver.firstName = ?3 and hp.personByReceiver.lastName = ?4)")
				.setParameter(1, "Harry").setParameter(2, "Potter").setParameter(3, "Ron").setParameter(4, "Weasley").uniqueResult();

		System.out.println("Puntos quitados a H y P por SS:" + puntosQuitados);		
	}

	private static void ejercicio1(Session sesion) {
		ejercicio1A(sesion);
		ejercicio1B(sesion);
	}

	private static void ejercicio1B(Session sesion) {
		Transaction tx = sesion.beginTransaction();
		Books b = new Books();
		
		// Creamos un reader para leer nuestro fichero
		try (BufferedReader br = new BufferedReader(new FileReader("src/paquete/Harry_Potter_libros.csv"))) {
		    String line;
		    
		    // La primera línea contiene los nombres de las columnas, por lo que la leemos para no insertarla en el bucle
		    br.readLine();
		    
		    // Por cada línea insertaremos un libro
		    while ((line = br.readLine()) != null) {
		    	b = new Books();
		        String[] values = line.split(";");
		        
		        // Indicamos los valores del objeto
		        b.setTitle(values[0]);
		        b.setYear(Integer.parseInt(values[1]));
		        
				Course asignatura = (Course)sesion.createQuery("select c from Course c where c.name = ?1")
						.setParameter(1, values[2]).uniqueResult();

				b.setCourse(asignatura);
				sesion.save(b);
		    }
		    
		    sesion.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void ejercicio1A(Session sesion) {
		// Iniciamos la transacción
		Transaction tx = sesion.beginTransaction();
		
		// String con la sentencia de la creación de la tabla
		String sql = "CREATE TABLE IF NOT EXISTS books(\r\n" + 
				"id INTEGER NOT NULL AUTO_INCREMENT,\r\n" + 
				"title varchar(255) not null,\r\n" + 
				"year integer,\r\n" + 
				"subject integer,\r\n" + 
				"primary key (id),\r\n" + 
				"foreign key (subject) references course(id));";
				
		// Ejecutamos la query
		sesion.createSQLQuery(sql).executeUpdate();
		
		// Confirmamos los cambios de la BBDD con un commit
		tx.commit();		
	}
}