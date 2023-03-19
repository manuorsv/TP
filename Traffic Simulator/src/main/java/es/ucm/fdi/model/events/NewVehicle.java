package es.ucm.fdi.model.events;

import java.util.ArrayList;
import java.util.Map;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.objects.Road;
import es.ucm.fdi.model.objects.RoadMap;
import es.ucm.fdi.model.objects.Vehicle;
import es.ucm.fdi.util.StringParser;

/**
 * Clase para los de eventos de creación de vehículos en el simulador.
 * 
 * @author Francisco Javier Blázquez
 * @version Examen final 2017-18
 */
public class NewVehicle extends Event {
	protected String vehicleId;    // Id del nuevo vehículo a generar
	protected int maxSpeed;        // Velocidad máxima del nuevo vehículo
	protected String[] itinerary;  // Ids de los cruces del itinerario

	public NewVehicle(int time, String vId, int mSpeed, String[] it) {
		super(time);
		
		vehicleId = vId;
		maxSpeed = mSpeed;
		itinerary = it;
	}

	public void describe(Map<String, String> out) {
		super.describe(out);
		out.put("Type", "New Vehicle " + vehicleId);
	}
	
	@Override
	public void execute(RoadMap map) throws IllegalArgumentException 
	{
		if (map.duplicatedId(vehicleId))
			throw new IllegalArgumentException("El id " + vehicleId + "está duplicado.");

		ArrayList<Road> itinerario = new ArrayList<>();
		for (int i = 1; i < itinerary.length; ++i)
		{
			Road road = map.getRoad(itinerary[i - 1], itinerary[i]);

			if (road == null)
				throw new IllegalArgumentException("El itinerario no es válido.");
			else
				itinerario.add(road);
		}
		
		map.addVehicle(new Vehicle(vehicleId, maxSpeed, itinerario));
	}
	
	public static class NewVehicleBuilder implements EventBuilder {
		protected final String TAG = "new_vehicle";

		protected int time;
		protected String id;
		protected int mSpeed;
		protected String[] it;

		public Event parse(IniSection sec) throws IllegalArgumentException {
			if (!sec.getTag().equals(TAG) || !esDeEsteTipo(sec))
				return null;

			       leerAtributosComunes(sec);
			return leerAtributosEspecificos(sec);
		}
		protected void leerAtributosComunes(IniSection sec) {
			time 	= StringParser.parseTime(sec.getValue("time"));
			id 		= StringParser.parseId(sec.getValue("id"));
			mSpeed 	= StringParser.parseIntValue(sec.getValue("max_speed"));
			it 		= StringParser.parseIdList(sec.getValue("itinerary"));
		}
		
		//Métodos específicos de cada tipo de vehículo (sobreescribir en hijos)
		protected boolean esDeEsteTipo(IniSection sec) {
			return sec.getValue("type") == null;
		}
		protected Event leerAtributosEspecificos(IniSection sec) {
			return new NewVehicle(time, id, mSpeed, it);
		}

	}
}
