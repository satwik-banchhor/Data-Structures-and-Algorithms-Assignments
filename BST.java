public class BST<K extends Comparable<K>> {
	public Node<K> root;
	
	BST(Node<K> node) {
		root = node;
	}

	public int insert(K insert_node_value) {
		Node<K> temp_parent = null;
		Node<K> compare_node = this.root;
		int step_count = 1;
		//insert_node is to be inserted in a subtree of compare_node
		//step_count stores the number of nodes encountered till now
		while (compare_node.value!=null) {
			temp_parent = compare_node;
			if (compare_node.value.compareTo(insert_node_value)<0) {
				compare_node = compare_node.right_child;
			}
			else {
				compare_node = compare_node.left_child;
			}
			step_count++;
		}
		//compare_node is the position at which insert_node is to be inserted
		//step_count is the total number of nodes encountered while inserting
		compare_node.value = insert_node_value;
		compare_node.parent = temp_parent;
		compare_node.left_child = new Node<K>(null); 
		compare_node.right_child = new Node<K>(null);
		return step_count;
	}
	
	public int delete(K delete_node_value) {
		//Assumption: A node with value delete_node_value exists in BST
		Node<K> compare_node = root;
		int step_count = 1;
		boolean is_left = false;
		//node to be deleted is not found in the BST before the compare_node
		//and will be found in the future
		//step_count stores the number of nodes encountered till now
		//is_left: true is compare_node is the left child of its parent
		while (compare_node.value.compareTo(delete_node_value)!=0) {
			if (compare_node.value.compareTo(delete_node_value)<0) {
				compare_node = compare_node.right_child;
				is_left = false;
			}
			else {
				compare_node = compare_node.left_child;
				is_left = true;
			}
			step_count++;
		}
		//compare_node is the required node to be deleted
		//number_of_steps = number of nodes encountered while searching 
		//the node to be deleted
		//is_left: true is compare_node is the left child of its parent
		Node<K> delete_node = compare_node;
		if (delete_node.left_child.value==null & delete_node.right_child.value==null) {
			delete_node.value = null;
			delete_node.left_child = null;
			delete_node.right_child = null;
		}
		else if (delete_node.left_child.value==null) {
			if (delete_node.parent==null) {
				root = delete_node.right_child;
			}
			else if (is_left) {
				delete_node.parent.left_child = delete_node.right_child;
			}
			else {
				delete_node.parent.right_child = delete_node.right_child;
			}
			step_count++;
		}
		else if (delete_node.right_child.value==null) {
			if (delete_node.parent==null) {
				root = delete_node.right_child;
			}
			else if (is_left) {
				delete_node.parent.left_child = delete_node.left_child;
			}
			else {
				delete_node.parent.right_child = delete_node.left_child;
			}
			step_count++;
		}
		else {
			//TODO: (i)transfer predecessor (ii)shift left sub-tree of deleted node
			Node<K> current_node = delete_node.left_child;
			boolean moved_right = false;
			step_count++;
			//predecessor lies in the right sub-BST of the current_node or is the 
			//current_node itself
			while (current_node.right_child.value!=null) {
				current_node = current_node.right_child;
				moved_right = true;
				step_count++;
			}
			//predecessor is the current_node
			delete_node.value = current_node.value;
			if (current_node.left_child.value==null) {
				current_node.parent.right_child = current_node.left_child;
			}
			if (moved_right) {
				current_node.parent.right_child = current_node.left_child;
				step_count++;
			}
			else {
				current_node.parent.left_child = current_node.left_child;
				step_count++;
			}
			//We have deleted current_node as its right BST is empty
			//and its left is shifted is to its position
		}
		return step_count;
	}
	
	public Object[] search(K search_node_value) {
		//Returns null if not found, address/path followed to get search_node
		String address = "";
		Node<K> current_node = root;
		boolean found = false;
		boolean is_empty = current_node.value==null;
		int x=0;
		int step_count = 1;
		if (!is_empty) {
			x = current_node.value.compareTo(search_node_value);
			found = x==0;
		}
		//is_empty tells whether the current_node is empty or not
		//found: tells whether node with search_node_value is found
		//before and including curren_node(only if the current_node is not empty)
		//Address: The path that we are following while searching
		while (!is_empty & !found) {
			if (x<0) {
				current_node = current_node.right_child;
				address = address + "R";
			}
			else {
				current_node = current_node.left_child;
				address = address + "L";
			}
			step_count++;
			is_empty = current_node.value==null;
			if (!is_empty) {
				x = current_node.value.compareTo(search_node_value);
				found = x==0;
			}
		}
		//found: tells whether node with search_node_value exists in BST or not
		if (!found) {return null;}
		Object[] ret_array = new Object[3];
		ret_array[0] = current_node;
		ret_array[1] = step_count;
		ret_array[2] = address;
		return ret_array;
	}
}