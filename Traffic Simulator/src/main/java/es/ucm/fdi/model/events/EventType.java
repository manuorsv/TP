package es.ucm.fdi.model.events;

/**
 * Enumerado que representa el tipo del evento.
 * 
 * @author Francisco Javier Blázquez
 * @version 23/03/18
 * @deprecated Cada evento tiene una clase distinta, no es necesaria la
 *             distinción con un enumerado.
 */
public enum EventType {
	NEW_JUNCTION, NEW_ROAD, NEW_VEHICLE, NEW_FAULT, EVENT_ERROR;

	/** Devuelve la representación escrita de los distintos eventos. */
	public String toString() {
		switch (this) {
			case NEW_JUNCTION :
				return "new_junction";
			case NEW_ROAD :
				return "new_road";
			case NEW_VEHICLE :
				return "new_vehicle";
			case NEW_FAULT :
				return "make_vehicle_faulty";
			default :
				return ""; // crear posible excepción para esto
		}
	}
	/**
	 * Devuelve el tipo de evento que representa la cadena parámetro (no
	 * distingue mayúsculas y minúsculas)
	 */
	public static EventType eventType(String name) {
		name.toLowerCase();

		switch (name) // Switch de String no vale en c++, testearlo
		{
			case "new_junction" :
				return NEW_JUNCTION;
			case "new_road" :
				return NEW_ROAD;
			case "new_vehicle" :
				return NEW_VEHICLE;
			case "make_vehicle_faulty" :
				return NEW_FAULT;
			default :
				return EVENT_ERROR; // conviene lanzar excepción
		}

	}
}
