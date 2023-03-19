package tp.pr3;

public class Position 
{
	private int _fila;
	private int _columna;
	
	public Position(int i, int j) 		{_fila = i; _columna = j;} 			//Construye el objeto de la clase Position con un valor de fila i y col j
	/**Devuelve la fila*/
	public int fil()		 			{ return _fila; }					//Dada una posición, devuelve la fila de esta
	/**Devuelve la columna*/
	public int col() 					{ return _columna;}					//Dada una posición, devuelve la columna de esta
	/**Suma uno a la fila y la columna*/
	public void add(int i, int j) 		{_fila += i; _columna +=j;}			//Suma a la fila i y a la columna j
	/**Modifica los valores de fila y columna*/
	public void modify(int i, int j)	{_fila = i;  _columna = j;}
	/**Devuelve la posicion vecina dentro del rango de la direccion*/
	public Position neighbour(int size, Direction dir)						//Devuelve la posicion vecina dentro del rango en funcion de la direccion
	{			
		int fila = _fila, columna = _columna;								//Evitamos modificar la posicion desde la que se llama
		
			switch (dir)													//No controla el caso de ERROR!
			{
			case UP:		if(_fila > 0)         	fila--;			break;
			case DOWN:		if(_fila < size-1)   	fila++;			break;
			case RIGHT:		if(_columna < size-1) 	columna++;		break;
			case LEFT:		if(_columna > 0)		columna--;		break;
			}
			
		return new Position(fila, columna);
	}
	/**Devuelve si dos posiciones son iguales*/
	public boolean equals(Position pos)
	{
		return pos.fil() == _fila && pos.col() == _columna;		
	}
}
