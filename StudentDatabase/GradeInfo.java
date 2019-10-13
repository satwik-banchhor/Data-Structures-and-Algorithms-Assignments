public class GradeInfo implements GradeInfo_ {
	String letter_grade;
	int num_grade;
	GradeInfo(String grade){
		letter_grade = grade;
		num_grade = GradeInfo_.gradepoint(GradeInfo_.LetterGrade.valueOf(grade));
	}
}
