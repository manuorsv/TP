package es.ucm.fdi.model.events;

import java.security.InvalidParameterException;
import java.util.Map;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.objects.Junction;
import es.ucm.fdi.model.objects.Road;
import es.ucm.fdi.model.objects.RoadMap;
import es.ucm.fdi.util.StringParser;

public class NewRoad extends Event 
{
	protected String road_id;
	protected String junctionIniId;
	protected String junctionDestId;
	protected int length;
	protected int maxSpeed;

	public NewRoad(int time, String id, String iniId, String destId, int size, int mSpeed) 
	{
		super(time);
		
		road_id = id;
		junctionIniId = iniId;
		junctionDestId = destId;
		length = size;
		maxSpeed = mSpeed;
	}
	
	public String getTag() {
		return "new_road";
	}
	public void describe(Map<String, String> out) {
		super.describe(out);
		out.put("Type", "New Road " + road_id);
	}
	public void execute(RoadMap map) throws IllegalArgumentException 
	{
		if (map.duplicatedId(road_id))
			throw new IllegalArgumentException("Ya existe un objeto con el id " + road_id);

		// Cogemos el cruce de destino de la carretera
		Junction dest = map.getJunction(junctionDestId);
		Junction ini = map.getJunction(junctionIniId);
		
		if(dest == null || ini == null)
			throw new InvalidParameterException("Cruce no existente.");
		
		// Creamos la carretera, la añadimos al mapa y al cruce de destino
		Road road = new Road(road_id, maxSpeed, length, dest, ini);
		map.addRoad(road);
		dest.añadirCarreteraEntrante(road);
	}

	public static class NewRoadBuilder implements EventBuilder {
		
		protected int time;
		protected int maxSpeed;
		protected int roadSize;
		protected String origen;
		protected String dest;
		protected String id;
		
		public Event parse(IniSection sec) throws IllegalArgumentException 
		{
			if (!sec.getTag().equals("new_road") || !esDeEsteTipo(sec))
				return null;
		
			time 	= StringParser.parseTime(sec.getValue("time"));
			id		= StringParser.parseId(sec.getValue("id"));
			origen 	= StringParser.parseId(sec.getValue("src"));
			dest 	= StringParser.parseId(sec.getValue("dest"));
			maxSpeed= StringParser.parseIntValue(sec.getValue("max_speed"));
			roadSize= StringParser.parseIntValue(sec.getValue("length"));

			return concretarTipo(sec);
		}
		
		//Métodos específicos de cada tipo (sobreescribir los hijos)
		protected boolean esDeEsteTipo(IniSection sec)
		{
			return sec.getValue("type") == null;
		}
		protected NewRoad concretarTipo(IniSection sec)
		{
			return new NewRoad(time, id, origen, dest, roadSize, maxSpeed);
		}
	}	
}