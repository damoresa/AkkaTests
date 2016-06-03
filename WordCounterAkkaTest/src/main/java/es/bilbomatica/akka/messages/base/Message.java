package es.bilbomatica.akka.messages.base;

public interface Message extends java.io.Serializable {
	
	public MessageType getMessageType();
}
