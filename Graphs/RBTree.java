
public class RBTree<T extends Comparable> {
	public RedBlackNode<T> root;
	
    public boolean insert(T key) {
    	RedBlackNode<T> insertnode=this.searchinsertpos(key);
    	if (insertnode==null) {
    		root=new RedBlackNode<T>(key,Colour.black,null,null,null);
    		return true;
    	}
    	else if (insertnode.key.compareTo(key)==0) {//Existing key case
    		return false;
    		//insertnode.getValues().add(value);
    	}
    	else {
    		if (insertnode.colour==Colour.black) {//BlackParent case
    			if (insertnode.key.compareTo(key)>0) {
    				RedBlackNode<T> toinsertnode=new RedBlackNode<T>(key,Colour.red,insertnode,null,null);
    				insertnode.leftchild=toinsertnode;
    			}
    			else{
    				RedBlackNode<T> toinsertnode=new RedBlackNode<T>(key,Colour.red,insertnode,null,null);
    				insertnode.rightchild=toinsertnode;
    			}
    		}
    		else {//Red Parent case
    			RedBlackNode<T> toinsertnode;
    			if (insertnode.key.compareTo(key)>0) {
    				toinsertnode=new RedBlackNode<T>(key,Colour.red,insertnode,null,null);
    				insertnode.leftchild=toinsertnode;
    			}
    			else{
    				toinsertnode=new RedBlackNode<T>(key,Colour.red,insertnode,null,null);
    				insertnode.rightchild=toinsertnode;
    			}
    			remove_double_red(toinsertnode);
    		}
    		return true;
    	}
    }

    public void remove_double_red(RedBlackNode<T> node) {
		if (node.parent==null) {//node is a red root
			node.colour=Colour.black;//Colour root black
		}
		else if (node.parent.colour==Colour.red) {
			String configuration="";
			RedBlackNode<T> parent=node.parent;
			if (parent.leftchild!=null) {
				if (parent.leftchild.key.compareTo(node.key)==0) {
					configuration="L";
				}
				else {
					configuration="R";
				}
			}
			else {
				configuration="R";
			}
			RedBlackNode<T> grandparent=parent.parent;
			RedBlackNode<T> uncle;
			Colour uncle_colour=Colour.black;
			//Getting configuration
			if (grandparent.leftchild!=null) {
				if (grandparent.leftchild.key.compareTo(parent.key)==0) {
					configuration="L"+configuration;
					uncle=grandparent.rightchild;
					if (uncle!=null) {
						uncle_colour=uncle.colour;
					}
				}
				else {
					configuration="R"+configuration;
					uncle=grandparent.leftchild;
					if (uncle!=null) {
						uncle_colour=uncle.colour;
					}
				}				
			}
			else {
				configuration="R"+configuration;
				uncle=grandparent.leftchild;
			}
			if (uncle_colour.compareTo(Colour.red)==0) {//Case red uncle
				grandparent.colour=Colour.red;
				uncle.colour=Colour.black;
				parent.colour=Colour.black;
				remove_double_red(grandparent);
			}
			else {//Case Black uncle
				RedBlackNode<T> t1;
				RedBlackNode<T> t2;
				RedBlackNode<T> t3;
				RedBlackNode<T> t4;
				RedBlackNode<T> t5;
				RedBlackNode<T> n=node;
				RedBlackNode<T> p=parent;
				RedBlackNode<T> g=grandparent;
//				RedBlackNode<T> u=uncle;
				RedBlackNode<T> ggp=grandparent.parent;
				if (configuration.compareTo("LL")==0) {
					t1=node.leftchild;
					t2=node.rightchild;
					t3=parent.rightchild;
//					t4=uncle.leftchild;
//					t5=uncle.rightchild;
					rotateLL(n,p,g,t1,t2,t3,ggp);
				}
				else if (configuration.compareTo("LR")==0) {
					t1=parent.leftchild;
					t2=node.leftchild;
					t3=node.rightchild;
//					t4=uncle.leftchild;
//					t5=uncle.rightchild;
					rotateLR(n,p,g,t1,t2,t3,ggp);
				}
				else if (configuration.compareTo("RL")==0) {
//					t1=uncle.leftchild;
//					t2=uncle.rightchild;
					t3=node.leftchild;
					t4=node.rightchild;
					t5=parent.rightchild;
					rotateRL(n,p,g,t3,t4,t5,ggp);
				}
				else if (configuration.compareTo("RR")==0) {
//					t1=uncle.leftchild;
//					t2=uncle.rightchild;
					t3=parent.leftchild;
					t4=node.leftchild;
					t5=node.rightchild;
					rotateRR(n,p,g,t3,t4,t5,ggp);
				}
			}
		}
	}
    
    public void rotateLL(RedBlackNode<T> n,RedBlackNode<T> p,RedBlackNode<T> g,RedBlackNode<T> t1,RedBlackNode<T> t2,RedBlackNode<T> t3, RedBlackNode<T> ggp) {
    	if (ggp==null) {//grandparent was root
    		root=p;
    	}
    	else if (ggp.leftchild!=null){
    		if (ggp.leftchild.key.compareTo(g.key)==0) {
    			ggp.leftchild=p;
    		}
    		else {
        		ggp.rightchild=p;
    		}
    	}
    	else {
    		ggp.rightchild=p;
    	}
    	g.parent=p;
    	g.colour=Colour.red;
    	g.leftchild=t3;
//    	g.rightchild=u;
    	p.parent=ggp;
    	p.colour=Colour.black;
    	p.leftchild=n;
    	p.rightchild=g;
//    	u.parent=g;
//    	u.colour=Colour.black;
//    	u.leftchild=t4;
//    	u.rightchild=t5;
    	n.parent=p;
    	n.colour=Colour.red;
    	n.leftchild=t1;
    	n.rightchild=t2;
    	if (t1!=null) {
    		t1.parent=n;
    	}
    	if (t2!=null) {
    		t2.parent=n;
    	}
    	if (t3!=null) {
    		t3.parent=g;
    	}
//    	if (t4!=null) {
//    		t4.parent=u;
//    	}
//    	if (t5!=null) {
//    		t5.parent=u;
//    	}
    }

    public void rotateLR(RedBlackNode<T> n,RedBlackNode<T> p,RedBlackNode<T> g,RedBlackNode<T> t1,RedBlackNode<T> t2,RedBlackNode<T> t3, RedBlackNode<T> ggp) {
    	if (ggp==null) {//grandparent was root
    		root=n;
    	}
    	else if (ggp.leftchild!=null){
    		if (ggp.leftchild.key.compareTo(g.key)==0) {
    			ggp.leftchild=n;
    		}
    		else {
        		ggp.rightchild=n;
    		}
    	}
    	else {
    		ggp.rightchild=n;
    	}
    	g.parent=n;
    	g.colour=Colour.red;
    	g.leftchild=t3;
//    	g.rightchild=u;
    	p.parent=n;
    	p.colour=Colour.red;
    	p.leftchild=t1;
    	p.rightchild=t2;
//    	u.parent=g;
//    	u.colour=Colour.black;
//    	u.leftchild=t4;
//    	u.rightchild=t5;
    	n.parent=ggp;
    	n.colour=Colour.black;
    	n.leftchild=p;
    	n.rightchild=g;
    	if (t1!=null) {
    		t1.parent=p;
    	}
    	if (t2!=null) {
    		t2.parent=p;
    	}
    	if (t3!=null) {
    		t3.parent=g;
    	}
//    	if (t4!=null) {
//    		t4.parent=u;
//    	}
//    	if (t5!=null) {
//    		t5.parent=u;
//    	}
    }

    public void rotateRL(RedBlackNode<T> n,RedBlackNode<T> p,RedBlackNode<T> g,RedBlackNode<T> t3,RedBlackNode<T> t4,RedBlackNode<T> t5, RedBlackNode<T> ggp) {
    	if (ggp==null) {//grandparent was root
    		root=n;
    	}
    	else if (ggp.leftchild!=null){
    		if (ggp.leftchild.key.compareTo(g.key)==0) {
    			ggp.leftchild=n;
    		}
    		else {
        		ggp.rightchild=n;
    		}
    	}
    	else {
    		ggp.rightchild=n;
    	}
    	g.parent=n;
    	g.colour=Colour.red;
//    	g.leftchild=u;
    	g.rightchild=t3;
    	p.parent=n;
    	p.colour=Colour.red;
    	p.leftchild=t4;
    	p.rightchild=t5;
//    	u.parent=g;
//    	u.colour=Colour.black;
//    	u.leftchild=t1;
//    	u.rightchild=t2;
    	n.parent=ggp;
    	n.colour=Colour.black;
    	n.leftchild=g;
    	n.rightchild=p;
//    	if (t1!=null) {
//    		t1.parent=u;
//    	}
//    	if (t2!=null) {
//    		t2.parent=u;
//    	}
    	if (t3!=null) {
    		t3.parent=g;
    	}
    	if (t4!=null) {
    		t4.parent=p;
    	}
    	if (t5!=null) {
    		t5.parent=p;
    	}
    }

    public void rotateRR(RedBlackNode<T> n,RedBlackNode<T> p,RedBlackNode<T> g,RedBlackNode<T> t3,RedBlackNode<T> t4,RedBlackNode<T> t5, RedBlackNode<T> ggp) {
    	if (ggp==null) {//grandparent was root
    		root=p;
    	}
    	else if (ggp.leftchild!=null){
    		if (ggp.leftchild.key.compareTo(g.key)==0) {
    			ggp.leftchild=p;
    		}
    		else {
        		ggp.rightchild=p;
    		}
    	}
    	else {
    		ggp.rightchild=p;
    	}
    	g.parent=p;
    	g.colour=Colour.red;
//    	g.leftchild=u;
    	g.rightchild=t3;
    	p.parent=ggp;
    	p.colour=Colour.black;
    	p.leftchild=g;
    	p.rightchild=n;
//    	u.parent=g;
//    	u.colour=Colour.black;
//    	u.leftchild=t1;
//    	u.rightchild=t2;
    	n.parent=p;
    	n.colour=Colour.red;
    	n.leftchild=t4;
    	n.rightchild=t5;
//    	if (t1!=null) {
//    		t1.parent=u;
//    	}
//    	if (t2!=null) {
//    		t2.parent=u;
//    	}
    	if (t3!=null) {
    		t3.parent=g;
    	}
    	if (t4!=null) {
    		t4.parent=n;
    	}
    	if (t5!=null) {
    		t5.parent=n;
    	}
    }

    public RedBlackNode<T> search(T key) {
    	//Returns null if not found, address/path followed to get search_node
   		RedBlackNode<T> currentnode = root;
   		boolean found = false;
   		boolean is_empty = currentnode==null;
   		int x=0;
   		if (!is_empty) {
   			x = currentnode.key.compareTo(key);
   			found = x==0;
   		}
   		//is_empty tells whether the current_node is empty or not
   		//found: tells whether node with search_node_value is found
   		//before and including curren_node(only if the current_node is not empty)
   		while (!is_empty & !found) {
   			if (x<=0) {
   				currentnode = currentnode.rightchild;
   			}
   			else {
   				currentnode = currentnode.leftchild;
   			}
   			is_empty = currentnode==null;
   			if (!is_empty) {
   				x = currentnode.key.compareTo(key);
   				found = x==0;
   			}
  		}
   		//found: tells whether node with search_node_value exists in BST or not
   		//step_count: number of times we compared the search_node_value with a node
   		if (!found) {return null;}
   		return currentnode;
    }
    
    public RedBlackNode<T> searchinsertpos(T key) {
    	//Returns null if not found, address/path followed to get search_node
    	RedBlackNode<T> currentparent = null;
   		RedBlackNode<T> currentnode = root;
   		boolean found = false;
   		boolean is_empty = currentnode==null;
   		int x=0;
   		if (!is_empty) {
   			x = currentnode.key.compareTo(key);
   			found = x==0;
   		}
   		//is_empty tells whether the current_node is empty or not
   		//found: tells whether node with search_node_value is found
   		//before and including curren_node(only if the current_node is not empty)
   		while (!is_empty & !found) {
   			currentparent=currentnode;
   			if (x<=0) {
   				currentnode = currentnode.rightchild;
   			}
   			else {
   				currentnode = currentnode.leftchild;
   			}
   			is_empty = currentnode==null;
   			if (!is_empty) {
   				x = currentnode.key.compareTo(key);
   				found = x==0;
   			}
  		}
   		//found: tells whether node with search_node_value exists in BST or not
   		//step_count: number of times we compared the search_node_value with a node
   		if (!found) {return currentparent;}
   		return currentnode;
    }
}