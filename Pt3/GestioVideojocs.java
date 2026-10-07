import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Gestió d'un catàleg de videojocs (CRUD) amb persistència en fitxer binari.
 */
public class GestioVideojocs {

    private static final String FITXER = "videojocs.dat";
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<Videojoc> videojocs = carregarVideojocs();
        int opcio;

        do {
            mostrarMenu();
            opcio = llegirEnter("Tria una opció: ");

            switch (opcio) {
                case 1 -> afegir(videojocs);
                case 2 -> llistar(videojocs);
                case 3 -> cercar(videojocs);
                case 4 -> actualitzar(videojocs);
                case 5 -> eliminar(videojocs);
                case 6 -> {
                    desarVideojocs(videojocs);
                    System.out.println("Canvis desats. Sortint del programa...");
                }
                default -> System.out.println("Opció incorrecta. Torna-ho a provar.");
            }
        } while (opcio != 6);

        sc.close();
    }

    // ---------- Menú ----------

    private static void mostrarMenu() {
        System.out.println("\n===== GESTIÓ DE VIDEOJOCS =====");
        System.out.println("1. Afegir videojoc");
        System.out.println("2. Llistar tots els videojocs");
        System.out.println("3. Cercar videojocs per títol");
        System.out.println("4. Actualitzar un videojoc");
        System.out.println("5. Eliminar un videojoc");
        System.out.println("6. Sortir");
    }

    // ---------- CRUD ----------

    private static void afegir(ArrayList<Videojoc> videojocs) {
        System.out.print("Títol: ");
        String titol = sc.nextLine();
        System.out.print("Gènere: ");
        String genere = sc.nextLine();
        int any = llegirEnter("Any de llançament: ");
        System.out.print("Plataforma: ");
        String plataforma = sc.nextLine();
        double preu = llegirDecimal("Preu: ");

        videojocs.add(new Videojoc(titol, genere, any, plataforma, preu));
        desarVideojocs(videojocs);
        System.out.println("Videojoc afegit i desat correctament.");
    }

    private static void llistar(ArrayList<Videojoc> videojocs) {
        if (videojocs.isEmpty()) {
            System.out.println("No hi ha videojocs al catàleg.");
            return;
        }
        System.out.println("--- Llista de videojocs ---");
        for (int i = 0; i < videojocs.size(); i++) {
            System.out.println((i + 1) + ". " + videojocs.get(i));
        }
    }

    private static void cercar(ArrayList<Videojoc> videojocs) {
        System.out.print("Text a cercar en el títol: ");
        String text = sc.nextLine().toLowerCase();
        boolean trobat = false;

        for (Videojoc v : videojocs) {
            if (v.getTitol().toLowerCase().contains(text)) {
                System.out.println(v);
                trobat = true;
            }
        }
        if (!trobat) {
            System.out.println("No s'ha trobat cap videojoc amb aquest títol.");
        }
    }

    private static void actualitzar(ArrayList<Videojoc> videojocs) {
        if (videojocs.isEmpty()) {
            System.out.println("No hi ha videojocs per actualitzar.");
            return;
        }
        llistar(videojocs);
        int num = llegirEnter("Número del videojoc a actualitzar: ");
        if (num < 1 || num > videojocs.size()) {
            System.out.println("Número no vàlid.");
            return;
        }

        Videojoc v = videojocs.get(num - 1);
        System.out.println("(Deixa en blanc per mantenir el valor actual)");

        System.out.print("Nou títol [" + v.getTitol() + "]: ");
        String s = sc.nextLine();
        if (!s.isBlank()) v.setTitol(s);

        System.out.print("Nou gènere [" + v.getGenere() + "]: ");
        s = sc.nextLine();
        if (!s.isBlank()) v.setGenere(s);

        System.out.print("Nou any [" + v.getAnyLlancament() + "]: ");
        s = sc.nextLine();
        if (!s.isBlank()) {
            try { v.setAnyLlancament(Integer.parseInt(s.trim())); }
            catch (NumberFormatException e) { System.out.println("Any no vàlid, es manté l'anterior."); }
        }

        System.out.print("Nova plataforma [" + v.getPlataforma() + "]: ");
        s = sc.nextLine();
        if (!s.isBlank()) v.setPlataforma(s);

        System.out.print("Nou preu [" + v.getPreu() + "]: ");
        s = sc.nextLine();
        if (!s.isBlank()) {
            try { v.setPreu(Double.parseDouble(s.trim().replace(',', '.'))); }
            catch (NumberFormatException e) { System.out.println("Preu no vàlid, es manté l'anterior."); }
        }

        desarVideojocs(videojocs);
        System.out.println("Videojoc actualitzat i desat.");
    }

    private static void eliminar(ArrayList<Videojoc> videojocs) {
        if (videojocs.isEmpty()) {
            System.out.println("No hi ha videojocs per eliminar.");
            return;
        }
        llistar(videojocs);
        int num = llegirEnter("Número del videojoc a eliminar: ");
        if (num < 1 || num > videojocs.size()) {
            System.out.println("Número no vàlid.");
            return;
        }
        Videojoc eliminat = videojocs.remove(num - 1);
        desarVideojocs(videojocs);
        System.out.println("Eliminat: " + eliminat.getTitol());
    }

    // ---------- Entrada segura ----------

    private static int llegirEnter(String missatge) {
        while (true) {
            System.out.print(missatge);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Introdueix un número enter vàlid.");
            }
        }
    }

    private static double llegirDecimal(String missatge) {
        while (true) {
            System.out.print(missatge);
            try {
                return Double.parseDouble(sc.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Introdueix un número vàlid (ex: 59.99).");
            }
        }
    }

    // ---------- Persistència ----------

    @SuppressWarnings("unchecked")
    public static ArrayList<Videojoc> carregarVideojocs() {
        ArrayList<Videojoc> videojocs = new ArrayList<>();
        File fitxer = new File(FITXER);
        if (fitxer.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fitxer))) {
                videojocs = (ArrayList<Videojoc>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error carregant videojocs: " + e.getMessage());
            }
        }
        return videojocs;
    }

    public static void desarVideojocs(ArrayList<Videojoc> videojocs) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FITXER))) {
            oos.writeObject(videojocs);
        } catch (IOException e) {
            System.out.println("Error desant videojocs: " + e.getMessage());
        }
    }
}
