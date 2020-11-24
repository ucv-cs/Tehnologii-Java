
/**
 * P1. Scrieţi un applet Java care să conţină următoarele: o zonă de editare
 * TextArea cu 5 linii si 10 coloane, un buton cu eticheta OK şi un grup de
 * componente CheckBox. Setaţi textul zonei de editare la un anume şir de
 * caractere. Utilizaţi diverse culori şi font-uri.
 */

/**
 * Appleturile Java sunt scoase din uz începând cu JDK 9. În JDK 15.0.1 le-am înlocuit cu un GUI folosind swing.
 */

import javax.swing.*;
import java.awt.*;

public class Crapplet {
	JFrame window;
	JTextArea textArea;
	JButton button;
	JCheckBox chk1, chk2, chk3;

	Crapplet() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

		window = new JFrame();
		window.setSize(285, 450);
		window.setLocation(dimension.width / 2 - window.getSize().width / 2,
				dimension.height / 2 - window.getSize().height / 2);

		textArea = new JTextArea();
		textArea.setBounds(10, 10, 250, 200);
		textArea.setRows(5);
		textArea.setColumns(10);
		textArea.setText("un anume șir de caractere");
		textArea.setForeground(Color.RED);

		button = new JButton("OK");
		button.setBounds(10, 220, 250, 30);
		button.setFont(new Font("Consolas", Font.PLAIN, 12));

		chk1 = new JCheckBox("C#", true);
		chk1.setBounds(10, 250, 200, 50);
		chk1.setFont(new Font("Monospace", Font.ITALIC | Font.BOLD | Font.ROMAN_BASELINE, 12));
		chk1.setForeground(new Color(230, 155, 60));

		chk2 = new JCheckBox("is nicer than", true);
		chk2.setBounds(10, 300, 200, 50);
		chk2.setFont(new Font("Arial", Font.BOLD, 14));
		chk2.setForeground(Color.BLUE);

		chk3 = new JCheckBox("Java");
		chk3.setBounds(10, 350, 200, 50);
		chk3.setFont(new Font("Times", Font.ITALIC, 16));
		chk3.setForeground(new Color(128, 240, 100));
		chk3.setBackground(Color.MAGENTA);

		window.add(textArea);
		window.add(button);
		window.add(chk1);
		window.add(chk2);
		window.add(chk3);

		window.setLayout(null);
		window.setVisible(true);
	}

	public static void main(String[] args) {
		new Crapplet();
	}
}