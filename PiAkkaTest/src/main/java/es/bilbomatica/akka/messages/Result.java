package es.bilbomatica.akka.messages;

public class Result {
	
	private double value;
	
	public Result(double value) {
		this.value = value;
	}
	
	public double getValue() {
		return this.value;
	}
}
