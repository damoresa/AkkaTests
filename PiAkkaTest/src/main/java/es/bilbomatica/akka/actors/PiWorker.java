package es.bilbomatica.akka.actors;

import akka.actor.UntypedActor;
import es.bilbomatica.akka.messages.Result;
import es.bilbomatica.akka.messages.Work;

public class PiWorker extends UntypedActor {
	
	private static int numWorker = 0;
	private String workerCode;
	
	public PiWorker(String workerCode) {
		synchronized (PiWorker.class)
		{
			this.workerCode = workerCode + numWorker;
			numWorker++;
		}
	}
	
	private double calculatePiFor(int start, int noElements) {
		double acc = 0.0;
		
		// Apply formulae
		for (int i = start * noElements; i <= ((start + 1) * noElements - 1); i++)
		{
			acc += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1);
		}
		
		return acc;
	}

	@Override
	public void onReceive(Object message) throws Exception {
		
		if (message instanceof Work)
		{
			Work work = (Work) message;
			
			double result = this.calculatePiFor(work.getStart(), work.getNoElements());
			Result resultObj = new Result(result);
			
			System.out.println(" >> Worker " + workerCode + " obtained result " + result);
			
			this.getSender().tell(resultObj, this.getSelf());
		}
		else
		{
			this.unhandled(message);
		}
		
	}

}
