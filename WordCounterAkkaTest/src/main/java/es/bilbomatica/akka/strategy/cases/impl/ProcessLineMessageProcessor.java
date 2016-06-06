package es.bilbomatica.akka.strategy.cases.impl;

import es.bilbomatica.akka.actors.LineProcessingActor;
import es.bilbomatica.akka.actors.base.ProcessingActor;
import es.bilbomatica.akka.messages.LineProcessedMessage;
import es.bilbomatica.akka.messages.ProcessLineMessage;
import es.bilbomatica.akka.messages.base.Message;
import es.bilbomatica.akka.strategy.cases.MessageProcessor;

public class ProcessLineMessageProcessor implements MessageProcessor {

	@Override
	public void processMessage(ProcessingActor actor, Message message) {
		
		ProcessLineMessage processLineMessage = (ProcessLineMessage) message;
		LineProcessingActor lineProcessingActor = (LineProcessingActor) actor;
		
		int noWords = 0;
		
		if (processLineMessage.getLine() != null)
		{
			noWords += processLineMessage.getLine().split("\\s+").length;
		}
		
		lineProcessingActor.getSender().tell(new LineProcessedMessage(noWords), lineProcessingActor.getSelf());
	}

}
