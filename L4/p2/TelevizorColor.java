public class TelevizorColor extends Televizor {
	int diagonala = 52;
	int an_fabricatie = 2000;

	int getAnFabricatie() {
		return an_fabricatie + 1;
	}

	int getDiagonala() {
		return diagonala - 1;
	}
}