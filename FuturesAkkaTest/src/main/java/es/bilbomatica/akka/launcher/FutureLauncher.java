package es.bilbomatica.akka.launcher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import akka.dispatch.ExecutionContexts;
import akka.dispatch.Futures;
import akka.dispatch.Mapper;
import es.bilbomatica.akka.callable.RandomPause;
import es.bilbomatica.akka.listeners.PrintResult;
import scala.concurrent.Await;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class FutureLauncher {

	public static void main(String[] args) {

		ExecutorService executorService = Executors.newFixedThreadPool(4);
		ExecutionContext executionContext = ExecutionContexts.fromExecutorService(executorService);

		List<Future<Long>> futures = new ArrayList<Future<Long>>();

		System.out.println(" Adding 3 random pauses");

		futures.add(Futures.future(new RandomPause(), executionContext));
		futures.add(Futures.future(new RandomPause(), executionContext));
		futures.add(Futures.future(new RandomPause(), executionContext));

		System.out.println("There are " + futures.size() + " RandomPause's currently running");

		// Futures sequence
		Future<Iterable<Long>> futuresSequence = Futures.sequence(futures, executionContext);

		// Find the sum of the odd numbers
		Future<Long> futureSum = futuresSequence.map(new Mapper<Iterable<Long>, Long>() {
			public Long apply(Iterable<Long> ints) {
				
				long sum = 0;
				
				for (Long i : ints)
				{
					sum += i;
				}
				
				return sum;
			}
		}, executionContext);

		// block until the futures come back
		futureSum.onSuccess(new PrintResult(), executionContext);

		try
		{
			System.out.println("Result : " + Await.result(futureSum, Duration.apply(10, TimeUnit.SECONDS)));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		executorService.shutdown();
	}
}
