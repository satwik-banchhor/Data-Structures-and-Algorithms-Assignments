import java.util.Iterator;

public class Hostel implements Entity_ , Iterable<Student_>{
	String entname;
	Student_[] Students;
	int count;
	int hostelindex=0;
	Hostel(String name,int i){
		entname = name;
		count = i;
		Students = null;
	}
	public String name() {
		return entname;
	}
	public Iterator<Student_> studentList(){
		return this.iterator();
	}
	public Iterator<Student_> iterator() {
		StudentIterator iterobj = new StudentIterator();
		iterobj.SArray = Students;
		return iterobj;
	}	
}