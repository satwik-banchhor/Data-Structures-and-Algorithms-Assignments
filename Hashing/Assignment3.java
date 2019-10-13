import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Assignment3 {
//	static int number_of_insertions=0;//remove
//	static int total_Steps=0;//remove
	private static void process_inputs(MyHashTable_<Pair<String,String>,Student> hashtable,String input_file_path) {
		BufferedReader fileiter = null;
		try {
			fileiter = new BufferedReader(new FileReader(new File(input_file_path)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line = "";
		try {
			line = fileiter.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (line != null) {
			String[] input = line.split(" ");
			if (input[0].compareTo("insert")==0) {
				Pair<String,String> Insert_Key = new Pair<String,String>(input[1],input[2]);
				Student Insert_Value = new Student(input[1],input[2],input[3],input[4],input[5]);
				if (hashtable.contains(Insert_Key)){
					System.out.println("E");
				}
				else {
					int steps = hashtable.insert(Insert_Key, Insert_Value);
					System.out.println(steps);
//					number_of_insertions++; //remove
//					total_Steps = total_Steps + steps;//remove
				}
			}
			else if (input[0].compareTo("update")==0) {
				Pair<String,String> Update_Key = new Pair<String,String>(input[1],input[2]);
				Student Update_Value = new Student(input[1],input[2],input[3],input[4],input[5]);
				if (hashtable.contains(Update_Key)){
					System.out.println(hashtable.update(Update_Key, Update_Value));
				}
				else {
					System.out.println("E");
				}				
			}
			else if (input[0].compareTo("delete")==0) {
				Pair<String,String> Delete_Key = new Pair<String,String>(input[1],input[2]);
				if (hashtable.contains(Delete_Key)){
					System.out.println(hashtable.delete(Delete_Key));
				}
				else {
					System.out.println("E");
				}
			}
			else if (input[0].compareTo("contains")==0) {
				Pair<String,String> Membership_Check_Key = new Pair<String,String>(input[1],input[2]);
				if (hashtable.contains(Membership_Check_Key)) {
					System.out.println("T");
				}
				else {
					System.out.println("F");
				}
			}
			else if (input[0].compareTo("get")==0) {
				Pair<String,String> Get_Key = new Pair<String,String>(input[1],input[2]);
				try {
					Student Got_Obj = hashtable.get(Get_Key);
					String f = Got_Obj.fname();
					String l = Got_Obj.lname();
					String h = Got_Obj.hostel();
					String d = Got_Obj.department();
					String c = Got_Obj.cgpa();
					System.out.println(f+" "+l+" "+h+" "+d+" "+c);
				} catch (NotFoundException e) {
					System.out.println("E");
				}
			}
			else if (input[0].compareTo("address")==0) {
				Pair<String,String> Address_Key = new Pair<String,String>(input[1],input[2]);
				try {
					String address_of_key = hashtable.address(Address_Key);
					System.out.println(address_of_key);
				} catch (NotFoundException e) {
					System.out.println("E");
				}
			}
			try {
				line = fileiter.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String args[]) {
		int tablesize = Integer.valueOf(args[0]);
		String input_file_path = args[2];
		String implementation_type = args[1];
		MyHashTable_<Pair<String,String>, Student> hashtable;
		if (implementation_type.compareTo("DH")==0) {
			hashtable = new MyHashTableDH<Pair<String,String>,Student>(tablesize);
		}
		else{hashtable = new MyHashTableSCBST<Pair<String,String>,Student>(tablesize);
		}
		process_inputs(hashtable,input_file_path);
//		float av_steps = Float.valueOf(total_Steps)/number_of_insertions;//remove
//		System.out.println(av_steps);
	}
}
