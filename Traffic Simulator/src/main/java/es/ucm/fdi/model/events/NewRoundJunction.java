package es.ucm.fdi.model.events;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.objects.Junction;
import es.ucm.fdi.model.objects.RoundJunction;
import es.ucm.fdi.util.StringParser;

/**
 * Clase encargada de encapsular toda la información y funcionalidad relativa al
 * evento del simulador de añadir un nuevo cruce circular avanzado, incluyendo
 * la propia construccción de este tipo de eventos.
 * 
 * @author Francisco Javier Blázquez Martínez
 * @version Examen final 2017-18
 */
public class NewRoundJunction extends NewJunction {
	private int minDurationVerde;
	private int maxDurationVerde;

	public NewRoundJunction(String id, int time, int minDurationVerde,
			int maxDurationVerde) {
		super(id, time);

		this.minDurationVerde = minDurationVerde;
		this.maxDurationVerde = maxDurationVerde;
	}

	@Override
	protected Junction construyeElemento() {
		return new RoundJunction(junction_id, minDurationVerde, maxDurationVerde);
	}

	public static class NewRoundJunctionBuilder extends NewJunction.NewJunctionBuilder {
		
		private int minDurationVerde;
		private int maxDurationVerde;

		@Override
		protected boolean esDeEsteTipo(IniSection sec) {
			return sec.getValue("type").equals("rr");
		}
		@Override
		protected Event leerAtributosEspecificos(IniSection sec) {
			minDurationVerde = StringParser.parseIntValue(sec.getValue("min_time_slice"));
			maxDurationVerde = StringParser.parseIntValue(sec.getValue("max_time_slice"));

			return new NewRoundJunction(id, time, minDurationVerde, maxDurationVerde);
		}
	}
}
