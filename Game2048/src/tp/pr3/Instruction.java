package tp.pr3;

public enum Instruction 
{
	HELP, MOVE, RESET, EXIT, ERROR;
	
	public Instruction instruccion(String inst)
	{
		inst = inst.toLowerCase();
		
		if(inst.equals("help"))				return HELP;
		if(inst.equals("move"))				return MOVE;
		if(inst.equals("reset"))			return RESET;
		if(inst.equals("exit"))				return EXIT;
		else 								return ERROR;
	}
	//DEFINIDO EN CONTROLLER!!
}
