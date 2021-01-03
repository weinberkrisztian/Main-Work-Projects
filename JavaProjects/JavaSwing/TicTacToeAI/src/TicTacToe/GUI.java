package TicTacToe;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

// maga a játék felülete
public class GUI extends JFrame implements ActionListener, basicAddingSteps {
	JButton bt1 = new JButton();
	JButton bt2 = new JButton();
	JButton bt3 = new JButton();
	JButton bt4 = new JButton();
	JButton bt5 = new JButton();
	JButton bt6 = new JButton();
	JButton bt7 = new JButton();
	JButton bt8 = new JButton();
	JButton bt9 = new JButton();
	ArrayList<JButton> buttons = new ArrayList<JButton>();
	Container cp = getContentPane();
	// ennek állításával érjük el,hoy X vagy O a következõ jel
	boolean nextPlayer = true;
	// a felület ellenõrzéséhez használt változók
	String topString = "";
	String midString = "";
	String botString = "";
	String firstCross = "";
	String secCross = "";
	String leftString = "";
	String middleString = "";
	String rightString = "";
	String allLength = "";
	popUp popup;
	Wins wins;
	char mode;

	public GUI(char mode) {
		addButtons(this.buttons);
		this.mode = mode;
		this.wins = new Wins();
		this.popup = new popUp(this, wins);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("TicTacToe");
		setLocationRelativeTo(null);
		setSize(300, 300);
		GridLayout grdLaz = new GridLayout(3, 3);
		cp.setLayout(grdLaz);
		addElements();
		addListener();
		setVisible(true);

	}

	void addButtons(ArrayList<JButton> buttons) {
		buttons.add(bt1);
		buttons.add(bt2);
		buttons.add(bt3);
		buttons.add(bt4);
		buttons.add(bt5);
		buttons.add(bt6);
		buttons.add(bt7);
		buttons.add(bt8);
		buttons.add(bt9);
	}

	// könnyû fokozat, egy random helyre rak az ellenfél
	int easyMode() {
		int random;
		boolean done = false;
		do {
			random = (int) (Math.random() * 9);
			if (allLength.length() != 9 && buttons.get(random).getText().isEmpty()) {
				buttons.get(random).setText("O");

				done = true;
			}
		} while (done != true);

		return random;
	}

	// közepes fokozat, amely figyeli hogy a következõ lépéssel nyerhet-e illetve
	// ellenõrzi hogy Te nyernél e a köetkezõ körben,
	// ilyen esetben ezt megakadályozza.
	int mediumMode() {

		for (int i = 0; i < buttons.size(); i++) {
			if (allLength.length() != 9 && buttons.get(i).getText().isEmpty()) {
				if (checkStringMediumWin(i) == true) {
					buttons.get(i).setText("O");
					return i;
				}
			}
		}

		for (int j = 0; j < buttons.size(); j++) {
			if (allLength.length() != 9 && buttons.get(j).getText().isEmpty()) {
				if (checkStringMediumNoDefeat(j) == true) {
					buttons.get(j).setText("O");
					return j;
				}
			}
		}

		return easyMode();
	}

	void reset() {
		topString = "";
		midString = "";
		botString = "";
		firstCross = "";
		secCross = "";
		leftString = "";
		middleString = "";
		rightString = "";
		allLength = "";
		buttons.get(0).setText("");
		buttons.get(1).setText("");
		buttons.get(2).setText("");
		buttons.get(3).setText("");
		buttons.get(4).setText("");
		buttons.get(5).setText("");
		buttons.get(6).setText("");
		buttons.get(7).setText("");
		buttons.get(8).setText("");
		nextPlayer = true;
	}

	// medium generál egy lépést elõre, ami alapján eldönti,hogy hova kell lépnie,
	// hogy nyerjen
	boolean checkStringMediumWin(int i) {
		tempBoard tempB = new tempBoard();
		tempB.setTempStringForButts(buttons);
		tempB.setTempStringArray(getStringArr());
		tempB.setAllLengthTemp(allLength);
		tempB.getTempStringForButts().set(i, "O");

		tempB.setTempStringArray(tempB.addToStrings(i, tempB.getTempStringForButts(), tempB.getTempStringArray()));
		if (tempB.getTempStringArray().contains("OOO"))
			return true;
		else
			return false;
	}

	// ugyanugy legenerálja elõre a lépést,csak azuzt figyeli,hogy Te ne nyerjél.
	boolean checkStringMediumNoDefeat(int i) {
		tempBoard tempB = new tempBoard();
		tempB.setTempStringForButts(buttons);
		tempB.setTempStringArray(getStringArr());
		tempB.setAllLengthTemp(allLength);
		tempB.getTempStringForButts().set(i, "X");

		tempB.setTempStringArray(tempB.addToStrings(i, tempB.getTempStringForButts(), tempB.getTempStringArray()));
		if (tempB.getTempStringArray().contains("XXX"))
			return true;
		else
			return false;
	}

	ArrayList<String> getStringArr() {
		ArrayList<String> strings = new ArrayList<String>();
		strings.add(leftString);
		strings.add(middleString);
		strings.add(rightString);
		strings.add(topString);
		strings.add(midString);
		strings.add(botString);
		strings.add(firstCross);
		strings.add(secCross);

		return strings;
	}

	// megnézi,hogy a stringek tartalmaznak e 3X ill 3O t, ezestben megvan a gyõztes
	void checkStrings() {
		ArrayList<String> strings = new ArrayList<String>();
		strings.add(leftString);
		strings.add(middleString);
		strings.add(rightString);
		strings.add(topString);
		strings.add(midString);
		strings.add(botString);
		strings.add(firstCross);
		strings.add(secCross);

		if (strings.contains("XXX")) {
			popup.winner.setText("X wins!");
			wins.countX = wins.countX + 1;
			popup.setEnabled(true);
			popup.setVisible(true);
			this.setEnabled(false);
			this.setVisible(false);

		} else if (strings.contains("OOO")) {
			popup.winner.setText("O wins!");
			wins.countO = wins.countO + 1;
			popup.setEnabled(true);
			popup.setVisible(true);
			this.setEnabled(false);
			this.setVisible(false);
		} else if (allLength.length() == 9) {
			popup.winner.setText("Draw");
			popup.setEnabled(true);
			popup.setVisible(true);
			this.setEnabled(false);
			this.setVisible(false);

		}

	}

	void randomButton(int index) {
		switch (index) {
		case 0:
			topString = topString.concat(buttons.get(0).getText());
			firstCross = firstCross.concat(buttons.get(0).getText());
			leftString = leftString.concat(buttons.get(0).getText());
			allLength = allLength.concat(buttons.get(0).getText());
			break;
		case 1:
			topString = topString.concat(buttons.get(1).getText());
			middleString = middleString.concat(buttons.get(1).getText());
			allLength = allLength.concat(buttons.get(1).getText());
			break;
		case 2:
			topString = topString.concat(buttons.get(2).getText());
			secCross = secCross.concat(buttons.get(2).getText());
			rightString = rightString.concat(buttons.get(2).getText());
			allLength = allLength.concat(buttons.get(2).getText());
			break;
		case 3:

			midString = midString.concat(buttons.get(3).getText());
			leftString = leftString.concat(buttons.get(3).getText());
			allLength = allLength.concat(buttons.get(3).getText());
			break;
		case 4:

			midString = midString.concat(buttons.get(4).getText());
			firstCross = firstCross.concat(buttons.get(4).getText());
			secCross = secCross.concat(buttons.get(4).getText());
			middleString = middleString.concat(buttons.get(4).getText());
			allLength = allLength.concat(buttons.get(4).getText());
			break;
		case 5:

			midString = midString.concat(buttons.get(5).getText());
			rightString = rightString.concat(buttons.get(5).getText());
			allLength = allLength.concat(buttons.get(5).getText());
			break;

		case 6:

			botString = botString.concat(buttons.get(6).getText());
			secCross = secCross.concat(buttons.get(6).getText());
			leftString = leftString.concat(buttons.get(6).getText());
			allLength = allLength.concat(buttons.get(6).getText());
			break;
		case 7:

			botString = botString.concat(buttons.get(7).getText());
			middleString = middleString.concat(buttons.get(7).getText());
			allLength = allLength.concat(buttons.get(7).getText());
			break;

		case 8:

			botString = botString.concat(buttons.get(8).getText());
			firstCross = firstCross.concat(buttons.get(8).getText());
			rightString = rightString.concat(buttons.get(8).getText());
			allLength = allLength.concat(buttons.get(8).getText());
			break;
		}

	}

	@Override
	public void addElements() {
		cp.add(buttons.get(0));
		cp.add(buttons.get(1));
		cp.add(buttons.get(2));
		cp.add(buttons.get(3));
		cp.add(buttons.get(4));
		cp.add(buttons.get(5));
		cp.add(buttons.get(6));
		cp.add(buttons.get(7));
		cp.add(buttons.get(8));
	}

	@Override
	public void addListener() {
		buttons.get(0).addActionListener(this);
		buttons.get(1).addActionListener(this);
		buttons.get(2).addActionListener(this);
		buttons.get(3).addActionListener(this);
		buttons.get(4).addActionListener(this);
		buttons.get(5).addActionListener(this);
		buttons.get(6).addActionListener(this);
		buttons.get(7).addActionListener(this);
		buttons.get(8).addActionListener(this);
	}

	// minimax, amely egy rekuzio segitségével szintenként elõre legenerálja a játék
	// lehetséges kimeneteleit,
	// majd kiértékeli,hogy melyik lenne a legideálisabb lépés a vereség
	// elkerüléséhez.
	public double minimax(tempBoard tempB, int level, boolean next) {
		next = !next;
		if (tempB.getTempStringArray().contains("OOO")) {
			return 1000 / Math.pow(level, level);
		} else if (tempB.getTempStringArray().contains("XXX")) {
			return -1000 / Math.pow(level, level);
		} else if (tempB.getAllLengthTemp().length() == 9) {
			return 0;
		} else {
			double sumValue = 0;
			for (int i = 0; i < 9; i++) {
				if (tempB.getAllLengthTemp().length() != 9 && tempB.getTempStringForButts().get(i).isEmpty()) {

					// uj temp letrehozas, tempB átadásával
					tempBoard tempBInside = new tempBoard();

					tempBInside.setTempStringForInsideStirng(tempB.getTempStringForButts());
					tempBInside.setTempStringArray(tempB.getTempStringArray());
					tempBInside.setAllLengthTemp(tempB.getAllLengthTemp());
					if (next == true) {
						tempBInside.getTempStringForButts().set(i, "O");
					} else {
						tempBInside.getTempStringForButts().set(i, "X");
					}
					tempBInside.setTempStringArray(tempBInside.addToStrings(i, tempBInside.getTempStringForButts(),
							tempBInside.getTempStringArray()));
					// sumValue+=minmaxrekurzio -- level +1 kovi szinthez
					sumValue += minimax(tempBInside, level + 1, next);
					tempBInside.getTempStringForButts().set(i, "");
				}
			}
			return sumValue;
		}

	}
	// nehéz fokozat,amely a fenti minimax függvénnyel kértékeli a lehetséges lépéseket
	public int hardMode() {
		Map<Integer, Double> values = new HashMap<Integer, Double>();
		for (int i = 0; i < 9; i++) {
			if (allLength.length() != 9 && buttons.get(i).getText().isEmpty()) {

				;
				tempBoard tempB = new tempBoard();
				tempB.setTempStringForButts(buttons);
				tempB.setTempStringArray(getStringArr());
				tempB.setAllLengthTemp(allLength);
				tempB.getTempStringForButts().set(i, "O");
				tempB.setTempStringArray(
						tempB.addToStrings(i, tempB.getTempStringForButts(), tempB.getTempStringArray()));
				values.put(i, minimax(tempB, 1, true));
			}
		}
		Double max = null;
		for (int j = 0; j < 9; j++) {
			if (max == null || (values.get(j) != null && values.get(j) > max)) {

				max = values.get(j);
			}

		}

		for (int k = 0; k < 9; k++) {
			if (values.get(k) == max) {
				buttons.get(k).setText("O");
				return k;
			}
		}
		return easyMode();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// bal fels gomb
		if (e.getSource() == buttons.get(0)) {
		// elsõ lépés: megnézi hogy az adott hely üres e
			if (buttons.get(0).getText().isEmpty()) {
				// második: megnézi, hogy melyik fél jön
				if (nextPlayer == true) {
					// true esetében X a következõ lépés
					buttons.get(0).setText("X");
					// hozzá konkatálja a stringekhez amelyek érintik az adott mezõt
					//(jelen esetben felsõ sor, bal oszlop, elsõ átló és az allLength smely figyeli,hogy vége e a meccsnek)
					randomButton(0);
					if (allLength.length() < 8) {
						// a selectDiff osztályban átadott paraméterrel eldönti, hogy melyik módot futtassa
						switch (mode) {
						case 'e':
							// visszad egy random indexet
							int index = easyMode();
							randomButton(index);
							break;
						case 'm':
							// olyan indexet ad vissza amivel nyerhet vagy megelõzi a vereséget,ha nem lehetséges, akkor easyModdal random lépést választ
							int MediumIndex = mediumMode();
							randomButton(MediumIndex);

							break;
						case 'h':
							// kis korrigálás, hogy nehezebb legyen megverni :)
							if (allLength.length() == 1) {
								buttons.get(8).setText("0");
								randomButton(8);
							} else {
								// elõre lejatszott lépesekbõl választja ki a számára optimálisat(nem verhetetlen)
								int HardIndex = hardMode();
								randomButton(HardIndex);
							}
							break;
						default:
							// ha nincs megadva AI mód akkor átáll a következõ játékosra
							nextPlayer = false;
							break;
						}
					}
				} else {
					// ha nem volt true a nextPlayer, ezért a következö lépés O lesz
					buttons.get(0).setText("O");
					randomButton(0);
					nextPlayer = true;
				}
				// ellenõrzi hogy nyert e valaki
				checkStrings();

			}
			// az elõzõhöz hasonlóan mindegyik gombra
		} else if (e.getSource() == buttons.get(1)) {
			if (buttons.get(1).getText().isEmpty()) {
				if (nextPlayer == true) {
					buttons.get(1).setText("X");
					randomButton(1);
					if (allLength.length() < 8) {
						switch (mode) {
						case 'e':
							int index = easyMode();
							randomButton(index);
							break;
						case 'm':
							int MediumIndex = mediumMode();
							randomButton(MediumIndex);

							break;
						case 'h':
							int HardIndex = hardMode();
							randomButton(HardIndex);

							break;
						default:
							nextPlayer = false;
							break;
						}
					}
				} else {
					buttons.get(1).setText("O");
					randomButton(1);
					nextPlayer = true;
				}
				checkStrings();
			}
		} else if (e.getSource() == buttons.get(2)) {
			if (buttons.get(2).getText().isEmpty()) {
				if (nextPlayer == true) {
					buttons.get(2).setText("X");
					randomButton(2);
					if (allLength.length() < 8) {
						switch (mode) {
						case 'e':
							int index = easyMode();
							randomButton(index);
							break;
						case 'm':
							index = mediumMode();
							randomButton(index);

							break;
						case 'h':
							if (allLength.length() == 1) {
								buttons.get(6).setText("0");
								randomButton(6);
							} else {
								int HardIndex = hardMode();
								randomButton(HardIndex);
							}
							break;
						default:
							nextPlayer = false;
							break;
						}
					}
				} else {
					buttons.get(2).setText("O");
					randomButton(2);
					nextPlayer = true;
				}
				checkStrings();
			}

		} else if (e.getSource() == buttons.get(3)) {
			if (buttons.get(3).getText().isEmpty()) {
				if (nextPlayer == true) {
					buttons.get(3).setText("X");
					randomButton(3);
					if (allLength.length() < 8) {
						switch (mode) {
						case 'e':
							int index = easyMode();
							randomButton(index);
							break;
						case 'm':
							index = mediumMode();
							randomButton(index);

							break;
						case 'h':
							int HardIndex = hardMode();
							randomButton(HardIndex);
							break;
						default:
							nextPlayer = false;
							break;
						}
					}
				} else {
					buttons.get(3).setText("O");
					randomButton(3);
					nextPlayer = true;
				}
				checkStrings();
			}

		} else if (e.getSource() == buttons.get(4)) {
			if (buttons.get(4).getText().isEmpty()) {
				if (nextPlayer == true) {
					buttons.get(4).setText("X");
					randomButton(4);
					if (allLength.length() < 8) {
						switch (mode) {
						case 'e':
							int index = easyMode();
							randomButton(index);
							break;
						case 'm':
							int MediumIndex = mediumMode();
							randomButton(MediumIndex);
							break;
						case 'h':
							int HardIndex = hardMode();
							randomButton(HardIndex);
							break;
						default:
							nextPlayer = false;
							break;
						}
					}
				} else {
					buttons.get(4).setText("O");
					randomButton(4);
					nextPlayer = true;
				}
				checkStrings();

			}

		} else if (e.getSource() == buttons.get(5)) {
			if (buttons.get(5).getText().isEmpty()) {
				if (nextPlayer == true) {
					buttons.get(5).setText("X");
					randomButton(5);
					if (allLength.length() < 8) {
						switch (mode) {
						case 'e':
							int index = easyMode();
							randomButton(index);
							break;
						case 'm':
							int MediumIndex = mediumMode();
							randomButton(MediumIndex);

							break;
						case 'h':
							int HardIndex = hardMode();
							randomButton(HardIndex);
							break;
						default:
							nextPlayer = false;
							break;
						}
					}
				} else {
					buttons.get(5).setText("O");
					randomButton(5);
					nextPlayer = true;
				}
				checkStrings();

			}

		} else if (e.getSource() == buttons.get(6)) {
			if (buttons.get(6).getText().isEmpty()) {
				if (nextPlayer == true) {
					buttons.get(6).setText("X");
					randomButton(6);
					if (allLength.length() < 8) {
						switch (mode) {
						case 'e':
							int index = easyMode();
							randomButton(index);
							break;
						case 'm':
							int MediumIndex = mediumMode();
							randomButton(MediumIndex);
							break;
						case 'h':
							if (allLength.length() == 1) {
								buttons.get(2).setText("0");
								randomButton(2);
							} else {
								int HardIndex = hardMode();
								randomButton(HardIndex);
							}
							break;
						default:
							nextPlayer = false;
							break;
						}
					}
				} else {
					buttons.get(6).setText("O");
					randomButton(6);
					nextPlayer = true;
				}
				checkStrings();

			}

		} else if (e.getSource() == buttons.get(7)) {
			if (buttons.get(7).getText().isEmpty()) {
				if (nextPlayer == true) {
					buttons.get(7).setText("X");
					randomButton(7);
					if (allLength.length() < 8) {
						switch (mode) {
						case 'e':
							int index = easyMode();
							randomButton(index);
							break;
						case 'm':
							int MediumIndex = mediumMode();
							randomButton(MediumIndex);
							break;
						case 'h':
							int HardIndex = hardMode();
							randomButton(HardIndex);
							break;
						default:
							nextPlayer = false;
							break;
						}
					}
				} else {
					buttons.get(7).setText("O");
					randomButton(7);
					nextPlayer = true;
				}
				checkStrings();

			}

		} else {
			if (buttons.get(8).getText().isEmpty()) {
				if (nextPlayer == true) {
					buttons.get(8).setText("X");
					randomButton(8);
					if (allLength.length() < 8) {
						switch (mode) {
						case 'e':
							int index = easyMode();
							randomButton(index);
							break;
						case 'm':
							int MediumIndex = mediumMode();
							randomButton(MediumIndex);

							break;
						case 'h':
							if (allLength.length() == 1) {
								buttons.get(0).setText("0");
								randomButton(0);
							} else {
								int HardIndex = hardMode();
								randomButton(HardIndex);
							}
							break;
						default:
							nextPlayer = false;
							break;
						}
					}
				} else {
					buttons.get(8).setText("O");
					randomButton(8);
					nextPlayer = true;
				}
				checkStrings();

			}

		}

	}

}
