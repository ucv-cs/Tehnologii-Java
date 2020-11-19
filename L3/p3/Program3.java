/**
 * P3. Creaţi următoarea ierarhie de clase: Disc(superclasa), SSD(subclasa),
 * HDD(subclasa). Un disc are un nume şi o capacitate. Un SSD are în plus o
 * stare (1 dacă este “write-protected”, 0 altfel). Un HDD are în plus un
 * controler (de tip sir de caractere; exemplu: “IDE”, “SCSI”). Superclasa are
 * un constructor (cu parametri) şi o funcţie de afişare (afişează valorile
 * datelor membru). Clasa SSD are un constructor, o funcţie de afişare şi o
 * funcţie care setează (modifică) starea SSD-ului. Clasa HDD are un constructor
 * şi o funcţie de afişare. Scrieţi un program Java care lucrează cu obiecte de
 * tipul celor 3 clase.
 */

public class Program3 {
	public static void main(String[] args) {
		Disc disc = new Disc("Seagate", 1024000);
		disc.Afiseaza();
		System.out.println();

		SSD ssd = new SSD("A-Data", 2048000, false);
		ssd.Afiseaza();
		System.out.println();
		ssd.SetStare(true);
		ssd.Afiseaza();
		System.out.println();

		HDD hdd = new HDD("WD", 1024000, "SCSI");
		hdd.Afiseaza();
	}
}
