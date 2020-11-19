/**
 * P1. Scrieţi un program Java pentru a rezolva ecuaţia de gradul doi.
 */

public class ecuatia_gr_2 {
	public static void main(String[] args) {
		solve(0, 2, 3);
		solve(-1, 2, -3);
		solve(3, 0, 3);
	}

	/**
	 * Primeste parametrii numerici ai ecuatiei si afiseaza fie solutiile, fie
	 * mesaje de eroare.
	 *
	 * @param a
	 * @param b
	 * @param c
	 */
	public static void solve(double a, double b, double c) {
		if (a == 0) {
			System.out.println("Daca a = 0, ecuatia nu este de gradul 2. Rezolvarea nu va continua.");
			return;
		}

		// calculeaza delta
		double delta = b * b - a * a * c;

		if (delta < 0) {
			System.out.println("delta < 0, deci ecuatia nu are solutii in R. Rezolvarea nu va continua.");
			return;
		}

		// calculeaza solutiile reale
		double x1 = (-b + Math.sqrt(delta)) / 2 * a;
		double x2 = (-b - Math.sqrt(delta)) / 2 * a;

		// afiseaza solutiile
		System.out.printf(
				"Pentru ecuatia %.4fx^2 + %.4fx + %.4f = 0, solutiile in R sunt:\nx1 = %.4f\nx2 = %.4f\n", a, b, c,
				x1, x2);
	}
}
