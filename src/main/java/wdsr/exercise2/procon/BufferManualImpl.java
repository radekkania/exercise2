package wdsr.exercise2.procon;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;


/**
 * Task: implement Buffer interface without using any *Queue classes from java.util.concurrent package.
 * Any combination of "synchronized", *Lock, *Semaphore, *Condition, *Barrier, *Latch is allowed.
 */
public class BufferManualImpl implements Buffer { 
	private List<Order> buffer = new LinkedList<>();
	private Semaphore semaphore = new Semaphore(0);
	private Semaphore mutex = new Semaphore(1);
	
	public void submitOrder(Order order) throws InterruptedException {
		mutex.acquire();
		buffer.add(order);
		mutex.release();
		semaphore.release();
	}
	
	public Order consumeNextOrder() throws InterruptedException {
		Order order = null;
		semaphore.acquire(1);
		mutex.acquire();
		order = buffer.remove(0);
		mutex.release();
		return order;
	}
			
}
