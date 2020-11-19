/**
 * 2. Care din următoarele expresii sunt legale? (Alegeţi una sau mai multe.) A.
 * int x=6; x=!x; B. int x=6; if (!(x>3)) { } C. int x=6; x=~x;
 */
public class q2 {
	public static void main(String[] args) {
		// raspuns: B, C

		// A
		// int x = 6; x = !x;
		// eroare: The operator ! is undefined for the argument type(s) int
		// ! este operator logic, care se aplica tipurilor boolean, nu int

		// B
		// int x=6; if (!(x>3)) { }
		// expresia este legala; testul logic se face asupra unei expresii
		// evaluabile boolean (x>3), dar corpul lui if nu cuprinde nicio
		// instructiune

		// C
		int x = 6;
		System.out.printf("x = %1$d\n%2$s\n", x, intToBinaryString(x));
		x = ~x;
		System.out.printf("x = ~x = %1$d\n%2$s\n", x, intToBinaryString(x));
		// expresia este legala; ~ este operator de negatie binara
		// (inverseaza toti bitii operandului valid; int este un operand valid)
		// in Java int are 4 B (32 b = dword), deci:
		// +6 = 00000000 00000000 00000000 00000110
		// ~6 = 11111111 11111111 11111111 11111001 = -7
	}

	/**
	 * Returneaza ca string reprezentarea binara formatata a unui integer
	 * @param i integer
	 * @return String
	 */
	public static String intToBinaryString(int i) {
		return String.format("%32s", Integer.toBinaryString(i)).replace(" ", "0")
				.replaceFirst("(\\d{8})(\\d{8})(\\d{8})(\\d{8})", "$1 $2 $3 $4");
	}
}