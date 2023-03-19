package tp.pr3.control.commands;

import tp.pr3.*;
import java.io.*;
import java.util.Scanner;
import tp.pr3.exceptions.*;

public class SaveCommand extends Command
{
	private String fileName;
	private boolean filename_confirmed;
	public static String filenameInUseMsg = "The file already exists, do you want to overwrite it ? (Y/N)";

	public SaveCommand(String filename)
	{
		super("save", "Save your current game in the specified file");
		fileName = filename; filename_confirmed = false;
	}
	public Command parse(String[] commandWords, Controller controller) throws InvalidParameterException
	{
		if (commandName.equals(commandWords[0]))
		{
			if (commandWords.length == 1)
				throw new NullFileException(); // Extends InvalidParameterException
			else if (commandWords.length == 2)
			{
				String aux = confirmFileNameStringForWrite(commandWords[1], new Scanner(System.in)); //Suponemos que el monstruo funciona bien
				
				//No controlamos que aux sea un nombre posible de fichero porque si no lo fuera el monstruo de arriba lanzaría una excepcion de
				//tipo InvalidFileName y no seguiría la ejecución aquí porque se apaña en controller.
				return new SaveCommand(aux);
			}
			else
			{
				throw new BlankSpacesException();
			}
		} 
		else    return null;
	}
	public boolean execute(Game game, Controller controller)
	{
		try (BufferedWriter buffer = new BufferedWriter(new FileWriter( new File("partidas", fileName))))
		{
			game.store(buffer);
			System.out.println("Game successfully saved to file; use load command to reload it.");
			return true;
		}
		catch (IOException e)
		{
			return false;
		}
	}
	private String confirmFileNameStringForWrite(String filenameString, Scanner in) throws InvalidFileNameException
	{
		String loadName = filenameString;
		filename_confirmed = false;
		
		while (!filename_confirmed)
		{
			File file = new File("partidas", loadName);
			
			if (MyStringUtils.validFileName(loadName))
			{
				if (!file.exists())
					filename_confirmed = true;
				else
				{
					loadName = getLoadName(filenameString, in);
				}
			} 
			else
			{
				throw new InvalidFileNameException();
			}
		}
		return loadName;
	}
	public String getLoadName(String filenameString, Scanner in) throws InvalidFileNameException
	{
		String newFilename = null;
		boolean yesOrNo = false;
		
		while (!yesOrNo)
		{
			System.out.print(filenameInUseMsg + ": ");
			String[] responseYorN = in.nextLine().toLowerCase().trim().split("\\s+");
			if (responseYorN.length == 1)
			{
				switch (responseYorN[0])
				{
				case "y":
				{
					yesOrNo = true;
					newFilename = filenameString;
					filename_confirmed = true;
				}
					break;
				case "n":
				{
					yesOrNo = true;
					System.out.print("Introduzca un nuevo nombre para el fichero: ");
					newFilename = in.nextLine();		//No vacía el buffer
				}
					break;
				default:
				{
					System.out.println("Please answer 'Y' or 'N'");
				}
				}
			} else
			{
				System.out.println("Opcion introducida no válida");
			}
		}
		return newFilename;
	}

}
