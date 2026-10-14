import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class CrearZoo {

    public static void main(String[] args) {
        // Dades dels animals: id, nom, espècie, aliment, edat
        String[][] animals = {
                {"1", "Pingüí", "Aptenodytes forsteri", "Peix", "5"},
                {"2", "Lleó", "Panthera leo", "Carn", "8"},
                {"3", "Elefant", "Loxodonta africana", "Herba", "20"},
                {"4", "Girafa", "Giraffa camelopardalis", "Fulles", "12"},
                {"5", "Tigre", "Panthera tigris", "Carn", "6"}
        };

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Element arrel <zoo>
            Element zoo = doc.createElement("zoo");
            doc.appendChild(zoo);

            for (String[] a : animals) {
                Element animal = doc.createElement("animal");
                animal.setAttribute("id", a[0]);

                animal.appendChild(crearElement(doc, "nom", a[1]));
                animal.appendChild(crearElement(doc, "especie", a[2]));
                animal.appendChild(crearElement(doc, "aliment", a[3]));
                animal.appendChild(crearElement(doc, "edat", a[4]));

                zoo.appendChild(animal);
            }

            // Escriure el document a un fitxer amb format indentat
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            transformer.transform(new DOMSource(doc), new StreamResult(new File("zoo.xml")));

            System.out.println("Fitxer zoo.xml creat correctament.");

        } catch (Exception e) {
            System.out.println("Error en crear l'XML: " + e.getMessage());
        }
    }

    // Mètode auxiliar per crear un element amb text
    private static Element crearElement(Document doc, String nom, String text) {
        Element element = doc.createElement(nom);
        element.setTextContent(text);
        return element;
    }
}
