package es.ucm.fdi.model.objects;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Encargada de gestionar todos los objetos del simulador. Cada uno de estos
 * con un identificador único.
 * 
 * OJO! Con esta implementación no se permite que haya más de una carretera 
 * que una dos cruces con el mismo sentido del tráfico.
 * 
 * @author Francisco Javier Blázquez
 * @version Examen final 2017-18
 */
public class RoadMap 
{
	private Map<String, SimulatedObject> simObjects; 
	private List<Junction> junctions; 
	private List<Road> roads; 
	private List<Vehicle> vehicles; 
	private Map<Junction, Map<Junction, Road>> possibleWays;
	//posibleWays nos ayuda a saber que cruces se comunican

	public RoadMap() 
	{
		simObjects = new HashMap<>();
		possibleWays = new HashMap<>();
		junctions = new ArrayList<>();
		roads = new ArrayList<>();
		vehicles = new ArrayList<>();
	}

	public boolean duplicatedId(String id) {
		return simObjects.containsKey(id);
	}
	public List<Road> getRoads() {
		return Collections.unmodifiableList(roads);
	}
	public List<Junction> getJunctions() {
		return Collections.unmodifiableList(junctions);
	}
	public List<Vehicle> getVehicles() {
		return Collections.unmodifiableList(vehicles);
	}
	public void addJunction(Junction junc) {
		simObjects.put(junc.getId(), junc);
		junctions.add(junc);
		
		Map<Junction, Road>	cruceCarretera = new HashMap<>();
		possibleWays.put(junc, cruceCarretera);
	}
	public void addRoad(Road road) {
		simObjects.put(road.getId(), road);
		roads.add(road);
		
		Junction cruceIni = road.getJunctionIni();
		Junction cruceFin = road.getJunctionFin();
		
		//Marcamos que es posible ir de un cruce a otro
		possibleWays.get(cruceIni).put(cruceFin, road);
	}
	public void addVehicle(Vehicle vehic) {
		simObjects.put(vehic.getId(), vehic);
		vehicles.add(vehic);
	}
	public Vehicle getVehicle(String id) {
		if (simObjects.get(id) instanceof Vehicle)
			return (Vehicle) simObjects.get(id);
		else
			return null;
	}
	public Junction getJunction(String id) {
		if (simObjects.get(id) instanceof Junction)
			return (Junction) simObjects.get(id);
		else
			return null;
	}
	public Road getRoad(String id) {
		if (simObjects.get(id) instanceof Road)
			return (Road) simObjects.get(id);
		else
			return null;
	}

	/** 
	 * @return La carretera que une ini y fin, null si no existe.
	 */
	public Road getRoad(Junction ini, Junction fin)
	{
		return possibleWays.get(ini).get(fin);
	}
	/** 
	 * @return La carretera que une ini y fin, null si no existe.
	 */
	public Road getRoad(String junctionIniId, String junctionFinId)
	{
		return getRoad(getJunction(junctionIniId), getJunction(junctionFinId));
	}

}
