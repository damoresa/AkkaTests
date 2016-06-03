package es.bilbomatica.akka.messages;

import es.bilbomatica.akka.messages.base.Message;
import scala.concurrent.duration.Duration;

public class FileProcessedMessage implements Message {
	
	private static final long serialVersionUID = 5812861146640531602L;
	
	private Integer noWorkers;
	private Integer noWords;
	private Duration duration;
	
	public FileProcessedMessage(Integer noWorkers, Integer noWords, Duration duration) {
		this.noWorkers = noWorkers;
		this.noWords = noWords;
		this.duration = duration;
	}
	
	public Integer getNoWorkers() {
		return this.noWorkers;
	}
	
	public Integer getNoWords() {
		return this.noWords;
	}
	
	public Duration getDuration() {
		return this.duration;
	}
}
