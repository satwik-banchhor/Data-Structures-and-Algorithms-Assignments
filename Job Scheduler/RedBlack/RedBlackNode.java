package RedBlack;

import Util.RBNodeInterface;

import java.util.ArrayList;
import java.util.List;

enum Colour{
	red,black
}

public class RedBlackNode<T extends Comparable, E> implements RBNodeInterface<E> {
	public Colour colour;
	public T key;
	private List<E> values;
	public RedBlackNode<T,E> leftchild;
	public RedBlackNode<T,E> rightchild;
	public RedBlackNode<T,E> parent;
	
	RedBlackNode(T key,E value,Colour colour,RedBlackNode<T,E> parent,RedBlackNode<T,E> leftchild,RedBlackNode<T,E> rightchild) {
		this.key=key;
		this.values=new ArrayList<E>();
		this.values.add(value);
		this.colour=colour;
		this.parent=parent;
		this.leftchild=leftchild;
		this.rightchild=rightchild;
	}
    public E getValue() {
        return this.values.get(0);
    }

    @Override
    public List<E> getValues() {
        return values;
    }
}
