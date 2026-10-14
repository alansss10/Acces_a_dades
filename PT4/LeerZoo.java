import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class LeerZoo {

    public static void main(String[] args) {
        try {
            File fitxer = new File("zoo.xml");

            if (!fitxer.exists()) {
                System.out.println("No es troba zoo.xml. Executa primer CrearZoo.");
                return;
            }

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(fitxer);
            doc.getDocumentElement().normalize();

            NodeList animals = doc.getElementsByTagName("animal");

            // Part 2: mostrar tots els animals
            System.out.println("=== Tots els animals ===");
            for (int i = 0; i < animals.getLength(); i++) {
                mostrarAnimal((Element) animals.item(i));
            }

            // Bonus 1: comptar animals
            System.out.println("=== Total d'animals al zoo: " + animals.getLength() + " ===");
            System.out.println();

            // Bonus 2: animals que mengen "Carn"
            System.out.println("=== Animals que mengen Carn ===");
            int carnivors = 0;
            for (int i = 0; i < animals.getLength(); i++) {
                Element animal = (Element) animals.item(i);
                if (getText(animal, "aliment").equalsIgnoreCase("Carn")) {
                    mostrarAnimal(animal);
                    carnivors++;
                }
            }
            if (carnivors == 0) {
                System.out.println("Cap animal menja carn.");
            }

        } catch (Exception e) {
            System.out.println("Error en llegir l'XML: " + e.getMessage());
        }
    }

    private static void mostrarAnimal(Element animal) {
        System.out.println("Animal #" + animal.getAttribute("id"));
        System.out.println("Nom: " + getText(animal, "nom"));
        System.out.println("Espècie: " + getText(animal, "especie"));
        System.out.println("Aliment: " + getText(animal, "aliment"));
        System.out.println("Edat: " + getText(animal, "edat"));
    }

    private static String getText(Element pare, String etiqueta) {
        return pare.getElementsByTagName(etiqueta).item(0).getTextContent();
    }
}
