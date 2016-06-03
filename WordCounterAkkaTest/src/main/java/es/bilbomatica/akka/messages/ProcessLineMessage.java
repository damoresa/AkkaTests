package es.bilbomatica.akka.messages;

import es.bilbomatica.akka.messages.base.Message;

public class ProcessLineMessage implements Message {
	
	private static final long serialVersionUID = 8683006099978091327L;
	
	private String line;
	
	public ProcessLineMessage(String line) {
		this.line = line;
	}
	
	public String getLine() {
		return this.line;
	}
}
