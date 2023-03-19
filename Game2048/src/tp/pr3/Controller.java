package tp.pr3;

import java.util.Scanner;
import java.util.Random;
import tp.pr3.control.*;
import tp.pr3.control.commands.*;
import tp.pr3.rules.*;
import tp.pr3.exceptions.*;

public class Controller 
{
	private Game _juego;
	private Scanner _in;
	private boolean _generateNewCell;
	
	public Controller(Game juego, Scanner in)  					//Le asignamos a juego los datos ya obtenidos por teclado
	{
		_juego = juego;
		_in = in;
		_generateNewCell = false;
	}	
	/**Lee una cadena de texto, la parsea y ejecuta el comando*/
	public void run()											//Lee las órdenes por consola y las ejecuta
	{
		boolean printGame = true;
		Command command = null;
		String task; String[] commandInfoWords;
		
		do {
			if(printGame)	_juego.print();									//Mostramos el tablero 
			try{
			task = _in.nextLine();
			task = task.toLowerCase();
			commandInfoWords = task.split("\\s+");
			command = CommandParser.parseCommand(commandInfoWords, this);
			
			printGame = command.execute(_juego,  this);
			_juego.lostGame();
			_juego.youWin();
			}
			catch(NullParseException e){
				System.out.println(e);
				command = new HelpCommand();												//Quizá algo feo
			}
			catch(InvalidParameterException e){
				System.out.println(e);
				command = new HelpCommand();
			}
			catch(GameOverException e){
				_juego.print();
				System.out.println(e);
				command = new ExitCommand();
			}
			catch(WinGameException e){
				_juego.print();
				System.out.println(e);
				command = new ExitCommand();				
			}
			catch(NumberFormatException e) {
				System.out.println("The values must me numbers");
				command = new HelpCommand();
			}
			catch(NegativeArraySizeException e){
				System.out.println("The size of the board must be a positive number");
			}
		} while(command.getCommandText() != "exit");						
	}
	public boolean generateNewCell()
	{
		return _generateNewCell;
	}
	public void setGenerateNewCell(boolean bool)
	{
		_generateNewCell = bool;
	}
	public void cambiaJuego(GameType gameMode, int size, int initCells, int seed)
	{
		_juego = new Game(gameMode, size, initCells, seed);
	}
	public void setGame(Game juego)
	{
		_juego = juego;
	}

}
