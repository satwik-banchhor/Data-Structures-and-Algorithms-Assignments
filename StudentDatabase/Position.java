public class Position<T> implements Position_<T> {
	public T val;
	public Position<T> nextpos;
	Position(){
		val = null;
		nextpos = null;
	}
	public T value() {
		return val;
	}
	public Position<T> after(){
		return nextpos;
	}
}