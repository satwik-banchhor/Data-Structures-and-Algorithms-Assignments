enum Status{
	Empty, Temp_Empty, Full
}
public class KeyValueStatus<K, T> {
	public K key;
	public T value;
	public Status status;
	KeyValueStatus(){
		status = Status.Empty;
	}
}
