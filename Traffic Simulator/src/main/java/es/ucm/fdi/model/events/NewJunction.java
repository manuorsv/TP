package es.ucm.fdi.model.events;

import java.util.Map;

import es.ucm.fdi.model.objects.Junction;
import es.ucm.fdi.model.objects.RoadMap;
import es.ucm.fdi.util.StringParser;
import es.ucm.fdi.ini.*;

/**
 * Clase encargada de encapsular toda la información y funcionalidad relativa al
 * evento del simulador de añadir un nuevo cruce simple, incluyendo la propia
 * construccción de este tipo de eventos.
 * 
 * @author Francisco Javier Blázquez
 */
public class NewJunction extends Event {
	protected String junction_id;

	public NewJunction(String junctionId, int time) {
		super(time);
		junction_id = junctionId;
	}

	public void execute(RoadMap map) throws IllegalArgumentException {
		if (map.duplicatedId(junction_id))
			throw new IllegalArgumentException(
					"Ya existe un objeto con el id " + junction_id + '.');

		Junction junc = construyeElemento();
		map.addJunction(junc);
	}

	protected Junction construyeElemento() {
		return new Junction(junction_id);
	}

	public static class NewJunctionBuilder implements EventBuilder {
		// Atributos de junction, comunes a todos los cruces
		protected final String TAG = "new_junction";
		protected int time;
		protected String id;

		// Método general para construir los cruces
		public Event parse(IniSection sec) throws IllegalArgumentException {
			if (!sec.getTag().equals(TAG) || !esDeEsteTipo(sec))
				return null;

			leerAtributosComunes(sec);
			return leerAtributosEspecificos(sec);
		}
		public void leerAtributosComunes(IniSection sec) {
			time = StringParser.parseTime(sec.getValue("time"));
			id = StringParser.parseId(sec.getValue("id"));
		}

		// Métodos sobreescritos por los demás cruces
		protected boolean esDeEsteTipo(IniSection sec) {
			return sec.getValue("type") == null;
		}
		protected Event leerAtributosEspecificos(IniSection sec) {
			return new NewJunction(id, time);
		}
	}

	public void describe(Map<String, String> out) {
		super.describe(out);
		out.put("Type", "New Junction " + junction_id);
	}

}
