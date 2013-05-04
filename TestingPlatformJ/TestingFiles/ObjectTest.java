public class ObjectTest{
	
	public int older(Student a, Student b){
		int id = 0;
		if(a.studentAge >= b.studentAge){
			id = a.studentID;
		}
		else{
			id = b.studentID;
		}
		return id;
	}
}
