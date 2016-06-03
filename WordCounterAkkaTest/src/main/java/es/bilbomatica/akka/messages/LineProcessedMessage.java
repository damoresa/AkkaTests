package es.bilbomatica.akka.messages;

import es.bilbomatica.akka.messages.base.Message;

public class LineProcessedMessage implements Message {
	
	private static final long serialVersionUID = 4632192228779834248L;
	
	private Integer noWords;
	
	public LineProcessedMessage(Integer noWords) {
		this.noWords = noWords;
	}
	
	public Integer getNoWords() {
		return this.noWords;	
	}
}
