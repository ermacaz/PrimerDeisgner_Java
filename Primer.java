import java.util.HashMap;
import java.lang.Math;
/**
	Matt Hamada
	
	The primer class is a subset of the DNA class.
	This class contains information such as the direction of the primer (forward/reverse)
	as well as the melting temperature of the primer.

*/
public class Primer extends DNA
{
	//which position is the primer
	public static enum Direction {FORWARD, REVERSE};

	public Direction dir;

	/**
		Constructor for assigning sequence
		@param sequence primer's sequence
	*/
	public Primer(String seq, Direction d)
	{
		setSequence(seq);
		dir = d;
	}

	/**
		tmCalc calculates the melting temperature of the primer.  The calculations are based on equations found in
		SantaLucia,J et. al. Biochemistry, 1996, 35, 3555-3562
		Owczarzy,R et. al. Biochemistry, 2008, 47, 5336-5353 and
		Owczarzy,R et. al. Biochemistry, 2004, 43, 3537-3554

		First calculates tm for 1M Na+ (tmn) based on SantaLucia et. al.
		Then adjusts Tm based on [Na+] and [Mg2+] using methods found in
		Owczarzy et. al.
		@param sequence the sequence of the primer
		@return A double of the melting temperature.
	*/
	public double tmCalc()
	{
		//constants found in Owczarzy et. al.
		double mg = 0.0013; //1.3 mM Mg2+
		double na = 0.05;	//50 mM Na+
		double fgc = DNA.gcFraction(getSequence());
		double n = Math.log(na) / Math.log(2);	//needs log base 2
		double m = Math.log10(mg);
		//adjustment if sequence complements itsself
		double adjust = (getSequence() == DNA.complement(getSequence())) ? 1.0 : 4.0;

		//Tm at 1M Na+
		//from SantaLucia et. al. equation 11.
		double tmn = (getH()*1000) / 
			(getS() + (1.987 * Math.log(0.0000001 / adjust)));
		//inverse tmn and adjust temp per [Na+] and [Mg2+]
		//adjustment from Owczarzy et. al. 2004 equation 4.
		
		double tmInv = (1/tmn);
		double tm = tmInv + ((4.29*fgc) - 3.95) * Math.pow(10, -5) *
			n + (9.46 * Math.pow(10, -6)) * Math.pow(n, 2);

		//un-inverse with adjustments and convert from Kelvin to celcius
		double tmFinal = (1/tm) - 273.15;
		return tmFinal;

	}	

	//helper functions for tmCalc

	/**
		getH calculates the change in enthalpy
		of the primer when binding
		@param sequence the primer sequence
		@return A double of the change in enthalpy in kcal/mol
	*/
	private double getH()
	{
		double deltaH = 0.0;

		//hashmap of values per basepairs
		HashMap<String,Double> dHtable = new HashMap<String,Double>();
			dHtable.put("AA", -7.9);
			dHtable.put("AT", -7.2);
			dHtable.put("TA", -7.2);
			dHtable.put("CA", -8.5);
			dHtable.put("GT", -8.4);
			dHtable.put("CT", -7.8);
			dHtable.put("GA", -8.2);
			dHtable.put("CG", -10.6);
			dHtable.put("GC", -10.6);
			dHtable.put("GG", -8.0);
			dHtable.put("TT", -7.9);
			dHtable.put("AG", -7.8);
			dHtable.put("AC", -8.4);
			dHtable.put("TC", -8.2);
			dHtable.put("TG", -8.5);
			dHtable.put("CC", -8.0);

		//scan string to add dH value
		char[] seqArray = getSequence().toCharArray();
		String temp;
		for (int i = 0; i < getSequence().length()-1; i++)
		{
			char[] ctemp = { seqArray[i], seqArray[i+1] };
			temp = new String(ctemp);
			deltaH += dHtable.get(temp);
		}

		//adjust dH based on first and last bases of sequence
		if (seqArray[0] == 'A' || seqArray[0] == 'T')
			deltaH += 2.3;
		if (seqArray[getSequence().length()-1] == 'A' || 
			seqArray[getSequence().length()-1] == 'T')
			deltaH += 2.3;
		if (seqArray[0] == 'G' || seqArray[0] == 'C')
			deltaH += 0.1;
		if (seqArray[getSequence().length()-1] == 'G' || 
			seqArray[getSequence().length()-1] == 'C')
			deltaH += 0.1;

		return deltaH;
	}

	/**
		getS calculates the change in entropy
		of the primer when binding
		@param sequence the primer sequence
		@return A double of the change in entropyy in cal/mol
	*/
	private double getS()
	{
		double deltaS = 0.0;

		//hashmap of values per basepairs
		HashMap<String,Double> dStable = new HashMap<String,Double>();
			dStable.put("AA", -22.0);
			dStable.put("AT", -20.4);
			dStable.put("TA", -21.3);
			dStable.put("CA", -22.7);
			dStable.put("GT", -22.4);
			dStable.put("CT", -21.0);
			dStable.put("GA", -22.2);
			dStable.put("CG", -27.2);
			dStable.put("GC", -24.4);
			dStable.put("GG", -19.9);
			dStable.put("TT", -22.2);
			dStable.put("AG", -21.0);
			dStable.put("AC", -22.4);
			dStable.put("TC", -22.2);
			dStable.put("TG", -22.7);
			dStable.put("CC", -19.9);

		//scan string to add dH value
		char[] seqArray = getSequence().toCharArray();
		String temp;
		for (int i = 0; i < getSequence().length()-1; i++)
		{
			char[] ctemp = { seqArray[i], seqArray[i+1] };
			temp = new String(ctemp);
			deltaS += dStable.get(temp);
		}

		//adjust dH based on first and last bases of sequence
		if (seqArray[0] == 'A' || seqArray[0] == 'T')
			deltaS += 4.1;
		if (seqArray[getSequence().length()-1] == 'A' || 
			seqArray[getSequence().length()-1] == 'T')
			deltaS += 4.1;
		if (seqArray[0] == 'G' || seqArray[0] == 'C')
			deltaS += -2.8;
		if (seqArray[getSequence().length()-1] == 'G' || 
			seqArray[getSequence().length()-1] == 'C')
			deltaS += -2.8;
		if (getSequence().equals(DNA.reverse(getSequence())))
			deltaS += -1.4;

		return deltaS;
	}

}