package tp.pr3.exceptions;

public class NullGametypeException extends InvalidParameterException{
	public NullGametypeException(){
		super("play must be followed by one of the game types: original, inverse or fib");
	}
}
