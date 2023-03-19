package tp.pr3.rules;

import java.util.Random;
import tp.pr3.*;

public class RulesFib implements GameRules
{
	public static int STOP_VALUE = 144;
	public static int NEW_VALUE1 = 1;
	public static int NEW_VALUE2 = 2;
	private int[] v = {1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144};
	
	public int merge(Cell self, Cell other)
	{
		if(vecinos(self.getValue(), other.getValue()))
		{
			other.modify(self.getValue() + other.getValue());
			self.modify(0);
			return other.getValue();
		}
		else
			return 0;
	}
	public void actualizaBest(Board board, int candidato)
	{
		if(board.getBest() < candidato)		board.setBest(candidato);
	}
	public void addNewCellAt(Board board, Position pos, Random rand)
	{
		if(rand.nextDouble() < 0.9)		board.setCell(pos, NEW_VALUE1);
		else							board.setCell(pos, NEW_VALUE2);
	}
	public boolean win(Board board)
	{
		return board.getBest() == STOP_VALUE;
	}
	public int getWinValue(Board board)
	{
		return board.getBest();
	}
	public void setBest(Board board)
	{
		board.setBest(NEW_VALUE1);		
	}
	public boolean vecinos(int i, int j)
	{
		boolean encontrado = false; int k;
	
		for( k = 11; k >= 0 && !encontrado; k--)
			encontrado = (i == v[k]);
	
		return v[k+2] == j || v[k] == j;
	}
	public boolean canMergeNeighbours(Cell cell1, Cell cell2) 
	{
		return vecinos(cell1.getValue(), cell2.getValue());
	}
}
