/**
 * 10. În codul de mai jos, care sunt posibilele tipuri pentru variabila result?
 * (Alegeţi cel mai complet răspuns adevărat.) byte b=11; short s=13; result=b*
 * ++s; A. byte, short, int, long, float, double B. boolean, byte, short, char,
 * int, long, float, double C. byte, short, char, int, long, float, double D.
 * byte, short, char E. int, long, float, double
 */
public class q10 {
	public static void main(String[] args) {
		// raspuns: E

		byte b = 11;
		short s = 13;
		int result = b * ++s; // rezultatul inmultirii este evaluat ca int, deci tipul minim este int (seria
								// int, long, float, double)
		System.out.println(result);
	}
}