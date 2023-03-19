package es.ucm.fdi.model.events;

import es.ucm.fdi.ini.IniSection;

/**
 * Interfaz que implementan todos los eventos del simulador para poder
 * ser parseados a partir de una sección de formato ini.
 * 
 * @author Francisco Javier Blázquez Martínez
 * @version Examen final 2017-18
 */
public interface EventBuilder 
{
	public Event parse(IniSection sec);
}
