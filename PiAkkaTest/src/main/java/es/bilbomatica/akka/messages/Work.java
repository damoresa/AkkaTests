package es.bilbomatica.akka.messages;

public class Work {
	
	private int start;
	private int noElements;
	
	public Work(int start, int noElements) {
		this.start = start;
		this.noElements = noElements;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getNoElements() {
		return noElements;
	}
}
