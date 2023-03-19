package tp.pr3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;
import tp.pr3.rules.GameRules;

public class Board 
{
	private Cell[][] _board;
	private int _boardSize;
	private ArrayAsList _libres;
	private ArrayAsList _fusionadas;
	private int _best;
	
 	public Board(int size)								//Crea un tablero vacío (pero inicializado) de size x size. Inicializa el array de pos libres
	{
		_boardSize = size; _board = new Cell[size][size];
		_libres = new ArrayAsList(size*size); _best = 0;
		_fusionadas = new ArrayAsList(size*size/2);
		
		for (int i = 0; i < size; i++)
		for (int j = 0; j < size; j++)
				{_board[i][j] = new Cell(); 			
				 _libres.insert(i,j);}
	}
 	/**Establece value como valor de la celda de posicion pos*/
	public void setCell(Position pos, int value)		//Asigna a la celda en la posicion dada el valor dado
	{
		_board[pos.fil()][pos.col()].modify(value);
	}
	public int getValue(Position pos)						//Devuelve el valor de la celda en la posicion pos
	{
		return _board[pos.fil()][pos.col()].getValue();
	}
	public Cell[][] boardGame()
	{
		return _board;
	}
	/**Actualiza el array de posiciones libres recorriendo el tablero entero*/
	public void actualizaLibres()								//Actualiza el array _libres con posiciones libres del tablero
	{	
		_libres.setSize(0);
		
		for (int i = 0; i < _boardSize; i++)
		for (int j = 0; j < _boardSize; j++)
			if (_board[i][j].isEmpty()) 
				_libres.insert(i, j);
	}
	/**Elige una posicion aleatoria y le pone una celda con valor inicial dependiendo del juego*/
	public void newCell(Random random)					//Coloca en una casilla libre una nueva celda (2 o 4) 
	{
		Position aux = _libres.choice(random);
		
		if( random.nextDouble() < 0.9)
			_board[aux.fil()][aux.col()].modify(2);
		else
			_board[aux.fil()][aux.col()].modify(4);
	}
	/**Devuelve si una celda esta vacia*/
	public boolean isEmpty(Position pos)				//True si la celda de esa posicion es vacía NO CONTROLA RANGOS!!
	{
		return _board[pos.fil()][pos.col()].isEmpty();
	}
	/**Llama a doMerge de Cell y si las ha fusionado mete esa celda en el array de fusionadas y comprueba si
	 el resultado de la fusion es mejor valor. Devuelve si se ha realizado la fusion*/
	public int doMerge(Position pos1, Position pos2, GameRules rules)	//Fusiona y actualiza el mejor valor según las reglas del juego
	{
		int fusion = _board[pos1.fil()][pos1.col()].doMerge(_board[pos2.fil()][pos2.col()], rules);
		
		if (fusion != 0)
		{
			_fusionadas.insert(pos2.fil(), pos2.col());
			rules.actualizaBest(this, _board[pos2.fil()][pos2.col()].getValue());
		}
		
		return fusion;
	}
	/**Realiza el movimiento y fusion de una celda en una direccion*/
	public int moveMerge(Position pos,Direction dir, GameRules rules, Controller  controller)//Desplaza una celda y la fusiona si puede al llegar al final del trayecto
	{
		Position vecina = pos.neighbour(_boardSize, dir);   									//PASO DELICADO!! COMPROBAR
		int fusion = 0;
		
		if(!pos.equals(vecina)) 						//Si ella es su propia vecina es porque no hay nada que mover							
		{
			if(isEmpty(vecina))
			{
				controller.setGenerateNewCell(true);
				setCell(vecina, getValue(pos));						//Trasladamos a la celda vecina
				setCell(pos, 0);									//Liberamos la que se traslada
				fusion = moveMerge(vecina, dir ,rules, controller);	//Repetimos 	RECURSION!
			}
			else if (!fusionada(vecina))
			{
				fusion = doMerge(pos, vecina, rules);				
			}
		}
		
		return fusion;
	}
	/**Devuelve si una celda pertenece al array de fusionadas*/
	public boolean fusionada(Position pos)
	{
		return _fusionadas.pertenece(pos);
	}
	/**Realiza el movimiento de todas las celdas en una direccion*/
	public int ejecucionMovimiento(Controller controller, int i, int inci, int j, int incj, Direction dir, GameRules rules)
	{	
		Position pos = new Position(0,0);
		int movimiento = 0;
		_fusionadas.setSize(0);
		
		for (int k = i; rango(k); k+= inci)
		for (int l = j; rango(l); l+= incj )
			{
				pos.modify(k,l);
				
				if (!isEmpty(pos))
					movimiento += moveMerge(pos,dir, rules, controller);
			}
		
		if(movimiento > 0)					controller.setGenerateNewCell(true);
		if(controller.generateNewCell()) 	actualizaLibres();
		
		return movimiento;
	}
	/**Devuelve si un valor esta en el rango del tablero*/
	public boolean rango(int i)
	{
		return 0<= i && i <=_boardSize-1; 
	}
	/**Suma los valores del array de fusionadas para obtener la puntuacion del movimiento*/
	public int sumaFusionadas()
	{	
		int suma = 0;
		
		for (int i = 0; i<_fusionadas.getSize(); i++)
			suma += getValue(_fusionadas.getPosition(i));
		
		return suma;
	}
	/**Devuelve mejor valor*/
	public int getBest()
	{
		return _best;
	}
	/**Genera un String del tablero entero*/
	public String toString()
	{

		int cellSize = 7;
		String space = " ";
		String vDelimiter = "|";
		String hDelimiter = "-";
		String finalBoard="";;
		
		for(int i=0; i<_boardSize; ++i){
			finalBoard += space;
			for(int q=0; q<_boardSize-1; ++q){
				for(int p=0; p<cellSize; ++p){
					finalBoard += hDelimiter;
					}
			finalBoard += hDelimiter;
			}
			for(int p=0; p<cellSize; ++p)	finalBoard += hDelimiter;
			finalBoard += "\n";
			for(int j=0; j<_boardSize; ++j){
				finalBoard += vDelimiter;
			if(_board[i][j].getValue()!=0)	finalBoard += MyStringUtils.centre(String.valueOf(_board[i][j].getValue()), 7);
			else  finalBoard += MyStringUtils.centre(space, 7);
		}
			finalBoard += vDelimiter + "\n";
	}
		finalBoard += space;
		for(int q=0; q<_boardSize-1; ++q){
			for(int p=0; p<cellSize; ++p){
				finalBoard += hDelimiter;
				}
		finalBoard += hDelimiter;
		}
		for(int p=0; p<cellSize; ++p)	finalBoard += hDelimiter;
		
		return finalBoard;
	}
	/**Cambia el tablero por aState y el mejor valor de board por bestVal*/
	public void setState(Cell[][] aState, int bestVal)	//Cambia de estado del tablero
	{	
		_board = aState;
		_best = bestVal;
		actualizaLibres();
	} 	
	public int getSize()
	{
		return _boardSize;
	}
	public boolean freeCells()
	{
		return _libres.getSize() > 0;
	}
	public void setBest(int mejor)
	{
		_best = mejor;
	}
	public Position freePosition(Random rand)
	{
		if(_libres.getSize() > 0)
			return _libres.choice(rand);
		else
			return null;
	}
	public boolean canMerge(GameRules rules){
		int i=0;
		boolean merge = false;
		while(i<_boardSize && !merge){
			int j=0;
			while(j<_boardSize-1 && !merge){
				if(rules.canMergeNeighbours(_board[i][j], _board[i][j+1])) merge = true;
				else ++j;
			}
			++i;
		}
		int j = 0;
		while(j<_boardSize && !merge){
			i = 0;
			while(i<_boardSize-1 && !merge){
				if(rules.canMergeNeighbours(_board[i][j], _board[i+1][j])) merge = true;
				else ++i;
			}
			++j;
		}
		return merge;
	}
	public void store(BufferedWriter buffer) throws IOException
	{
		for(int i = 0; i < _boardSize; i++)
		{
			for (int j = 0; j < _boardSize; j++)
				buffer.write(_board[i][j].toString() + "	");
			buffer.newLine();
		}
	}
	public void load(BufferedReader in) throws IOException
	{
		String aux = in.readLine();
		String [] linea = aux.split("\t");
		
		_boardSize = linea.length;
		_board = new Cell[_boardSize][_boardSize];
		
		for(int i = 0; i < _boardSize; i++)
			_board[0][i] = new Cell(Integer.parseInt(linea[i]));
		
		_libres = new ArrayAsList(_boardSize*_boardSize);
		_fusionadas = new ArrayAsList(_boardSize*_boardSize/2);
		
		for(int i = 1; i < _boardSize; i++)
		{
			aux = in.readLine(); linea = aux.split("\t");
			
			for (int j = 0; j < _boardSize; j++)
				_board[i][j] = new Cell(Integer.parseInt(linea[j]));
		}
		//Aquí ya tenemos el tablero leido
		actualizaLibres();
	}
}