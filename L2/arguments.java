/**
 * P2. Scrieţi un program Java care să determine numărul de parametri furnizaţi
 * în linia de comandă la execuţia programului. Dacă acest număr este 0, atunci
 * programul afişează “0 argumente”; dacă în linia de comandă se furnizează un
 * singur parametru atunci programul afişează “1 argument” precum şi valoarea
 * acestui argument; altfel se va afişa textul “mai multe argumente” şi valorile
 * acestora.
 */

public class arguments {
	public static void main(String[] args) {
		int count = args.length;

		switch (count) {
			case 0:
				System.out.print("0 argumente");
				break;

			case 1:
				System.out.printf("1 argument: %s", args[0]);
				break;

			default:
				System.out.print("mai multe argumente: ");
				for (int i = 0; i < count; i++) {
					System.out.print(args[i] + " ");
				}
				break;
		}
	}
}
