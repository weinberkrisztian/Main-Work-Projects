package minesweeper;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class GUI extends JFrame implements GUIIF, ActionListener,MouseListener{
	
	public JButton[][] buttonGroup=null;
	public String[][] mineArray=null;
	public String[][] markedArray=null;
	public Container cp=getContentPane();
	public static String X="X";
	public Integer maxRow=null;
	public Integer maxColumn=null;
	
	public GUI(int row, int column,int numberOfMines) {
		maxRow=row;
		maxColumn=column;
		setupMarkArray(maxRow, maxColumn);
		generateButtons(row, column,numberOfMines);
		addActionListenerOnButtons(buttonGroup);
		setupClickListener(maxRow, maxColumn);
		setTitle("Minesweeper by W.K.");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setSize(750, 750);
	}

	@Override
	public void generateButtons(int row, int column,int numberOfMines) {
		buttonGroup=new JButton[row][column];
		GridLayout gridLayout=new GridLayout(row, column);
		cp.setLayout(gridLayout);
		
		
		
		
		for (int i = 0; i < maxRow; i++) {
			for (int j = 0; j < maxColumn; j++) {
				buttonGroup[i][j]=new JButton();
				cp.add(buttonGroup[i][j]);
			}
		}
		
		setMinesArray(row, column,numberOfMines);
	}
	
	@Override
	public void setMinesArray(int row, int column,int numberOfMines) {
		mineArray=new String[row][column];
		if(numberOfMines != 0 && numberOfMines!=(row*column)) {
			int mineCounter=0;
			while (mineCounter!=numberOfMines) {
				int rowMine=(int) (Math.random() * row);
				int columnMine=(int) (Math.random() * column);
				if(mineArray[rowMine][columnMine]!=X) {
				mineArray[rowMine][columnMine]=X;
				mineCounter++;
				}
			}
		}else {
			System.out.println("nem jóó "+buttonGroup.length);
		}
	//	testMinePositions(row,column);
	}
	
	private void testMinePositions(int row,int column) {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
					if(mineArray[i][j]==X) {
//						buttonGroup[i][j].setText(X);
					System.out.println("Row: "+(i+1) +" Column: "+(j+1));
				}
			}
		}
	}
	
	@Override
	public void addActionListenerOnButtons(JButton[][] buttonGroup) {
		for (int i = 0; i < maxRow; i++) {
			for (int k = 0; k < maxColumn; k++) {
				buttonGroup[i][k].addActionListener(this);
				
			}
			
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < maxRow; i++) {
			for (int j = 0; j < maxColumn; j++) {
				if(e.getSource()==buttonGroup[i][j] && buttonGroup[i][j].getText()=="" && mineArray[i][j]!=X) {
					buttonGroup[i][j].setText(String.valueOf(GameLogic.checkMinesAround(i, j, buttonGroup, mineArray, maxRow, maxColumn)));
					if(checkWin()) {
						gameEnd("You win!");
					}
				}else if(e.getSource()==buttonGroup[i][j] && mineArray[i][j]==X) {
					for (int l = 0; l < maxRow; l++) {
						for (int k = 0; k < maxColumn; k++) {
							if(mineArray[l][k]==X) {
							buttonGroup[l][k].setText(X);
							buttonGroup[l][k].setBackground(Color.RED);
							}
						}
						
					}
					gameEnd("Game over!");
					
					
				}
			
			}
			
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	if(SwingUtilities.isRightMouseButton(e)) {
		for (int i = 0; i < maxRow; i++) {
			for (int j = 0; j < maxColumn; j++) {
				if(e.getSource()==buttonGroup[i][j] && buttonGroup[i][j].getText()=="") {
					buttonGroup[i][j].setText("?");
					markedArray[i][j]="?";
					buttonGroup[i][j].setBackground(Color.YELLOW);
				}else if(e.getSource()==buttonGroup[i][j] && buttonGroup[i][j].getText()=="?") {
					buttonGroup[i][j].setText("");
					markedArray[i][j]="";
					buttonGroup[i][j].setBackground(null);
				}
				
			}
		}
		if(checkWin()) {
			gameEnd("You win!");
		}
	}

		
		
	}

	private boolean checkWin() {
		boolean win=true;
		for (int i = 0; i < maxRow; i++) {
			for (int j = 0; j < maxColumn; j++) {
				if(mineArray[i][j]==X && win) {
					if(markedArray[i][j]!="?")
					win=false;
				}
			}
		}
		for (int i = 0; i < maxRow; i++) {
			for (int j = 0; j < maxColumn; j++) {
				if(buttonGroup[i][j].getText()=="") {
					win=false;
				}
			}
		}	
		
		return win;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setupClickListener(int maxRow, int maxColumn) {
		for (int i = 0; i < maxRow; i++) {
			for (int j = 0; j < maxColumn; j++) {
					buttonGroup[i][j].addMouseListener(this);
				}
			}
			
		}

	@Override
	public void setupMarkArray(int row, int column) {
		markedArray=new String[row][column];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
					markedArray[i][j]="";
				}
			}
		
	}
		
	private void gameEnd(String message) {
		setEnabled(false);
		JOptionPane.showMessageDialog(null, message);
		dispose();
		StarterGUI startGui=new StarterGUI();
	}



}
