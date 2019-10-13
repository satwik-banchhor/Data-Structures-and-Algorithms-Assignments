import java.lang.Math;
public class MyHashTableDH<K,T> implements MyHashTable_<K,T>{
	private Object[] hashtable;
	private int tablesize;
	MyHashTableDH(int size){
		tablesize = size;
		hashtable = new Object[tablesize];
		for (int i=0;i<tablesize;i++) {
			hashtable[i] = new KeyValueStatus<K,T>();
		}
	}
	public static long djb2(String str, int hashtableSize) { 
	    long hash = 5381; 
	    for (int i = 0; i < str.length(); i++) { 
	        hash = ((hash << 5) + hash) + str.charAt(i); 
	    } 
	    return Math.abs(hash) % hashtableSize; 
	}
	public static long sdbm(String str, int hashtableSize) { 
	    long hash = 0; 
	    for (int i = 0; i < str.length(); i++) { 
	        hash = str.charAt(i) + (hash << 6) + (hash << 16) - hash; 
	    } 
	    return Math.abs(hash) % (hashtableSize - 1) + 1; 
	}
	public int insert(K key, T obj) {
		//Assumption: The hash-table does not have a slot with Key
		long i = djb2(key.toString(),tablesize);
		long j = sdbm(key.toString(),tablesize);
		int index = (int) (i);
		int num_of_hashes=1;
		//INV: Sequence of Hashes of index till & not including index are Full
		//num_of_hashes includes index and all indices before index in index's hash sequence
		while (((KeyValueStatus<K,T>)hashtable[index]).status==Status.Full){
			index = (int) ((index + j)%tablesize);
			num_of_hashes++;
		}
		//INV & !Loop_Cond => Before index:Full at index:not Full and num_of_hashes is correct count  
		((KeyValueStatus<K,T>)hashtable[index]).status = Status.Full;
		((KeyValueStatus<K,T>)hashtable[index]).key = key;
		((KeyValueStatus<K,T>)hashtable[index]).value = obj;
		return num_of_hashes;
	}
	
	public int update(K key, T obj) {
		//Assumption: The hash-table has a slot with Key
		long i = djb2(key.toString(),tablesize);
		long j = sdbm(key.toString(),tablesize);
		int index = (int) (i);
		int number_of_hashes = 1; 
		boolean found = (((KeyValueStatus<K,T>)hashtable[index]).key.equals(key));
		//INV: Key is not found in the Sequence of Hashes of index till & not including index 
		//num_of_hashes includes index and all indices before index in index's hash sequence
		while (!found){
			index = (int) ((index + j)%tablesize);
			number_of_hashes++;
			found = (((KeyValueStatus<K,T>)hashtable[index]).key.equals(key));
		}
		//INV & !Loop_Cond => Key found at index and num_of_hashes is correct count
		((KeyValueStatus<K,T>)hashtable[index]).key = key;
		((KeyValueStatus<K,T>)hashtable[index]).value = obj;
		return number_of_hashes;
	}
    public int delete(K key) {
    	//Assumption: The hash-table has a slot with Key
    	long i = djb2(key.toString(),tablesize);
		long j = sdbm(key.toString(),tablesize);
		int index = (int) (i);
		int number_of_hashes = 1; 
		boolean found = (((KeyValueStatus<K,T>)hashtable[index]).key.equals(key));
		//INV: Key is not found in the Sequence of Hashes of index till & not including index 
		//num_of_hashes includes index and all indices before index in index's hash sequence
		while (!found){
			index = (int) ((index + j)%tablesize);
			number_of_hashes++;
			found = (((KeyValueStatus<K,T>)hashtable[index]).key.equals(key));
		}
		//INV & !Loop_Cond => Key found at index and num_of_hashes is correct count
		((KeyValueStatus<K,T>)hashtable[index]).status = Status.Temp_Empty;
	    return number_of_hashes;
    }
    public boolean contains(K key) {
    	long i = djb2(key.toString(),tablesize);
		long j = sdbm(key.toString(),tablesize);
		int index = (int) (i);
		boolean found = false;
		boolean empty_at_index = (((KeyValueStatus<K,T>)hashtable[index]).status==Status.Empty);
		if (!empty_at_index) {
			found = (((KeyValueStatus<K,T>)hashtable[index]).key.equals(key));
		}
		int number_of_hashes = 1;
		//INV: Key not found Before index
		//is_empty is for current index
		//!is_empty => found is for current index
		//empty at index => found is false
		//number_of_hashes includes index
		while (!empty_at_index & !found & number_of_hashes<=tablesize){
			index = (int) ((index + j)%tablesize);
			empty_at_index = (((KeyValueStatus<K,T>)hashtable[index]).status==Status.Empty);
			if (!empty_at_index) {
				found = (((KeyValueStatus<K,T>)hashtable[index]).key.equals(key));
			}
			number_of_hashes++;
		}
		//INV & !Loop_Cond => found tells found or not at index
		boolean is_temp_empty = ((KeyValueStatus<K,T>)hashtable[index]).status==Status.Temp_Empty;
		found = (found & !is_temp_empty);
    	return found;
    }
    public T get(K key) throws NotFoundException{
    	long i = djb2(key.toString(),tablesize);
		long j = sdbm(key.toString(),tablesize);
		int index = (int) (i);
		boolean found = false;
		boolean empty_at_index = (((KeyValueStatus<K,T>)hashtable[index]).status==Status.Empty);
		if (!empty_at_index) {
			found = (((KeyValueStatus<K,T>)hashtable[index]).key.equals(key));
		}
		int number_of_hashes = 1;
		//INV: Key not found Before index
		//is_empty is for current index
		//!is_empty => found is for current index
		//empty at index => found is false
		//number_of_hashes includes index
		while (!empty_at_index & !found & number_of_hashes<=tablesize){
			index = (int) ((index + j)%tablesize);
			empty_at_index = (((KeyValueStatus<K,T>)hashtable[index]).status==Status.Empty);
			if (!empty_at_index) {
				found = (((KeyValueStatus<K,T>)hashtable[index]).key.equals(key));
			}
			number_of_hashes++;
		}
		//INV & !Loop_Cond => found tells found or not
		boolean is_temp_empty = ((KeyValueStatus<K,T>)hashtable[index]).status==Status.Temp_Empty;
		found = (found & !is_temp_empty);
   		if (!found) {
   			throw new NotFoundException();
   		}
   		return (T) ((KeyValueStatus<K,T>)hashtable[index]).value;
    }
    public String address(K key) throws NotFoundException {
    	long i = djb2(key.toString(),tablesize);
		long j = sdbm(key.toString(),tablesize);
		int index = (int) (i);
		boolean found = false;
		boolean empty_at_index = (((KeyValueStatus<K,T>)hashtable[index]).status==Status.Empty);
		if (!empty_at_index) {
			found = (((KeyValueStatus<K,T>)hashtable[index]).key.equals(key));
		}
		int number_of_hashes = 1;
		//INV: Key not found Before index
		//is_empty is for current index
		//!is_empty => found is for current index
		//empty at index => found is false
		//number_of_hashes includes index
		while (!empty_at_index & !found & number_of_hashes<=tablesize){
			index = (int) ((index + j)%tablesize);
			empty_at_index = (((KeyValueStatus<K,T>)hashtable[index]).status==Status.Empty);
			if (!empty_at_index) {
				found = (((KeyValueStatus<K,T>)hashtable[index]).key.equals(key));
			}
			number_of_hashes++;
		}
		//INV & !Loop_Cond => found tells found or not
		boolean is_temp_empty = ((KeyValueStatus<K,T>)hashtable[index]).status==Status.Temp_Empty;
		found = (found & !is_temp_empty);
   		if (!found) {
   			throw new NotFoundException();
 
   		}
   		return String.valueOf(index);
    }
}