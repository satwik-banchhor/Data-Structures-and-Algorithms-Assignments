
public class PriorityQueue<V> implements QueueInterface<V>{

    private NodeBase<V>[] queue;
    private int capacity, currentSize;
	
    //TODO Complete the Priority Queue implementation
    // You may create other member variables/ methods if required.
    @SuppressWarnings("unchecked")
	public PriorityQueue(int capacity) {    
    	this.capacity = capacity;
    	this.queue = new NodeBase[this.capacity];
    	this.currentSize = 0;
    }

    public int size() {
    	return this.currentSize;
    }

    public boolean isEmpty() {
    	return (this.currentSize==0);
    }
	
    public boolean isFull() {
    	return (this.currentSize==this.capacity);
    }

    public void enqueue(Node<V> node) {
    	if(!this.isFull()) {
    	int priority = node.priority;
        boolean pos_found = false;
        int index = this.currentSize;
        this.queue[index] = node;
    	while (!pos_found && index>0) {
    		if (this.queue[index-1].priority<=priority){
    			pos_found=true;
    		}
    		else {
    			this.queue[index] = this.queue[index-1];
    			this.queue[index-1] = node;
    			index = index-1;
    		}
    	}    		
    	this.currentSize++; 
    	}
    }

    // In case of priority queue, the dequeue() should 
    // always remove the element with minimum priority value
    public NodeBase<V> dequeue() {
    	if (!this.isEmpty()) {
    	NodeBase<V> obj = this.queue[0];
    	for (int i=0; i<this.currentSize-1;i++) {
    		this.queue[i]=this.queue[i+1];
    	}
    	this.queue[currentSize-1] = null;
    	this.currentSize = this.currentSize-1;
    	return obj;
    	}
    	return null;
    }

    public void display () {
	if (this.isEmpty()) {
            System.out.println("Queue is empty");
	}
	for(int i=0; i<currentSize; i++) {
            queue[i].show();
	}
    }
    
    public void add(int priority,V value) {
    	Node<V> node = new Node<V>(priority,value);
    	node.priority = priority;
    	node.value = value;
    	boolean pos_found = false;
    	int index = this.currentSize;
    	this.queue[index] = node;
    	while (!pos_found && index>0) {
    		if (this.queue[index-1].priority<=priority){
    			pos_found=true;
    		}
    		else {
    			this.queue[index] = this.queue[index-1];
    			this.queue[index-1] = node;
    			index = index-1;
    		}
    	}
    	if (pos_found == false) {
    		this.queue[0]=node;
    	}
    	this.currentSize++;
    	
    }
    
    public NodeBase<V> removeMin() {
    	NodeBase<V> obj = this.queue[0];
    	for (int i=0; i<this.currentSize-1;i++) {
    		this.queue[i]=this.queue[i+1];
    	}
    	this.queue[currentSize-1] = null;
    	this.currentSize = this.currentSize-1;  	
    	return obj;    	
    }
}

