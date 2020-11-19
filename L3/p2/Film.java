/**
 * P2. Modificaţi clasa Film astfel: -adăugaţi clasei o metodă statică care să
 * returneze valoarea datei membru durata. -încercaţi să apelaţi metoda afisare(
 * ) în cadrul metodei printCinema( ) şi invers, apelaţi metoda printCinema( )
 * în cadrul metodei afisare( ). Explicaţi la fiecare caz în parte rezultatele
 * obţinute.
 */

public class Film {
	static int pretBilet = 30000;
	static String cinematograf = "Patria";
	public String nume;
	public int durata; // in minute
	public boolean luatOscar;

	public Film(String num, int nrMin, boolean oscar) {
		nume = num;
		durata = nrMin;
		luatOscar = oscar;
	}

	public static int getDurata() {
		// Eroare: Cannot make a static reference to the non-static field
		// durataJava(33554506)
		// metoda statică (de clasă) încearcă accesarea unui membru (durata) non-static
		// (de instanță); acest lucru nu este posibil în mod direct, ci numai printr-o
		// referință la un obiect anume; v. getDurata2()

		return durata; // programul nu se compilează în această formă
	}

	public static int getDurata2(Film film) {
		return film.durata;
	}

	static int getPretBilet() {
		return pretBilet;
	}

	static void printCinema(Film f) {
		System.out.println("Filmul " + f.nume + " este difuzat la cinematograful " + cinematograf);
		// Eroare: Cannot make a static reference to the non-static method afisare()
		// from the type FilmJava(603979977)
		// metoda statică printCinema() încearcă accesarea unei metode non-statice
		// (afisare())

		afisare(); // programul nu se compilează în această formă
	}

	public void afisare() {
		System.out.println("Nume: " + nume);
		System.out.println("Durata (in minute): " + durata);// getDurata()); /* pentru verificarea afirmației de la
															// getDurata() */
		if (luatOscar) {
			System.out.println("Filmul a primit premiul Oscar\n");
		} else {
			System.out.println("Filmul nu a primit premiul Oscar\n");
		}
		// metoda non-statică afisare() poate accesa metoda statică (de clasă)
		// printCinema()
		// în afișarea finală va apărea de două ori textul "Filmul Titanic este difuzat
		// la cinematograful Patria": mai întâi ca urmare a apelului imediat următor,
		// apoi ca urmare a apelului Film.printCinema(f1) din main().
		printCinema(this);
	}

	public static void main(String args[]) {
		System.out.println("Pretul unui bilet este: " + Film.getPretBilet() + " lei");
		Film f1 = new Film("Titanic", 180, true);
		f1.afisare();
		Film.printCinema(f1);

		System.out.println(Film.getDurata2(f1)); // exemplificare pentru getDurata2()
	}

}