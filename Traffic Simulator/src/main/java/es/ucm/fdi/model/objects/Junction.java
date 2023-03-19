package es.ucm.fdi.model.objects;

import java.security.InvalidParameterException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representación y funcionalidad de un cruce en la simulación.
 * 
 * @author Francisco Javier Blázquez Martínez
 * @version Examen final 2017-18
 */
public class Junction extends SimulatedObject 
{	
	protected Map<String, ArrayDeque<Vehicle>> colas; 
	protected List<String> incomingRoadIds;
	protected int numCarreterasEntrantes; 
	protected int semaforo; 

	public Junction(String id) {
		super(id);
		colas = new HashMap<>();
		incomingRoadIds = new ArrayList<>();
		numCarreterasEntrantes = 0;
		semaforo = -1;
	}

	public void avanza() {
		if (numCarreterasEntrantes > 0)
		{
			if (semaforo == -1)
				inicializaSemaforo();

			if (colaEnVerde() != null && colaEnVerde().size() > 0) {
				colaEnVerde().pop().moverASiguienteCarretera();
				haPasadoVehiculo();
			}

			avanzarSemaforo();
		}
	}
	protected void haPasadoVehiculo() {
		;//Solo para los cruces avanzados
	}
	protected void inicializaSemaforo() {
		semaforo = numCarreterasEntrantes - 1;
	}
	protected void avanzarSemaforo() {
		semaforo = (semaforo + 1) % numCarreterasEntrantes;
	}
	protected ArrayDeque<Vehicle> colaEnVerde() {
		return colas.get(incomingRoadIds.get(semaforo));
	}
	public void entraVehiculo(Vehicle car) {
		
		if(!colas.containsKey(car.actualRoad().getId()))
			throw new InvalidParameterException("El coche no puede entrar al cruce.");
		
		colas.get(car.actualRoad().getId()).addLast(car);
		car.setVelocidadActual(0);
	}
	public void añadirCarreteraEntrante(Road road) 
	{
		incomingRoadIds.add(road.getId());
		colas.put(road.getId(), new ArrayDeque<>());
		numCarreterasEntrantes++;

		completarAñadirCarretera(road);
	}
	protected void completarAñadirCarretera(Road road) {
		;
	}
	
	//Para mostrar información al exterior (formato concreto)
	public String getHeader() {
		return "junction_report";
	}
	public void fillReportDetails(Map<String, String> camposValor) {
		camposValor.put("queues", colaCruce());
	}
	protected String colaCruce() {
		StringBuilder cola = new StringBuilder();

		for (int i = 0; i < incomingRoadIds.size(); i++) {
			cola.append(("(" + incomingRoadIds.get(i) + ","
					+ (i == semaforo ? "green" + fillColaDetails() : "red") + ",["
					+ vehiculosCola(i) + "]),"));
		}

		String aux = cola.toString();

		if (aux.length() > 0)
			aux = aux.substring(0, aux.length() - 1); // Eliminamos la ',' final

		return aux;
	}
	protected String fillColaDetails() {
		return "";
	}
	protected String vehiculosCola(int index) {
		String vehiculos = "";

		for (Vehicle v : colas.get(incomingRoadIds.get(index)))
			vehiculos += v.getId() + ",";

		if (vehiculos.length() > 0)
			vehiculos = vehiculos.substring(0, vehiculos.length() - 1); 

		return vehiculos;
	}
	public void describe(Map<String, String> out) {
		super.describe(out);
		out.put("Green", estadoVerde());
		out.put("Red", estadoRojo());
	}
	public String estadoVerde() {
		String aux = "";
		aux += "[";
		if (semaforo != -1) {
			aux += "(" + incomingRoadIds.get(semaforo) + ",green," + "["
					+ vehiculosCola(semaforo) + "])";
		}
		aux += "]";

		return aux;
	}
	private String estadoRojo() {
		String aux = "";
		aux += "[";
		for (int i = 0; i < semaforo; ++i) {
			aux += "(" + incomingRoadIds.get(i) + ",red," + "[" + vehiculosCola(i) + "]"
					+ "),";
		}
		for (int i = semaforo + 1; i < incomingRoadIds.size(); ++i) {
			aux += "(" + incomingRoadIds.get(i) + ",red," + "[" + vehiculosCola(i) + "]"
					+ "),";
		}
		if (aux.length() > 1)
			aux = aux.substring(0, aux.length() - 1);
		aux += "]";

		return aux;

	}

}// Junction
