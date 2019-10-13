public class BST<K extends Comparable<K>> {
	public Node<K> root;
	
	BST(Node<K> node) {
		root = node;
	}

	public int insert(K insert_node_value) {
		Node<K> compare_node = root;
		Node<K> temp_parent = null;
		int step_count = 1;
		boolean is_left = false;
		//insert_node_value is to be inserted in a subtree of compare_node
		//step_count stores the number of nodes encountered till now
		//temp_parent is the parent of current_node
		while (compare_node!=null) {
			temp_parent = compare_node;
			if (compare_node.value.compareTo(insert_node_value)<=0) {
				compare_node = compare_node.right_child;
				is_left = false;
			}
			else {
				compare_node = compare_node.left_child;
				is_left = true;
			}
			step_count++;
		}
		//compare_node is the position at which insert_node is to be inserted
		//step_count is the total number of nodes encountered while inserting
		if (temp_parent==null) {
			root = new Node<K>(insert_node_value,temp_parent,null,null);
		}
		else {
			compare_node = new Node<K>(insert_node_value,temp_parent,null,null);
			if (is_left) {
				temp_parent.left_child = compare_node;
			}
			else {
				temp_parent.right_child = compare_node;
			}
		}
		return step_count;
	}
	
	public int delete(K delete_node_value) {
		//I : SEARCHING THE NODE TO DELETE
		//Assumption: A node with value delete_node_value exists in BST
		Node<K> compare_node = root;
		Node<K> temp_parent = root.parent;
		int step_count = 1;
		boolean is_left = false;
		//node to be deleted is not found in the BST before the compare_node
		//and will be found in the future
		//step_count stores the number of nodes encountered till now
		//is_left: true is compare_node is the left child of its parent
		while (!compare_node.value.equals(delete_node_value)) {
			temp_parent = compare_node;
			if (temp_parent.value.compareTo(delete_node_value)<=0) {
				compare_node = temp_parent.right_child;
				is_left = false;
			}
			else {
				compare_node = temp_parent.left_child;
				is_left = true;
			}
			step_count++;
		}
		//compare_node is the required node to be deleted
		//number_of_steps = number of nodes encountered while searching 
		//the node to be deleted
		//is_left: true is compare_node is the left child of its parent temp_parent
		Node<K> delete_node = compare_node;
		Node<K> delete_parent = temp_parent;
		if (delete_node.left_child==null & delete_node.right_child==null) {
			if (delete_parent==null) {
				root = null;
			}
			else if (is_left) {
				delete_parent.left_child = null;
			}
			else {
				delete_parent.right_child = null;
			}
		}
		else if (delete_node.left_child==null) {
			if (delete_parent==null) {
				//both the references pointing to the delete_node
				//now point to some other node
				delete_node.right_child.parent = null;
				root = delete_node.right_child;
			}
			else if (is_left) {
				//
				delete_parent.left_child = delete_node.right_child;
				delete_node.right_child.parent = delete_parent;
			}
			else {
				delete_parent.right_child = delete_node.right_child;
				delete_node.parent = delete_parent;
			}
			step_count++;
		}
		else if (delete_node.right_child==null) {
			if (delete_parent==null) {
				delete_node.left_child.parent = null;
				root = delete_node.left_child;
			}
			else if (is_left) {
				delete_parent.left_child = delete_node.left_child;
				delete_node.left_child.parent = delete_parent;
			}
			else {
				delete_node.parent.right_child = delete_node.left_child;
				delete_node.left_child.parent = delete_parent;
			}
			step_count++;
		}
		else {
			//TODO: (i)transfer successor 
			//(ii)shift right sub-tree of deleted node i.e. successor
			Node<K> current_node = delete_node.right_child;
			boolean moved_left = false;
			step_count++;
			//successor lies in the right sub-BST of the current_node or is the 
			//current_node itself
			while (current_node.left_child!=null) {
				current_node = current_node.left_child;
				moved_left = true;
				step_count++;
			}
			//successor is the current_node and has no left child
			Node<K> successor = current_node;
			delete_node.value = successor.value;
			//now we have to delete the successor node which has no left child
			if (moved_left) {
				//moved_left=>successor is the left child of its parent
				if (successor.right_child==null) {
					successor.parent.left_child = null;
				}
				else {
					successor.parent.left_child = successor.right_child;
					successor.right_child.parent = successor.parent;
					step_count++;
				}
			}
			else {
				//!moved_left=>successor is the right child of delete_node
				if (successor.right_child==null) {
					delete_node.right_child = successor.right_child;
				}
				else {
					delete_node.right_child = successor.right_child;
					successor.right_child.parent = delete_node;	
				}
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
		boolean is_empty = current_node==null;
		int x=0;
		int step_count = 0;
		if (!is_empty) {
			x = current_node.value.compareTo(search_node_value);
			found = current_node.value.equals(search_node_value);
			step_count++;
		}
		//is_empty tells whether the current_node is empty or not
		//found: tells whether node with search_node_value is found
		//before and including curren_node(only if the current_node is not empty)
		//Address: The path that we are following while searching
		while (!is_empty & !found) {
			if (x<=0) {
				current_node = current_node.right_child;
				address = address + "R";
			}
			else {
				current_node = current_node.left_child;
				address = address + "L";
			}
			is_empty = current_node==null;
			if (!is_empty) {
				x = current_node.value.compareTo(search_node_value);
				step_count++;
				found = current_node.value.equals(search_node_value);
			}
		}
		//found: tells whether node with search_node_value exists in BST or not
		//step_count: number of times we compared the search_node_value with a node
		if (!found) {return null;}
		Object[] ret_array = new Object[3];
		ret_array[0] = current_node;
		ret_array[1] = step_count;
		ret_array[2] = address;
		return ret_array;
	}
}