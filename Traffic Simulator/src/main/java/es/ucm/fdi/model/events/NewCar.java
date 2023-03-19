package es.ucm.fdi.model.events;

import java.util.ArrayList;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.objects.Car;
import es.ucm.fdi.model.objects.Road;
import es.ucm.fdi.model.objects.RoadMap;
import es.ucm.fdi.util.StringParser;

public class NewCar extends NewVehicle {
	
	protected int resistance;
	protected double fault_probability;
	protected int max_fault_duration;
	protected long seed;

	public NewCar(int res, double fProb, int mFDur, long s, int time, String vId, int mSpeed, String[] it) {
		super(time, vId, mSpeed, it);
		
		resistance = res;
		fault_probability = fProb;
		max_fault_duration = mFDur;
		seed = s;
	}
	public NewCar(int res, double fProb, int mFDur, int time, String vId, int mSpeed, String[] it) {
		this(res, fProb, mFDur, System.currentTimeMillis(), time, vId, mSpeed, it);
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
		
		map.addVehicle(new Car(resistance, fault_probability, max_fault_duration, seed, vehicleId, maxSpeed,itinerario));
	}
	
	
	public static class NewCarBuilder extends NewVehicle.NewVehicleBuilder {

		protected boolean esDeEsteTipo(IniSection sec) {
			return sec.getValue("type").equals("car");
		}
		protected Event leerAtributosEspecificos(IniSection sec) 
		{
			int resist 	 = StringParser.parseIntValue(sec.getValue("resistance"));
			double fProb = StringParser.parseDoubleValue(sec.getValue("fault_probability"));
			int mFDur	 = StringParser.parseIntValue(sec.getValue("max_fault_duration"));

			if (sec.getValue("seed") != null) {
				long seed = Long.parseLong(sec.getValue("seed"));
				return new NewCar(resist, fProb, mFDur, seed, time, id, mSpeed, it);
			} else
				return new NewCar(resist, fProb, mFDur, time, id, mSpeed, it);
		}
	}
}
