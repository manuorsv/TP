package es.ucm.fdi.model.objects;

import java.util.Map;
import es.ucm.fdi.model.Describable;

/**
 * Clase que sirve de marco para todos los objetos de la simulación. Con su
 * identificador y su funcionalidad más básica.
 * 
 * @author Francisco Javier Blázquez Martínez.
 * @version Examen final 2017-18
 */
public abstract class SimulatedObject implements Describable 
{
	protected String id; 

	public SimulatedObject(String identificador) {
		id = identificador;
	}
	public String getId() {
		return id;
	}
	public void report(int time, Map<String, String> camposValor) {
		camposValor.put("", getHeader());
		camposValor.put("id", id);
		camposValor.put("time", Integer.toString(time));
		fillReportDetails(camposValor);
	}
	public void describe(Map<String, String> out) {
		out.put("ID", id);
	}
	
	//Para comunicar con el exterior (formato de la práctica)
	public abstract void fillReportDetails(Map<String, String> camposValor);
	public abstract String getHeader();
	
}// SimulatedObject
