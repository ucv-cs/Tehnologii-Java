
/**
 * P2. Scrieţi un applet Java care să conţină un grup de 2 componente CheckBox
 * etichetate “red” şi “blue”, şi un buton etichetat “Clear all”. Utilizatorul
 * va bifa una din opţiunile “red” sau “blue”. La efectuarea unui click cu mouse-ul
 * pe suprafaţa applet-ului se va desena un cerc colorat roşu sau albastru în funcţie
 * de opţiunea aleasă. La apăsarea butonului “Clear all” se vor şterge toate cercurile
 * desenate până în acel moment.
 */

/**
 * Appleturile Java sunt scoase din uz începând cu JDK 9. În JDK 15.0.1 le-am înlocuit cu un GUI folosind swing si awt.
 */

import javax.swing.*;
import java.awt.*;

public class P2 {
	JFrame window;
	JTextArea textArea;
	JButton button;
	CheckboxGroup cbg; // din awt; in swing nu exista
	Checkbox chk1, chk2, chk3; // din awt; in swing exista JCheckBox, dar nu putea fi grupat folosind
								// awt.CheckboxGroup...

	P2() {
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

		cbg = new CheckboxGroup();

		chk1 = new Checkbox("Lorem", cbg, true);
		chk1.setBounds(10, 250, 200, 50);
		chk1.setFont(new Font("Monospace", Font.ITALIC | Font.BOLD | Font.ROMAN_BASELINE, 12));
		chk1.setForeground(new Color(230, 155, 60));

		chk2 = new Checkbox("ipsum", cbg, false);
		chk2.setBounds(10, 300, 200, 50);
		chk2.setFont(new Font("Arial", Font.BOLD, 14));
		chk2.setForeground(Color.BLUE);

		chk3 = new Checkbox("dolor", cbg, false);
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
		new P2();
	}
}