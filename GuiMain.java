import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.awt.GraphicsEnvironment;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
	Matt Hamada

	GuiMain contains the main function of the program.  Builds GUI.
	*/
public class GuiMain extends JFrame
{

	private EnzymePanel enzPanel;
	
	private GenePanel genePanel;		//holds gene, primers, tms
	
	private JPanel btnPanel;
	private JButton btnCalculate;		//calculate button

	private final int WINDOW_WIDTH = 700;
	private final int WINDOW_HEIGHT = 1000;


	/**
		Constructor.  Builds and shows GUI
	*/
	public GuiMain()
	{
		setTitle("Primer Designer");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocation(400,300);

		//build panel components
		enzPanel = new EnzymePanel();
		add(enzPanel, BorderLayout.WEST);

		genePanel = new GenePanel();
		add(genePanel, BorderLayout.EAST);

		//add calculate button
		btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnCalculate = new JButton("Calculate");
		btnCalculate.addActionListener(new CalcButtonListener());
		btnPanel.add(btnCalculate);

		add(btnPanel, BorderLayout.SOUTH);


		pack();
		setVisible(true);
	}

	private class CalcButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{			
			DNA insert;
			String gene = genePanel.getGeneSequence();
			//verify gene a valid sequence and format
			try
			{
				gene = DNA.verifyAndClean(gene);				
			}
			catch (InvalidBaseException ex)
			{
				JOptionPane.showMessageDialog(null, ex.getMessage());
				return;
			}
			catch(GeneLengthException ex)
			{
				JOptionPane.showMessageDialog(null, ex.getMessage());
				return;
			}
			insert = new DNA(gene);

			//check user has enzymes added to list
			if (enzPanel.getNumEnzymesChosen() == 0)
			{
				JOptionPane.showMessageDialog(null, "Error: must choose atleast one enzyme");
				return;
			}

			//get all enzyme pairs
			ArrayList<ArrayList<ReEnzyme>> enzPairs = ListCompare.pairEnzymes(
														enzPanel.getEnzymeList());
			//calculate all primer pairs
			ArrayList<ArrayList<Primer>> primPairs = ListCompare.pairPrimers(
														insert, enzPairs);
			//calculate all tm pairs
			double[][] tmPairs = ListCompare.tmPairs(primPairs);
			
			//index of best pair
			int index = ListCompare.pickTmPair(tmPairs, primPairs.size());

			//output to user
			DecimalFormat tmFormat = new DecimalFormat("##.##");
			genePanel.setForwardSequence(
				primPairs.get(index).get(0).getSequence());
			genePanel.setReverseSequence(
				primPairs.get(index).get(1).getSequence());
			genePanel.setForwardTm(
				tmFormat.format(tmPairs[index][0]));
			genePanel.setReverseTm(
				tmFormat.format(tmPairs[index][1]));
			genePanel.setForwardEnzyme(
				enzPairs.get(index).get(0).getName());
			genePanel.setReverseEnzyme(
				enzPairs.get(index).get(1).getName());
			
		}
	}

	
	

	public static void main(String[] args)
	{
		new GuiMain();
	}

}