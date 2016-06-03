package es.bilbomatica.akka.actors;

import akka.actor.UntypedActor;
import es.bilbomatica.akka.messages.base.Message;
import es.bilbomatica.strategy.handler.MessageHandler;
import es.bilbomatica.strategy.handler.impl.MessageHandlerImpl;

public class LineProcessingActor extends UntypedActor {
	
	private final MessageHandler messageHandler = MessageHandlerImpl.getInstance();

	@Override
	public void onReceive(Object message) throws Exception {
		
		if (message instanceof Message)
		{
			messageHandler.handleMessage((UntypedActor)this, (Message)message);
		}
		else
		{
			this.unhandled(message);
		}
	}

}
