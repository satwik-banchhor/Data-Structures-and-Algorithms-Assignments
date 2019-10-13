import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Seller<V> extends SellerBase<V> {
    public Seller (int sleepTime, int catalogSize, Lock lock, Condition full, Condition empty, PriorityQueue<V> catalog, Queue<V> inventory) {
        //TODO Complete the constructor method by initializing the attibutes
        // ...
    	this.setSleepTime(sleepTime);
    	this.lock = lock;
    	this.full = full;
    	this.empty = empty;
    	this.catalog = catalog;
    	this.inventory = inventory;    	
    }
    
    public void sell() throws InterruptedException {
	try {
            //TODO Complete the try block for produce method
            // ...
		lock.lock();
		while (catalog.isFull()) {
			full.await();
		}
		if(inventory.size()!=0){
			NodeBase<V> dequeued_item = inventory.dequeue();		
			@SuppressWarnings({ "unchecked", "rawtypes" })
			Node<V> new_item =  new Node(dequeued_item.getPriority(),(Item) dequeued_item.getValue());
			this.catalog.enqueue(new_item);
		}
		empty.signal();
	} catch(Exception e) {
            e.printStackTrace();
	} finally {
            //TODO Complete this block
		lock.unlock();
	}		
    }
}
