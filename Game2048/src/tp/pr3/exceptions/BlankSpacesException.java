package tp.pr3.exceptions;

public class BlankSpacesException extends InvalidParameterException
{
	public BlankSpacesException()
	{
		super("The file name contains blank spaces");
	}
}
