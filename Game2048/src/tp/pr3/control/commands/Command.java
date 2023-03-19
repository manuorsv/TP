package tp.pr3.control.commands;

import tp.pr3.*;
import tp.pr3.exceptions.*;

public abstract class Command {
	 private String helpText;
	 private String commandText;
	 protected final String commandName;
	 
	 public Command(String commandInfo, String helpInfo)
	 {
		 commandText = commandInfo;
		 helpText = helpInfo;
		 String[] commandInfoWords = commandText.split("\\s+");
		 commandName = commandInfoWords[0];
	 }
	 /**Realiza la ejecucion de un comando*/
	 public abstract boolean execute(Game game, Controller controller);
	 /**Realiza el parse de una cadena de texto con un comando*/ 
	 public abstract Command parse(String[] commandWords, Controller controller) throws InvalidParameterException;
	 /**Devuelve el mensaje de ayuda de un comando*/
	 public String helpText() 
	 {
		 return " " + commandText + ": " + helpText;
	 }
	 /**Devuelve el texto del comando*/
	 public String getCommandText(){	 return commandText;}
	 /**Devuelve el mensaje de ayuda del comando*/
	 public String getHelpText(){		return helpText;}
}
