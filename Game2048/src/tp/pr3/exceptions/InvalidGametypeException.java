package tp.pr3.exceptions;

public class InvalidGametypeException extends InvalidParameterException{
	public InvalidGametypeException(){
		super("Unknown game type for play command");
	}
}
