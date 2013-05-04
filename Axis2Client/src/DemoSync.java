import com.sun.tools.ws.processor.model.Service;

import org.tempuri.*;

public class DemoSync
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		try
		{
			StuInfoWebServiceStub stub = new StuInfoWebServiceStub();

			// get name
			GetStudentName param1 = new GetStudentName();
			param1.setID_No("1201210631");
			GetStudentNameResponse response1 = stub.getStudentName(param1);
			System.out.println(response1.getGetStudentNameResult());

			// get Gender
			GetStudentGender param3 = new GetStudentGender();
			param3.setID_No("1201210631");
			GetStudentGenderResponse response3 = stub.getStudentGender(param3);
			System.out.println(response3.getGetStudentGenderResult());

			// get Major
			GetStudentMajor param4 = new GetStudentMajor();
			param4.setID_No("1201210631");
			GetStudentMajorResponse response4 = stub.getStudentMajor(param4);
			System.out.println(response4.getGetStudentMajorResult());

			// get mail address
			GetStudentEmail param2 = new GetStudentEmail();
			param2.setID_No("1201210631");
			GetStudentEmailResponse response2 = stub.getStudentEmail(param2);
			System.out.println(response2.getGetStudentEmailResult());
			
			//del a student
			DeleteStudentInfo param5 = new DeleteStudentInfo();
			param5.setID_No("1201219999");
			DeleteStudentInfoResponse response5 = stub.deleteStudentInfo(param5);
			System.out.println(response5.getDeleteStudentInfoResult());
			
			//ins a student
			InsertStudentInfo param6 = new InsertStudentInfo();
			param6.setInfo("1201219999;路人甲;男;软件开发;1201219999@com");
			System.out.println(stub.insertStudentInfo(param6).getInsertStudentInfoResult());
			
			//upd a student
			UpdateStudentInfo param7= new UpdateStudentInfo();
			param7.setInfo("1201219999;路人甲;男;软件开发;1201219999@pub.ss.pku.edu.cn");
			System.out.println(stub.updateStudentInfo(param7).getUpdateStudentInfoResult());
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
}
