package es.bilbomatica.strategy.cases.impl;

import es.bilbomatica.akka.actors.CompletionListenerActor;
import es.bilbomatica.akka.messages.FileProcessedMessage;
import es.bilbomatica.strategy.cases.MessageProcessor;

public class FileProcessedMessageProcessor implements MessageProcessor<CompletionListenerActor, FileProcessedMessage> {

	@Override
	public void processMessage(CompletionListenerActor actor, FileProcessedMessage message) {

		System.out.println(" ---------------------------------------------------- ");
		System.out.println(" >> File has been processed with : " + message.getNoWorkers() + " workers");
		System.out.println(" >> File has been processed in : " + message.getDuration());
		System.out.println(" >> The processed file contains : " + message.getNoWords() + " words");
		System.out.println(" ---------------------------------------------------- ");
		
		actor.getContext().system().shutdown();
	}

}
