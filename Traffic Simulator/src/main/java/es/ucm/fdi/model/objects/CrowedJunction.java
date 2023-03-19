package es.ucm.fdi.model.objects;

import java.util.Map;

import es.ucm.fdi.ini.IniSection;

/**
 * Representación y funcionalidad de un cruce avanzado en la simulación. El
 * tiempo que un semáforo está en verde se calcula para favorecer el rápido
 * tránsito en carreteras con una cola de vehículos grande.
 * 
 * @author Francisco Javier Blázquez Martínez
 * @version Examen final 2017-18
 */
public class CrowedJunction extends	Junction {
	
	private int tiempoConsumido; // Para controlar el numTicks que lleva en
									// verde el semáforo que permite el paso
	private int limiteDeTiempo; // Limite de tiempo para esta carretera para
								// este momento.

	/**
	 * Constructora usual.
	 * 
	 * @see Junction#Junction(String)
	 */
	public CrowedJunction(String id) {
		super(id);
		tiempoConsumido = 0;
		limiteDeTiempo = 1;
	}

	@Override
	public void inicializaSemaforo() {
		semaforo = numCarreterasEntrantes - 1;
		tiempoConsumido = 0;
	}
	@Override
	public void avanzarSemaforo() {
		tiempoConsumido++;

		if (tiempoConsumido == limiteDeTiempo) // Hay que hacer transición
		{
			int maximo = -1;
			int indiceMax = 0;

			for (int i = 0; i < incomingRoadIds.size(); i++)
				if (colas.get(incomingRoadIds.get(i)).size() > maximo) {
					maximo = colas.get(incomingRoadIds.get(i)).size();
					indiceMax = i;
				}

			if (maximo != 0)
				semaforo = indiceMax;

			else
				semaforo = (semaforo + 1) % numCarreterasEntrantes;

			tiempoConsumido = 0;
			limiteDeTiempo = Math.max(maximo / 2, 1);

		}
	}
	@Override
	public void fillReportDetails(Map<String, String> camposValor) {
		camposValor.put("queues", colaCruce());
		camposValor.put("type", "mc");
	}
	@Override
	protected String fillColaDetails() {
		return ":" + (limiteDeTiempo - tiempoConsumido);
	}

	@Override
	public String estadoVerde() {
		String aux = "";
		aux += "[";
		if (semaforo != -1) {
			aux += "(" + incomingRoadIds.get(semaforo) + ",green" + fillColaDetails()
					+ ',' + "[" + vehiculosCola(semaforo) + "])";
		}
		aux += "]";

		return aux;
	}

}// CrowedJunction
