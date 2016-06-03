package es.bilbomatica.akka.messages;

import es.bilbomatica.akka.messages.base.Message;
import es.bilbomatica.akka.messages.base.MessageType;

public class ProcessLineMessage implements Message {
	
	private static final long serialVersionUID = 8683006099978091327L;
	
	private MessageType messageType = MessageType.REQUEST;
	
	private String line;
	
	public ProcessLineMessage(String line) {
		this.line = line;
	}
	
	public String getLine() {
		return this.line;
	}
	
	@Override
	public MessageType getMessageType() {
		return messageType;
	}
}
