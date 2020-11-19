import java.io.*;

public class CitireFisier {
	public static void citesteFisier(String fis) {
		FileReader f = null;
		try {
			// Deschidem fisierul
			System.out.println(" Deschidem fisierul " + fis);
			f = new FileReader(fis);
			// Citim si afisam fisierul caracter cu caracter
			int c;
			while ((c = f.read()) != -1)
				System.out.print((char) c);
		} catch (FileNotFoundException e) {
			// Tratam un tip de exceptie
			System.err.println(" Fisierul nu a fost gasit !");
			System.err.println(" Exceptie : " + e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			// Tratam alt tip de exceptie
			System.out.println(" Eroare la citirea din fisier !");
			e.printStackTrace();
		} finally {
			if (f != null) {
				// Inchidem fisierul
				System.out.println("\nInchidem fisierul .");
				try {
					f.close();
				} catch (IOException e) {
					System.err.println(" Fisierul nu poate fi inchis !");
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String args[]) {
		if (args.length > 0)
			citesteFisier(args[0]);
		else
			System.out.println(" Lipseste numele fisierului !");
	}
}