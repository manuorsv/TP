package tp.pr3.control.commands;

import tp.pr3.*;

public class HelpCommand extends NoParamsCommand 
{
	public HelpCommand(){	super("help", "print this help message");}
	/**Ejecuta el comando Help*/
	public boolean execute(Game game, Controller controller)
	{
		System.out.println(CommandParser.commandHelp());
		return false;
	}
}
