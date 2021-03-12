package PriorityQueue;

public class Priority<T extends Comparable> implements Comparable<Priority<T>> {
	public T priority;
	public int index;
	public Priority(T value,int index) {
		this.priority = value;
		this.index = index;
	}

	public int compareTo(Priority<T> p2) {
		if (this.priority.compareTo(p2.priority)!=0) {
			return this.priority.compareTo(p2.priority);
		}
		else {
			if (index<p2.index) {
				return 1;
			}
			else {
				return -1;
			}
		}
	}
	
}
