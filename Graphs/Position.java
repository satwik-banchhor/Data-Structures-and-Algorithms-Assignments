public class Position<T>{
	public T val;
	public Position<T> prevpos;
	public Position<T> nextpos;
	Position(T e){
		this.val = e;
		this.prevpos=null;
		this.nextpos = null;
	}
}