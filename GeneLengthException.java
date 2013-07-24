/**
	Matt Hamada

	GeneLengthException is thrown when a gene less than 10 bases is given.
*/

public class GeneLengthException extends Exception
{
	public GeneLengthException()
	{
		super("Gene must be at least 13 bases.");
	}
}