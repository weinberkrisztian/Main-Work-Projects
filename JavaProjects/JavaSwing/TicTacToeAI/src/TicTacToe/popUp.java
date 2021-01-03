package TicTacToe;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

// PopUp ablak amely kiirja a gyõztest, új játék gomb,rekordok ellenõrzése vagy kilépés.
public class popUp extends JFrame implements ActionListener {
	static JButton btExit = new JButton("Exit!");
	static JButton btNewGame = new JButton("New Game!");
	static JButton btRecords = new JButton("Check records!");
	static JLabel winner = new JLabel("", SwingConstants.CENTER);
	Container cp = getContentPane();
	GUI gui;
	Wins wins;

	public popUp(GUI gui, Wins wins) {
		this.gui = gui;
		this.wins = wins;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Game End");
		setLocationRelativeTo(null);
		setSize(300, 300);
		GridLayout grdL = new GridLayout(4, 1);
		cp.setLayout(grdL);
		cp.add(winner);
		cp.add(btNewGame);
		cp.add(btRecords);
		cp.add(btExit);

		btExit.addActionListener(this);
		btNewGame.addActionListener(this);
		btRecords.addActionListener(this);
		setEnabled(false);
		setVisible(false);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btExit) {
			System.exit(0);

		} else if (e.getSource() == btNewGame) {
			gui.setEnabled(true);
			gui.setVisible(true);
			wins.setVisible(false);
			wins.setEnabled(false);
			this.setEnabled(false);
			this.setVisible(false);
			gui.reset();
		} else if (e.getSource() == btRecords) {
			wins.setEnabled(true);
			wins.lbWinsX.setText(String.valueOf(wins.countX));
			wins.lbWinsO.setText(String.valueOf(wins.countO));
			wins.setVisible(true);
		}

	}
}
