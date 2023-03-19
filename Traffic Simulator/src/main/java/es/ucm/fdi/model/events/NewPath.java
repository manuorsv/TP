package es.ucm.fdi.model.events;

import es.ucm.fdi.model.objects.Junction;
import es.ucm.fdi.model.objects.Path;
import es.ucm.fdi.model.objects.Road;
import es.ucm.fdi.model.objects.RoadMap;

import java.security.InvalidParameterException;

import es.ucm.fdi.ini.IniSection;

public class NewPath extends NewRoad {

	public NewPath(int time, String id, String src, String dest, int l, int mSpeed) {
		super(time, id, src, dest, l, mSpeed);
	}

	public void execute(RoadMap map) throws IllegalArgumentException 
	{
		// Cogemos el cruce de destino de la carretera
		Junction dest = map.getJunction(junctionDestId);
		Junction ini = map.getJunction(junctionIniId);
		
		if(dest == null || ini == null)
			throw new InvalidParameterException("Cruce no existente.");
		
		// Creamos la carretera, la añadimos al mapa y al cruce de destino
		Road road = new Path(road_id, maxSpeed, length, dest, ini);
		map.addRoad(road);
		dest.añadirCarreteraEntrante(road);
	}
	
	public static class NewPathBuilder extends NewRoadBuilder {

		protected boolean esDeEsteTipo(IniSection sec)
		{
			return sec.getValue("type").equals("dirt");
		}
		protected NewRoad concretarTipo(IniSection sec)
		{
			return new NewPath(time, id, origen, dest, roadSize, maxSpeed);
		}
	}
}
