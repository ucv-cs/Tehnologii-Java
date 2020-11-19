/**
 * 3. Care dintre următoarele expresii rezultă într-o expresie pozitivă a lui x?
 * (Alegeţi una.) A. int x=-1; x=x >>> 5; B. int x=-1; x=x >>> 32; C. byte x=-1;
 * x=x >>> 5; D. int x=-1; x=x >> 5;
 */
public class q3 {
	public static void main(String[] args) {
		// raspuns: A

		// A
		int x = -1; // 11111111 11111111 11111111 11111111
		System.out.printf("x = %1$d\n%2$s\n", x, intToBinaryString(x));
		x = x >>> 5; //00000111 11111111 11111111 11111111 = 134217727
		System.out.printf("x = x >>> 5 = %1$d\n%2$s\n", x, intToBinaryString(x));

		// B
		// int x = -1; // 11111111 11111111 11111111 11111111
		// System.out.printf("x = %1$d\n%2$s\n", x, intToBinaryString(x));
		// x = x >>> 32; // 11111111 11111111 11111111 11111111 = -1
		// System.out.printf("x = x >>> 32 = %1$d\n%2$s\n", x, intToBinaryString(x));

		// C
		// byte x = -1;
		// x = x >>> 5; // eroare: Type mismatch: cannot convert from int to byte

		// D
		// int x = -1; // 11111111 11111111 11111111 11111111
		// System.out.printf("x = %1$d\n%2$s\n", x, intToBinaryString(x));
		// x = x >> 5; // 11111111 11111111 11111111 11111111 = -1
		// System.out.printf("x = x >> 5 = %1$d\n%2$s\n", x, intToBinaryString(x));
	}

	/**
	 * Returneaza ca string reprezentarea binara formatata a unui integer
	 *
	 * @param i integer
	 * @return String
	 */
	public static String intToBinaryString(int i) {
		return String.format("%32s", Integer.toBinaryString(i)).replace(" ", "0")
				.replaceFirst("(\\d{8})(\\d{8})(\\d{8})(\\d{8})", "$1 $2 $3 $4");
	}
}