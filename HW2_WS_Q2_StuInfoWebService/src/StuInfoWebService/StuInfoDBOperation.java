package StuInfoWebService;

import java.*;
import java.sql.*;

public class StuInfoDBOperation
{

	private java.sql.Connection	connection		= null;

	private final String		url				= "jdbc:sqlserver://";
	private final String		serverName		= "BUNNY-PC\\SQLEXPRESS";
	private final String		portNumber		= "1433";
	private final String		databaseName	= "StuInfoDB";
	private final String		userName		= "sa";
	private final String		password		= "test";

	// constructor
	public StuInfoDBOperation()
	{
		// TODO Auto-generated constructor stub
	}

	private String getConnectionUrl()
	{
		return url + serverName
				+ ";Integrated Security=True;Connect Timeout=15;Encrypt=False;TrustServerCertificate=False";
	}

	private java.sql.Connection getConnection()
	{
		try
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(getConnectionUrl(), userName, password);
			// if (connection != null)
			// System.out.println("Connection Successful!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			// System.out.println("Error Trace in getConnection() : " +
			// e.getMessage());
		}
		return connection;
	}

	private void closeConnection()
	{
		try
		{
			if (connection != null)
				connection.close();
			connection = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String GetStudentInfo(String Key, String ID_No)
	{
		java.sql.Statement stmt = null;
		String str_sql = "";
		String returnValueString;

		try
		{
			connection = this.getConnection();
			stmt = connection.createStatement();

			if (connection != null)
			{
				str_sql = "SELECT " + "*" + " from StuInfoDB.dbo.WSStudent where ID_No=" + ID_No;
				ResultSet rs = stmt.executeQuery(str_sql);
				if (rs.next())
				{
					returnValueString = rs.getString(Key);
				}
				else
				{

					returnValueString = "0 found";
				}
			}
			else
			{
				returnValueString = "Error: No active Connection";
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();

			returnValueString = e.getMessage();
		}
		finally
		{
			if (stmt != null)
			{
				try
				{
					stmt.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			closeConnection();
		}
		return returnValueString;
	}

	public String DelStudentInfo(String ID_No)
	{
		java.sql.Statement stmt = null;
		String str_sql = "";
		String returnValueString;

		try
		{
			connection = this.getConnection();
			stmt = connection.createStatement();

			if (connection != null)
			{
				str_sql = "DELETE from StuInfoDB.dbo.WSStudent where ID_No=" + ID_No;
				if (stmt.executeUpdate(str_sql) == 1)
				{
					returnValueString = "Success, 1 found and 1 deleted";
				}
				else
				{
					returnValueString = "Failed, 0 found and 0 deleted";
				}
			}
			else
			{
				returnValueString = "Error: No active Connection";
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();

			returnValueString = e.getMessage();
		}
		finally
		{
			if (stmt != null)
			{
				try
				{
					stmt.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			closeConnection();
		}
		return returnValueString;

	}

	public String InsStudentInfo(String Info)
	{
		java.sql.Statement stmt = null;
		String str_sql = "";
		String returnValueString;

		try
		{
			connection = this.getConnection();
			stmt = connection.createStatement();

			if (connection != null)
			{
				String[] StuInfo = Info.split(";");
				if (StuInfo.length == 5)
				{
					for (int i = 0; i < StuInfo.length; i++)
					{
						StuInfo[i] = "'" + StuInfo[i] + "'";
					}
					str_sql = "INSERT into StuInfoDB.dbo.WSStudent(ID_No,Name,Gender,Major,Email_Address) VALUES ("
							+ StuInfo[0] + "," + StuInfo[1] + "," + StuInfo[2] + "," + StuInfo[3] + "," + StuInfo[4]
							+ ")";
					if (stmt.executeUpdate(str_sql) == 1)
					{
						returnValueString = "Success, 1 inserted";
					}
					else
					{
						returnValueString = "Failed, 0 inserted";
					}

				}
				else
				{
					returnValueString = "Failed, illegal parameter";
				}
			}
			else
			{
				returnValueString = "Error: No active Connection";
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();

			returnValueString = e.getMessage();
		}
		finally
		{
			if (stmt != null)
			{
				try
				{
					stmt.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			closeConnection();
		}
		return returnValueString;

	}

	public String UpdStudentInfo(String Info)
	{
		java.sql.Statement stmt = null;
		String str_sql = "";
		String returnValueString;

		try
		{
			connection = this.getConnection();
			stmt = connection.createStatement();

			if (connection != null)
			{
				String[] StuInfo = Info.split(";");
				if (StuInfo.length == 5)
				{
					for (int i = 0; i < StuInfo.length; i++)
					{
						StuInfo[i] = "'" + StuInfo[i] + "'";
					}
					str_sql = "UPDATE StuInfoDB.dbo.WSStudent SET Name=" + StuInfo[1] + ",Gender=" + StuInfo[2]
							+ ",Major=" + StuInfo[3] + ",Email_Address=" + StuInfo[4] + "WHERE ID_No=" + StuInfo[0];
					if (stmt.executeUpdate(str_sql) == 1)
					{
						returnValueString = "Success, 1 updated";
					}
					else
					{
						returnValueString = "Failed, 0 updated";
					}

				}
				else
				{
					returnValueString = "Failed, illegal parameter";
				}
			}
			else
			{
				returnValueString = "Error: No active Connection";
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();

			returnValueString = e.getMessage();
		}
		finally
		{
			if (stmt != null)
			{
				try
				{
					stmt.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			closeConnection();
		}
		return returnValueString;
	}
}
