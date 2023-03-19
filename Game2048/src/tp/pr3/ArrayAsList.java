package tp.pr3;

import java.util.Random;

public class ArrayAsList 
{
	private int _size;
	private Position[] _list;
						 											
	public ArrayAsList(int size) 									//OJO!! no inicializa el array de posiciones libres
	{
		_size = 0;	_list = new Position[size];
	}
	public void setSize(int size)   	{ _size = size; }				//Modifica el tamaño del vector de pos libres
	public int getSize()            	{ return _size; }				//Devuelve el numero de posiciones libres
	public Position getPosition(int n)  { return _list[n]; }			//OJO!! NO CONTROLA EXCEDERSE DEL RANGO
	/**Inserta una posicion en el array*/
	public void insert(int i, int j)								//Aumenta el tamaño y añade una nueva posicion libre
	{
		_list[_size] = new Position(i,j); _size++;
	}
	/**Mezcla aletoriamente los elementos del array*/
	public void shuffle(Random random)								//Baraja el array de posiciones libres
	{
		//Random random = new Random();								//Variable local o argumento?¿?¿
		
		for (int i = _size - 1; i > 1; i--)
			swap(i, random.nextInt(_size));						
	}
	/**Intercambia dos elementos del array*/
	public void swap(int i, int j)									//Intercambia _list[i] y _list[j]
	{
		Position aux = _list[i];
		_list[i] = _list[j];
		_list[j] = aux;
	}
	/**Obtiene una posición aleatoria del array y la devuelve*/
	public Position choice(Random random)							//Elige al azar una posicion libre. Random es 
	{
		int aux = random.nextInt(_size);
		Position pos = _list[aux];									//Toma una posicion libre aleatoria
		
		_list[aux] = _list[--_size];								//Reduce y actualiza el array 
		
		return pos;
	}
	/**Recorre el array hasta que encuentra la posicion o lo recorre entero. Si lo encuntra devuelve true, si lo recorre entero y no lo encuentra devuelve false*/
	public boolean pertenece(Position pos)
	{
		boolean pertenece = false;
		int i = 0;
		
		while (!pertenece && i < _size)
		{
			if (pos.equals(_list[i]))
				 pertenece = true;
			else i++;
		}
		
		return pertenece;
	}
}
