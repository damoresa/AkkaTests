package es.bilbomatica.strategy.handler;

import es.bilbomatica.akka.actors.base.ProcessingActor;
import es.bilbomatica.akka.messages.base.Message;

public interface MessageHandler {
	
	public void handleMessage(ProcessingActor actor, Message message);
}
