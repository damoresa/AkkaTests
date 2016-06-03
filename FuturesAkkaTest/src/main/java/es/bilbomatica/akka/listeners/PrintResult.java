package es.bilbomatica.akka.listeners;

import akka.dispatch.OnSuccess;

public class PrintResult extends OnSuccess<Long> {

	@Override
	public void onSuccess(Long parameter) throws Throwable {

        System.out.println("PrintResults says: Total pause was for " + parameter + " milliseconds");
	}

}
