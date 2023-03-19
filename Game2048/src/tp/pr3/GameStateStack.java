package tp.pr3;

import java.util.*;

public class GameStateStack
{
	private static final int CAPACITY = 20;
	private GameState[] stateStack;
	private int _size;
	private int _lastIn;							//Última posición del array ocupada.
	
	public GameStateStack()
	{
		_size = 0;
		stateStack = new GameState[CAPACITY];
		_lastIn = 0;
	}
	/**Devuelve el array de estados*/
	public GameState[] getStack(){ 	return stateStack;}		//Accedentes
	/**Devuelve el tamaño del array*/
	public int getSize(){	return _size;}
	/**Devueve la ultima posicion ocupada*/
	public int getLastIn(){		return _lastIn;}
	/**Introduce un estado en el array*/
	public void push(GameState state)					//Introduce estado
	{
		if (isEmpty()){									//Si vacío lo mete en la primera pos
			stateStack[0] = state;
			_lastIn= 0 ;
			_size++;	
		}
		else if(_size < CAPACITY && _lastIn ==_size-1){		//En el caso de que el más antiguo esté en la pos 0
			++_lastIn;
			stateStack[_lastIn] = state;
			++_size;
		}
		else{
			if(_lastIn < CAPACITY-1){					//Si está lleno y la última pos ocupada no es la última lo mete en la siguiente
				++_lastIn;
				stateStack[_lastIn] = state;
				if(_size < CAPACITY) ++_size;			//Aumenta el tamaño si no estaba ya full*
			}
			else{										//Si es la última lo mete en la primera.
				_lastIn = 0;
				stateStack[_lastIn] = state;
				if(_size < CAPACITY) ++_size;			//*Igual
			}
		}
	}
	/**Devuelve el ultimo estado introducido*/
	public GameState pop(){		return stateStack[_lastIn];}
	/**Devuelve si el array esta vacio*/
	public boolean isEmpty()									//Comprueba si está vacío
	{
		if(_size == 0) return true;
		else return false;
	}
	/**Retrocede una posicion en el array (equivale a eliminar ese estado del array)*/
	public void goBack()								//Retrocede en el array (cuando se haga undo)
	{
		if(0<_lastIn){
			--_lastIn;
			--_size;
		}
		else{
			_lastIn = CAPACITY - 1;
			--_size;
		}
	}
	/**Vacia por completo el array de estados*/
	public void emptyStack()
	{
		_size= 0;
		_lastIn = 0;
	} 
}
