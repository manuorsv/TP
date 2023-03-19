package es.ucm.fdi.model.exceptions;

@SuppressWarnings("serial")
public class SimulationFailedException extends Exception {
	public SimulationFailedException() {
		super("The simulation failed.");
	}
	public SimulationFailedException(String msg) {
		super(msg);
	}
	public SimulationFailedException(String infile, String outfile, int time,
			Exception e) {
		super("Ha fallado la simulación con características:\n" + "-> tiempo: " + time
				+ "\n" + "-> fichero de entrada: " + infile + "\n"
				+ "-> fichero de salida: " + outfile + "\n" + "Motivo:\n"
				+ e.getMessage());
	}
}
