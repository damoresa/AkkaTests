package es.bilbomatica.akka.launcher;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import es.bilbomatica.akka.actors.Listener;
import es.bilbomatica.akka.actors.Master;
import es.bilbomatica.akka.messages.Calculate;

public class PiLauncher {
	/**
	 * ________________________________________________________
	 * TC - 1
	 *  - noWorkers = 4
	 *		>> Received Pi approximation : 3.141592653553702
	 *		>> Pi approximation duration : 4414 milliseconds
	 * ________________________________________________________
	 * 
	 * TC - 2
	 *  - noWorkers = 2
	 *		>> Received Pi approximation : 3.141592653553702
	 *		>> Pi approximation duration : 6105 milliseconds
	 * ________________________________________________________
	 * 
	 * TC - 3
	 *  - noWorkers = 1
	 *		>> Received Pi approximation : 3.141592653553702
	 *		>> Pi approximation duration : 8340 milliseconds
	 * ________________________________________________________
	 */
	
	private static final Integer noWorkers = 4;
	private static final Integer noElements = 40000;
	private static final Integer noMessages = 40000;
	
	public static void main(String[] args) {
		
		ActorSystem system = ActorSystem.create("PiSystem");
		
		final ActorRef listener = system.actorOf(Props.create(Listener.class), "listener");
		
		ActorRef master = system.actorOf(Props.create(Master.class, noWorkers, noMessages, noElements, listener), "master");
		
		master.tell(new Calculate(), null);
	}
}
