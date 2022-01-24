package paquete;

import org.neodatis.odb.ClassRepresentation;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.ODBRuntimeException;

public class NeoDatisDB_LOTR_Management {
	
	public void generateIndexes() {
		// Generamos los índices de todas las tablas
		generateIndexBook();
		generateIndexChapter();
		generateIndexCharacter();
		generateIndexDialog();
		generateIndexMovie();
		generateIndexRealm();
		generateIndexRealmForName();
	}
	
	private void generateIndexBook(){
		// Realizamos la creación/apertura de la base de datos
		ODB odb = ODBFactory.open("lotr_pruebas");
		
		// Los índices pueden crearse antes de hacer una búsqueda,
		// o para respetar claves, antes de hacer un guardado.
		if(!odb.getClassRepresentation(Book.class).existIndex("book-index")) {
			String[] fieldIndex = {"id"};
			ClassRepresentation representacionClase = odb.getClassRepresentation(Book.class);
			representacionClase.addUniqueIndexOn("book-index", fieldIndex, true);
		}		
		
		odb.close(); // Cerramos la conexión
	}

	private void generateIndexChapter(){
		// Realizamos la creación/apertura de la base de datos
		ODB odb = ODBFactory.open("lotr_pruebas");
		
		// Los índices pueden crearse antes de hacer una búsqueda,
		// o para respetar claves, antes de hacer un guardado.
		if(!odb.getClassRepresentation(Chapter.class).existIndex("chapter-index")) {
			String[] fieldIndex = {"id"};
			ClassRepresentation representacionClase = odb.getClassRepresentation(Chapter.class);
			representacionClase.addUniqueIndexOn("chapter-index", fieldIndex, true);
		}		
		
		odb.close(); // Cerramos la conexión
	}

	private void generateIndexDialog(){
		// Realizamos la creación/apertura de la base de datos
		ODB odb = ODBFactory.open("lotr_pruebas");
		
		// Los índices pueden crearse antes de hacer una búsqueda,
		// o para respetar claves, antes de hacer un guardado.
		if(!odb.getClassRepresentation(Dialog.class).existIndex("dialog-index")) {
			String[] fieldIndex = {"id"};
			ClassRepresentation representacionClase = odb.getClassRepresentation(Dialog.class);
			representacionClase.addUniqueIndexOn("dialog-index", fieldIndex, true);
		}		
		
		odb.close(); // Cerramos la conexión
	}

	private void generateIndexMovie(){
		// Realizamos la creación/apertura de la base de datos
		ODB odb = ODBFactory.open("lotr_pruebas");
		
		// Los índices pueden crearse antes de hacer una búsqueda,
		// o para respetar claves, antes de hacer un guardado.
		if(!odb.getClassRepresentation(Movie.class).existIndex("movie-index")) {
			String[] fieldIndex = {"id"};
			ClassRepresentation representacionClase = odb.getClassRepresentation(Movie.class);
			representacionClase.addUniqueIndexOn("movie-index", fieldIndex, true);
		}		
		
		odb.close(); // Cerramos la conexión
	}

	private void generateIndexCharacter(){
		// Realizamos la creación/apertura de la base de datos
		ODB odb = ODBFactory.open("lotr_pruebas");
		
		// Los índices pueden crearse antes de hacer una búsqueda,
		// o para respetar claves, antes de hacer un guardado.
		if(!odb.getClassRepresentation(Character.class).existIndex("character-index")) {
			String[] fieldIndex = {"id"};
			ClassRepresentation representacionClase = odb.getClassRepresentation(Character.class);
			representacionClase.addUniqueIndexOn("character-index", fieldIndex, true);
		}		
		
		odb.close(); // Cerramos la conexión
	}

	private void generateIndexRealm(){
		// Realizamos la creación/apertura de la base de datos
		ODB odb = ODBFactory.open("lotr_pruebas");
		
		// Los índices pueden crearse antes de hacer una búsqueda,
		// o para respetar claves, antes de hacer un guardado.
		if(!odb.getClassRepresentation(Realm.class).existIndex("realm-index")) {
			String[] fieldIndex = {"id"};
			ClassRepresentation representacionClase = odb.getClassRepresentation(Realm.class);
			representacionClase.addUniqueIndexOn("realm-index", fieldIndex, true);
		}	
		
		odb.close(); // Cerramos la conexión
	}

	private void generateIndexRealmForName(){
		// Realizamos la creación/apertura de la base de datos
		ODB odb = ODBFactory.open("lotr_pruebas");
		
		// Los índices pueden crearse antes de hacer una búsqueda,
		// o para respetar claves, antes de hacer un guardado.
		if(!odb.getClassRepresentation(Realm.class).existIndex("realm-nombre-index")) {
			String[] fieldIndex = {"name"};
			ClassRepresentation representacionClase = odb.getClassRepresentation(Realm.class);
			representacionClase.addUniqueIndexOn("realm-nombre-index", fieldIndex, true);
		}	
		
		odb.close(); // Cerramos la conexión
	}
	
	public void guardarObjetoSegunClase(ODB odb, Object objeto) {
		// Almacenamos el objeto y informamos al usuario
		try {
			odb.store(objeto);
			System.out.println("Objeto creado satisfactoriamente");
		}catch(ODBRuntimeException e) {
			// En el caso de que suceda un error informaremos de ello mostrando el mensaje y el motivo del error
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}		
	}

}
