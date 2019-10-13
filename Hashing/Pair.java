public class Pair<X, Y> implements Comparable<Pair<X, Y>>{
	private X x;
	private Y y;
	Pair(X x_val,Y y_val){
		this.x = x_val;
		this.y = y_val;
	}
	public String toString() {
		return x.toString()+y.toString();
	}
	public int compareTo(Pair<X, Y> another_pair) {
		//Using only first names to compare keys for BST
		int x = this.compareString().compareTo(another_pair.compareString());
//		if (x==0) {
//			x = this.toString().compareTo(another_pair.toString());
//		}
		return x;
	}
	public String compareString() {
		return this.x.toString();
	}
	public boolean equals(Object obj) {
		//Using full name to compare equality of keys
		Pair<X, Y> another_pair = (Pair<X, Y>) obj;
		return (this.toString().compareTo(another_pair.toString())==0); 
	}
}