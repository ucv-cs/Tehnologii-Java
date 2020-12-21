
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

import org.w3c.dom.events.MouseEvent;

import java.awt.*;
import java.awt.geom.*;

public class P2 extends JFrame /*implements MouseEvent*/ {
	JFrame window;
	JButton button;
	CheckboxGroup cbg; // din awt; in swing nu exista
	Checkbox chk1, chk2; // din awt; in swing exista JCheckBox, dar nu putea fi grupat folosind
							// awt.CheckboxGroup...
	Shape circle;

	P2() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

		window = new JFrame();
		window.setSize(285, 450);
		window.setLocation(dimension.width / 2 - window.getSize().width / 2,
				dimension.height / 2 - window.getSize().height / 2);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		button = new JButton("Clear all");
		button.setBounds(10, 220, 250, 30);
		button.setFont(new Font("Consolas", Font.PLAIN, 12));

		cbg = new CheckboxGroup();

		chk1 = new Checkbox("red", cbg, true);
		chk1.setBounds(10, 250, 200, 50);
		chk1.setFont(new Font("Arial", Font.ITALIC | Font.BOLD | Font.ROMAN_BASELINE, 12));
		chk1.setForeground(Color.RED);

		chk2 = new Checkbox("blue", cbg, false);
		chk2.setBounds(10, 300, 200, 50);
		chk2.setFont(new Font("Arial", Font.BOLD, 14));
		chk2.setForeground(Color.BLUE);

		circle = new Ellipse2D.Float(10.0f, 10.0f, 10.0f, 10.0f);

		window.add(chk1);
		window.add(chk2);
		window.add(button);

		window.setLayout(null);
		window.setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(Color.GREEN);
		g.drawOval(480, 480, 200, 200);
		g.fillOval(480, 480, 200, 200);
	}

	public static void main(String[] args) {
		P2 app = new P2();
		app.paint(new Graphics());
	}
}