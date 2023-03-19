package tp.pr3;

import java.util.EmptyStackException;
import java.util.Random;
import java.util.Scanner;
import java.io.*;
import tp.pr3.control.commands.*;
import tp.pr3.control.*;
import tp.pr3.rules.*;
import tp.pr3.exceptions.*;

public class Game 
{
	private int _size, _initCells, _points;
	private long _seed;															//Guardamos la semilla para poder hacer un reset identico altablero inicial
	private Board _board;
	private static Random _random;												//static para que siempre que se use se modifique se llame desde donde sea
	private GameStateStack _undoStack; 
	private GameStateStack _redoStack;
	private GameRules _currentRules;
	private GameType _gameType;
	
	public Game(GameType gameMode, int size, int numIni, long seed)
	{
		_size = size; _seed = seed; _points = 0;
		_currentRules = GameType.rules(gameMode);	
		_initCells = numIni;
		_random = new Random(seed);
		_board = _currentRules.createBoard(size);
		_undoStack = new GameStateStack();
		_redoStack = new GameStateStack();
		_gameType = gameMode;
		
		_currentRules.initBoard(_board, numIni, _random);
	}
	/**Devuelve el board de game*/
	public Board boardGame()
	{
		return _board;
	}
	/**Muestra el estado acutual del tablero*/
	public void print()
	{
		System.out.println(_board.toString());
		System.out.print("best value: "); System.out.print(_board.getBest());
		System.out.print("   score: ");   System.out.println(_points);
	}
	/**Realiza el movimiento en una direccion y carga el array de undo, asi como sumar la puntuacion y establecer una nueva celda*/
	public void move(Direction dir, Controller controller)							
	{
		int move = 0; controller.setGenerateNewCell(false);						//False generar nueva celda significa que ni se ha movido ni se ha fusionado
		
		_undoStack.push(getState());
		
		switch (dir)
		{
		case UP:  	move = _board.ejecucionMovimiento(controller,1, 1, 0, 1, Direction.UP, _currentRules); 					break;
		case DOWN:	move = _board.ejecucionMovimiento(controller,_size-2, -1, 0, 1, Direction.DOWN, _currentRules);			break;
		case RIGHT: move = _board.ejecucionMovimiento(controller,0, 1, _size-2, -1, Direction.RIGHT, _currentRules);		break;
		case LEFT:	move = _board.ejecucionMovimiento(controller,0, 1, 1, 1, Direction.LEFT, _currentRules);				break;
		}
		
		_points += move;
		
		if(controller.generateNewCell())	{_currentRules.addNewCell(_board, _random); 
											 _redoStack.emptyStack();}
		else								 _undoStack.goBack();
	}
	/**Reinicia la partida*/
	public void reset()
	{
		_random = new Random(_seed);
		_board  = new Board(_size);
		_points = 0;
		_undoStack.emptyStack();
		_redoStack.emptyStack();
		
		for (int i = 0; i < _initCells; i++)
			_board.newCell(_random);
	}
	/**Acaba la partida*/
	public void exit()
	{
		System.out.println("Game Over  :(");
	}
	/**Comprueba si se ha perdido la partida*/
	public void lostGame() throws GameOverException
	{
		if(_currentRules.lose(_board)) throw new GameOverException();
	}
	/**Devuelve el estado actual de board*/
	public GameState getState()							//Crea un objeto GameState del estado actual
	{
		return new GameState(_board.boardGame(), _points, _board.getBest());
	}
	/**Cambia el estado de board*/
	public void setState(GameState aState)				//Establece el estado de tablero
	{
		_board.setState(aState.getState(), aState.getBest());
		_points = aState.getPoints();
	}
	/**Deshace un estado del tablero*/
	public void undo()	throws EmptyStackException						//Deshace el hacer 
	{
		if(_undoStack.getSize()>0){
			_redoStack.push(getState());
			setState(_undoStack.pop());
			_undoStack.goBack();
		}
		else {
			throw new EmptyStackException();
		}
	}
	/**Rehace un estado del tablero deshecho*/
	public void redo()	throws EmptyStackException							//Rehace el deshacer
	{
		if(0 < _redoStack.getSize()){
			_undoStack.push(getState());
			setState(_redoStack.pop());
			_redoStack.goBack();
		}
		else throw new EmptyStackException();
	}
	public void youWin() throws WinGameException
	{
		if(_currentRules.win(_board)) throw new WinGameException();
	}
	public void store(BufferedWriter buffer) throws IOException
	{
		buffer.write("This file stores a saved 2048 game");
		buffer.newLine(); 		buffer.newLine();
		_board.store(buffer);	buffer.newLine();
		buffer.write(String.valueOf(_initCells) + " " + String.valueOf(_points) + " " + GameType.toString(_gameType));
	}
	public GameType load(BufferedReader in) throws IOException
	{ 
		String aux = in.readLine();
		String [] aux2;
		
		if(aux.equals("This file stores a saved 2048 game"))
		{
			aux = in.readLine();
			_board.load(in);
			_size = _board.getSize();
			aux = in.readLine();
			aux = in.readLine();
			aux2 = aux.split("\\s+");
			_initCells = Integer.parseInt(aux2[0]);
			_points = Integer.parseInt(aux2[1]);
			_gameType = GameType.gameMode(aux2[2]);
			_currentRules = GameType.rules(_gameType);
			_currentRules.actualizaBest(_board);
			_undoStack.emptyStack();
			_redoStack.emptyStack();
		}
		
		return _gameType;		
	}
}
