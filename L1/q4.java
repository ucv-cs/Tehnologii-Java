/**
 * 4. Ce rezultă după rularea următorului cod? class Xor{ public static void
 * main(String args[]){ byte b=10; // 00001010 in binar byte c=15; // 00001111
 * in binary b=(byte) (b ^ c); System.out.println(“b contine ”+b); } } A.
 * Output-ul este: b contine 10 B. Output-ul este: b contine 5 C. Output-ul
 * este: b contine 250 D. Output-ul este: b contine 245
 */
public class q4 {
	public static void main(String args[]) {
		// raspuns: B

		byte b = 10; // 00000000 00000000 00000000 00001010
		System.out.printf("b = %1$d\n%2$s\n", b, intToBinaryString(b));
		byte c = 15; // 00000000 00000000 00000000 00001111
		System.out.printf("c = %1$d\n%2$s\n", c, intToBinaryString(c));
		b = (byte) (b ^ c); // 00000000 00000000 00000000 00000101 = 5
		System.out.printf("b = b ^ c = %1$d\n%2$s\n", b, intToBinaryString(b));
		System.out.println("b contine " + b);
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
