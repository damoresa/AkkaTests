package es.bilbomatica.akka.actors;

import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;
import akka.routing.RouterConfig;
import es.bilbomatica.akka.messages.Calculate;
import es.bilbomatica.akka.messages.PiApproximation;
import es.bilbomatica.akka.messages.Result;
import es.bilbomatica.akka.messages.Work;
import scala.concurrent.duration.Duration;

public class Master extends UntypedActor {
	
	private int noMessages;
	private int noElements;
	
	private double pi;
	private int noResults;
	private final long startTime = System.currentTimeMillis();
	
	private ActorRef listener;
	private ActorRef workerRouter;
	
	public Master(final int noWorkers, int noMessages, int noElements, ActorRef listener) {
		
		this.noMessages = noMessages;
		this.noElements = noElements;
		this.listener = listener;
		
		RouterConfig router = new RoundRobinPool(noWorkers);
		
		this.workerRouter = this.getContext().actorOf(Props.create(PiWorker.class, "Worker_").withRouter(router), "WorkerRouter");
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		
		if (message instanceof Calculate)
		{
			for (int started = 0; started < this.noMessages; started++)
			{
				workerRouter.tell(new Work(started, noElements), this.getSelf());
			}
		}
		else if (message instanceof Result)
		{
			Result result = (Result) message;
			
			pi += result.getValue();
			noResults++;
			
			if (noResults == noMessages)
			{
				Duration duration = Duration.create(System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS);
				
				listener.tell(new PiApproximation(duration, pi), this.getSelf());
				this.getContext().stop(this.getSelf());
			}
		}
		else
		{
			this.unhandled(message);
		}
	}
}
