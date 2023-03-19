package es.ucm.fdi.model.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.security.InvalidParameterException;

/**
 * Representación y funcionalidad de un vehículo básico en el simulador.
 * 
 * @author Francisco Javier Blázquez
 * @version Examen final 2017-18
 */
public class Vehicle extends SimulatedObject 
{
	protected ArrayList<Road> itinerario;  //Carreteras itinerario
	protected int indiceItinerario; 	   //Posicion en itinerario
	protected int localizacion; 		   //Dist origen carretera
	protected int kilometrage; 		       //Dist total recorrida
	protected int velActual; 			   //Velocidad actual
	protected int velMaxima; 			   //Velocidad máxima
	protected int tiempoAveria; 		   //Tiempo hasta repararse
	protected boolean enDestino;           //True si fin de trayecto
	

	public Vehicle(String id, int maxSpeed, List<Road> trayecto) {
		super(id);

		itinerario = (ArrayList<Road>) trayecto;
		velMaxima = maxSpeed;
		indiceItinerario = 0;
		kilometrage = 0;
		velActual = 0;
		tiempoAveria = 0;
		localizacion = 0;
		enDestino = false;
		
		//No contemplamos que exista un vehículo fuera de una carretera.
		actualRoad().entraVehiculo(this);
	}

	protected void avanza() {
		if (tiempoAveria > 0)	--tiempoAveria;
		
		else {
			// 1. Hacemos que propiamente avance en la carretera actual
			int aux = localizacion;
			localizacion += velActual;

			// 2. Si ha llegado al final de la carretera espera en el cruce
			if (localizacion >= actualRoad().getLongitud()) {
				localizacion = actualRoad().getLongitud();
				velActual = 0;
				actualRoad().getJunctionFin().entraVehiculo(this);
			}

			// 3. La distancia recorrida se suma a la total
			kilometrage += localizacion - aux;
		}
	}
	public void moverASiguienteCarretera() 
	{
		if (!actualRoad().saleVehiculo(this))
			throw new IllegalStateException("Error al cambiar de carretera el vehículo " + id + '.');

		velActual = 0;
		localizacion = 0;

		if (indiceItinerario + 1 < itinerario.size()) {
			++indiceItinerario;
			actualRoad().entraVehiculo(this);
		} else
			enDestino = true;
	}
	public Road actualRoad() {
		return itinerario.get(indiceItinerario);
	}
	public void setTiempoAveria(int tiempoAveria) {
		if (tiempoAveria > 0) {
			this.tiempoAveria += tiempoAveria;
			setVelocidadActual(0);
		}
		else if (tiempoAveria < 0)
			throw new InvalidParameterException("Tiempo de avería negativo no válido.");
	}
	public void setVelocidadActual(int nuevaVelocidad) {
		if (nuevaVelocidad < 0)
			throw new InvalidParameterException("Velocidad negativa no válida.");

		if (nuevaVelocidad <= velMaxima)
			velActual = nuevaVelocidad;
		else
			velActual = velMaxima;
	}
	public boolean averiado() {
		return tiempoAveria > 0;
	}
	public int getLocalizacion() {
		return localizacion;
	}
	
	//Para mostrar información al exterior (formato concreto)
	public String getHeader() {
		return "vehicle_report";
	}
	@Override
	public void fillReportDetails(Map<String, String> camposValor) {
		camposValor.put("speed", "" + velActual);
		camposValor.put("kilometrage", "" + kilometrage);
		camposValor.put("faulty", "" + tiempoAveria);
		camposValor.put("location", localizacionString());
	}
	protected String localizacionString() {
		return enDestino
				? "arrived"
				: "(" + actualRoad().getId() + "," + localizacion + ")";
	}

	@Override
	public void describe(Map<String, String> out) {
		super.describe(out);
		out.put("Road", actualRoad().getId());
		out.put("Location", "" + location());
		out.put("Speed", "" + velActual);
		out.put("Km", "" + kilometrage);
		out.put("Faulty Units", "" + tiempoAveria);
		out.put("Itinerary", itineraryDesc());
	}
	private String itineraryDesc() {
		String aux = "";
		aux += '[' + itinerario.get(0).getJunctionIni().getId();
		for (Road r : itinerario) {
			aux += ',' + r.cruceFin.getId();
		}
		aux += ']';

		return aux;
	}
	private String location() {
		if (enDestino) {
			return "arrived";
		} else {
			return "" + localizacion;
		}
	}

}// Vehicle
