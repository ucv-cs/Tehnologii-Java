/**
 * 9. Se va compila următorul cod ? byte b=2; byte b1=3; b=b*b1;
 */
public class q9 {
	public static void main(String[] args) {
		// raspuns: nu

		byte b = 2;
		byte b1 = 3;
		// b = b * b1; // eroare: Type mismatch: cannot convert from int to byte
		// b * b1 este evaluat ca int (4 B) > byte (1 B)
		// functioneaza cu un cast: b = (byte)(b * b1);
		b = (byte) (b * b1);
		System.out.println(b);
	}
}