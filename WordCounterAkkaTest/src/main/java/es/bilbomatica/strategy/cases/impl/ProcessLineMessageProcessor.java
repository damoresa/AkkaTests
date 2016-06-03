package es.bilbomatica.strategy.cases.impl;

import es.bilbomatica.akka.actors.LineProcessingActor;
import es.bilbomatica.akka.messages.LineProcessedMessage;
import es.bilbomatica.akka.messages.ProcessLineMessage;
import es.bilbomatica.strategy.cases.MessageProcessor;

public class ProcessLineMessageProcessor implements MessageProcessor<LineProcessingActor, ProcessLineMessage> {

	@Override
	public void processMessage(LineProcessingActor actor, ProcessLineMessage message) {
		
		int noWords = 0;
		
		if (message.getLine() != null)
		{
			noWords += message.getLine().split("\\s+").length;
		}
		
		actor.getSender().tell(new LineProcessedMessage(noWords), actor.getSelf());
	}

}
