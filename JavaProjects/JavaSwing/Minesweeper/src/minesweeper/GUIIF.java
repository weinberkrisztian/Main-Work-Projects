package minesweeper;

import java.util.Map;

import javax.swing.JButton;

public interface GUIIF {

	public void generateButtons(int row, int column, int numberOfMines);
	
	public  void addActionListenerOnButtons(JButton [][] buttonGroup);
	
	public void setupClickListener(int row, int column);
	
	public void setupMarkArray(int row, int column);
	
	public void setMinesArray(int row, int column,int numberOfMines);
}
