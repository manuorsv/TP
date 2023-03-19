package es.ucm.fdi.control;

public class Stepper {
	private Runnable before;
	private Runnable during;
	private Runnable after;
	private volatile boolean stopRequest = false;
	private int steps;
	
	
	public Stepper(Runnable b, Runnable d, Runnable a) {
		before = b;
		during = d;
		after = a;
	}
	
	public Thread start(int numSteps, int delay) {
		steps = numSteps;
		stopRequest = false;
		
		Thread thread = new Thread ( () -> {
			try {
				before.run();
				while(!stopRequest && steps > 0) {
					during.run();
					try {
						Thread.sleep(delay);
					} catch(InterruptedException e) {
						//Just to stop the execution, jumps to finally			
					}
					--steps;
				}
			} finally {
				after.run();
			}
		});
		thread.start();
		
		return thread;
	}
	
	public void stop() {
		stopRequest = true;
	}
}
