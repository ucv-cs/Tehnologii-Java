
/**
 * 1. După executarea fragmentului de cod de mai jos, care sunt valorile
 * variabilelor x, a şi b? int x, a=6,b=7; x=a++ + b++; A. x=15, a=7, b=8 B.
 * x=15, a=6, b=7 C. x=13, a=7, b=8 D. x=13, a=6, b=7
 */
public class q1 {
	public static void main(String[] args) {
		// raspuns: C

		int x, a = 6, b = 7;
		x = a++ + b++;
		// x este suma lui a si b inainte de incrementare (6+7=13)
		// la iesirea din expresie a si b sunt incrementate (a=7, b=8)
		System.out.printf("x=%d, a=%d, b=%d", x, a, b);
		// ouput: x=13, a=7, b=8 (C.)
	}
}