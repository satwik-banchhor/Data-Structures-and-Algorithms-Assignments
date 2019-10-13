import java.util.Iterator;

public class Department implements Entity_ , Iterable<Student_>{
	String entname;
	Student_[] Students;
	int count;
	int depindex=0;
	Department(String name,int i){
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
