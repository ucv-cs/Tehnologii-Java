public class Maimuta extends Mamifer {
	public Maimuta() {
		System.out.println("Apelul metodei MarcheazaTeritoriul() din constructorul clasei Maimuta:");
		this.MarcheazaTeritoriul();
	}

	public void MarcheazaTeritoriul() {
		// cod prin care se marcheaza teritoriul cu frunze si crengi
		String crengi = "@/\\@/\\@/\\@/\\@/\\@/\\";
		System.out.println(crengi);
		System.out.println("\\@/\\ Maimuta \\@/\\");
		System.out.println(crengi + "\n");
	}
}