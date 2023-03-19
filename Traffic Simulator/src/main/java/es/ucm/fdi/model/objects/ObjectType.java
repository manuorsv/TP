package es.ucm.fdi.model.objects;

/**
 * Enumerado para distinguir cada uno de los distintos objetos de la simulación.
 * 
 * @deprecated Pues no es necesario distinguir el tipo explícitamente entre las
 *             clases que heredan de SimObject.
 * @author Francisco Javier Blázquez Martínez
 * @version 02/05/18
 */
public enum ObjectType {
	VEHICLE, ROAD, JUNCTION, OBJECT_ERROR;

	/** Devuelve la representación escrita de los distintos objetos. */
	public String toString() {
		switch (this) {
			case VEHICLE :
				return "vehicle";
			case ROAD :
				return "road";
			case JUNCTION :
				return "junction";
			default :
				return ""; // Crear una excepción que se lance si no se pasa por
							// parámetro un objeto válido
		}
	}

	/**
	 * Dada una palabra devuelve el tipo del objeto que representa (no distingue
	 * mayúsculas y minúsculas).
	 */
	public static ObjectType objectType(String name) {
		name.toLowerCase();

		switch (name) {
			case "vehicle" :
				return ObjectType.VEHICLE;
			case "road" :
				return ObjectType.ROAD;
			case "junction" :
				return ObjectType.JUNCTION;
			default :
				return ObjectType.OBJECT_ERROR; // Lanzar aquí tambien la
												// excepción de objeto de tipo
												// inválido.
		}
	}
}
