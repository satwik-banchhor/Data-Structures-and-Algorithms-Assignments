package Trie;


import Util.NodeInterface;


public class TrieNode<T> implements NodeInterface<T> {
	private T value;
	private TrieNode<T>[] children;
	public TrieNode<T> parent;
	public int index;
	public int childcount;
	TrieNode(T value,int size){
		this.value=value;
		childcount=0;
		this.children=new TrieNode[size];
	}
    @Override
    public T getValue() {
        return this.value;
    }
    public TrieNode<T>[] getChildren(){
    	return this.children;
    }
    public void setValue(T value) {
    	this.value=value;
    }
}