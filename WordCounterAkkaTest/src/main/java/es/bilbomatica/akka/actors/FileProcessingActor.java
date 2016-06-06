package es.bilbomatica.akka.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;
import akka.routing.RouterConfig;
import es.bilbomatica.akka.actors.base.Actors;
import es.bilbomatica.akka.actors.base.ProcessingActor;
import es.bilbomatica.akka.factory.MessageProcessorFactory;
import es.bilbomatica.akka.factory.impl.MessageProcessorFactoryImpl;
import es.bilbomatica.akka.messages.base.Message;
import es.bilbomatica.akka.strategy.cases.MessageProcessor;

public class FileProcessingActor extends UntypedActor implements ProcessingActor {
	
	private final Actors actor = Actors.FILE_PROCESSING;
	
	private final MessageProcessorFactory messageProcessorFactory = MessageProcessorFactoryImpl.getInstance();
	
	private final long start = System.currentTimeMillis();
	
	private Integer noWorkers;
	private ActorRef completionListener;
	private ActorRef workerRouter;
	
	private volatile boolean running = false;
	private int noLines;
	private int noProcessedLines;
	
	private int noWords;
	
	public FileProcessingActor(int noWorkers, ActorRef completionListener) {
		this.noWorkers = noWorkers;
		this.completionListener = completionListener;
		
		RouterConfig routerConfig = new RoundRobinPool(noWorkers);
		
		this.workerRouter = this.getContext()
									.actorOf(Props.create(LineProcessingActor.class)
											.withRouter(routerConfig), "LineProcessorRouterActor");
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		
		if (message instanceof Message)
		{
			ProcessingActor processingActor = (ProcessingActor) this;
			Message receivedMessage = (Message) message;
			
			MessageProcessor messageProcessor = 
					messageProcessorFactory.getMessageProcessor(processingActor, receivedMessage);
			messageProcessor.processMessage(processingActor, receivedMessage);
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

	public long getStart() {
		return start;
	}

	public Integer getNoWorkers() {
		return noWorkers;
	}

	public ActorRef getCompletionListener() {
		return completionListener;
	}

	public ActorRef getWorkerRouter() {
		return workerRouter;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public int getNoLines() {
		return noLines;
	}
	
	public void increaseNoLines() {
		noLines++;
	}

	public int getNoProcessedLines() {
		return noProcessedLines;
	}
	
	public void increaseNoProcessedLines() {
		noProcessedLines++;
	}

	public int getNoWords() {
		return noWords;
	}
	
	public void increaseNoWords(int ammount) {
		noWords += ammount;
	}

}
