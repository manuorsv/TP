package es.ucm.fdi.model.events;

import java.util.Map;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.objects.RoadMap;
import es.ucm.fdi.util.StringParser;

public class MakeVehicleFaulty extends Event {
	private int faultDuration;
	private String[] vehicleId;

	public MakeVehicleFaulty(int time, int faultDur, String[] vId) {
		super(time);
		faultDuration = faultDur;
		vehicleId = vId;
	}

	public void execute(RoadMap map) throws IllegalArgumentException {
		boolean validIds = true;
		for (int i = 0; i < vehicleId.length; ++i) {
			if (!map.duplicatedId(vehicleId[i])) {
				validIds = false;
				break;
			}
		}
		if (validIds) {
			for (int i = 0; i < vehicleId.length; ++i) {
				map.getVehicle(vehicleId[i]).setTiempoAveria(faultDuration);
			}
		} else {
			throw new IllegalArgumentException(
					"No hay ningún vehículo con la id " + vehicleId + '.');
		}
	}
	public static class NewVehicleFaulty implements EventBuilder {
		public Event parse(IniSection sec) throws IllegalArgumentException {
			if (!sec.getTag().equals("make_vehicle_faulty")) {
				return null;
			} else {
				int tm = StringParser.parseTime(sec.getValue("time"));
				try {
					String[] vehic = StringParser.parseIdList(sec.getValue("vehicles"));
					int dur = StringParser.parseIntValue(sec.getValue("duration"));
					return new MakeVehicleFaulty(tm, dur, vehic);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(
							"Algo ha fallado con alguno de los atributos.\n"
									+ e.getMessage(),
							e);
				}
			}
		}
	}
	public void describe(Map<String, String> out) {
		super.describe(out);
		out.put("Type", "Break Vehicles " + vehiclesForFaultyDesc());
	}

	private String vehiclesForFaultyDesc() {

		String aux = "";
		aux += "[";
		for (int i = 0; i < vehicleId.length; ++i) {
			aux += vehicleId[i] + ",";
		}
		aux = aux.substring(0, aux.length() - 1);
		aux += "]";

		return aux;
	}

}
