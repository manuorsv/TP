package es.ucm.fdi.model.objects;

import java.util.Map;

/**
 * Representación y funcionalidad de un camino en el simulador. Tipo de carretera
 * con rango de velocidades ampliado.
 * 
 * @author Francisco Javier Blázquez
 * @version Examen final 2017-18
 */
public class Freeway extends Road 
{
	private int lanes;

	public Freeway(String id, int maxSpeed, int size, int lanes, Junction junc, Junction ini) {
		super(id, maxSpeed, size, junc, ini);
		this.lanes = lanes;
	}

	@Override
	public void fillReportDetails(Map<String, String> camposValor) {
		camposValor.put("type", "lanes");
		camposValor.put("state", vehiclesInRoad());
	}
	@Override
	public int velocidadAvance(int numAveriados) 
	{
		int factorDeReduccion = Math.max(1, vehiculos.sizeOfValues());
		int velocidadReducida = ((maxVelocidad * lanes) / factorDeReduccion) + 1;
	
		int velocidadBase = Math.min(maxVelocidad, velocidadReducida);

		if (numAveriados < lanes)
			return velocidadBase;
		else
			return velocidadBase / 2;
	}
}
