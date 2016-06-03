package es.bilbomatica.akka.actors;

import akka.actor.UntypedActor;
import es.bilbomatica.akka.actors.base.Actors;
import es.bilbomatica.akka.actors.base.ProcessingActor;
import es.bilbomatica.akka.messages.base.Message;
import es.bilbomatica.strategy.handler.MessageHandler;
import es.bilbomatica.strategy.handler.impl.MessageHandlerImpl;

public class CompletionListenerActor extends UntypedActor implements ProcessingActor {
	
	private final Actors actor = Actors.COMPLETION_LISTENER;
	
	private final MessageHandler messageHandler = MessageHandlerImpl.getInstance();

	@Override
	public void onReceive(Object message) throws Exception {
		
		if (message instanceof Message)
		{
			messageHandler.handleMessage(this, (Message)message);
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
