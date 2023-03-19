package es.ucm.fdi.model.events;

import es.ucm.fdi.model.objects.Freeway;
import es.ucm.fdi.model.objects.Junction;
import es.ucm.fdi.model.objects.Road;
import es.ucm.fdi.model.objects.RoadMap;

import java.security.InvalidParameterException;

import es.ucm.fdi.ini.IniSection;

public class NewFreeway extends NewRoad {

	private int lanes;

	public NewFreeway(int time, String id, String src, String dest, int l, int mSpeed,
			int nLanes) {
		super(time, id, src, dest, l, mSpeed);
		lanes = nLanes;
	}

	public void execute(RoadMap map) throws IllegalArgumentException {
		if (map.duplicatedId(road_id))
			throw new IllegalArgumentException("Ya existe un objeto con el id " + road_id);
		
		// Cogemos el cruce de destino de la carretera
		Junction dest = map.getJunction(junctionDestId);
		Junction ini = map.getJunction(junctionIniId);
		
		if(dest == null || ini == null)
			throw new InvalidParameterException("Cruce no existente.");
		
		// Creamos la carretera, la añadimos al mapa y al cruce de destino
		Road road = new Freeway(road_id, maxSpeed, length, lanes, dest, ini);
		map.addRoad(road);
		dest.añadirCarreteraEntrante(road);
	}
	
	
	public static class NewFreewayBuilder extends NewRoadBuilder
	{
		protected boolean esDeEsteTipo(IniSection sec)
		{
			return 	sec.getValue("type").equals("lanes");
		}
		protected NewRoad concretarTipo(IniSection sec)
		{
			int lanes = Integer.valueOf(sec.getValue("lanes"));
			
			return new NewFreeway(time, id, origen, dest, roadSize, maxSpeed, lanes);
		}
	}
}
