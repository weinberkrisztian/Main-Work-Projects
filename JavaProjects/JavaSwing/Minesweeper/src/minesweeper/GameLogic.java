package minesweeper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class GameLogic implements GameLogicIF {

	public static int checkMinesAround(int row, int column, JButton[][] buttonGroup, String[][] mineArray, int maxRow,
			int maxColumn) {
		int mineCount = 0;
		// top
		if (row > 0) {
			if (mineArray[row - 1][column] == GUI.X) {
				mineCount++;
			}
			if (column > 0) {
				if (mineArray[row - 1][column - 1] == GUI.X) {
					mineCount++;
				}
			}
			if (column < maxColumn-1) {
				if (mineArray[row - 1][column + 1] == GUI.X) {
					mineCount++;
				}
			}

		}

		// bottom
		if (row < maxRow-1) {
			if (mineArray[row + 1][column] == GUI.X) {
				mineCount++;
			}
			if (column > 0) {
				if (mineArray[row + 1][column - 1] == GUI.X) {
					mineCount++;
				}
			}
			if (column < maxColumn-1) {
				if (mineArray[row + 1][column + 1] == GUI.X) {
					mineCount++;
				}
			}

		}
		
		//leftSide
		if(column > 0) {
			if (mineArray[row][column-1] == GUI.X) {
				mineCount++;
			}
		}
		
		//rightSide
		if(column < maxColumn-1) {
			if (mineArray[row][column+1] == GUI.X) {
				mineCount++;
			}
		}
		return mineCount;

	}



}
