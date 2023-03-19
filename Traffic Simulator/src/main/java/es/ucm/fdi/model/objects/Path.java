package es.ucm.fdi.model.objects;

import java.util.Map;

/**
 * Representación y funcionalidad de un camino en el simulador. Tipo de carretera
 * con restricciones de velocidad adicionales.
 * 
 * @author Francisco Javier Blázquez
 * @version Examen final 2017-18
 */
public class Path extends Road {

	public Path(String id, int maxSpeed, int size, Junction junc, Junction ini) {
		super(id, maxSpeed, size, junc, ini);
	}

	@Override
	public void fillReportDetails(Map<String, String> camposValor) {
		camposValor.put("type", "dirt");
		camposValor.put("state", vehiclesInRoad());
	}
	@Override
	public int velocidadAvance(int numAveriados) {
		return maxVelocidad / (1 + numAveriados);
	}
}
