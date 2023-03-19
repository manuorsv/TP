package tp.pr3.control.commands;

import java.util.EmptyStackException;
import tp.pr3.*;

public class RedoCommand extends NoParamsCommand{
	public RedoCommand(){	super("redo", "redo the last undone command");}
	/**Ejecuta el comando Redo*/
	public boolean execute(Game game, Controller controller)
	{
		try{
			game.redo();
			return true;
		}
		catch(EmptyStackException e){
			System.out.println("Nothing to redo" + "\n");
			return false;
		}
	}
}