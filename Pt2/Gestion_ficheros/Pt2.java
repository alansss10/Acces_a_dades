import java.io.*;
import java.util.Scanner;

public class Pt2 {

    static String invertir(String linia) {
        return new StringBuilder(linia).reverse().toString();
    }

    // Desplaza cada carácter 'clau' posiciones en Unicode
    static String desplacar(String linia, int clau) {
        StringBuilder sb = new StringBuilder();
        for (char c : linia.toCharArray()) {
            sb.append((char) (c + clau));
        }
        return sb.toString();
    }

    static void xifrar(String entrada, String sortida, int clau) {
        System.out.println("Xifrant " + entrada + " ...");
        try (BufferedReader br = new BufferedReader(new FileReader(entrada));
             BufferedWriter bw = new BufferedWriter(new FileWriter(sortida))) {

            String linia;
            int n = 0;
            while ((linia = br.readLine()) != null) {
                bw.write(desplacar(invertir(linia), clau));
                bw.newLine();
                n++;
            }
            System.out.println("Fet! " + n + " línies xifrades a " + sortida);

        } catch (FileNotFoundException e) {
            System.out.println("Error: fitxer no trobat -> " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error d'entrada/sortida: " + e.getMessage());
        }
    }

    static void desxifrar(String entrada, String sortida, int clau) {
        System.out.println("Desxifrant " + entrada + " ...");
        try (BufferedReader br = new BufferedReader(new FileReader(entrada));
             BufferedWriter bw = new BufferedWriter(new FileWriter(sortida))) {

            String linia;
            int n = 0;
            while ((linia = br.readLine()) != null) {
                bw.write(invertir(desplacar(linia, -clau)));
                bw.newLine();
                n++;
            }
            System.out.println("Fet! " + n + " línies desxifrades a " + sortida);

        } catch (FileNotFoundException e) {
            System.out.println("Error: fitxer no trobat -> " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error d'entrada/sortida: " + e.getMessage());
        }
    }

   public static void main(String[] args) {
    String entrada   = "Pt2/dades/alumnes.txt";
    String xifrat    = "Pt2/dades/xifrat.txt";
    String desxifrat = "Pt2/dades/desxifrat.txt";

    try (Scanner sc = new Scanner(System.in)) {
        System.out.print("Introdueix la clau (nombre enter, p. ex. 3): ");

        int clau;
        try {
            clau = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Clau no vàlida, s'usa 3 per defecte.");
            clau = 3;
        }

        xifrar(entrada, xifrat, clau);
        desxifrar(xifrat, desxifrat, clau);
    }
    }
}