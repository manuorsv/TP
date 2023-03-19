package tp.pr3.rules;

import java.util.Random;
import tp.pr3.*;

public class Rules2048 implements GameRules
{
	public static final int STOP_VALUE = 2048;
	public static final int NEW_VALUE1 = 2;
	public static final int NEW_VALUE2 = 4;

	public int merge(Cell self, Cell other)					//No controla si se puede hacer la fusión, solamente la hace
	{
		if(self.getValue() == other.getValue())
		{
			other.modify(2*other.getValue());
			self.modify(Cell.VACIA);
			return other.getValue();
		}
		else 
			return 0;
	}
	public void actualizaBest(Board board, int candidato)
	{
		if (candidato > board.getBest())
			board.setBest(candidato);
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
		return board.getBest() == STOP_VALUE;
	}
	public int getWinValue(Board board)
	{
		return board.getBest();
	}
	@Override
	public void setBest(Board board)
	{
		board.setBest(NEW_VALUE1);		
	}
}
