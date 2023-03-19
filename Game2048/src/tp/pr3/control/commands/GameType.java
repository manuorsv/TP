package tp.pr3.control.commands;

import tp.pr3.rules.*;

public enum GameType
{
	ORIG, FIB, INV, NONE;
	
	public static GameType gameMode(String aux)
	{
		aux.toLowerCase();
		
		switch(aux)
		{
		case "original": 	return ORIG;
		case "fib": 		return FIB;	
		case "inverse": 	return INV;	
		default: 			return NONE;
		}
	}
	public static GameRules rules(GameType gameMode)
	{
		switch(gameMode)
		{
		case ORIG:	return new Rules2048();
		case FIB:	return new RulesFib();
		case INV:	return new RulesInverse();
		default:	return null;
		}
	}
	public static String toString(GameType gameMode)
	{
		switch(gameMode)
		{
		case ORIG:	return "original";
		case FIB:	return "fib";
		case INV:	return "inverse";
		default:	return null;
		}
	}
}
