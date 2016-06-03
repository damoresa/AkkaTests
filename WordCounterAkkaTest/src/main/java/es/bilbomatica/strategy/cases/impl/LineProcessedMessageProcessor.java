package es.bilbomatica.strategy.cases.impl;

import java.util.concurrent.TimeUnit;

import es.bilbomatica.akka.actors.FileProcessingActor;
import es.bilbomatica.akka.messages.FileProcessedMessage;
import es.bilbomatica.akka.messages.LineProcessedMessage;
import es.bilbomatica.strategy.cases.MessageProcessor;
import scala.concurrent.duration.Duration;

public class LineProcessedMessageProcessor implements MessageProcessor<FileProcessingActor, LineProcessedMessage> {

	@Override
	public void processMessage(FileProcessingActor actor, LineProcessedMessage message) {
		
		actor.increaseNoWords(message.getNoWords());
		actor.increaseNoProcessedLines();
		
		if (actor.getNoProcessedLines() == actor.getNoLines())
		{
			Duration duration = Duration.create(System.currentTimeMillis() - actor.getStart(), TimeUnit.MILLISECONDS);
			
			actor.getCompletionListener().tell(
						new FileProcessedMessage(actor.getNoWorkers(), actor.getNoWords(), duration)
						, actor.getSelf());
			actor.setRunning(false);
			actor.context().stop(actor.getSelf());
		}
	}

}
