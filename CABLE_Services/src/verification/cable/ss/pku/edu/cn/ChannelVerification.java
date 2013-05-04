package verification.cable.ss.pku.edu.cn;

import java.sql.*;

public class ChannelVerification
{

	private java.sql.Connection	connection		= null;

	private final String		url				= "jdbc:sqlserver://";
	private final String		serverName		= "BUNNY-PC\\SQLEXPRESS";
	private final String		userName		= "sa";
	private final String		password		= "test";

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

	public String GetChannelInfo(String CustomerID, int EventID)
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
				str_sql = "SELECT " + "*" + " from CableDatabase.dbo.CustomerCableInfo where CustomerID='" + CustomerID+"'";
				ResultSet rs = stmt.executeQuery(str_sql);
				if (rs.next())
				{
					returnValueString = rs.getString("channel" + EventID);
				}
				else
				{
					returnValueString = "0";
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

	public String UpdChannelInfo(String CustomerID, int EventID)
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

				str_sql = "UPDATE CableDatabase.dbo.CustomerCableInfo SET Channel" + EventID
						+ "='True' WHERE CustomerID='" + CustomerID+"'";
				if (stmt.executeUpdate(str_sql) == 1)
				{
					returnValueString = "1";
				}
				else
				{
					returnValueString = "0";
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

//	public static void main(String[] args)
//	{
//		// TODO Auto-generated method stub
//		ChannelVerification c = new ChannelVerification();
//		String s = new String();
//		s = c.GetChannelInfo("John", 4);
//		System.out.println(s);
//		
//		String s2 = new String();
//		s2 =c.UpdChannelInfo("John", 4);
//		System.out.println(s2);
//
//	}
}
