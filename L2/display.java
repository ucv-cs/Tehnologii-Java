/**
 * P3. Scrieţi un program Java ce constă dintr-o clasă care la rândul ei conţine
 * două funcţii. Prima este o funcţie static String f(String s) care afişează
 * valoarea şirului de caractere s la care se concatenează şirul de caractere “<
 * - - >” şi apoi returnează s. A doua este metoda main în cadrul cărei se
 * apelează funcţia f de mai sus în cadrul unor comenzi System.out.println;
 * funcţia f se va apela o dată pentru un şir de caractere oarecare (ales de
 * programator) şi apoi pentru primul argument din linia de comandă dacă acesta
 * există.
 */

class display {
	static String f(String s) {
		System.out.println(s + "< _ _ >");
		return s;
	}

	public static void main(String[] args) {
		System.out.println(f("blaaaa"));
		if (args.length > 0) {
			System.out.println(f(args[0]));
		}
	}
}