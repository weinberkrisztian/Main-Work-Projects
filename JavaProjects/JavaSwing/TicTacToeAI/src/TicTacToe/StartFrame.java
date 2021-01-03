package TicTacToe;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

// Inditó felület, amely összeköti a különféle módokat.
public class StartFrame extends JFrame implements ActionListener, basicAddingSteps {
	static JButton btExit = new JButton("Exit");
	static JButton btPlayFriend = new JButton("Play Against Friend");
	static JButton btAI = new JButton("Play Against AI.");
	static JLabel messeage = new JLabel("Please select mode!", SwingConstants.CENTER);
	Container cp = getContentPane();

	public StartFrame() {
		setTitle("Welcome!");
		setLocationRelativeTo(null);
		setSize(300, 300);
		GridLayout grdL = new GridLayout(4, 1);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		cp.setLayout(grdL);
		addElements();
		addListener();

		setEnabled(true);
		setVisible(true);
	}

	@Override
	public void addListener() {
		btAI.addActionListener(this);
		btPlayFriend.addActionListener(this);
		btExit.addActionListener(this);
	}

	@Override
	public void addElements() {

		cp.add(messeage);
		cp.add(btPlayFriend);
		cp.add(btAI);
		cp.add(btExit);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btExit) {
			System.exit(0);
		} else if (e.getSource() == btPlayFriend) {
			this.setVisible(false);
			GUI gui = new GUI(' ');
			this.setEnabled(false);

		} else if (e.getSource() == btAI) {
			selectDiff diff = new selectDiff(this);
			this.setVisible(false);
			this.setEnabled(false);

		}
	}

}
