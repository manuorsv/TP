package es.ucm.fdi.model.objects;

import java.util.List;
import java.util.Map;

public class Bike extends Vehicle {

	public Bike(String id, int maxSpeed, List<Road> trayecto) {
		super(id, maxSpeed, trayecto);
	}

	@Override
	public void setTiempoAveria(int tiempoAveria) {
		if (velActual > velMaxima / 2) {
			super.setTiempoAveria(tiempoAveria);
		}
	}
	@Override
	public void fillReportDetails(Map<String, String> camposValor) {
		camposValor.put("speed", "" + velActual);
		camposValor.put("kilometrage", "" + kilometrage);
		camposValor.put("type", "bike");
		camposValor.put("faulty", "" + tiempoAveria);
		camposValor.put("location", localizacionString());
	}
}
