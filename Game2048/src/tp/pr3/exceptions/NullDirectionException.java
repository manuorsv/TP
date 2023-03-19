package tp.pr3.exceptions;

public class NullDirectionException extends InvalidParameterException{
	public NullDirectionException(){
		super("move must be followed by one of the directions: up, down, left or right");
	}
}
