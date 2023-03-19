package tp.pr3;

public class MoveResults 
{
	private boolean _moved;
	private int _points;
	private int _maxTokens;
	private int _numMovs;
	
	public MoveResults()
	{
		_moved = false; _points = 0;
		_maxTokens = 0; _numMovs = 0;
	}
	public void sumaPuntos(int puntos)
	{
		_points += puntos;
	}
	public void maxTokens(int maximo)
	{
		_maxTokens = maximo;
	}
	public void movimiento(boolean mov)
	{
		_moved = mov;
	}
	public int highest() { return _maxTokens;}
	public int points()  { return _points;}

}
