/**
 * P4. Scrieţi un program Java ce constă din următoarele: Se construieşte un
 * vector unidimensional cu elemente de tip float şi se iniţializează elementele
 * cu valori arbitrare alese de programator (atât pozitive cât şi negative).
 * Într-o buclă iterativă se vor afişa valorile acestor elemente cu următoarea
 * specificare: dacă se întâlneşte un element negativ nu se va afişa ci se va
 * sări la următorul element din vector.
 */

public class vec {
	public static void main(String[] args) {
		float[] elems = { 0, -2, 3, -4, 7.23f };

		for (int i = 0; i < elems.length; i++) {
			if (elems[i] >= 0) {
				System.out.println(elems[i]);
			}
		}
	}
}
