package es.bilbomatica.akka.strategy.cases.impl;

import java.util.concurrent.TimeUnit;

import es.bilbomatica.akka.actors.FileProcessingActor;
import es.bilbomatica.akka.actors.base.ProcessingActor;
import es.bilbomatica.akka.messages.FileProcessedMessage;
import es.bilbomatica.akka.messages.LineProcessedMessage;
import es.bilbomatica.akka.messages.base.Message;
import es.bilbomatica.akka.strategy.cases.MessageProcessor;
import scala.concurrent.duration.Duration;

public class LineProcessedMessageProcessor implements MessageProcessor {

	@Override
	public void processMessage(ProcessingActor actor, Message message) {
		
		FileProcessingActor fileProcessingActor = (FileProcessingActor) actor;
		LineProcessedMessage lineProcessed = (LineProcessedMessage) message;
		
		fileProcessingActor.increaseNoWords(lineProcessed.getNoWords());
		fileProcessingActor.increaseNoProcessedLines();
		
		if (fileProcessingActor.getNoProcessedLines() == fileProcessingActor.getNoLines())
		{
			Duration duration = Duration.create(System.currentTimeMillis() - fileProcessingActor.getStart(), TimeUnit.MILLISECONDS);
			
			fileProcessingActor.getCompletionListener().tell(
						new FileProcessedMessage(fileProcessingActor.getNoWorkers(), fileProcessingActor.getNoWords(), duration)
						, fileProcessingActor.getSelf());
			fileProcessingActor.setRunning(false);
			fileProcessingActor.context().stop(fileProcessingActor.getSelf());
		}
	}

}
