package es.bilbomatica.strategy.handler;

import akka.actor.UntypedActor;
import es.bilbomatica.akka.messages.base.Message;

public interface MessageHandler {
	
	public void handleMessage(UntypedActor actor, Message message);
}
