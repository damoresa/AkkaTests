package es.bilbomatica.akka.factory;

import es.bilbomatica.akka.actors.base.ProcessingActor;
import es.bilbomatica.akka.messages.base.Message;
import es.bilbomatica.akka.strategy.cases.MessageProcessor;

public interface MessageProcessorFactory {
	
	public MessageProcessor getMessageProcessor(ProcessingActor actor, Message message);
}
