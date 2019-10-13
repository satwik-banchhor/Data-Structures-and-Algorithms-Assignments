// This class implements the Queue
public class Queue<V> implements QueueInterface<V>{
	
    //TODO Complete the Queue implementation
    private NodeBase<V>[] queue;
    private int capacity, currentSize, front, rear;
	
    @SuppressWarnings("unchecked")
	public Queue(int capacity) {    
    	this.capacity = capacity;
    	queue = new Node[this.capacity];
    	this.currentSize = 0;
    	this.front = 0;
    	this.rear = -1;
    }

    public int size() {
    	return this.currentSize; 
    }

    public boolean isEmpty() {
    	return (this.currentSize==0);
    }
	
    public boolean isFull() {
    	return (this.currentSize==capacity);
    }

    public void enqueue(Node<V> node) {
    	if (!this.isFull()) {
    	this.rear++;
        this.currentSize++;
    	this.queue[rear] = node;
    	}
    }

    public NodeBase<V> dequeue() {
    	if (!this.isEmpty()) {
    	NodeBase<V> obj = this.queue[front];
    	for (int i=0; i<this.currentSize-1;i++) {
    		this.queue[i] = this.queue[i+1];
    	}
    	this.queue[this.currentSize-1] = null;
    	this.rear = this.rear-1;
    	this.currentSize=this.currentSize-1;
    	return obj;
    	}
    	return null;
    }

}

