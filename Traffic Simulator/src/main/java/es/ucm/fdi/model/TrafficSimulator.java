package es.ucm.fdi.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.events.EventFactory;
import es.ucm.fdi.model.objects.*;
import es.ucm.fdi.model.objects.Road;
import es.ucm.fdi.model.objects.RoadMap;
import es.ucm.fdi.model.objects.Vehicle;
import es.ucm.fdi.util.MultiTreeMap;

/**
 * Clase encargada del propio simulador de tráfico.
 */
public class TrafficSimulator 
{
	private static final int DEFAULT_SET_TIME = 0; 

	private MultiTreeMap<Integer, Event> listaEventos; 
	private List<Listener> observadores; 
	private RoadMap mapa;
	private int reloj; 
	

	/**
	 * Constructora usual. Crea un simulador vacío de objetos y de eventos.
	 */
	public TrafficSimulator() {
		reloj = DEFAULT_SET_TIME;
		mapa = new RoadMap();
		listaEventos = new MultiTreeMap<>();
		observadores = new ArrayList<>();
	}

	/**
	 * Inserta un evento en el simulador manteniendo la ordenación por tiempo y
	 * orden de insercción. Notifica esta insercción a los observadores.
	 * 
	 * @param evento
	 *            El evento que se inserta al simulador.
	 * @see #fireUpdateEvent(EventType, String)
	 */
	public void insertaEvento(Event evento) {
		listaEventos.putValue(evento.getTime(), evento);

		fireUpdateEvent(EventType.NEW_EVENT, null);
	}
	/**
	 * Ejecuta la simulación durante un número de pasos y escribe los datos e
	 * informes de esta en el flujo out. Tras esto se notifica a los
	 * observadores que el estado de la simulación ha cambiado.
	 * 
	 * @param numTicks
	 *            Numero de ticks que se ejecuta la simulación.
	 * @param out
	 *            Flujo de salida en el que escribir los informes.
	 * @see #fireUpdateEvent(EventType, String)
	 * @throws IllegalStateException
	 *             Si no se consigue ejecutar correctamente un evento o no se
	 *             puede escribir el informe de un paso de la simulación por el
	 *             flujo de salida.
	 */
	public void ejecuta(int numTicks, OutputStream out) {
		try {
			for (int i = 0; i < numTicks; ++i) {
				// 1. ejecutar los eventos correspondientes a ese tiempo
				if (listaEventos.get(reloj) != null) {
					for (Event e : listaEventos.get(reloj))
						e.execute(mapa);
				}

				// 2. invocar al método avanzar de las carreteras
				for (Road road : mapa.getRoads())
					road.avanza();

				// 3. invocar al método avanzar de los cruces
				for (Junction junc : mapa.getJunctions())
					junc.avanza();

				// 4. this.contadorTiempo++;
				reloj++;

				fireUpdateEvent(EventType.ADVANCED, null);

				// 5. esciribir un informe en OutputStream
				if (out != null)
					generaInforme(out);
			}
		} catch (Exception e) {
			fireUpdateEvent(EventType.ERROR, "No se pudo ejecutar un evento en el tiempo "
					+ reloj + " ticks:\n" + e.getMessage());
		}
	}
	/**
	 * Escribe en el flujo de salida un informe de la situación de todos los
	 * objetos en el instante de la llamada. Escribe primero los informes de los
	 * cruces, después los de las carreteras y por último los vehículos.
	 * 
	 * @param out
	 *            Flujo de salida para los datos.
	 * @throws IOException
	 *             Si no se consigue almacenar bien el informe generado.
	 */
	public void generaInforme(OutputStream out) {
		Ini ini = new Ini();
		
		// 1. Escribir el report de los cruces
		for (Junction junc : mapa.getJunctions())
			ini.addsection(seccionObjeto(reloj, junc));
		
		// 2. Escribir el report de las carreteras
		for (Road road : mapa.getRoads())
			ini.addsection(seccionObjeto(reloj, road));

		// 3. Escribir el report de los vehículos
		for (Vehicle car : mapa.getVehicles())
			ini.addsection(seccionObjeto(reloj, car));

		// 4. Almacenamos el informe de este paso de la simulación.
		try {
			ini.store(out);
		} catch (IOException e) {
			fireUpdateEvent(EventType.ERROR,"Error en el informe del tiempo " + reloj + ".\n");
		}
	}
	/**
	 * Pasa del report en mapa del objeto a formato IniSection.
	 */
	private IniSection seccionObjeto(int time, SimulatedObject simObject)
	{
		LinkedHashMap<String, String> reportMap = new LinkedHashMap<>();
		simObject.report(time, reportMap);
		
		IniSection sec = new IniSection(reportMap.get(""));
		reportMap.remove("");
		
		reportMap.forEach((key, value) -> sec.setValue(key, value));
		
		return sec;
	}
	/**
	 * Carga los eventos guardados en formato IniSection del inputStream y los
	 * inserta en el simulador.
	 * 
	 * @param inputStream
	 *            Flujo de entrada de los datos de la simulación.
	 * @throws IllegalStateException
	 *             Si no se consigue cargar alguno de los eventos.
	 */
	public void leerDatosSimulacion(InputStream inputStream) {
		try {
			// Cargamos todo el fichero en la variable ini 
			Ini ini = new Ini(inputStream);

			// Parseamos uno a uno los eventos de las secciones
			Event evento;

			for (IniSection s : ini.getSections()) {
				evento = EventFactory.buildEvent(s); 
				insertaEvento(evento);
			}
		} catch (IllegalArgumentException e) {
			fireUpdateEvent(EventType.ERROR,"Error al cargar uno de los eventos:\n" + e.getMessage());

		} catch (IOException e) {
			fireUpdateEvent(EventType.ERROR, "Error al leer el fichero de eventos.");
		}
	}
	/**
	 * Resetea el estado del simulador. No elimina los eventos cargados pero sí
	 * vuelve al instante inicial.
	 */
	public void reset() {
		reloj = DEFAULT_SET_TIME;
		mapa = new RoadMap();

		fireUpdateEvent(EventType.RESET, null);
	}

	// Para mvc
	/**
	 * Enumerado con todos los eventos que se contempla notificar a los
	 * observadores.
	 */
	public enum EventType {
		REGISTERED, RESET, NEW_EVENT, ADVANCED, ERROR;
	}
	/**
	 * Interfaz con los métodos que los observadores deben implementar para dar
	 * respuesta a las notificaciones del simulador.
	 */
	public interface Listener {
		void update(UpdateEvent ue, String error);
	}
	/**
	 * Clase que permite representar el estado del simulador para mandar este a
	 * los observadores.
	 */
	public class UpdateEvent {
		private EventType tipoEvento;

		public UpdateEvent(EventType tipo) {
			tipoEvento = tipo;
		}
		public EventType getType() {
			return tipoEvento;
		}
		public RoadMap getRoadMap() {
			return mapa;
		}
		public List<Event> getEventQueue() {
			return listaEventos.valuesList();
		}
		public int getCurrentTime() {
			return reloj;
		}
	}

	/**
	 * Permite añadir observadores al estado de la simulación.
	 */
	public void addSimulatorListener(Listener listener) {
		observadores.add(listener);

		listener.update(new UpdateEvent(EventType.REGISTERED), null);
	}
	/** Permite eliminar simuladores al estado de la simulación. */
	public void removeSimulatorListener(Listener listener) {
		observadores.remove(listener);
	}
	/**
	 * Método para notificar a los observadores que ha ourrido un evento de un
	 * tipo determinado y mostrarles el estado del simulador. Permitiendoles
	 * reaccionar a este evento como consideren más oportuno.
	 */
	private void fireUpdateEvent(EventType type, String error) {
		UpdateEvent ue = new UpdateEvent(type);

		for (int i = 0; i < observadores.size(); i++)
			observadores.get(i).update(ue, error);
	}

}// TrafficSimulator
