import java.lang.StringBuilder;

/**
	Matt Hamada
	
	This class encodes DNA objects. 
	Contains methods for reversing, complementing, parallel seq
*/

public class DNA
{
	private String sequence;

	//default constructor
	public DNA()
	{

	}

	/**
		constructor for assigning sequence
		@param newSequence sequence to assign instance object
	*/
	public DNA(String newSequence)
	{
		//set sequence to uppercase
		char[] newSequenceArray = newSequence.toCharArray();
		for (int i = 0; i < newSequenceArray.length; i++)
		{
			newSequenceArray[i] = Character.toUpperCase(newSequenceArray[i]);
		}

		newSequence = new String(newSequenceArray);
		sequence = newSequence;
	}



	/**
		getSequence accessor for sequence field
		@return sequence field of instance object
	*/
	public String getSequence()
	{
		return sequence;
	}

	/**
		setSequence mutator for sequence field
		@param newSequence sequence to assign instance object
	*/
	public void setSequence(String newSequence)
	{
		//set sequence to upper case
		char[] newSequenceArray = newSequence.toCharArray();
		for (int i = 0; i < newSequenceArray.length; i++)
		{
			newSequenceArray[i] = Character.toUpperCase(newSequenceArray[i]);
		}

		newSequence = new String(newSequenceArray);
		sequence = newSequence;
	}

	/**
		complement switches A and T, as well as G and C of a given sequence string
		@param sequence String of sequence
		@return String of sequence complement
	*/
	public static String complement(String sequence)
	{
		char[] sequenceArray = sequence.toCharArray();
		//convert A <--> T and C <--> G
		for (int i = 0; i < sequenceArray.length; i++)
		{
			sequenceArray[i] = Character.toUpperCase(sequenceArray[i]);
			if (sequenceArray[i] == 'A')
				sequenceArray[i] = 'T';
			else if (sequenceArray[i] == 'T')
				sequenceArray[i] = 'A';
			else if (sequenceArray[i] == 'C')
				sequenceArray[i] = 'G';
			else if (sequenceArray[i] == 'G')
				sequenceArray[i] = 'C';

		}
		sequence = new String(sequenceArray);
		return sequence;
	}

	/**
		Reverse return a given sequence string in reverse order
		@param sequence String of sequence
		@return String of reverse sequence
	*/
	public static String reverse(String sequence)
	{
		char[] sequenceArray = sequence.toCharArray();
		char[] returnArray = new char[sequenceArray.length];

		//reverse string
		int counter = 0;	//for counting up index
		for (int i = sequenceArray.length-1; i >= 0; i--)
		{
			returnArray[counter++] = sequenceArray[i];
		}

		sequence = new String(returnArray);
		return sequence;
	}

	/**
		Reverse return a given sequence string in reverse order and complemented
		@param sequence String of sequence
		@return String of parallel sequence
	*/
	public static String parallel(String sequence)
	{
		sequence = DNA.complement(sequence);
		sequence = DNA.reverse(sequence);
		return sequence;
	}

	/**
		gcFraction calculates the fraction of G and C bases 
		in the sequence
		@param sequence the DNA sequence
		@return a double of the fraction
	*/
	public static double gcFraction(String sequence)
	{
		int numGC = 0;
		char[] seqArray = sequence.toCharArray();
		//count g and c
		for (char base: seqArray)
		{
			if (base == 'C' || base == 'G')
				numGC++;
		}

		double ratio = numGC / (double)sequence.length();
		return ratio;
	}

	/**
		verifyAndClean will take an input sequence, remove all spaces and numbers,
		and capitalize bases.  Throws InvalidBaseException if it enounters
		a char that is not a g t c A G T C.
		@param gene A string of the raw sequence
		@return a String of the cleaned sequence
	*/
	public static String verifyAndClean(String gene) 
							throws InvalidBaseException, GeneLengthException
	{
		//convert to array to iterate over
		char[] geneArray = gene.toCharArray();

		//build clean version here
		StringBuilder cleanGene = new StringBuilder("");

		//clean sequence, throw InvalidBaseSequence if bad sequence entered
		for (int i = 0; i < gene.length(); i++)
		{
			switch(geneArray[i])
			{
				case 'a':
				case 'A':
					cleanGene.append("A");
					break;
				case 'g':
				case 'G':
					cleanGene.append("G");
					break;
				case 'c':
				case 'C':
					cleanGene.append("C");
					break;
				case 't':
				case 'T':
					cleanGene.append("T");
					break;
				case ' ':
				case '.':
				case '>':
				case ';':
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
				case '\t':
				case '\n':
					break;
				default:
					throw new InvalidBaseException();
			}			
		}
		if (cleanGene.length() < 13)
				throw new GeneLengthException();

		return cleanGene.toString();
	}



}