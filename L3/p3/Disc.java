public class Disc {
	String nume;
	int capacitate;

	public Disc(String nume_, int capacitate_) {
		nume = nume_;
		capacitate = capacitate_;
	}

	public void Afiseaza() {
		System.out.print(nume + ", " + capacitate + " MB");
	}

}