package tp.pr3.rules;

import tp.pr3.*;
import java.util.Random;

public interface GameRules
{
	public int merge(Cell self, Cell other);
	public void actualizaBest(Board board, int candidato);
	public void addNewCellAt(Board board, Position pos, Random rand);
	public boolean win(Board board);
	public int getWinValue(Board board);
	public void setBest(Board board);
	default void actualizaBest(Board board)
	{
		Position pos = new Position(0,0);
		int maximo = board.getValue(pos);
		
		for(int i = 0; i < board.getSize(); i++)
		for(int j = 0; j < board.getSize(); j++)
		{
			pos.modify(i,j);
			
			if(board.getValue(pos) > maximo)
				maximo = board.getValue(pos);
		}
		
		board.setBest(maximo);
	}
	default void addNewCell(Board board, Random rand)		//No controla que no queden posiciones libres
	{
		Position pos = board.freePosition(rand);
		
		if(pos != null)
		{
			addNewCellAt(board, pos, rand);
			board.actualizaLibres();		
		}
	}
	default Board createBoard(int size)
	{
		return new Board(size);
	}
	default void initBoard(Board board, int initCells, Random rand)
	{
		int max = board.getSize(); max *= max; setBest(board);					//Para no meter más celdas que posiciones hay
		
		for(int i = 0; i < initCells && i < max; i++)
			addNewCell(board, rand);
	}
	default boolean lose(Board board)
	{
		return !board.freeCells() && !board.canMerge(this);
	}
	default boolean canMergeNeighbours(Cell cell1, Cell cell2) 
	{
		return cell1.getValue() == cell2.getValue();
	}
	
}
