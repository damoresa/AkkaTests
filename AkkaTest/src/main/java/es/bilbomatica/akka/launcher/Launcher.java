package es.bilbomatica.akka.launcher;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import es.bilbomatica.akka.actors.HelloActor;

public class Launcher {
	
	public static void main(String[] args) {
		
		ActorSystem system = ActorSystem.create("HelloWorldSystem");
		
		final ActorRef helloActor = system.actorOf(Props.create(HelloActor.class), "HelloActor");
		
		helloActor.tell("Hola", null);
	}
}
