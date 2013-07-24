import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
	Matt Hamada
	
	GUI component holding gene sequence, primers, and Tms
*/

public class GenePanel extends JPanel
{
	private JTextArea txtGeneSeq;			//holds gene sequence
	private JScrollPane geneScroll;
	private JPanel geneTextPanel;			//subpanel to hold text area
	private JPanel calculatedPanel;			//holds forward primers and tms
	private JPanel forwardPanel;			//sub panel for forward tm and primer
	private JPanel reversePanel;			//sub panel for reverse tm and primer
	private JLabel fPrimerLabel;			//label forward primer
	private JLabel rPrimerLabel;			//label reverse primer
	private JLabel fTmLabel;				//label forward tm
	private JLabel rTmLabel;				//label reverse tm
	private JLabel fEnzLabel;				//label forward enzyme
	private JLabel rEnzLabel;				//label reverse enzyme
	private JTextField txtpForward;			//calculated forward primer
	private JTextField txtTmForward;		//calculated forward Tm
	private JTextField txtEnzForward;		//forward enzyme chosen
	private JTextField txtpReverse;			//calculated reverse primer
	private JTextField txtTmReverse;		//calculated reverse Tm
	private JTextField txtEnzReverse;		//Reverse enzyme chosen

	/**
		Constructor to build part of GUI.  Adds text area for gene sequence
		and read only text fields for calculated primers and Tms
	*/
	public GenePanel()
	{
		setLayout(new BorderLayout());
		//create scrollable text area
		geneTextPanel = new JPanel();
		txtGeneSeq = new JTextArea(10, 50);
		txtGeneSeq.setLineWrap(true);
		txtGeneSeq.setFont(new Font("Monospaced", Font.PLAIN, 15));
		txtGeneSeq.setToolTipText("Enter gene sequence here.");
		txtGeneSeq.setText("Enter gene sequence here.");
		//allow for clearing help txt upon click
		txtGeneSeq.addMouseListener(new GeneSeqMouseListener());
		//txtGeneSeq.setPreferredSize(new Dimension(30, 150));
		geneScroll = new JScrollPane(txtGeneSeq);
		geneScroll.setVerticalScrollBarPolicy(
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		geneTextPanel.add(geneScroll);
		add(geneTextPanel, BorderLayout.NORTH);

		//add primer and tm text fields and labels
		// panel for each directions primer tm combo added to main panel
		calculatedPanel = new JPanel(new GridLayout(2, 1));
		forwardPanel = new JPanel();
		reversePanel = new JPanel();

		txtpForward = new JTextField(30);
		txtpForward.setEditable(false);
		txtpForward.setFont(new Font("Monospaced", Font.PLAIN, 15));
		fPrimerLabel = new JLabel("Forward: ");

		forwardPanel.add(fPrimerLabel);
		forwardPanel.add(txtpForward);

		txtTmForward = new JTextField(5);
		txtTmForward.setEditable(false);
		txtTmForward.setFont(new Font("Monospaced", Font.PLAIN, 15));
		fTmLabel = new JLabel("Tm (°C): ");
		forwardPanel.add(fTmLabel);
		forwardPanel.add(txtTmForward);

		fEnzLabel = new JLabel("Enzyme: ");
		txtEnzForward = new JTextField(8);
		txtEnzForward.setEditable(false);
		txtEnzForward.setFont(new Font("Monospaced", Font.PLAIN, 15));
		forwardPanel.add(fEnzLabel);
		forwardPanel.add(txtEnzForward);

		txtpReverse = new JTextField(30);
		txtpReverse.setEditable(false);
		txtpReverse.setFont(new Font("Monospaced", Font.PLAIN, 15));
		rPrimerLabel = new JLabel("Reverse: ");
		reversePanel.add(rPrimerLabel);
		reversePanel.add(txtpReverse);

		txtTmReverse = new JTextField(5);
		txtTmReverse.setEditable(false);
		txtTmReverse.setFont(new Font("Monospaced", Font.PLAIN, 15));
		rTmLabel = new JLabel("Tm (°C): ");
		reversePanel.add(rTmLabel);
		reversePanel.add(txtTmReverse);

		rEnzLabel = new JLabel("Enzyme: ");
		txtEnzReverse = new JTextField(8);
		txtEnzReverse.setEditable(false);
		txtEnzReverse.setFont(new Font("Monospaced", Font.PLAIN, 15));
		reversePanel.add(rEnzLabel);
		reversePanel.add(txtEnzReverse);

		calculatedPanel.add(forwardPanel);
		calculatedPanel.add(reversePanel);

		add(calculatedPanel, BorderLayout.CENTER);

	}

	/**
		getGeneSequence returns the text in the text area.
		@return The text area's text as a String
	*/
	public String getGeneSequence()
	{
		return txtGeneSeq.getText();
	}

	/**
		setForwardSequence displays text on the GUI
		@param sequence text to be displayed.
	*/
	public void setForwardSequence(String sequence)
	{
		txtpForward.setText(sequence);
	}

	/**
		setReverseSequence displays text on the GUI
		@param sequence text to be displayed.
	*/
	public void setReverseSequence(String sequence)
	{
		txtpReverse.setText(sequence);
	}

		/**
		setForwardTm displays text on the GUI
		@param Tm text to be displayed.
	*/
	public void setForwardTm(String tm)
	{
		txtTmForward.setText(tm);
	}

	/**
		setReverseTm displays text on the GUI
		@param Tm text to be displayed.
	*/
	public void setReverseTm(String tm)
	{
		txtTmReverse.setText(tm);
	}

		/**
		setForwardEnzyme displays text on the GUI
		@param enzyme text to be displayed.
	*/
	public void setForwardEnzyme(String enzyme)
	{
		txtEnzForward.setText(enzyme);
	}

	/**
		setReverseEnzyme displays text on the GUI
		@param enzyme text to be displayed.
	*/
	public void setReverseEnzyme(String enzyme)
	{
		txtEnzReverse.setText(enzyme);
	}

	private class GeneSeqMouseListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			if (txtGeneSeq.getText().equals("Enter gene sequence here."))
			{
				txtGeneSeq.setText("");
			}
		}
	}

}
