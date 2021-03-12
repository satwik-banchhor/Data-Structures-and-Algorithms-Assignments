public class LinkedList<T extends Comparable<T>> implements Comparable<LinkedList<T>>{
	int length=0;
	Position<T> head;
	Position<T> end;
	
	public void add(T e){		
		if (this.end==null) {//LinkedList is empty
			this.head = new Position<T>(e);
			this.end=this.head;
			this.length++;
		}
		else {
			this.end.nextpos=new Position<T>(e);
			this.end.nextpos.prevpos = this.end;
			this.end=this.end.nextpos;
			this.length++;
		}
	}
	
	public void remove(Position<T> e) {
		if (this.length==1) {//(this.head.val.compareTo(e.val)==0 && this.end.val.compareTo(e.val)==0) {//head=end=e
			this.head=null;
			this.end=null;
			this.length--;
			return;
		}
		if (e.prevpos==null) {//(this.head.val.compareTo(e.val)==0) {//e=head
			this.head = this.head.nextpos;
			this.head.prevpos = null;
			this.length--;
			return;
		}
		if (e.nextpos==null) {//(this.end.val.compareTo(e.val)==0) {//e=end
			this.end = this.end.prevpos;
			this.end.nextpos = null;
			this.length--;
			return;
		}
		Position<T> prev = e.prevpos;
		Position<T> next = e.nextpos;
		prev.nextpos = next;
		next.prevpos = prev;		
		this.length--;
	}

	@Override
	public int compareTo(LinkedList<T> o) {
		return 0;
	}
	
	//DEBUG
	public String toString() {
		String return_string = "";
		Position<T> iterpos = this.head;
		while (iterpos!=null) {
			return_string = return_string+iterpos.val.toString()+"-->";
			iterpos = iterpos.nextpos;
		}
		return return_string;
	}
}