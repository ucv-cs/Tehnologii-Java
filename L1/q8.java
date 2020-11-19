/**
 * 8. Care linie din următorul cod nu se va compila? 1. byte b=5; 2. char c=’5’;
 * 3. short s=55; 4. int i=555; 5. float f=555.5f; 6. b=s; 7. i=c; 8. if(f>b) 9.
 * f=i;
 */
public class q8 {
	public static void main(String[] args) {
		// raspuns: 6

		byte b = 5;
		char c = '5';
		short s = 55;
		int i = 555;
		float f = 555.5f;
		// b = s; // byte are 1 B, iar short 2 B;
		// conversia implicita (mic -> mare) nu ar functiona
		// un cast ar functiona cu valorile date:
		// b = (byte) s
		i = c;
		if (f > b)
		f = i;
	}
}