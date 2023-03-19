package tp.pr3.exceptions;

public class NoExistFileException extends InvalidParameterException
{
	public NoExistFileException()
	{
		super("El fichero no existe.");
	}
}
