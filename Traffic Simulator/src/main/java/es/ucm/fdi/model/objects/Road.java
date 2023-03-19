package es.ucm.fdi.model.objects;

import java.util.List;
import java.util.Map;
import es.ucm.fdi.util.MultiTreeMap;

/**
 * Representación y funcionalidad de una carretera en el simulador.
 * 
 * @author Francisco Javier Blázquez
 * @version Examen final 2017-18
 */
public class Road extends SimulatedObject 
{
	protected int longitud; 	  // Longitud de la carretera
	protected int maxVelocidad;   // Velocidad máxima de circulación 
	protected Junction cruceFin;  // Cruce en el que termina la carretera
	protected Junction cruceIni;  // Cruce de origen de la carretera
	protected MultiTreeMap<Integer, Vehicle> vehiculos; 

	public Road(String id, int maxSpeed, int size, Junction fin, Junction ini) {
		super(id);
		
		cruceFin = fin;
		cruceIni = ini;
		maxVelocidad = maxSpeed;
		longitud = size;
		vehiculos = new MultiTreeMap<Integer, Vehicle>((a, b) -> b - a);
	}

	public void avanza() 
	{
		if (vehiculos.sizeOfValues() > 0) 
		{
			MultiTreeMap<Integer, Vehicle> aux = new MultiTreeMap<>((a, b) -> b - a);
			int numAveriados = 0;

			for (Vehicle v : vehiculos.innerValues()) {
				if (v.getLocalizacion() != longitud){
					
					if (v.averiado())
						numAveriados++;
					else
						v.setVelocidadActual(velocidadAvance(numAveriados));

					v.avanza();
				}
				aux.putValue(v.getLocalizacion(), v);
			}

			vehiculos = aux;
		}
	}
	public int getLongitud() {
		return longitud;
	}
	public Junction getJunctionFin() {
		return cruceFin;
	}
	public Junction getJunctionIni() {
		return cruceIni;
	}
	public List<Vehicle> vehicles() {
		return vehiculos.valuesList();
	}
	protected void entraVehiculo(Vehicle vehicle) {
		vehiculos.putValue(0, vehicle);
	}
	public boolean saleVehiculo(Vehicle vehicle) {
		return vehiculos.removeValue(longitud, vehicle);
	}
	protected int velocidadAvance(int numAveriados) {
		int velocidadBase = Math.min(maxVelocidad,
				((int) (maxVelocidad / vehiculos.sizeOfValues())) + 1);

		if (numAveriados == 0)
			return velocidadBase;
		else
			return velocidadBase / 2;
	}
	
	//Para mostrar información al exterior (formato concreto)
	public String getHeader() {
		return "road_report";
	}
	public void fillReportDetails(Map<String, String> camposValor) {
		camposValor.put("state", vehiclesInRoad());
	}
	protected String vehiclesInRoad() {
		StringBuilder aux = new StringBuilder();

		for (Vehicle v : vehiculos.innerValues())
			aux.append(('(' + v.getId() + ',' + v.getLocalizacion() + "),"));

		String pal = aux.toString();

		if (pal.length() != 0)
			pal = pal.substring(0, pal.length() - 1);

		return pal;
	}
	public void describe(Map<String, String> out) {
		super.describe(out);
		
		out.put("Source", cruceIni.getId());
		out.put("Target", cruceFin.getId());
		out.put("Lenght", "" + longitud);
		out.put("Max Speed", "" + maxVelocidad);
		out.put("Vehicles", vehiclesInRoadDesc());
	}
	private String vehiclesInRoadDesc() {
		StringBuilder aux = new StringBuilder();

		for (Vehicle v : vehiculos.innerValues()) 
			aux.append('[' + v.getId() + ',' + v.getLocalizacion() + "],");

		String pal = aux.toString();
		
		if (pal.length() != 0) 
			pal = pal.substring(0, pal.length() - 1);
		
		return pal;
	}
}// Road
