package es.bilbomatica.akka.actors;

import akka.actor.UntypedActor;

public class HelloActor extends UntypedActor {
	
	@Override
	public void onReceive(Object message) throws Exception {
		
		if (message instanceof String)
		{
			String msg = (String) message;
			
			System.out.println(msg);
		}
		else
		{
			this.unhandled(message);
		}
	}
}
