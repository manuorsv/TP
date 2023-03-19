package es.ucm.fdi.util;

/**
 * Clase con la funcionalidad necesaria para
 * 
 */
public class StringParser {
	public static int parseTime(String value) {
		if (value == null) {
			return 0;
		} else {
			int time = Integer.parseInt(value);

			if (time >= 0) {
				return time;
			} else {
				throw new IllegalArgumentException("El valor " + time + " no es válido.");
			}
		}
	}
	/**
	 * Comprueba si un id es un identificador válido o no.
	 * 
	 * @return True si el identificador está formado por caracteres
	 *         alfanuméricos o el carácter '_'.
	 */
	public static boolean isValidId(String id) {
		boolean valid = true;
		int i = 0;
		while (i < id.length() && valid) {
			if (!Character.isLetterOrDigit(id.charAt(i)) && id.charAt(i) != '_') {
				valid = false;
			}
			++i;
		}
		return valid;
	}
	/**
	 * Devuelve el id resultado de parsear un valor.
	 * 
	 * @throws IllegalArgumentException
	 *             si el id pasado no es válido.
	 */
	public static String parseId(String value) {
		if (value == null) {
			throw new IllegalArgumentException("Falta el id en la IniSection.");
		} else {
			if (isValidId(value)) {
				return value;
			} else {
				throw new IllegalArgumentException("El id " + value + " no es válido.");
			}
		}
	}
	/**
	 * Toma un string de ids consecutivos separados por comas y parsea estos.
	 * 
	 * @return La lista de identificadores parseada.
	 * @throws IllegalArgumentException
	 *             Si la sección es errónea o algún identificador es no válido.
	 */
	public static String[] parseIdList(String value) throws IllegalArgumentException {
		if (value == null) {
			throw new IllegalArgumentException(
					"Falta un atributo de lista en la IniSection");
		} else {
			String[] ids = value.split("\\,");
			for (int i = 0; i < ids.length; ++i) {
				if (!isValidId(ids[i])) {
					throw new IllegalArgumentException(
							"El id " + ids[i] + " no es válido.");
				}
			}
			return ids;
		}
	}
	/**
	 * Parsea un entero de un String asegurándose que es mayor o igual que cero.
	 * 
	 * @throws IllegalArgumentException
	 *             Si la sección es errónea o el valor del entero no es válido.
	 */
	public static int parseIntValue(String value) throws IllegalArgumentException {
		if (value == null) {
			throw new IllegalArgumentException("Falta un valor entero.");
		} else {
			int val = Integer.parseInt(value);
			if (val < 0) {
				throw new IllegalArgumentException(
						"El valor entero " + value + " no es válido.");
			} else {
				return val;
			}
		}
	}
	/**
	 * Parsea un double de un String asegurándose que es mayor o igual que cero
	 * y menor o igual que uno.
	 * 
	 * @throws IllegalArgumentException
	 *             Si la sección es errónea o el valor del entero no es válido.
	 */
	public static double parseDoubleValue(String value) throws IllegalArgumentException {
		if (value == null) {
			throw new IllegalArgumentException("Falta un valor double en la IniSection");
		} else {
			double val = Double.parseDouble(value);
			if (val < 0 || val > 1) {
				throw new IllegalArgumentException(
						"El valor " + value + " no es válido.");
			} else {
				return val;
			}
		}
	}
}
