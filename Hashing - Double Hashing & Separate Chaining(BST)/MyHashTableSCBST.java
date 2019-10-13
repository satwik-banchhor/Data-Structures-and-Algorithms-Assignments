import java.lang.Math;
public class MyHashTableSCBST<K extends Comparable<K>,T> implements MyHashTable_<K,T>{
	private Object[] hashtable;
	private int tablesize;
	public MyHashTableSCBST(int size) {
		tablesize = size;
		hashtable = new Object[tablesize];
		for (int i=0;i<tablesize;i++) {
			BST<KeyVal<K,T>> new_BST = new BST<KeyVal<K,T>>(null);
			hashtable[i] = new_BST;
		}
	}
	public static long djb2(String str, int hashtableSize) {
	long hash = 5381;
	for (int i = 0; i < str.length(); i++) {
	hash = ((hash << 5) + hash) + str.charAt(i);
	}
	return Math.abs(hash) % hashtableSize;
	}
	
	public int insert(K key, T obj){
	   //Assumption: The hash-table does not have a KeyValue with Key key
	   KeyVal<K,T> insert_KeyVal = new KeyVal<K,T>(key,obj);
	   int index = (int) djb2(key.toString(), tablesize);
	   BST<KeyVal<K,T>> current_BST = (BST<KeyVal<K,T>>) hashtable[index];
	   return current_BST.insert(insert_KeyVal);
	}
	 
	public int update(K key, T obj) {
		//Assumption: The hash-table has a KeyVal with Key key
		KeyVal<K,T> update_KeyVal = new KeyVal<K,T>(key,obj);
		int index = (int) djb2(key.toString(), tablesize);
		BST<KeyVal<K,T>> current_BST = (BST<KeyVal<K,T>>) hashtable[index];
		Object[] ref_steps_address = current_BST.search(update_KeyVal);
		Node<KeyVal<K,T>> key_node = (Node<KeyVal<K,T>>) ref_steps_address[0];
		key_node.value = update_KeyVal;
		return (int) ref_steps_address[1];
	}
	 
	public int delete(K key) {
		//Assumption: The hash-table has a KeyVal with Key key
		KeyVal<K,T> delete_KeyVal = new KeyVal<K,T>(key,null);
		int index = (int) djb2(key.toString(), tablesize);
		BST<KeyVal<K,T>> current_BST = (BST<KeyVal<K,T>>) hashtable[index];
		return current_BST.delete(delete_KeyVal);
	}
	 
	public boolean contains(K key) {
		KeyVal<K,T> search_KeyVal = new KeyVal<K,T>(key,null);
		int index = (int) djb2(key.toString(), tablesize);
		BST<KeyVal<K,T>> current_BST = (BST<KeyVal<K,T>>) hashtable[index];
		return (current_BST.search(search_KeyVal)!=null);
	}
 
	public T get(K key) throws NotFoundException{
		KeyVal<K,T> update_KeyVal = new KeyVal<K,T>(key,null);
		int index = (int) djb2(key.toString(), tablesize);
		BST<KeyVal<K,T>> current_BST = (BST<KeyVal<K,T>>) hashtable[index];
		Object[] ref_steps_address = current_BST.search(update_KeyVal);
		if (ref_steps_address==null) {throw new NotFoundException();}
		return ((Node<KeyVal<K,T>>)ref_steps_address[0]).value.value;
	}
 
	public String address(K key) throws NotFoundException{
		KeyVal<K,T> update_KeyVal = new KeyVal<K,T>(key,null);
		int index = (int) djb2(key.toString(), tablesize);
		BST<KeyVal<K,T>> current_BST = (BST<KeyVal<K,T>>) hashtable[index];
		Object[] ref_steps_address = current_BST.search(update_KeyVal);
		if (ref_steps_address==null) {throw new NotFoundException();}
		return String.valueOf(index)+"-"+(String)ref_steps_address[2];
	}
}
