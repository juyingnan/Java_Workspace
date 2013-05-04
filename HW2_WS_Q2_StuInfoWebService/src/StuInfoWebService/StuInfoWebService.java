package StuInfoWebService;

import java.sql.*;

public class StuInfoWebService
{
	//Get Student Name
	public String GetStudentName(String ID_No)
	{
		StuInfoDBOperation s = new StuInfoDBOperation();
		return s.GetStudentInfo("Name", ID_No);
	}

	//Get Student Email Address
	public String GetStudentEmailAddress(String ID_No)
	{
		StuInfoDBOperation s = new StuInfoDBOperation();
		return s.GetStudentInfo("Email_Address", ID_No);
	}
	
	//Get Student Gender
	public String GetStudentGender(String ID_No)
	{
		StuInfoDBOperation s = new StuInfoDBOperation();
		return s.GetStudentInfo("Gender", ID_No);
	}
	
	//Get Student Major
	public String GetStudentMajor(String ID_No)
	{
		StuInfoDBOperation s = new StuInfoDBOperation();
		return s.GetStudentInfo("Major", ID_No);
	}
	
	//Delete one Student, return the row affected
	public String DeleteStudentInfo(String ID_No)
	{
		StuInfoDBOperation s = new StuInfoDBOperation();
		return s.DelStudentInfo(ID_No);
	}
	
	//Insert one Student, return the row affected
	public String InsertStudentInfo(String Info)
	{
		StuInfoDBOperation s = new StuInfoDBOperation();
		return s.InsStudentInfo(Info);
	}
	
	//Update one Student, return the row affected
	public String UpdateStudentInfo(String Info)
	{
		StuInfoDBOperation s = new StuInfoDBOperation();
		return s.UpdStudentInfo(Info);
	}

}