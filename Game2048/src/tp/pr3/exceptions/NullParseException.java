package tp.pr3.exceptions;

public class NullParseException extends Exception{
	public NullParseException(){
		super("WRONG instruction, type 'HELP' for help.");
	}
}
