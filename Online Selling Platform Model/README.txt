Implementations of required .java files :-

1. Queue.java :- 
	1.1. Description -- An object of type Queue represents a queue of fixed capacity, but the number of elements in the queue (Nodes), currrentSize of the queue can vary and it must be less than or equal to the capacity of the queue.
	The queue is implemented using an array of length capacity and objects of generic type NodeBase<V>. front and rear represent the indices of the nodes in the queue at the front and the rear end respectively.
	1.2 Implemented methods:-
		1.2.1. enqueue(Node<V> node) -- Adds the element node taken as arguement to the rear end of the queue and increments the variables rear and currentSize by 1
		1.2.2. dequeue() -- returns the node at the front end and shifts all the other nodes forward by one step, decrements variables rear and currentSize by 1 and sets the last element to null. In this way the first element gets removed
		
2. PriorityQueue.java :- 
	2.1. Description -- Implements a priority queue using a private array NodeBase[this.capacity] of fixed capacity and variable currentSize similar to Queue.
	In the array the elements (Nodes) are stored in decreasing order of priority i.e. the first element or node has the highest priority.
	2.2. Implemented methods :-
		2.2.1. enqueue(Node<V> node) -- Takes a node and inserts it in the priority queue at its correct position by comparing its priorities with all the nodes starting from the rear end and swapping repeatedly until the correct position is found.
		2.2.2. dequeue() -- returns the element of the highest priority from the priority queue which is the first element of the private array this.queue and shifts all the subsequent elements in the array one step forward, sets the last element to null and decrements the currentSize of the array by 1. In this way the element in the priority queue of the highest priority gets removed.
		2.2.3. add(priority,value) -- similar to enqueue only the format of arguements differs and is used only for testing
		2.2.4. removeMin() -- Similar to dequeue. Only sed for testing

3. Buyer.java :-
	3.1. Description -- Extends the BuyerBase<V> class. Represents a buyer thread that consumes items from the catalog if it is not empty.
	
	3.2. buy() method -- 
		3.2.1. Acquires the common lock used by all buyers and sellers.
		3.2.2. If the catalog is empty then callls empty.await() and releases the common lock and becomes active once a seller signals the buyer that an element is added to the catalog by calling empty.signal() after producing an item in the catalog.
		3.2.3. If the catalog was not empty or once the buyer thread aquires the lock again after being signaled it consumes an item from the catalog.
		3.2.4. calls full.signal() which signals a seller, who tried to produce an item in the catalog from the inventory but was unable to produce as the catalog was full, to race to acquire the common lock and try to produce once again as an item has been consumed from the catalog and its is no longer full.
		3.2.5. finally the buyer thread releases the acquired common lock.
		
4. Seller.java :-
	4.1. Description -- Extends the SellerBase<V> class. Represents a seller thread that produces items in the catalog if it is not full.
	
	4.2. sell() method :-
		4.2.1. Acquires the common lock used by all buyers and sellers.
		4.2.2. If the catalog is full then calls full.await() and releases the common lock and becomes active once a cinsumer consumes an item from the catalog and calls full.signal().
		4.2.3. If the catalog was not full or once the seller thread acquires the lock again after being signaled it produces an item in the catalog by dequeuing an item from the invenntory and enqueuing it in the catalog priority queue.
		4.2.4. calls empty.signal() which signals a waiting buyer thread, that couldn't consume an item from the catalog as it was empty, to become active again and try to acquire the lock again as an item has been produced in the catalog and its no longer empty.
		4.2.5. finally the seller thread releases the acquired common lock.
	