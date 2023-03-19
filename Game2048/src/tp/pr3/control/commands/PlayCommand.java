package tp.pr3.control.commands;

import java.util.Scanner;
import tp.pr3.*;
import tp.pr3.rules.*;
import tp.pr3.exceptions.*;

public class PlayCommand extends Command
{
	protected int randomSeed, boardSize, initialCells; 
	protected GameType gameType;

	public PlayCommand(GameType gameStile)
	{
		super("play", "changes the game rules");
		gameType = gameStile;
	}
	public boolean execute(Game game, Controller controller)
	{
			Scanner in = new Scanner(System.in); String aux;
			
			System.out.print("Please enter a size of the board: ");
			boardSize = leerIntConDefecto(in, 4);
			System.out.print("Please enter the number of initial cells: ");
			initialCells = leerIntConDefecto(in, 2);
			System.out.print("Please enter an initial seed for the pseudo-random number generator: ");
			randomSeed = leerIntConDefecto(in, 0);
			
			controller.cambiaJuego(gameType, boardSize, initialCells, randomSeed);
		
		return true;
	}
	public Command parse(String[] commandWords, Controller controller) throws InvalidParameterException
	{
		if (commandWords[0].equals(commandName))
		{
			if (commandWords.length == 1)	throw new NullGametypeException(); 
			else{
				switch(commandWords[1]){
				case "original": 	return new PlayCommand(GameType.gameMode(commandWords[1]));
				case "fib":			return new PlayCommand(GameType.gameMode(commandWords[1]));
				case "inverse": 	return new PlayCommand(GameType.gameMode(commandWords[1]));
				default: 			throw new InvalidGametypeException();
				}
			}
		}
		else return null;
	}
	public int leerIntConDefecto(Scanner in, int defecto)
	{
		String aux = in.nextLine();
		String [] auxV;
		
		if(aux.equals(""))
		{
			System.out.println("Introducida opción por defecto: " + String.valueOf(defecto));
			return defecto;
		}
		else
		{
			auxV= aux.split("\\s+");
			return Integer.parseInt(auxV[0]);
		}
	}
}
