/**
 * P1. Implementaţi clasa Mamifer din cadrul exemplului de mai sus astfel: -
 * scrieţi codul pentru implementarea metodei: public void
 * MarcheazaTeritoriul(), în fiecare din clasele: Om, UnMembruAlGastii, Maimuta;
 * - încercaţi să apelaţi metoda: public void MarcheazaTeritoriul(), în cadrul
 * claselor, şi din afara lor. Explicaţi, la fiecare caz în parte, rezultatele
 * obţinute.
 */

/**
 * Răspuns: în urma executării metodei MarcheazaTeritoriul() fiecare obiect al
 * claselor în care a fost implementată va afișa textul specific din
 * implementare. Același lucru se întâmplă indiferent dacă metoda este apelată
 * din interiorul claselor (ex. din constructor, dar poate fi apelată și dintr-o
 * altă metodă non-statică - poate chiar un wrapper) sau ca metodă a unui
 * obiect.
 */

public class App {
	public static void main(String[] args) {

		Maimuta maimuta = new Maimuta();
		System.out.println("Apelul metodei MarcheazaTeritoriul() din afara clasei Maimuta:");
		maimuta.MarcheazaTeritoriul();

		UnMembruAlGastii tip = new UnMembruAlGastii();
		System.out.println("Apelul metodei MarcheazaTeritoriul() din afara clasei UnMembruAlGastii:");
		tip.MarcheazaTeritoriul();

		Om om = new Om();
		System.out.println("Apelul metodei MarcheazaTeritoriul() din afara clasei Om:");
		om.MarcheazaTeritoriul();
	}
}
