public class Student {
	public String nume;
	public byte an;
	public String grupa;
	public byte[] note;

	public Student(String nume_, byte an_, String grupa_, byte[] note_) {
		nume = nume_;
		an = an_;
		grupa = grupa_;
		note = note_;
	}

	public float CalculeazaMedia() {
		if (note.length >= 2) {
			return (note[0] + note[1]) / 2f;
		}
		return 0f;
	}

	public void AfiseazaStudent() {
		System.out.println(String.format("\nStudent: %1$s\nAn: %2$d\nGrupă: %3$s\nMedie: %4$.2f", nume, an, grupa,
				CalculeazaMedia()));
	}
}