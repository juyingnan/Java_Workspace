package axis2Client;

import java.io.Console;
import java.rmi.RemoteException;
import java.util.Scanner;

import org.apache.axis2.AxisFault;
import org.tempuri.DeleteStudentInfo;
import org.tempuri.DeleteStudentInfoResponse;
import org.tempuri.GetStudentEmail;
import org.tempuri.GetStudentEmailResponse;
import org.tempuri.GetStudentGender;
import org.tempuri.GetStudentGenderResponse;
import org.tempuri.GetStudentMajor;
import org.tempuri.GetStudentMajorResponse;
import org.tempuri.GetStudentName;
import org.tempuri.GetStudentNameResponse;
import org.tempuri.InsertStudentInfo;
import org.tempuri.StuInfoWebServiceStub;
import org.tempuri.UpdateStudentInfo;

public class StuInfoClient
{

	/**
	 * @param args
	 * @throws RemoteException
	 */
	public static void main(String[] args)
	{
		Console console = System.console();

		while (true)
		{
			boolean isBreak = false;
			System.out.println("TIPS: \t1. GET \t2. DELETE \t3. INSERT \t4. UPDATE \t-1:QUIT");
			System.out.print("Please Select One Operation:");
			Scanner scanner = new Scanner(System.in);
			int i=0;
			try
			{
				i = scanner.nextInt();
			}
			catch (Exception e)
			{
				// TODO: handle exception
				System.out.println(e.getMessage());
			}
			String inputString = "";
			switch (i)
			{
				case 1:
					// GET INFO
					StuInfoWebServiceStub stub;
					try
					{
						stub = new StuInfoWebServiceStub();
						System.out.print("Please INPUT the ID_No:");
						while (inputString.length() < 3)
						{
							inputString = scanner.nextLine();
						}
						// get name
						GetStudentName param1 = new GetStudentName();
						param1.setID_No(inputString);
						GetStudentNameResponse response1 = stub.getStudentName(param1);
						System.out.print(response1.getGetStudentNameResult() + "\t");

						// get Gender
						GetStudentGender param3 = new GetStudentGender();
						param3.setID_No(inputString);
						GetStudentGenderResponse response3 = stub.getStudentGender(param3);
						System.out.print(response3.getGetStudentGenderResult() + "\t");

						// get Major
						GetStudentMajor param4 = new GetStudentMajor();
						param4.setID_No(inputString);
						GetStudentMajorResponse response4 = stub.getStudentMajor(param4);
						System.out.print(response4.getGetStudentMajorResult() + "\t");

						// get mail address
						GetStudentEmail param2 = new GetStudentEmail();
						param2.setID_No(inputString);
						GetStudentEmailResponse response2 = stub.getStudentEmail(param2);
						System.out.print(response2.getGetStudentEmailResult() + "\n");

						break;
					}
					catch (Exception e)
					{
						// TODO Auto-generated catch block
						System.out.println("Error");
					}

				case 2:
					// DEL INFO
					StuInfoWebServiceStub stub2;
					try
					{
						stub2 = new StuInfoWebServiceStub();
						System.out.print("Please INPUT the ID_No:");
						while (inputString.length() < 3)
						{
							inputString = scanner.nextLine();
						}
						// del a student
						DeleteStudentInfo param5 = new DeleteStudentInfo();
						param5.setID_No(inputString);
						DeleteStudentInfoResponse response5 = stub2.deleteStudentInfo(param5);
						System.out.println(response5.getDeleteStudentInfoResult());
						break;
					}
					catch (Exception e)
					{
						// TODO Auto-generated catch block
						System.out.println("Error");
					}
					break;
				case 3:
					// DEL INFO
					StuInfoWebServiceStub stub3;
					try
					{
						stub3 = new StuInfoWebServiceStub();
						System.out.print("Please INPUT the Info to Insert:");
						while (inputString.length() < 8)
						{
							inputString = scanner.nextLine();
						}
						// ins a student
						InsertStudentInfo param6 = new InsertStudentInfo();
						param6.setInfo(inputString);
						System.out.println(stub3.insertStudentInfo(param6).getInsertStudentInfoResult());
						break;
					}
					catch (Exception e)
					{
						// TODO Auto-generated catch block
						System.out.println("Error");
					}
					break;
				case 4:
					// DEL INFO
					StuInfoWebServiceStub stub4;
					try
					{
						stub4 = new StuInfoWebServiceStub();
						System.out.print("Please INPUT the Info to Update:");
						while (inputString.length() < 8)
						{
							inputString = scanner.nextLine();
						}
						// upd a student
						UpdateStudentInfo param7 = new UpdateStudentInfo();
						param7.setInfo(inputString);
						System.out.println(stub4.updateStudentInfo(param7).getUpdateStudentInfoResult());
						break;
					}
					catch (Exception e)
					{
						// TODO Auto-generated catch block
						System.out.println("Error");
					}
					break;
				case -1:
					isBreak = true;
					break;

				default:
					break;
			}
			if (isBreak)
			{
				System.out.println("Exit");
				break;
			}
		}
	}

}
