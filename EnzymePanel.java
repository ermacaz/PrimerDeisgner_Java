import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
/**
	Matt Hamada
	
	GUI component holding lists of enzyme library and user selection
*/

public class EnzymePanel extends JPanel
{
	private JList lstEnzLibrary;		//enzymes available
	private JScrollPane enzScroll;		
	private JList lstUserPicks;			//enymes user has picked
	private JScrollPane userScroll;		

	private JPanel addPanel;
	private JPanel remPanel;
	private JButton btnAdd;					//add enzyme to user pick
	private JButton btnRemove;				//remove enzyme from user pick

	ReEnzyme[] enzList;
	ReEnzyme[] userList;
	ArrayList<ReEnzyme> userArrayList = new ArrayList<ReEnzyme>();

	public EnzymePanel()
	{
		loadEnzymes();

		setLayout(new GridLayout(2, 2, 5, 10));
		//set up enzyme library list
		lstEnzLibrary = new JList(enzList);
		lstEnzLibrary.setSelectionMode(
			ListSelectionModel.SINGLE_SELECTION);
		lstEnzLibrary.setVisibleRowCount(8);
		lstEnzLibrary.setToolTipText("Restriction Enzyme library.");
		enzScroll = new JScrollPane(lstEnzLibrary);
		add(enzScroll);

		//add button for user to add enzyme selection
		addPanel = new JPanel();
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new AddListener());

		addPanel.add(btnAdd);
		add(addPanel);

		//set up user chosen list
		lstUserPicks = new JList();
		lstUserPicks.setSelectionMode(
			ListSelectionModel.SINGLE_SELECTION);
		lstUserPicks.setVisibleRowCount(8);
		lstUserPicks.setToolTipText("User selected restriction enzymes.");
		
		userScroll = new JScrollPane(lstUserPicks);
		userScroll.setPreferredSize(new Dimension(100, 150));
		add(userScroll);

		//add remove button
		remPanel = new JPanel();
		btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new RemoveListener());
		remPanel.add(btnRemove);
		add(remPanel);
	}

	private void loadEnzymes()
	{
		//catch if file not available
		try
		{
			//load enzymes from file
			File ENZ_FILE = new File("enzymes.csv");
			Scanner enzInput = new Scanner(ENZ_FILE);
			
			while (enzInput.hasNextLine())
			{ 
				String[] parseLine = enzInput.nextLine().split(",");
				char[] seqArray = parseLine[1].toCharArray();

				//verify sequence integrity incase user adds unsupported seq
				boolean valid = true;
				for (char base : seqArray)
				{
					switch (base)
					{
						case 'a':
						case 'A':
						case 'g':
						case 'G':
						case 'c':
						case 'C':
						case 'T':
						case 't':
							break;
						default:
							valid = false;
							break;
					}
				}

				if (valid)
				{
					ReEnzyme temp = new ReEnzyme(parseLine[0], parseLine[1]);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Enzyme " + parseLine[0] + " not suppored, ignoring.");
				}
			}

			//add to enzList for listbox
			enzList = new ReEnzyme[ReEnzyme.ENZLIBRARY.size()];
			for (int i = 0; i < ReEnzyme.ENZLIBRARY.size(); i++)
			{
				enzList[i] = ReEnzyme.ENZLIBRARY.get(i);
			}
		}
		catch (FileNotFoundException ex)
		{
			JOptionPane.showMessageDialog(null, "could not open file, aborting!");
			System.exit(1);
		}
		
	}

	/**
		getEnzymeList returns a list of all enzymes the user has chosen
		@return an ArrayList of ReEnzyme objects the user has chosen
	*/
	public ArrayList<ReEnzyme> getEnzymeList()
	{
		ArrayList<ReEnzyme> userEnzymes = new ArrayList<ReEnzyme>();

		for (int i = 0; i < lstUserPicks.getModel().getSize(); i++)
		{
			userEnzymes.add((ReEnzyme) lstUserPicks.getModel().getElementAt(i));
		}

		return userEnzymes;
	}

	/**
		getNumEnzymesChosen returns the number of enzymes in users list
		@return an integer of the number of enzymes in users list
	*/
	public int getNumEnzymesChosen()
	{
		return lstUserPicks.getModel().getSize();
	}

	/**
		AddLister is event which occurs when Add button clicked
	*/
	private class AddListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//get selected enzyme object
			ReEnzyme selectedEnzyme = (ReEnzyme) lstEnzLibrary.getSelectedValue();
			
			//avoid adding null selection to list
			if (selectedEnzyme == null)
			{
				return;
			}
			//create new list
			DefaultListModel newList = new DefaultListModel();

			//if list empty
			if (lstEnzLibrary.getModel().getSize() == 0)
			{	
				//add to blank list
				newList.addElement(selectedEnzyme);
			}
			else
			{
				//copy current list to new list
				ListModel currentList = lstUserPicks.getModel();

				for (int i = 0; i < currentList.getSize(); i++)
				{
					newList.addElement(currentList.getElementAt(i));
				}
				//then add new selection to end
				newList.addElement(selectedEnzyme);
			}
			//update listet
			lstUserPicks.setModel(newList);	
		}
	}

	private class RemoveListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int selectedItem = lstUserPicks.getSelectedIndex();	
			//current count
			int count = lstUserPicks.getModel().getSize();

			DefaultListModel newList = new DefaultListModel();
			for (int i = 0; i < count; i++)
			{
				if (i != selectedItem)
				{
					newList.addElement(lstUserPicks.getModel().getElementAt(i));
				}
			}
			lstUserPicks.setModel(newList);
		}
	}
	
}