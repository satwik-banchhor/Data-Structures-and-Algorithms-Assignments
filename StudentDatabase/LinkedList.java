import java.util.Iterator;
public class LinkedList<T> implements LinkedList_<T> ,Iterable<Position_<T>>{
	int length=0;
	Position<T> head;
	Position<T> currentpos;
	LinkedList(){
		head = new Position<T>();
		currentpos = head;
	}
	public int count() {
		return length;
	}	
	public Position_<T> add(T e){		
		while (currentpos.nextpos != null) {
			currentpos = currentpos.after();
		}
		currentpos.nextpos = new Position<T>();
		currentpos = currentpos.after();
		currentpos.val = e;
		length = length + 1;
		return currentpos;
	}
	
	public Iterator<Position_<T>> iterator(){
		return (Iterator<Position_<T>>) new LListIterator();
	}
	
	public Iterator<Position_<T>> positions(){
		return this.iterator();
	}
	
	class LListIterator implements Iterator<Position_<T>>{
		Position<T> cursor;
		LListIterator(){
			cursor = head;
		}
		public boolean hasNext() {
			return (cursor.nextpos != null);
		}
		public Position_<T> next(){
			cursor = cursor.after();
			return cursor;
		}
	}
}