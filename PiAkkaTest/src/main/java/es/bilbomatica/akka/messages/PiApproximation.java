package es.bilbomatica.akka.messages;

import scala.concurrent.duration.Duration;

public class PiApproximation {
	
	private Duration duration;
	private double value;
	
	public PiApproximation(Duration duration, double value) {
		this.duration = duration;
		this.value = value;
	}

	public Duration getDuration() {
		return duration;
	}

	public double getValue() {
		return value;
	}
}
