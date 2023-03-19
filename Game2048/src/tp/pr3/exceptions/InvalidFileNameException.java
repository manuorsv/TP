package tp.pr3.exceptions;

public class InvalidFileNameException extends InvalidParameterException
{
	public InvalidFileNameException()
	{
		super("invalid file name");
	}
}
