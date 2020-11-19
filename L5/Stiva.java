
/**
 * L5. Implementaţi o clasă ce descrie parţial o stivă de numere întregi cu
 * operaţiile de adăugare a unui element, respectiv de scoatere a elementului
 * din vârful stivei. Dacă presupunem că stiva poate memora maxim 100 de
 * elemente, ambele operaţii pot provoca excepţii. Pentru a personaliza aceste
 * excepţii se va crea o clasă specifică denumită ExceptieStiva.
 */

import java.util.Random;

public class Stiva {
	private int capacitate;
	private int[] elemente;
	private int index;

	public Stiva() {
		capacitate = 100;
		elemente = new int[capacitate];
		index = -1; // indexul elementului din varful stivei
	}

	/**
	 * Adauga un element in varful stivei (push())
	 *
	 * @param element
	 * @throws ExceptieStiva
	 */
	public void Adauga(int element) throws ExceptieStiva {
		index++;
		if (index >= capacitate) {
			throw new ExceptieStiva(
					"\nEroare: Stiva este plina (" + capacitate + "), nu se mai poate adauga un element.");
		}
		elemente[index] = element;
	}

	/**
	 * Elimina elementul din varful stivei (pop())
	 *
	 * @throws ExceptieStiva
	 */
	public void Elimina() throws ExceptieStiva {
		index--;
		if (index < 0) {
			throw new ExceptieStiva("\nEroare: Stiva este goala (" + index + "), nu se mai poate elimina un element.");
		}
		elemente[index] = 0;
	}

	/**
	 * Afiseaza continutul stivei
	 */
	public void Afiseaza() {
		if (index >= 0) {
			System.out.print("\nstiva = {");
			for (int i = 0; i < index; i++) {
				System.out.print(elemente[i] + ", ");
			}
			System.out.printf("%d}\n", elemente[index]);
		} else {
			System.out.println("\nstiva = {}");
		}
	}

	public static void main(String[] args) throws ExceptieStiva {
		// adauga 95 de intregi aleatorii intr-o stiva; fixam un range intre 0 si 100
		Stiva stiva = new Stiva();
		Random random = new Random();

		// pentru a genera exceptii se poate modifica valoarea 100 din for loop:
		// ex. pentru overflow: 101
		// ex. pentru underflow: 0
		try {
			for (int i = 0; i < 10; i++) {
				stiva.Adauga(random.nextInt(100));
			}
			stiva.Afiseaza();
			stiva.Elimina();
			stiva.Afiseaza();
		} catch (ExceptieStiva e) {
			e.printStackTrace();
		}
	}
}

class ExceptieStiva extends Exception {
	private static final long serialVersionUID = 17823683L; // generat pentru a inlatura un warning

	public ExceptieStiva(String mesaj) {
		super(mesaj);
	}
}
