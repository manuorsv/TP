package es.ucm.fdi.model.objects;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class Car extends Vehicle {
	private int resistance; 
	private int kmSinceLastFaulty; 
	private double faultProbability; 
	private int maxFaultDuration; 
	private long seed; 
	private Random random; 

	public Car(int res, double fProb, int mFDur, long s, String id, int maxSpeed, List<Road> trayecto) {
		super(id, maxSpeed, trayecto);
		
		resistance = res;
		faultProbability = fProb;
		maxFaultDuration = mFDur;
		seed = s;
		random = new Random(seed);
		kmSinceLastFaulty = 0;
	}

	public int tiempoAveria() {
		return random.nextInt(maxFaultDuration) + 1;
	}
	public double posibleProbAveria() {
		return random.nextDouble();
	}
	
	@Override
	public void avanza() {
		int aux = kilometrage;

		if (!this.averiado() && kmSinceLastFaulty > resistance
				&& posibleProbAveria() < faultProbability) {
			this.setTiempoAveria(tiempoAveria());
			kmSinceLastFaulty = 0;
		}

		super.avanza();

		kmSinceLastFaulty += this.getLocalizacion() - aux;
	}
	@Override
	public void fillReportDetails(Map<String, String> camposValor) {
		camposValor.put("type", "car");
		camposValor.put("speed", "" + velActual);
		camposValor.put("kilometrage", "" + kilometrage);
		camposValor.put("faulty", "" + tiempoAveria);
		camposValor.put("location", localizacionString());
	}
} // Car
