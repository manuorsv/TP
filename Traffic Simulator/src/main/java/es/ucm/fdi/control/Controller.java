
package es.ucm.fdi.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import es.ucm.fdi.model.TrafficSimulator;

/**
 * Clase para controlar la ejecución de la simulación.
 * 
 * @author Francisco Javier Blázquez Martínez
 * @version Examen final 2017-18
 */
public class Controller {
	
	private int ticksSimulacion; 		// Duración de la simulación
	private OutputStream outputStream; 	// Flujo de salida de reports
	private InputStream inputStream; 	// Flujo de entrada de datos para la
	private TrafficSimulator simulador; // Simulador a controlar
	private String inputPath;			// Ruta de entrada de datos


	public Controller(String loadFilePath, String saveFilePath, int numTicks) throws Exception {
		try {
			inputPath = loadFilePath;
			inputStream = new FileInputStream(new File(loadFilePath));
			outputStream = new FileOutputStream(new File(saveFilePath));
			simulador = new TrafficSimulator();
			ticksSimulacion = numTicks;
		} catch (Exception e) {
			throw new Exception("Error al crear el controlador.", e);
		}
	}
	public Controller(String loadFilePath, int numTicks) throws Exception {
		try {
			inputPath = loadFilePath;
			outputStream = null;
			simulador = new TrafficSimulator();
			ticksSimulacion = numTicks;
		} catch (Exception e) {
			throw new Exception("Error al crear el controlador.", e);
		}
	}

	public void leerDatosSimulacion() {
		try {
			simulador.leerDatosSimulacion(inputStream);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		simulador.ejecuta(ticksSimulacion, outputStream);
	}
	public TrafficSimulator simulador() {
		return simulador;
	}
	public void ejecutaKPasos(int k) {
		simulador.ejecuta(k, outputStream);
	}
	public OutputStream getOutputStream() {
		return outputStream;
	}
	public int getTicksSim() {
		return ticksSimulacion;
	}
	public String getInputPath() {
		return inputPath;
	}
	public void setOutputStream(OutputStream flujoEscritura) {
		outputStream = flujoEscritura;
	}
}