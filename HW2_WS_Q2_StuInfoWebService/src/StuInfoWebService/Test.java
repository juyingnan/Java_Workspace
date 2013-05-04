package StuInfoWebService;

public class Test
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		StuInfoWebService s = new StuInfoWebService();
		System.out.println(s.GetStudentName("1201210631"));
		System.out.println(s.GetStudentEmailAddress("1201210631"));
		System.out.println(s.DeleteStudentInfo("1201210631"));
		System.out.println(s.InsertStudentInfo("1201210631;鞠英男;男;软件开发;1201210631@pub.ss.pku.edu.cn"));
		System.out.println(s.UpdateStudentInfo("1201210631;鞠英男;男;软件开发;1201210631@pub.ss.pku.edu.cn"));
	}

}
