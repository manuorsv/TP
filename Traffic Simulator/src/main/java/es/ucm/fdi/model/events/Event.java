package es.ucm.fdi.model.events;

import java.util.Map;

import es.ucm.fdi.model.Describable;
import es.ucm.fdi.model.objects.RoadMap;

/**
 * Da un contexto común a los distintos eventos que se pueden ejecutar en el
 * simulador.
 * 
 * @author Francisco Javier Blázquez
 * @version Examen final 2017-18
 */
public abstract class Event implements Describable 
{
	private int time; //Tiempo de ejecución del evento

	public Event(int time) {
		this.time = time;
	}
	
	public Integer getTime() {
		return time;
	}
	public void describe(Map<String, String> out) {
		out.put("Time", "" + time);
	}
	public abstract void execute(RoadMap mapa);
}
