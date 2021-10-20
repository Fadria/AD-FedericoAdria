package org.sfaci.gestionanimales.gui;

import org.sfaci.gestionanimales.base.Animal;
import org.sfaci.gestionanimales.util.Util;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.swing.JFileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;


/**
 * Controlador para la ventana
 * @author Santiago Faci
 * @version curso 2015-2016
 */
public class VentanaController implements ActionListener, KeyListener {

    private VentanaModel model;
    private Ventana view;

    private int posicion;

    public VentanaController(VentanaModel model, Ventana view) {
        this.model = model;
        this.view = view;
        anadirActionListener(this);
        anadirKeyListener(this);

        posicion = 0;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String actionCommand = event.getActionCommand();
        Animal animal = null;

        switch (actionCommand) {
            case "Nuevo":
                view.tfNombre.setText("");
                view.tfNombre.setEditable(true);
                view.tfCaracteristicas.setText("");
                view.tfCaracteristicas.setEditable(true);
                view.tfPeso.setText("");
                view.tfPeso.setEditable(true);
                view.tfRaza.setText("");
                view.tfRaza.setEditable(true);

                view.btGuardar.setEnabled(true);
                break;
            case "Guardar":

                if (view.tfNombre.getText().equals("")) {
                    Util.mensajeError("El nombre es un campo obligatorio", "Nuevo Animal");
                    return;
                }

                animal = new Animal();
                animal.setNombre(view.tfNombre.getText());
                animal.setRaza(view.tfRaza.getText());
                animal.setCaracteristicas(view.tfCaracteristicas.getText());

                // Comprobamos que el peso contiene sólo números
                if(view.tfPeso.getText().matches("^([+-]?\\d*\\.?\\d*)$")) {
                    animal.setPeso
                    (
                        // Si el usuario no indica el peso del animal proporcionaremos uno por defecto
                        (!view.tfPeso.getText().equals("")) ? Float.parseFloat(view.tfPeso.getText()) : 0.0f
                    );
                    model.guardar(animal);

                    view.btGuardar.setEnabled(false);
                }else{
                    JOptionPane.showMessageDialog(null, "Por favor, introduce un valor numérico en el peso");
                }

                // Al ya disponer de datos activamos los botones de modificación y de borrado
                view.btModificar.setEnabled(true);
                view.btEliminar.setEnabled(true);
                break;
            case "Modificar":
                animal = new Animal();
                animal.setNombre(view.tfNombre.getText());
                animal.setRaza(view.tfRaza.getText());
                animal.setCaracteristicas(view.tfCaracteristicas.getText());

                // Comprobamos que el peso contiene sólo números
                if(view.tfPeso.getText().matches("^([+-]?\\d*\\.?\\d*)$")) {
                    animal.setPeso
                            (
                                    // Si el usuario no indica el peso del animal proporcionaremos uno por defecto
                                    (!view.tfPeso.getText().equals("")) ? Float.parseFloat(view.tfPeso.getText()) : 0.0f
                            );
                    model.modificar(animal);
                }else{
                    JOptionPane.showMessageDialog(null, "Por favor, introduce un valor numérico en el peso");
                }
                break;
            case "Cancelar":
                view.tfNombre.setEditable(false);
                view.tfCaracteristicas.setEditable(false);
                view.tfPeso.setEditable(false);
                view.tfRaza.setEditable(false);

                animal = model.getActual();
                cargar(animal);

                view.btGuardar.setEnabled(false);
                break;
            case "Eliminar":
                if (JOptionPane.showConfirmDialog(null, "¿Está seguro?", "Eliminar", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
                    return;

                model.eliminar();
                animal = model.getActual();
                cargar(animal);
                break;
            case "Buscar":
                animal = model.buscar(view.tfBusqueda.getText());
                if (animal == null) {
                    Util.mensajeInformacion("No se ha encontrado ningún animal con ese nombre", "Buscar");
                    return;
                }
                cargar(animal);
                break;
            case "Primero":
                animal = model.getPrimero();
                cargar(animal);
                break;
            case "Anterior":
                animal = model.getAnterior();
                cargar(animal);
                break;
            case "Siguiente":
                animal = model.getSiguiente();
                cargar(animal);
                break;
            case "Ultimo":
                animal = model.getUltimo();
                cargar(animal);
                break;
            case "GenerateXML":
                generateXML();
                break;
            case "LoadXML":
                loadXML();
                break;
            default:
                break;
        }
    }

    // Función usada para guardar los datos de los animales en la variable model en formato de fichero XML
    private void generateXML()
    {
        try
        {
            // Creamos el documento
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // Creamos el elemento raíz, que contendrá todos los elementos de animales
            Element rootElement = createElementAnimals(doc);

            // Guardamos todos los datos en un fichero XML
            DOMSource fuente = new DOMSource(doc);

            // Pedimos al usuario la ruta donde quiere guardar el documento
            JFileChooser fileChooser = new JFileChooser();
            int accionUsuario = fileChooser.showOpenDialog(null);

            // Si el usuario selecciona donde guardar el fichero continuaremos la operación de guardado
            if (accionUsuario == JFileChooser.APPROVE_OPTION)
            {
                String ruta = fileChooser.getSelectedFile().toString(); // Guardamos el nombre de la ruta

                // Guardamos el fichero XML
                Result resultado = new StreamResult(new java.io.File(ruta));
                Transformer transformador = TransformerFactory.newInstance().newTransformer();
                transformador.transform(fuente, resultado);
            }else{
                // Si no selecciona un fichero, informamos de que la operación queda cancelada
                JOptionPane.showMessageDialog(null, "Operación cancelada: no se seleccióno la ruta de guardado");
            }

        }catch(Exception e){
            System.err.println("Error: " + e.getMessage()); //Mostramos el error que ha sucedido
        }
    }

    // Función usada para crear la estructura del documento XML
    private Element createElementAnimals(Document doc)
    {
        // Creamos la raíz del fichero XML
        Element animalsElement = doc.createElement("animales");
        doc.appendChild(animalsElement);

        // Añadimos los animales al nodo padre (etiquetas <animal>)
        animalsElement = getAnimals(doc, animalsElement);

        return animalsElement;
    }

    private Element getAnimals(Document doc, Element animalsElement)
    {
        // Creamos el elemento del primer animal
        Animal primerAnimal = model.getPrimero();
        createAnimalElement(primerAnimal, doc, animalsElement);

        // Creamos el resto de elementos del documento
        Animal animalPuntero;
        while((animalPuntero = model.getSiguiente())!= null)
        {
            createAnimalElement(animalPuntero, doc, animalsElement);
        }

        return animalsElement; // Devolvemos la estructura ya completa
    }

    private void createAnimalElement(Animal animal, Document doc, Element animalsElement)
    {
        // Elemento del animal
        Element elementAnimal = doc.createElement("animal");

        // Elemento usado para ir introduciendo los campos
        Element element;

        // Nodos de los datos del animal

        // Nombre
        element = doc.createElement("nombre");
        element.appendChild(doc.createTextNode(animal.getNombre()));
        elementAnimal.appendChild(element);

        // Raza
        element = doc.createElement("raza");
        element.appendChild(doc.createTextNode(animal.getRaza()));
        elementAnimal.appendChild(element);

        // Características
        element = doc.createElement("caracteristicas");
        element.appendChild(doc.createTextNode(animal.getCaracteristicas()));
        elementAnimal.appendChild(element);

        // Raza
        element = doc.createElement("peso");
        element.appendChild(doc.createTextNode(Double.toString(animal.getPeso())));
        elementAnimal.appendChild(element);

        animalsElement.appendChild(elementAnimal);
    }

    private void loadXML()
    {
        // Pedimos al usuario la ruta del documento a cargar
        JFileChooser fileChooser = new JFileChooser();
        int accionUsuario = fileChooser.showOpenDialog(null);

        // Si el usuario selecciona el fichero a cargar continuaremos la operación de cargado
        if (accionUsuario == JFileChooser.APPROVE_OPTION)
        {
            String ruta = fileChooser.getSelectedFile().toString(); // Guardamos el nombre de la ruta

            try {
                // Creamos el builder del documento indicado
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document documento = builder.parse(new File(ruta));

                // Obtenemos el listado de animales
                NodeList animales = documento.getElementsByTagName("animal");

                for(int i = 0; i < animales.getLength(); i++)
                {
                    // Accedemos al animal en concreto
                    Node animal = animales.item(i);

                    if(animal.getNodeType() == Node.ELEMENT_NODE)
                    {
                        Element elementAnimal = (Element) animal;

                        // Creamos una variable con los datos del animal cargado
                        Animal animalXML = new Animal();
                        animalXML.setNombre(elementAnimal.getElementsByTagName("nombre").item(0).getTextContent());
                        animalXML.setRaza(elementAnimal.getElementsByTagName("raza").item(0).getTextContent());
                        animalXML.setCaracteristicas(elementAnimal.getElementsByTagName("caracteristicas").item(0).getTextContent());
                        animalXML.setPeso(Float.parseFloat(elementAnimal.getElementsByTagName("peso").item(0).getTextContent()));

                        // Guardamos ese animal en la aplicación
                        model.guardar(animalXML);
                    }
                }

                // Al ya disponer de datos activamos los botones de modificación y de borrado
                view.btModificar.setEnabled(true);
                view.btEliminar.setEnabled(true);

                // Cargamos el primer animal para que el usuario vea que se han cargado datos
                cargar(model.getPrimero());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }else{
            // Si no selecciona un fichero, informamos de que la operación queda cancelada
            JOptionPane.showMessageDialog(null, "Operación cancelada: no se seleccionó un fichero");
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getSource() == view.tfBusqueda) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                view.btBuscar.doClick();
            }
        }
    }

    /**
     * Carga los datos de un animal en la vista
     * @param animal
     */
    private void cargar(Animal animal) {
        if (animal == null)
            return;

        view.tfNombre.setText(animal.getNombre());
        view.tfCaracteristicas.setText(animal.getCaracteristicas());
        view.tfRaza.setText(animal.getRaza());
        view.tfPeso.setText(String.valueOf(animal.getPeso()));

        view.tfNombre.setEditable(true);
        view.tfCaracteristicas.setEditable(true);
        view.tfPeso.setEditable(true);
        view.tfRaza.setEditable(true);
    }

    private void anadirKeyListener(KeyListener listener) {
        view.tfBusqueda.addKeyListener(listener);
    }

    private void anadirActionListener(ActionListener listener) {

        view.btNuevo.addActionListener(listener);
        view.btGuardar.addActionListener(listener);
        view.btModificar.addActionListener(listener);
        view.btEliminar.addActionListener(listener);
        view.btPrimero.addActionListener(listener);
        view.btAnterior.addActionListener(listener);
        view.btSiguiente.addActionListener(listener);
        view.btUltimo.addActionListener(listener);
        view.btBuscar.addActionListener(listener);
        view.btnGenerateXML.addActionListener(listener);
        view.btnLoadXML.addActionListener(listener);
    }
}
