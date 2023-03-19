package tp.pr3.exceptions;

public class NullFileException extends InvalidParameterException
{
	public NullFileException()
	{
		super("must be followed by a valid file name.");
	}
}
