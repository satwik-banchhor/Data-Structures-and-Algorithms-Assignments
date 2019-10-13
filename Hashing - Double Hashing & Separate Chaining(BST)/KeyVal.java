public class KeyVal<K extends Comparable<K>, T> implements Comparable<KeyVal<K,T>>{
	public K key;
	public T value;
	KeyVal(K new_key, T new_value){
		key = new_key;
		value = new_value;
	}
	public int compareTo(KeyVal<K,T> another_KeyVal) {
		return this.key.compareTo(another_KeyVal.key);
	}
	public boolean equals(Object obj) {
		//Using full name to compare equality of keys
		KeyVal<K,T> another_pair = (KeyVal<K,T>) obj;
		return (this.key.equals(another_pair.key)); 
	}
}
