package es.ucm.fdi.model.events;

import static org.junit.Assert.*;
import org.junit.Test;
import es.ucm.fdi.ini.IniSection;

public class EventFactoryTest {
	@Test
	public void parseIniSectionTest() {

		// Prueba 1: NewRoad
		IniSection s1 = new IniSection("new_road");

		s1.setValue("time", "0");
		s1.setValue("id", "r1");
		s1.setValue("src", "j1");
		s1.setValue("dest", "j2");
		s1.setValue("max_speed", "50");
		s1.setValue("length", "200");

		Event e = EventFactory.buildEvent(s1);

		//assertEquals("No bien parseado prueba 1", s1, e.toIniSection());

		/*
		 * Prueba 2: NewJunction IniSection s2 = new IniSection("new_road");
		 * 
		 * s2.setValue("time", "0"); s2.setValue("id" , "r1");
		 * s2.setValue("src", "j1"); s2.setValue("dest", "j2");
		 * s2.setValue("max_speed" , "50"); s2.setValue("length", "200");
		 * 
		 * e = EventFactory.buildEvent(s2);
		 * 
		 * assertEquals("No bien parseado prueba 1", s2, e.toIniSection());
		 * 
		 * //Prueba 3: NewVehicle IniSection s3 = new IniSection("new_road");
		 * 
		 * s3.setValue("time", "0"); s3.setValue("id" , "r1");
		 * s3.setValue("src", "j1"); s3.setValue("dest", "j2");
		 * s3.setValue("max_speed" , "50"); s3.setValue("length", "200");
		 * 
		 * e = EventFactory.buildEvent(s3);
		 * 
		 * assertEquals("No bien parseado prueba 1", s3, e.toIniSection());
		 */
	}
}
