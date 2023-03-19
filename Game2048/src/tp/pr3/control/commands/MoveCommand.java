package tp.pr3.control.commands;

import tp.pr3.*;
import tp.pr3.exceptions.*;

public class MoveCommand extends Command{
	private Direction moveDirection;
	
	public MoveCommand(Direction dirMove){	
		super("move",  "execute a move in one of the four directions, up, down, left, right");
		moveDirection = dirMove;
	}
	/**Hace parse con Move y si coincide compara el siguiente parametro para ver si es una direccion valida*/
	public Command parse(String[] commandWords, Controller controller) throws InvalidParameterException
	{
		if(commandWords[0].equals(commandName)){
			if(commandWords.length == 1){
				throw new NullDirectionException();
			}
			else{
			switch(commandWords[1])
			{
			case "up":		return new MoveCommand(Direction.UP);
			case "right":	return new MoveCommand(Direction.RIGHT);
			case "left":	return new MoveCommand(Direction.LEFT);
			case "down":	return new MoveCommand(Direction.DOWN);
			default:		throw new InvalidDirectionException();
			}
			}
		}
		else return null;
	}
	/**Ejecuta el comando Move*/
	public boolean execute(Game game, Controller controller)
	{
		game.move(moveDirection, controller);
		return true;
	}
}
