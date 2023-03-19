package tp.pr3;

import tp.pr3.*;

public enum Direction 
{
	UP, DOWN, LEFT, RIGHT, ERROR;
	
	/**Parsea la direccion*/
	public Direction direccion(String dir)
	{
		dir = dir.toLowerCase();		//Transforma la cadena a minúsculas
		
		if (dir.equals("up"))		return Direction.UP;
		if (dir.equals("down"))		return Direction.DOWN;
		if (dir.equals("right"))	return Direction.RIGHT;
		if (dir.equals("left"))		return Direction.LEFT;
		else 						return Direction.ERROR;
	}
}
