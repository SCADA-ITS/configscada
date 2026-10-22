package com.revenga.rits.back.equipment.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class ConcurrentEx {

	@Test
	public void main() throws InterruptedException {
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		List<Integer> list = new ArrayList<>();
		
		Runnable writer1 = () -> {
		    
		    for (int i = 0; i < 10000; i++) {
		    	
		    	try {
			    	list.add(1);
			    	
			    	Thread.sleep(0, 1);
			    } catch (Exception ex) {
			        ex.printStackTrace();
			    }

		    }
			
		};
		
		Runnable writer2 = () -> {
		    
			for (int i = 0; i < 10000; i++) {
		    	
		    	try {
			    	list.add(2);
			    	
			    } catch (Exception ex) {
			        ex.printStackTrace();
			    }
		    }
		};
		
		executor.submit(writer1);
		executor.submit(writer2);

		executor.awaitTermination(10000, TimeUnit.MILLISECONDS);
		executor.shutdown();
	}
}
