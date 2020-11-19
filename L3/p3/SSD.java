public class SSD extends Disc {
	byte stare;

	public SSD(String nume_, int capacitate_, Boolean stare_) {
		super(nume_, capacitate_);
		SetStare(stare_);
	}

	public void Afiseaza() {
		super.Afiseaza();
		System.out.print(", " + stare);
	}

	public void SetStare(Boolean stare_) {
		stare = (stare_ == true) ? (byte) 1 : (byte) 0;
	}
}