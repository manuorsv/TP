package tp.pr3.control.commands;

import tp.pr3.*;

public abstract class NoParamsCommand extends Command
{
	public NoParamsCommand(String commandInfo, String helpInfo){	super(commandInfo, helpInfo);}
	/**Parsea el texto con el nombre del comando. Si no coincide devuelve null*/
	public Command parse(String[] commandWords, Controller controller) 
	{
		if(commandWords.length > 1) return null;
		else{
			if (commandWords[0].equals(commandName))	return this;
			else return null;
		}
	}
	public abstract boolean execute(Game game, Controller controller);
}
