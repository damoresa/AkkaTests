package es.bilbomatica.akka.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;
import akka.routing.RouterConfig;
import es.bilbomatica.akka.messages.base.Message;
import es.bilbomatica.strategy.handler.MessageHandler;
import es.bilbomatica.strategy.handler.impl.MessageHandlerImpl;

public class FileProcessingActor extends UntypedActor {
	
	private final MessageHandler messageHandler = MessageHandlerImpl.getInstance();
	
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
			messageHandler.handleMessage((UntypedActor)this, (Message)message);
		}
		else
		{
			this.unhandled(message);
		}
	}

	public MessageHandler getMessageHandler() {
		return messageHandler;
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