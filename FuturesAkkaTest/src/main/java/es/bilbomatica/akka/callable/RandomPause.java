package es.bilbomatica.akka.callable;

import java.util.concurrent.Callable;

public class RandomPause implements Callable<Long> {
	
	private Long millisPause;
	
	public RandomPause() {
		// 2,000 to 10,000
		millisPause = Math.round(Math.random() * 8000) + 2000;
		System.out.println(this.toString() + " will pause for " + millisPause + "milliseconds. ");
	}
	
	@Override
	public Long call() throws Exception {
		
		Thread.sleep(millisPause);
		System.out.println(this.toString() + " was paused for " + millisPause + "milliseconds. ");
		return millisPause;
	}

}
