import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Pt1 {
    public static void main (String[] args) {
         try {
            File carpeta = new File("dades");
            if (!carpeta.exists() && !carpeta.mkdirs()) {
            throw new IOException("No s'ha pogut crear");
            }
            
            File fitxer = new File("dades/alumnes.txt");

            System.out.println(fitxer.getPath());
            System.out.println(fitxer.getAbsolutePath());

            if (fitxer.createNewFile()) {
             System.out.println("Fitxer creat");
            }else {
                 System.out.println("El fitxer ja existeix");
            }
            
            int contador = 0;
            int numLinies = 1;
            FileReader fr = new FileReader(fitxer);
            int c;
            int conteoPalabras = 0;    
            boolean dinsParaula = false;

            while ((c = fr.read()) != -1) {
            if ((char) c != '\n' && (char) c != '\r' && (char) c != ' ') {                   
                contador++;
            }
                System.out.println((char) c);
            if ((char) c == '\n') {
                numLinies++;
            }
        
        System.out.println("Número de caràcters: " + contador);
        System.out.println("Número de línies: " + numLinies);

        if (!Character.isWhitespace((char) c)) {
                    if (!dinsParaula) {
                        conteoPalabras++;
                        dinsParaula = true;
                    }
                } else {
                    dinsParaula = false;
        }
        System.out.println("Total paraules: " + conteoPalabras);
    }

        fr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}