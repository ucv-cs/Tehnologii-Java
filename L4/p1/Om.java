public class Om extends Mamifer {
	public Om() {
		System.out.println("Apelul metodei MarcheazaTeritoriul() din constructorul clasei Om:");
		this.MarcheazaTeritoriul();
	}

	public void MarcheazaTeritoriul() {
		// cod prin care se marcheaza teritoriul construind un gard
		String gard = "|^|^|^|^|^|";
		System.out.println(gard);
		System.out.println("|^|  Om |^|");
		System.out.println(gard + "\n");
	}
}