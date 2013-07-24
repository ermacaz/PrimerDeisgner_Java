import java.util.ArrayList;
import java.lang.Math;

/**
	Matt Hamada
	
	This class contains methods needed for building ReEnzyme and primer lists 
	as well as comparing them.  The bulk of the programs algoithmic processes is found here
*/
public class ListCompare
{
	/**
		pairEnzymes takes in a list of ReEnzyme objects.
		These are assumed in 5'->3' order of multiple cloning site
		@return ArrayList of arraylists of reEnzyme pairs possible.
	*/
	public static ArrayList<ArrayList<ReEnzyme>> pairEnzymes(ArrayList<ReEnzyme> enzList)
	{

		//return arraylist of arraylists
		ArrayList<ArrayList<ReEnzyme>> returnList = new ArrayList<ArrayList<ReEnzyme>>();
		
		//temp arraylist for each pair to add to return list
		
		for (int i = 0; i < enzList.size(); i++)
		{
			for (int j = i; j < enzList.size(); j++)
			{
				ArrayList<ReEnzyme> temp = new ArrayList<ReEnzyme>();
				temp.add(enzList.get(i));
				temp.add(enzList.get(j));
				returnList.add(temp);
			}
		}
		return returnList;
	}

	/**
		pairPrimers takes in an insert sequence and list of enzyme pairs
		and returns a list of pairs of primers possible
		@param insert a DNA object containing the insert sequence
		@param enzymePairs an ArrayList of ArrayLists of ReEnzyme object pairs
		@return an ArrayList of Arraylist of Primer object pairs
	*/
	public static ArrayList<ArrayList<Primer>> 
		pairPrimers(DNA insert, ArrayList<ArrayList<ReEnzyme>> enzymePairs)
	{
		//create base of forward and reverse pimers based on insert sequence
		String seqForward = insert.getSequence().substring(0, 12);
		String seqReverse = DNA.parallel(insert.getSequence()).substring(0, 12);

		ArrayList<ArrayList<Primer>> returnList = new ArrayList<ArrayList<Primer>>();
		
		for (ArrayList<ReEnzyme> enzPair : enzymePairs)
		{
			ArrayList<Primer> temp = new ArrayList<Primer>();

			//will hold full sequence of primer. Initially has GC clap sequence
			StringBuilder pForwardSeq = new StringBuilder("");
			StringBuilder pReverseSeq = new StringBuilder("");

			//combine cutsequence, gcclamp, and base insert sequence.
			pForwardSeq.append(enzPair.get(0).getCutSeq() + "GCGC" + seqForward);
			pReverseSeq.append(enzPair.get(1).getCutSeq() + "GCGC" + seqReverse);
			Primer pForward = new Primer(pForwardSeq.toString(), Primer.Direction.FORWARD);
			Primer pReverse = new Primer(pReverseSeq.toString(), Primer.Direction.REVERSE);

			//add pair to list
			temp.add(pForward);
			temp.add(pReverse);
			returnList.add(temp);
		}

		return returnList;
	}

	/**
		tmPairs takes in an ArrayList of ArrayLists of primer pairs
		and returns a 2d array of tm doubles
		@param primerPairs an ArrayList of ArrayList of Primer object pairs
		@return A 2d array of doubles of each pair's tms.
	*/
	public static double[][] tmPairs(ArrayList<ArrayList<Primer>> primerPairs)
	{
		double[][] returnPairs = new double[primerPairs.size()][2];

		//build 2d array of primer pair tm's
		for (int i = 0; i < primerPairs.size(); i++)
		{
			returnPairs[i][0] = primerPairs.get(i).get(0).tmCalc();
			returnPairs[i][1] = primerPairs.get(i).get(1).tmCalc();
		}

		return returnPairs;
	}

	/**
		pickTmPair tests for the lowest  absolute difference between tm pairs.
		@param 2d double array of tm Pairs
		@param size integer of array size
		@return an integer of the best pair's index.
	*/
	public static int pickTmPair(double[][] tmPairs, int size)
	{
		double[] diff = new double[size];
		double[] best = new double[2];
		int bestIndex = 0;

		//calculate difference between each tm pair
		for (int i = 0; i < size; i++)
		{
			diff[i] = Math.abs(tmPairs[i][0] - tmPairs[i][1]);
		}

		//set first as best by default before compare
		best[0] = tmPairs[0][0];
		best[1] = tmPairs[0][1];
		//get lowest difference
		for (int i = 0; i < size-1; i++)
		{		
			if (diff[i] < diff[bestIndex])
			{
				best[0] = tmPairs[i][0];
				best[1] = tmPairs[i][1];
				bestIndex = i;
			}

		}
		//check last on list
		if (diff[size-1] < diff[bestIndex])
		{
			best[0] = tmPairs[size-1][0];
			best[1] = tmPairs[size-1][1];
			bestIndex = size-1;
		}

		return bestIndex;

	}
}