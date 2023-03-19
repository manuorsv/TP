package tp.pr3;

import java.util.*;
import tp.pr3.control.commands.GameType;
import tp.pr3.rules.*;

public class Game2048 
{	
	public static void main(String[] args)
	{	
		Scanner in = new Scanner(System.in);
		Controller control; Game juego;
		try{
		int size = in.nextInt();												
		int numIni = in.nextInt();
		long seed = in.nextLong();
		String aux = in.nextLine();
		
		juego   = new Game(GameType.ORIG,size, numIni, seed);				
		control = new Controller(juego, in);								
			
		control.run();
		}
		catch(InputMismatchException e){
			System.out.println("The values must me numbers");
			e.printStackTrace();
		}
		catch(NegativeArraySizeException e){
			System.out.println("The size of the board must be a positive number");
			e.printStackTrace();
		}
	}
}
