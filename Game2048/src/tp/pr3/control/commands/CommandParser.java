package tp.pr3.control.commands;

import tp.pr3.*;
import tp.pr3.exceptions.*;

public class CommandParser { 
	private static final int NUM_COMMANDS = 9; 	//Número total de comandos disponibles
	private static Command[] availableCommands = {  new HelpCommand(), 				  new ResetCommand(), new ExitCommand(), 
													new MoveCommand(Direction.ERROR), new UndoCommand(),  new RedoCommand(), 
													new PlayCommand(GameType.NONE),   new SaveCommand(""),new LoadCommand("")};
	/**Recorre el array de comandos haciendo un parse con cada comando. Si no lo encuentra devuelve null*/
	public static Command parseCommand(String[] commandWords, Controller controller) throws NullParseException, InvalidParameterException
	{
		Command commandAux=null;
		int i = 0;
		try{
		while(commandAux == null && i<NUM_COMMANDS){												//Recorre el array de comandos hasta que coincide con
			commandAux = availableCommands[i].parse(commandWords, controller);						//uno o se queda sin comandos.
			++i;
		}
		if(commandAux != null) return commandAux;
		else throw new NullParseException();
		}
		catch(InvalidParameterException e){
			throw e;
		}
	}
	/**Muestra el mensaje de ayuda de todos los comandos*/
	public static String commandHelp()
	{
		String helpInfo = "";
		for(int i=0; i<NUM_COMMANDS; ++i) helpInfo += availableCommands[i].helpText() + "\n";
		return helpInfo;
	}
}

