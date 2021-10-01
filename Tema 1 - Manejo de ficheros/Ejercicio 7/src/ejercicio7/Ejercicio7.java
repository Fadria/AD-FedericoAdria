package ejercicio7;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Ejercicio7 {

	public static void main(String[] args) {
		try {
			// Cargamos el fichero XML con el que vamos a trabajar
			File inputFile = new File("src/ejercicio7/got.xml");
			
			// Creamos el factory SAX y lo usamos para crear nuestro parseador de XML
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			
			// Indicamos el handler que usaremos para trabajar con nuestro documento
			CharacterHandler characterHandler = new CharacterHandler();

			System.out.println("Comienzo del documento XML");
			
			// Parseamos el fichero
			saxParser.parse(inputFile, characterHandler);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

class CharacterHandler extends DefaultHandler 
{
	// Para indicar la profundidad de cada nivel dentro del XML usaremos este String
	String tabulation = "--->";
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) 
			throws SAXException 
	{	
		// Mostramos el nombre de la etiqueta y aumentamos la tabulación
		System.out.println(tabulation + qName);
		tabulation = "---" + tabulation;
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException 
	{
		// Eliminamos los 3 primeros carácteres al acabar un elemento, reducimos la tabulación
		tabulation = tabulation.substring(3, tabulation.length());
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException 
	{
		// Mostramos el contenido en el caso de que exista
		if(length > 1)
		{
			System.out.println(tabulation + new String(ch, start, length));			
		}		
	}
}