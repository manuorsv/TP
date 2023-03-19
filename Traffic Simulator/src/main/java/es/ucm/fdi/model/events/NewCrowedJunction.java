package es.ucm.fdi.model.events;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.objects.CrowedJunction;
import es.ucm.fdi.model.objects.Junction;

/**
 * Clase encargada de encapsular toda la información y funcionalidad relativa al
 * evento del simulador de añadir un nuevo cruce congestionado avanzado,
 * incluyendo la propia construccción de este tipo de eventos.
 * 
 * @author Francisco Javier Blázquez
 */
public class NewCrowedJunction extends NewJunction {

	public NewCrowedJunction(String id, int time) {
		super(id, time);
	}

	@Override
	protected Junction construyeElemento() {
		return new CrowedJunction(junction_id);
	}

	public static class NewCrowedJunctionBuilder extends NewJunction.NewJunctionBuilder {
		@Override
		protected boolean esDeEsteTipo(IniSection sec) {
			return sec.getValue("type").equals("mc");
		}
		@Override
		protected Event leerAtributosEspecificos(IniSection sec) {
			return new NewCrowedJunction(id, time);
		}

	}
}
