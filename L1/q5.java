/**
 * 5. Ce rezultă după încercarea de a compila şi rula următorul cod? 1.class
 * Conditional{ 2. public static void main(String args[]){ 3. int x=4; 4.
 * System.out.println(“valoarea este ”+((x > 4) ? 99.99:9)); 5. } 6.} A.
 * Output-ul este: valoarea este 99.99 B. Output-ul este: valoarea este 9 C.
 * Output-ul este: valoarea este 9.0 D. Se semnalează eroare de compilare la
 * linia 4.
 */
public class q5 {
	public static void main(String args[]) {
		int x = 4;
		// x > 4 e fals, deci se afiseaza ce urmeaza dupa : in operatorul ternar
		System.out.println("valoarea este " + ((x > 4) ? 99.99 : 9));
	}
}
