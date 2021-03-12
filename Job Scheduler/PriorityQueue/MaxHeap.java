package PriorityQueue;

import java.util.ArrayList;
import java.util.Queue;

public class MaxHeap<T extends Comparable> implements PriorityQueueInterface<T> {
	public ArrayList<Priority<T>> heap;
	private int nextin;
	public MaxHeap(){
		this.heap = new ArrayList<Priority<T>>();
		nextin=0;
	}
	

    @Override
    public void insert(T element) {
        int index=heap.size();
        Priority<T> el = new Priority<T>(element,nextin);
        this.nextin++;
        heap.add(el);
        while (index>0 & heap.get((index-1)/2).compareTo(heap.get(index))<0) {
        	heap.set(index,heap.get((index-1)/2));
        	index=(index-1)/2;
        	heap.set(index,el);
        }    		
    }

    @Override
    public T extractMax() {
    	int size=heap.size();
    	if (size==0) {
    		return null;
    	}
    	else {
	    	Priority<T> max_el=heap.get(0);
	    	Priority<T> el = heap.get(size-1);
	    	heap.set(0,el);
	    	heap.remove(size-1);
	    	size=size-1;
	    	int index = 0;
	    	boolean position_found=false;
	    	while (!position_found) {
	    		if (size-1<2*index+1) {
	    			position_found=true;
	    		}
	    		else if (size-1==2*index+1) {
	    			if (el.compareTo(heap.get(2*index+1))<0) {
	    				heap.set(index, heap.get(size-1));
	    				heap.set(size-1, el);
	    			}
	    			position_found=true;    				
	    		}    		
	    		else if ((el.compareTo(heap.get(2*index+1))>=0) & (el.compareTo(heap.get(2*index+2))>=0)) {
	    			position_found=true;
	    		}
	    		else {
	    			if (heap.get(2*index+1).compareTo(heap.get(2*index+2))>=0){
	    				heap.set(index, heap.get(2*index+1));
	    				heap.set(2*index+1, el);
	    				index=index*2+1;
	    			}
	    			else {
	    				heap.set(index, heap.get(2*index+2));
	    				heap.set(2*index+2, el);
	    				index=index*2+2;
	    			}
	    		}
	    	}
	        return max_el.priority;
    	}
    }
}