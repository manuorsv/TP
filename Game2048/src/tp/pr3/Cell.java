package tp.pr3;

import tp.pr3.rules.GameRules;

public class Cell 
{
	public static final int VACIA = 0;
	private int _valor;
	
	public Cell()
	{
		_valor = VACIA;
	}
	public Cell(int i)
	{
		_valor = i;
	}
	/**Cambia valor de celda*/
	public void modify(int valor) 	{_valor = valor;}					//Cambia el valor de la celda
	/**Devuelve el valor de celda*/
	public int getValue() 				{ return _valor;}					//Devuelve el valor de la celda	
	/**Devuelve si la celda esta vacia*/
	public boolean isEmpty()		{ return _valor == 0;}				//True si la celda vale 0, false en caso contrario
	/**Fusiona dos celdas si su valor coincide*/
	public int doMerge(Cell neighbour, GameRules rules)					//Colapsa con la celda vecina, es engullida por esta
	{																	
		return rules.merge(this, neighbour);	
	}
	public String toString()
	{
		return String.valueOf(_valor);
	}
}
