package es.bilbomatica.strategy.cases;

import akka.actor.UntypedActor;
import es.bilbomatica.akka.messages.base.Message;

public interface MessageProcessor<R extends UntypedActor, T extends Message> {
	
	public void processMessage(R actor, T message);
}
