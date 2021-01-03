package TicTacToe;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

// Rekord felület, mivel nem adatbázishoz csatlakozik minden játéknál frissül.
public class Wins extends JFrame implements ActionListener {
	static JLabel lbPlayer = new JLabel("Player", SwingConstants.CENTER);
	static JLabel lbWins = new JLabel("Wins", SwingConstants.CENTER);
	static JLabel lbX = new JLabel("X", SwingConstants.CENTER);
	static JLabel lbO = new JLabel("O", SwingConstants.CENTER);
	static JLabel lbWinsX = new JLabel("", SwingConstants.CENTER);
	static JLabel lbWinsO = new JLabel("", SwingConstants.CENTER);
	static JButton btHide = new JButton("Hide");
	static JButton btReset = new JButton("Reset");
	Container cp = getContentPane();

	// statikus osztályváltozókat növeli
	static int countX;
	static int countO;

	public Wins() {
		setTitle("Records");
		setSize(300, 300);
		GridLayout grdL = new GridLayout(4, 2);
		lbWinsX.setText(String.valueOf(countX));
		lbWinsO.setText(String.valueOf(countO));
		cp.setLayout(grdL);
		cp.add(lbPlayer);
		cp.add(lbWins);
		cp.add(lbX);
		cp.add(lbWinsX);
		cp.add(lbO);
		cp.add(lbWinsO);
		cp.add(btReset);
		cp.add(btHide);

		btHide.addActionListener(this);
		btReset.addActionListener(this);

		setVisible(false);
		setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btHide) {
			this.setVisible(false);
			this.setEnabled(false);

		} else if (e.getSource() == btReset) {
			countO = 0;
			countX = 0;
			lbWinsX.setText(String.valueOf(countX));
			lbWinsO.setText(String.valueOf(countO));

		}
	}

}
