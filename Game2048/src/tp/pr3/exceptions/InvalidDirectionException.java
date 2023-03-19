package tp.pr3.exceptions;

public class InvalidDirectionException extends InvalidParameterException
{
	public InvalidDirectionException()
	{
		super("Dirección introducida NO válida.");
	}
}
