package TicTacToe;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

// AI nehézség kiválasztó felület.
public class selectDiff extends JFrame implements ActionListener, basicAddingSteps {
	static JButton btEasy = new JButton("Easy");
	static JButton btMedium = new JButton("Medium");
	static JButton btHard = new JButton("Hard");
	static JButton btBack = new JButton("Back");
	Container cp = getContentPane();
	StartFrame sf;

	public selectDiff(StartFrame sf) {
		this.sf = sf;
		setTitle("Difficulty");
		setLocationRelativeTo(null);
		setSize(300, 300);
		GridLayout grdLaz = new GridLayout(4, 1);
		cp.setLayout(grdLaz);
		addElements();
		addListener();

		setVisible(true);
		setEnabled(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btBack) {
			this.setVisible(false);
			sf.setEnabled(true);
			sf.setVisible(true);
			this.setEnabled(false);
		}

		// a kulcs az 'e','m','h' atadott paraméter amellyel a GUI osztály tudni
		// fogja,hogy melyik módban kell futnia
		else if (e.getSource() == btEasy) {
			GUI gui = new GUI('e');
			this.setVisible(false);
			this.setEnabled(false);
		} else if (e.getSource() == btMedium) {
			GUI gui = new GUI('m');
			this.setVisible(false);
			this.setEnabled(false);
		} else if (e.getSource() == btHard) {
			GUI gui = new GUI('h');
			this.setVisible(false);
			this.setEnabled(false);
		}
	}

	@Override
	public void addListener() {
		btEasy.addActionListener(this);
		btMedium.addActionListener(this);
		btHard.addActionListener(this);
		btBack.addActionListener(this);

	}

	@Override
	public void addElements() {
		cp.add(btEasy);
		cp.add(btMedium);
		cp.add(btHard);
		cp.add(btBack);

	}
}
