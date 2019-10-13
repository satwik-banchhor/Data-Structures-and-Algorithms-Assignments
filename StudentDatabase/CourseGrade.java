public class CourseGrade implements CourseGrade_{
	String coursetitle_;
	String coursenum_;
	String grade_;
	CourseGrade(String title,String num,String grad){
		coursetitle_=title;
		coursenum_=num;
		grade_=grad;
	}
	public String coursetitle() {
		return coursetitle_;
	}

	public String coursenum() {
		return coursenum_;
	}

	public GradeInfo grade() {
		return new GradeInfo(grade_);		
	}
}