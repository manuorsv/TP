package es.ucm.fdi.model.events;

import java.util.ArrayList;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.objects.Bike;
import es.ucm.fdi.model.objects.Road;
import es.ucm.fdi.model.objects.RoadMap;

public class NewBike extends NewVehicle {

	public NewBike(int time, String vId, int mSpeed, String[] it) {
		super(time, vId, mSpeed, it);
	}
	
	public void execute(RoadMap map) throws IllegalArgumentException {
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
		
		map.addVehicle(new Bike(vehicleId, maxSpeed,itinerario));
	}

	public static class NewBikeBuilder extends NewVehicleBuilder implements EventBuilder {

		@Override
		protected boolean esDeEsteTipo(IniSection sec) {
			return sec.getValue("type").equals("bike");
		}
		@Override
		protected Event leerAtributosEspecificos(IniSection sec) {
			return new NewBike(time, id, mSpeed, it);
		}
	}
}
