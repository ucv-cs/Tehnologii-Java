/**
 * 7. Ce rezultă din următorul fragment de cod? int x=1; String
 * []names={"Fred","Jim","Sheila"}; names[--x]+="."; for(int
 * i=0;i<names.length;i++) System.out.println(names[i]); A. Output-ul include
 * Fred. B. Output-ul include Jim. C. Output-ul include Sheila. D. Nimic din
 * cele de mai sus.
 */
public class q7 {
	public static void main(String[] args) {
		// raspuns: A

		int x = 1;
		String[] names = { "Fred", "Jim", "Sheila" };
		names[--x] += "."; // --x = 0, names[0] = "Fred" + "." = "Fred."
		for (int i = 0; i < names.length; i++)
			System.out.println(names[i]);
	}
}
