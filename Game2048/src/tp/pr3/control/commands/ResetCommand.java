package tp.pr3.control.commands;

import tp.pr3.*;

public class ResetCommand extends NoParamsCommand
{
	public ResetCommand(){	super("reset", "start a new game");}
	/**Ejecuta el comando Reset*/
	public boolean execute(Game game, Controller controller)
	{
			game.reset();
			return true;
	}
}

