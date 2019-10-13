import java.util.Iterator;

public class StudentIterator implements Iterator<Student_>{
	int index;
	Student_[] SArray;
	StudentIterator() {
		index = -1;
		
	}
	public boolean hasNext() {
		if (index+1 < SArray.length) {
			return true;
		}
		else {
			return false;
		}
	}
	public Student_ next() {
		index++;
		return SArray[index];
	}

}
