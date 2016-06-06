package es.bilbomatica.akka.actors;

import akka.actor.UntypedActor;
import es.bilbomatica.akka.actors.base.Actors;
import es.bilbomatica.akka.actors.base.ProcessingActor;
import es.bilbomatica.akka.factory.MessageProcessorFactory;
import es.bilbomatica.akka.factory.impl.MessageProcessorFactoryImpl;
import es.bilbomatica.akka.messages.base.Message;
import es.bilbomatica.akka.strategy.cases.MessageProcessor;

public class CompletionListenerActor extends UntypedActor implements ProcessingActor {
	
	private final Actors actor = Actors.COMPLETION_LISTENER;
	
	private final MessageProcessorFactory messageProcessorFactory = MessageProcessorFactoryImpl.getInstance();

	@Override
	public void onReceive(Object message) throws Exception {
		
		if (message instanceof Message)
		{
			ProcessingActor processingActor = (ProcessingActor) this;
			Message receivedMessage = (Message) message;
			
			MessageProcessor messageProcessor = 
					messageProcessorFactory.getMessageProcessor(processingActor, receivedMessage);
			messageProcessor.processMessage(processingActor, receivedMessage);
			
			this.getContext().system().shutdown();
		}
		else
		{
			this.unhandled(message);
		}
	}

	@Override
	public Actors getActor() {
		return actor;
	}

}
