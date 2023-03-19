package tp.pr3.control.commands;

import tp.pr3.*;
import tp.pr3.exceptions.*;
import java.io.*;

public class LoadCommand extends Command
{
	private String fileName;
	private boolean filename_confirmed;
	
	public LoadCommand(String name)
	{
		super("load", "let continue a saved game");
		fileName = name; filename_confirmed = false;
	}
	public boolean execute(Game game, Controller controller)
	{
		try
		{
			File fil = 			new File("partidas", fileName);
			FileReader file = 	new FileReader(fil);
			BufferedReader in = new BufferedReader(file);
			
			GameType aux = game.load(in); 
			
			if (aux!= null) 	{System.out.println("Game successfully loaded from file: 2048, " + GameType.toString(aux) + " version");}
			else				 System.out.println("Error al cargar la partida.");
			
			return true;
		} 
		catch (IOException ioe) 
		{
			return false;
		}
	}
	public Command parse(String[] commandWords, Controller controller) throws InvalidParameterException
	{
		if(commandWords[0].equals(commandName))
		{
			if(commandWords.length == 1)	throw new NullFileException();				//Nosotros nunca controlamos load <filename> <basura>
			else
			{
				if(MyStringUtils.validFileName(commandWords[1]))
				{
					File fichero = new File("partidas", commandWords[1]);
					
					if(fichero.exists()) return new LoadCommand(commandWords[1]);
					else				 throw  new NoExistFileException();
				}
				else 					 throw  new InvalidFileNameException();
			}
			
		}
		else	return null;
	}
	

}
