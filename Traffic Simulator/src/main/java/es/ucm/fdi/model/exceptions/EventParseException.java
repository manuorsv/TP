package es.ucm.fdi.model.exceptions;

import es.ucm.fdi.ini.IniSection;

@SuppressWarnings("serial")
public class EventParseException extends Exception {
	public EventParseException() {

	}
	public EventParseException(IniSection s) {
		super("No se ha podido parsear la siguiente sección:\n" + s);
	}
}
