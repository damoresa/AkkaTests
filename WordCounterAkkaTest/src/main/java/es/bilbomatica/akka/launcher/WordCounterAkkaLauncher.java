package es.bilbomatica.akka.launcher;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import es.bilbomatica.akka.actors.CompletionListenerActor;
import es.bilbomatica.akka.actors.FileProcessingActor;
import es.bilbomatica.akka.messages.ProcessFileMessage;

public class WordCounterAkkaLauncher {
	
	private static final int NO_TESTS = 10;
	
	public static void main(String[] args) {
		
		System.out.println(" >> AKKA Word counting test ");
		
		for (int noTest = 1; noTest <= WordCounterAkkaLauncher.NO_TESTS; noTest++)
		{
			ActorSystem system = ActorSystem.create("WordCountSystem" + noTest);
			
			final ActorRef completionListener = system.actorOf(Props.create(CompletionListenerActor.class), "CompletionActor");
			final ActorRef fileProcessor = system.actorOf(Props.create(FileProcessingActor.class, noTest, completionListener), "FileProcessorActor");
			
			fileProcessor.tell(new ProcessFileMessage("C:\\lorem", "ipsum.txt"), null);
		}
	}
}
