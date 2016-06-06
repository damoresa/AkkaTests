package es.bilbomatica.akka.strategy.cases;

import es.bilbomatica.akka.actors.base.ProcessingActor;
import es.bilbomatica.akka.messages.base.Message;

public interface MessageProcessor {
	
	public void processMessage(ProcessingActor actor, Message message);
}
