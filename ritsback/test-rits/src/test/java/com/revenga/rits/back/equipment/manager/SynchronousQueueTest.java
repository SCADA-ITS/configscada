package com.revenga.rits.back.equipment.manager;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class SynchronousQueueTest {

	@Test
	public void main() throws InterruptedException {
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		SynchronousQueue<Integer> queue = new SynchronousQueue<>();
		
		Runnable producer = () -> {
		    Integer producedElement = ThreadLocalRandom
		      .current()
		      .nextInt();
		    try {
		        queue.put(producedElement);
		    } catch (InterruptedException ex) {
		        ex.printStackTrace();
		    }
		};
		
		Runnable consumer = () -> {
		    try {
		        Integer consumedElement = queue.take();
		    } catch (InterruptedException ex) {
		        ex.printStackTrace();
		    }
		};
		
		executor.execute(producer);
		executor.execute(consumer);

		executor.awaitTermination(500, TimeUnit.MILLISECONDS);
		executor.shutdown();
		assertEquals(queue.size(), 0);
	}
}
