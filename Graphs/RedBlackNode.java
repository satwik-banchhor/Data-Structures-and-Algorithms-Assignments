
enum Colour{
	red,black
}

public class RedBlackNode<T extends Comparable>{
	public Colour colour;
	public T key;
	public RedBlackNode<T> leftchild;
	public RedBlackNode<T> rightchild;
	public RedBlackNode<T> parent;
	
	RedBlackNode(T key,Colour colour,RedBlackNode<T> parent,RedBlackNode<T> leftchild,RedBlackNode<T> rightchild) {
		this.key=key;
		this.colour=colour;
		this.parent=parent;
		this.leftchild=leftchild;
		this.rightchild=rightchild;
	}
}
