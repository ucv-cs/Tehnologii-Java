/**
 * P2. Integraţi clasele Televizor şi TelevizorColor într-un program Java
 * funcţional, în care să se definească câte două instanţe pentru fiecare din
 * clasele de mai sus şi să se folosească metodele definite pentru a afişa
 * proprietăţile instanţelor. Explicaţi la fiecare caz în parte rezultatele
 * obţinute.
 */

/**
 * Răspuns: proprietățile afișate sunt identice întrucât: - pentru obiectele din
 * clasa Televizor se afișează valorile nemodificate ale membrilor an_fabricatie
 * (2001) și diagonala (51); - pentru obiectele din clasa derivată
 * TelevizorColor se afișează valorile modificate prin metodele
 * getAnFabricatie() (2000 + 1 = 2001) și getDiagonala() (52 -1 = 51)
 */

public class App {
	public static void main(String[] args) {
		String separator = "----------\n";

		System.out.println("Televizor 1:");
		Televizor tv1 = new Televizor();
		System.out.printf("getAnFabricatie() = %d [an_fabricatie = %d]\ngetDiagonala() = %d [diagonala = %d]\n",
				tv1.getAnFabricatie(), tv1.an_fabricatie, tv1.getDiagonala(), tv1.diagonala);

		System.out.println(separator + "Televizor 2:");
		Televizor tv2 = new Televizor();
		System.out.printf("getAnFabricatie() = %d [an_fabricatie = %d]\ngetDiagonala() = %d [diagonala = %d]\n",
				tv2.getAnFabricatie(), tv2.an_fabricatie, tv2.getDiagonala(), tv2.diagonala);

		System.out.println(separator + "TelevizorColor 1:");
		TelevizorColor tvc1 = new TelevizorColor();
		System.out.printf("getAnFabricatie() = %d [an_fabricatie = %d]\ngetDiagonala() = %d [diagonala = %d]\n",
				tvc1.getAnFabricatie(), tvc1.an_fabricatie, tvc1.getDiagonala(), tvc1.diagonala);

		System.out.println(separator + "TelevizorColor 2:");
		TelevizorColor tvc2 = new TelevizorColor();
		System.out.printf("getAnFabricatie() = %d [an_fabricatie = %d]\ngetDiagonala() = %d [diagonala = %d]\n",
				tvc2.getAnFabricatie(), tvc2.an_fabricatie, tvc2.getDiagonala(), tvc2.diagonala);
	}
}