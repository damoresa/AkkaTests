package es.bilbomatica.akka.strategy.cases.impl;

import es.bilbomatica.akka.actors.base.ProcessingActor;
import es.bilbomatica.akka.messages.FileProcessedMessage;
import es.bilbomatica.akka.messages.base.Message;
import es.bilbomatica.akka.strategy.cases.MessageProcessor;

public class FileProcessedMessageProcessor implements MessageProcessor {

	@Override
	public void processMessage(ProcessingActor actor, Message message) {
		
		FileProcessedMessage fileProcessed = (FileProcessedMessage) message;

		System.out.println(" ---------------------------------------------------- ");
		System.out.println(" >> File has been processed with : " + fileProcessed.getNoWorkers() + " workers");
		System.out.println(" >> File has been processed in : " + fileProcessed.getDuration());
		System.out.println(" >> The processed file contains : " + fileProcessed.getNoWords() + " words");
		System.out.println(" ---------------------------------------------------- ");
		
	}

}
