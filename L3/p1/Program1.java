/**
 * P1. Scrieţi clasa Student. Fiecare student are un nume, an, grupă, şi două
 * note obţinute la o anumită materie - una pe semestrul 1 iar cealaltă pe
 * semestrul 2. Clasa conţine un constructor ce iniţializează datele membru ale
 * clasei la valorile parametrilor săi, o funcţie care calculează şi returnează
 * media celor două note, şi o funcţie de afişare ce afişează valorile datelor
 * membru şi valoarea mediei. Scrieţi un program complet în care să utilizaţi
 * obiecte ale clasei Student.
 */

public class Program1 {
	public static void main(String[] args) {
		Student[] studenti = new Student[] { new Student("Popescu, Ion", (byte) 2, "221A", new byte[] { 7, 10 }),
				new Student("Ștefănescu, Anișoara", (byte) 2, "221B", new byte[] { 6, 9 }),
				new Student("Cîmpeanu, George", (byte) 2, "222A", new byte[] { 5, 8 }),
				new Student("Ilie, Ioana", (byte) 2, "222B", new byte[] { 5 }) }; // a lipsit de la un examen

		for (Student student : studenti) {
			student.AfiseazaStudent();
		}
	}
}
