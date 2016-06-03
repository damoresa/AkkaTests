package es.bilbomatica.akka.actors;

import akka.actor.UntypedActor;
import es.bilbomatica.akka.messages.PiApproximation;

public class Listener extends UntypedActor {

	@Override
	public void onReceive(Object message) throws Exception {
		
		if (message instanceof PiApproximation)
		{
			PiApproximation approximation = (PiApproximation) message;
			
			System.out.println(" >> Received Pi approximation : " + approximation.getValue());
			System.out.println(" >> Pi approximation duration : " + approximation.getDuration());
			
			this.getContext().system().shutdown();
		}
		else
		{
			this.unhandled(message);
		}
	}

}
