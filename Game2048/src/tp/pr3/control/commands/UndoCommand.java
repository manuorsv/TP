package tp.pr3.control.commands;

import java.util.EmptyStackException;
import tp.pr3.*;

public class UndoCommand extends NoParamsCommand
{
	public UndoCommand(){	super("undo", "undo the last command");}
	
	/**Ejecuta el comando Undo*/
	public boolean execute(Game game, Controller controller)
	{
		try{
			game.undo();
			return true;
		}
		catch(EmptyStackException e){
			System.out.println("Nothing to undo");
			return false;
		}
	}
}
