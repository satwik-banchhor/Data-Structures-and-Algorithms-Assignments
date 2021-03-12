package Trie;
import java.util.ArrayList;
public class Trie<T> implements TrieInterface {
	public TrieNode<T> root;
	public ArrayList<Integer[]> levels;
	public ArrayList<Integer> levelcounts;
	int maxlevel=0;
	public Trie(){
		this.root = new TrieNode<T>(null,95);
		levels = new ArrayList<Integer[]>();
		levelcounts = new ArrayList<Integer>();
		maxlevel=0;
	}
    @Override
    public boolean delete(String word) {
    	int i=0;
    	TrieNode<T> currentnode=this.root;
    	boolean notfound=false;
    	//INV:!notfound  =>  (currentnode = that for prefix of word -- word[0..i-1]) 
    	while (i<word.length() & !notfound) {
    		int index=getindex(word.charAt(i));
    		int level=i+1;
    		TrieNode[] children = currentnode.getChildren();
    		if (children[index]==null) {    		}
    		else {
    			currentnode=children[index];
    		}
    		i++;
    	}
    	//!notfound => currentnode is the node for the word
    	if (notfound) {
    		return false;
    	}
    	else {
    		if (currentnode.getValue()==null) {
    			return false;
    		}
    		else {
    			currentnode.setValue(null);
    			if (currentnode.childcount!=0) {
    				return true;
    			}
    			TrieNode<T> currentchild = currentnode;    			
    			currentnode = currentnode.parent;
    			boolean completed = false;
    			//Updating childcounts and node value of the node for the word
    			while (!completed) {
    				currentnode.childcount--;
    				if (currentnode.childcount==0 & currentnode.getValue()==null & currentnode.parent!=null) {
    					currentnode=currentnode.parent;
    				}
    				else {
    					completed = true;
    				}
    			}
    			//Assert: Counts of all the nodes have been updated
    			//Subtracting counts due to nodes with 0 children and null values
    			currentnode=root;
    			TrieNode<T> removed_node;
    			//Removing that part of the trie that was only due to this word
    			//currentnode = node for the prefix word[0..i-1]
    			//removed node: node removed from trie
    			for (i=0;i<word.length();i++) {
    				TrieNode<T>[] children = currentnode.getChildren();
    				int index = getindex(word.charAt(i));
    				if (children[index].getValue()==null & children[index].childcount==0) {
    					removed_node = children[index];
    					remove_update(removed_node,word,i);
    					children[index] = null;
    					break;
    				}
    				else {
    					currentnode=children[index];
    				}
    			}    			
    			//Updated the trie    			
    			return true;
    		}
    	}
    }
    
    private void remove_update(TrieNode<T> trienode, String word,int i) {
    	for (int index=i;index<word.length();index++) {
    		Integer[] levelstatus = levels.get(index);
        	levelstatus[getindex(word.charAt(index))] = levelstatus[getindex(word.charAt(index))]-1; 
        	if (levelstatus[getindex(word.charAt(index))]==0) {
        		levelcounts.set(i, levelcounts.get(index)-1);
        		if (levelcounts.get(index)==0) {
            		for (int levelindex=maxlevel-1;levelindex>=index;levelindex--) {
            			levels.remove(levelindex);
            		}
            		maxlevel=index;
            		break;
            	}
        	}
    	}
    }

    @Override
    public TrieNode search(String word) {
    	int i=0;
    	TrieNode<T> currentnode=this.root;
    	boolean notfound=false;
    	//INV:currentnode = that for prefix of word -- word[0..i-1] 
    	while (i<word.length() & !notfound) {
    		int index=getindex(word.charAt(i));
    		int level=i+1;
    		TrieNode[] children = currentnode.getChildren();
    		if (children[index]==null) {
    			notfound=true;
    		}
    		else {
    			currentnode=children[index];
    		}
    		i++;
    	}
    	//!notfound => currentnode is the node for the word
    	if (notfound) {
    		return null;
    	}
    	else {
    		if (currentnode.getValue()==null) {
    			return null;
    		}
    		else {
    			return currentnode;
    		}
    	}
    }

    @Override
    public TrieNode startsWith(String prefix) {
    	int i=0;
    	TrieNode<T> currentnode=this.root;
    	boolean notfound=false;
    	//INV:currentnode = that for prefix of word -- word[0..i-1] 
    	while (i<prefix.length() & !notfound) {
    		int index=getindex(prefix.charAt(i));
    		TrieNode[] children = currentnode.getChildren();
    		if (children[index]==null) {
    			notfound=true;
    		}
    		else {
    			currentnode=children[index];
    		}
    		i++;
    	}
    	//!notfound => currentnode is the node for the word
    	if (notfound) {
    		return null;
    	}
    	else {
    		return currentnode;    		
    	}
    }

    @Override
    public void printTrie(TrieNode trieNode) {
    	String prefix="";
    	TrieNode<T> currentnode = trieNode;
    	TrieNode<T>[] children = trieNode.getChildren();
    	if (trieNode.getValue()!=null) {
    		System.out.println(trieNode.getValue().toString());
    	}
    	for (int i=0;i<95;i++) {
    		if (children[i]==null) {
    			continue;
    		}
    		else {
    			printTrie(children[i]);
    		}
    	}
    }
    
    
    @Override
    public boolean insert(String word, Object value) {    	  	
    	int i=0;
    	TrieNode<T> currentnode=this.root;
    	//INV:currentnode = that for prefix of word -- word[0..i-1] 
    	while (i<word.length()) {
    		int index=getindex(word.charAt(i));
    		int level=i+1;
    		TrieNode[] children = currentnode.getChildren();
    		if (children[index]==null) {
    			children[index]=new TrieNode<T>(null,95);
    			update(level,index);
    			children[index].parent=currentnode;
    			children[index].index=index;
    			currentnode.childcount++;
    		}
    		currentnode=children[index];    
    		i++;
    	}
    	//Assert:currentnode = that for the word with a trie node created for 
    	//each prefix with a null value if it did not exist
    	if (currentnode.getValue()!=null) {
    		return false;
    	}
    	else {
    		T insert_obj=(T)value;
    		currentnode.setValue(insert_obj);
            return true;
    	}
    }
    
    private void update(int level, int index) {
    	if (level>maxlevel) {
    		Integer[] levelstatus = new Integer[95];
    		levelstatus[index]=1;
    		levels.add(levelstatus);
    		levelcounts.add(1);
			maxlevel++;
		}
		else {
			Integer[] levelstatus = levels.get(level-1);
			if (levelstatus[index]==null) {
				levelstatus[index]=1;
				levelcounts.set(level-1, levelcounts.get(level-1)+1);
				
			}
			else {
				levelstatus[index] = levelstatus[index]+1;
			}
		}
    }
    
    @Override
    public void printLevel(int level) {
    	if (level>=maxlevel+1 | level<=0) {
    		System.out.println("Level "+level+": ");
    	}
    	else {
    		String printstr="Level "+level+": ";
    		String str = "";
    		Integer[] levelstatus = levels.get(level-1);
    		for (int i=1;i<95;i++) {
    			if (levelstatus[i]!=null) {
    				for(int j=0;j<levelstatus[i];j++) {
    					str = str + getchar(i);
    				}
    			}
    		}
    		for (int i=0;i<str.length()-1;i++) {
    			printstr=printstr+str.charAt(i)+",";
    		}
    		printstr = printstr+str.charAt(str.length()-1);
    		System.out.println(printstr);
    	}
    }
    
    @Override
    public void print() {
    	System.out.println("-------------");
    	System.out.println("Printing Trie");
    	for (int level=1;level<=maxlevel+1;level++) {
    		printLevel(level);
    	}
    	System.out.println("-------------");
    }
    
    public int getindex(Character c) {
    	return (int)c-32;
    }
    
    public String getchar(int i) {
    	return Character.toString(((char)(i+32)));
    }
}