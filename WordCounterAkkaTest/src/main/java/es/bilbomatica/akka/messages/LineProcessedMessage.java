package es.bilbomatica.akka.messages;

import es.bilbomatica.akka.messages.base.Message;
import es.bilbomatica.akka.messages.base.MessageType;

public class LineProcessedMessage implements Message {
	
	private static final long serialVersionUID = 4632192228779834248L;
	
	private MessageType messageType = MessageType.RESPONSE;
	
	private Integer noWords;
	
	public LineProcessedMessage(Integer noWords) {
		this.noWords = noWords;
	}
	
	public Integer getNoWords() {
		return this.noWords;	
	}
	
	@Override
	public MessageType getMessageType() {
		return messageType;
	}
}
