import java.util.ArrayList;
/**
	Matt Hamada 
	This class encodes restrictin enzyme objects.
	They contain cutsite sequence Strings, and name Strings.
	global arraylist ENZLIBRARY holds all ReEnzyme objects created
*/
public class ReEnzyme
{
	//will hold all ReEnzyme objects created
	public static ArrayList<ReEnzyme> ENZLIBRARY = new ArrayList<ReEnzyme>();

	private String name;
	private String cutSeq; 

	/**
		Constructor. Assigns name and cutseq.
		will add to global list
		@param n ReEnzyme name
		@param cs cut sequence
	*/
	public ReEnzyme(String n, String cs)
	{
		name = n;
		cutSeq = cs;
		//add to list
		ENZLIBRARY.add(this);
	}

	//accessors and mutators
	/**
		getName will return the name in string form
		@return a string of the ReEnzyme name
	*/
	public String getName()
	{
		return name;
	}

	/**
		getCutSeq will return a stirng of the cut sequence
		@return A string of the cutsequence
	*/
	public String getCutSeq()
	{
		return cutSeq;
	}

	/**
		setName will set name of reEnzyme object
		@param newName name of ReEnzyme object to be set.
	*/
	public void setName(String newName)
	{
		name = newName;
	}

	/**
		setCutSeq will set cutSeq of reEnzyme object
		@param newCutSeq cutSeq of ReEnzyme object to be set.
	*/
	public void setCutSeq(String newCutSeq)
	{
		cutSeq = newCutSeq;
	}

	/**
		toString will overwrite the default method
		will allow list n GUI to show enzyme names
	*/
	public String toString()
	{
		return name;
	}
}