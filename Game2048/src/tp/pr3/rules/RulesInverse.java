package tp.pr3.rules;

import java.util.Random;
import tp.pr3.*;

public class RulesInverse implements GameRules
{
	public static final int STOP_VALUE = 2;
	public static final int NEW_VALUE1 = 2048;
	public static final int NEW_VALUE2 = 1024; 
	
	public int merge(Cell self, Cell other)
	{
		if(self.getValue() == other.getValue())
		{
			other.modify((self.getValue() + other.getValue())/4);
			self.modify(Cell.VACIA);
			return 2048/other.getValue();
		}
		else
			return 0;
	}
	public void setBest(Board board)
	{
		board.setBest(NEW_VALUE1);
	}
	public void actualizaBest(Board board, int candidato)
	{
		if(board.getBest() > candidato)	board.setBest(candidato);
	}
	public void addNewCellAt(Board board, Position pos, Random rand)
	{
		if (rand.nextDouble() < 0.9)
			board.setCell(pos, NEW_VALUE1);
		else
			board.setCell(pos, NEW_VALUE2);
	}
	public boolean win(Board board)
	{
		return board.getBest()== STOP_VALUE;
	}
	public int getWinValue(Board board)
	{
		return board.getBest();
	}
	public void actualizaBest(Board board)
	{
		Position pos = new Position(0,0);
		int minimo = board.getValue(pos);
		
		for(int i = 0; i < board.getSize(); i++)
		for(int j = 0; j < board.getSize(); j++)
		{
			pos.modify(i,j);
			
			if(board.getValue(pos) < minimo)
				minimo = board.getValue(pos);
		}
		
		board.setBest(minimo);
	}
}
