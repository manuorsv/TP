package es.ucm.fdi.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.events.EventBuilder;
import es.ucm.fdi.model.events.MakeVehicleFaulty.NewVehicleFaulty;
import es.ucm.fdi.model.events.NewJunction.NewJunctionBuilder;
import es.ucm.fdi.model.events.NewRoad.NewRoadBuilder;
import es.ucm.fdi.model.events.NewVehicle.NewVehicleBuilder;

public class ControllerTest {
	@Test
	public void LeerEntradaTest() {
		InputStream input;
		Event event;
		try {
			input = new FileInputStream(new File(
					"src/main/resources/readStr/examples/basic/05_twoVehiclesOneFaulty.ini"));
			Ini ini = new Ini(input);
			for (IniSection s : ini.getSections()) {
				try {
					event = getEvento(s);
					System.out.println(event.getTime());
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("No encuentra el fichero");
		} catch (IOException e) {
			System.out.println("Falla ini");
		}

	}
	public Event getEvento(IniSection s) throws IllegalArgumentException {
		Event event;
		List<EventBuilder> EventBuilderList;
		EventBuilderList = new ArrayList<>();
		EventBuilderList.add(new NewRoadBuilder());
		EventBuilderList.add(new NewJunctionBuilder());
		EventBuilderList.add(new NewVehicleBuilder());
		EventBuilderList.add(new NewVehicleFaulty());

		for (EventBuilder e : EventBuilderList) {
			// Parsea el evento con el correspondiente builder
			event = e.parse(s);

			// Si se consigue parsear correctamente lo devuelve
			if (event != null)
				return event;
		}

		throw new IllegalArgumentException(
				new Throwable("No se ha podido parsear la sección:\n" + s.toString()));
	}
}
