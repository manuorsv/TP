package es.ucm.fdi.model.events;

import es.ucm.fdi.ini.IniSection;

/**
 * Clase encargada de, dada una sección de formato ini, devolver el evento que
 * representa o lanzar una excepción si no es de un tipo contemplado y
 * permitido.
 * 
 * @author Francisco Javier Blázquez
 * @version 23/03/18
 */
public class EventFactory {
	/**
	 * Lista de constructores para todos los posibles eventos contemplados en la
	 * simulación.
	 */
	private final static EventBuilder[] builder = {
			new NewJunction.NewJunctionBuilder(),
			new NewRoad.NewRoadBuilder(),
			new NewVehicle.NewVehicleBuilder(), 
			new NewRoundJunction.NewRoundJunctionBuilder(),
			new MakeVehicleFaulty.NewVehicleFaulty(),
			new NewBike.NewBikeBuilder(), 
			new NewCar.NewCarBuilder(),
			new NewPath.NewPathBuilder(), 
			new NewFreeway.NewFreewayBuilder(),
			new NewCrowedJunction.NewCrowedJunctionBuilder()};

	/**
	 * Dada una sección de tipo IniSection devuelve el evento que representa.
	 * 
	 * @param s
	 *            Sección a parsear.
	 * @throws IllegalArgumentException
	 *             Si no se consigue parsear la sección o se produce un error
	 *             parseando esta.
	 */
	public static Event buildEvent(IniSection s) throws IllegalArgumentException {
		Event event;

		for (EventBuilder e : builder) {
			// Parsea el evento con el correspondiente builder (puede lanzar excepcion)
			event = e.parse(s);

			// Si se consigue parsear correctamente lo devuelve
			if (event != null)
				return event;
		}

		// Si llega aquí es porque no ha podido parsear esta sección correctamente
		throw new IllegalArgumentException("No se ha podido parsear la sección:\n" + s.toString());
	}
}