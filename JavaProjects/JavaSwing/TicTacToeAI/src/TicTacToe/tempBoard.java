package TicTacToe;

import java.util.ArrayList;

import javax.swing.JButton;

// ideiglenes felület a medium es hard fokozathoz
// ugyanazon az elven ellenõriz mint a GUI osztályban, csak nincsenek konkret gombok
public class tempBoard {

	char[][] tempArray = new char[3][3];
	ArrayList<JButton> tempButtArray = new ArrayList<JButton>();
	ArrayList<String> tempStringArray = new ArrayList<String>();
	ArrayList<String> tempStringForButts = new ArrayList<String>();

	String topStringTemp = "";
	String midStringTemp = "";
	String botStringTemp = "";
	String firstCrossTemp = "";
	String secCrossTemp = "";
	String leftStringTemp = "";
	String middleStringTemp = "";
	String rightStringTemp = "";
	String allLengthTemp = "";

	public ArrayList<String> getTempStringForButts() {
		return tempStringForButts;
	}

	public void setTempStringForButts(ArrayList<JButton> tempStringForButts) {
		for (int i = 0; i < 9; i++) {
			this.tempStringForButts.add(tempStringForButts.get(i).getText());

		}
	}

	public void setTempStringForInsideStirng(ArrayList<String> tempStringForInsideString) {
		tempStringForButts = tempStringForInsideString;

	}

	public String getAllLengthTemp() {
		return allLengthTemp;
	}

	public void setAllLengthTemp(String allLengthTemp) {
		this.allLengthTemp = allLengthTemp;
	}

	ArrayList<String> addStringsToArray() {
		ArrayList<String> strings = new ArrayList<String>();
		strings.add(leftStringTemp);
		strings.add(middleStringTemp);
		strings.add(rightStringTemp);
		strings.add(topStringTemp);
		strings.add(midStringTemp);
		strings.add(botStringTemp);
		strings.add(firstCrossTemp);
		strings.add(secCrossTemp);

		return strings;

	}

	public char[][] getTempArray() {
		return tempArray;
	}

	public void setTempArray(char[][] tempArray) {
		this.tempArray = tempArray;
	}

	public ArrayList<JButton> getTempButtArray() {
		return tempButtArray;
	}

	public void setTempButtArray(ArrayList<JButton> tempButtArray) {
		this.tempButtArray = tempButtArray;
	}

	public ArrayList<String> getTempStringArray() {
		return tempStringArray;
	}

	public void setTempStringArray(ArrayList<String> tempStringArray) {
		this.tempStringArray = tempStringArray;
	}

	public ArrayList<String> addToStrings(int index, ArrayList<String> buttons, ArrayList<String> strings) {
		leftStringTemp = strings.get(0);
		middleStringTemp = strings.get(1);
		rightStringTemp = strings.get(2);
		topStringTemp = strings.get(3);
		midStringTemp = strings.get(4);
		botStringTemp = strings.get(5);
		firstCrossTemp = strings.get(6);
		secCrossTemp = strings.get(7);

		switch (index) {
		case 0:
			topStringTemp = topStringTemp.concat(buttons.get(0));
			firstCrossTemp = firstCrossTemp.concat(buttons.get(0));
			leftStringTemp = leftStringTemp.concat(buttons.get(0));
			allLengthTemp = allLengthTemp.concat(buttons.get(0));
			break;
		case 1:
			topStringTemp = topStringTemp.concat(buttons.get(1));
			middleStringTemp = middleStringTemp.concat(buttons.get(1));
			allLengthTemp = allLengthTemp.concat(buttons.get(1));
			break;
		case 2:
			topStringTemp = topStringTemp.concat(buttons.get(2));
			secCrossTemp = secCrossTemp.concat(buttons.get(2));
			rightStringTemp = rightStringTemp.concat(buttons.get(2));
			allLengthTemp = allLengthTemp.concat(buttons.get(2));
			break;
		case 3:

			midStringTemp = midStringTemp.concat(buttons.get(3));
			leftStringTemp = leftStringTemp.concat(buttons.get(3));
			allLengthTemp = allLengthTemp.concat(buttons.get(3));
			break;
		case 4:

			midStringTemp = midStringTemp.concat(buttons.get(4));
			firstCrossTemp = firstCrossTemp.concat(buttons.get(4));
			secCrossTemp = secCrossTemp.concat(buttons.get(4));
			middleStringTemp = middleStringTemp.concat(buttons.get(4));
			allLengthTemp = allLengthTemp.concat(buttons.get(4));
			break;
		case 5:

			midStringTemp = midStringTemp.concat(buttons.get(5));
			rightStringTemp = rightStringTemp.concat(buttons.get(5));
			allLengthTemp = allLengthTemp.concat(buttons.get(5));
			break;

		case 6:

			botStringTemp = botStringTemp.concat(buttons.get(6));
			secCrossTemp = secCrossTemp.concat(buttons.get(6));
			leftStringTemp = leftStringTemp.concat(buttons.get(6));
			allLengthTemp = allLengthTemp.concat(buttons.get(6));
			break;
		case 7:

			botStringTemp = botStringTemp.concat(buttons.get(7));
			middleStringTemp = middleStringTemp.concat(buttons.get(7));
			allLengthTemp = allLengthTemp.concat(buttons.get(7));
			break;

		case 8:

			botStringTemp = botStringTemp.concat(buttons.get(8));
			firstCrossTemp = firstCrossTemp.concat(buttons.get(8));
			rightStringTemp = rightStringTemp.concat(buttons.get(8));
			allLengthTemp = allLengthTemp.concat(buttons.get(8));
			break;
		}

		return addStringsToArray();

	}

}
