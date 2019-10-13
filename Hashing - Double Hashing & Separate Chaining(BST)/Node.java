public class Node<K> {
	//if a Node has a non-null value then its children are Nodes with null values
	//for the same of value comparison for insertion
	public Node<K> right_child;
	public Node<K> left_child;
	public Node<K> parent;
	public K value;
	Node(K val, Node<K> parent_node, Node<K> left_node, Node<K> right_node){
		value = val;
		parent = parent_node;
		right_child = right_node;
		left_child = left_node;
	}
}
