package minesweeper;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

public class StarterGUI extends JFrame implements ActionListener  {

	Container cp=getContentPane();
	JLabel rowLabel=new JLabel("Set the number of rows: ");
	JLabel columnLabel=new JLabel("Set the number of colums: ");
	JLabel mineLabel=new JLabel("Set the number of mines: ");
	
	SpinnerNumberModel modelForRow = new SpinnerNumberModel(5, 5, 20, 1); 
	SpinnerNumberModel modelForColumn = new SpinnerNumberModel(5, 5, 20, 1); 
	JSpinner rowSpinner=new JSpinner(modelForRow);
	JSpinner columnSpinner=new JSpinner(modelForColumn);
	
	SpinnerNumberModel modelForMines = new SpinnerNumberModel(5, 1, 20*20, 1); 
	JSpinner mineSpinner=new JSpinner(modelForMines);
	
	JButton startButton=new JButton("Start Game");
	JPanel topPanel=null;
	JPanel bottomPanel=null;
	public StarterGUI() {
		setupComponents();

//		spinner1.addChangeListener(listener);
		
		setupChangeListener();
		
		setTitle("Start Menu");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setSize(400,400);
		
		
	}
	
	

	private void setupChangeListener() {
		// TODO Auto-generated method stub
		
	}



	private void setupComponents() {
		topPanel=new JPanel(new GridLayout(3, 2));
		topPanel.add(rowLabel);
		topPanel.add(rowSpinner);
		topPanel.add(columnLabel);
		topPanel.add(columnSpinner);
		topPanel.add(mineLabel);
		topPanel.add(mineSpinner);
		bottomPanel=new JPanel(new BorderLayout());
		bottomPanel.add(startButton,BorderLayout.CENTER);
		cp.setLayout(new GridLayout(2,1));
		cp.add(topPanel);
		cp.add(bottomPanel);
		startButton.addActionListener(this);
		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==startButton) {
			int rowSpinnerValue=(int)rowSpinner.getValue();
			int columnSpinnerValue=(int)columnSpinner.getValue();
			int mineNumberLimit=(rowSpinnerValue*columnSpinnerValue);
			int mineSpinnerValue=(int)mineSpinner.getValue();

			if(mineSpinnerValue>mineNumberLimit) {
				JOptionPane.showMessageDialog(null, "You can't have more than "+mineNumberLimit+" mines!");
			}else {
				GUI startGame=new GUI(rowSpinnerValue, columnSpinnerValue,mineSpinnerValue);
				dispose();
			}
		}
		
	}
}
