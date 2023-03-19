package tp.pr3.control.commands;

import tp.pr3.*;

public class ExitCommand extends NoParamsCommand{
	public ExitCommand(){	super("exit", " terminate the program");}
	/**Ejecuta el comando exit*/
	public boolean execute(Game game, Controller controller)
	{
		game.exit();
		return false;
	}
}
