public class HDD extends Disc {
	String controler;

	public HDD(String nume_, int capacitate_, String controler_) {
		super(nume_, capacitate_);
		controler = controler_;
	}

	public void Afiseaza() {
		super.Afiseaza();
		System.out.print(", " + controler);
	}
}