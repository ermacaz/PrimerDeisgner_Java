/**
	Matt Hamada

	InvalidBaseException is thrown when the user enters a sequence which contains a base 
	other than A, G, C, or T
*/

public class InvalidBaseException extends Exception
{
	public InvalidBaseException()
	{
		super("Only A G C and T are acceptable bases.");
	}
}