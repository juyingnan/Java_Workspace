import java.*;
import java.sql.*;

public class test_connection
{
	private java.sql.Connection	con				= null;
	private final String		url				= "jdbc:sqlserver://";
	private final String		serverName		= "BUNNY-PC\\SQLEXPRESS";
	private final String		portNumber		= "1433";
	private final String		databaseName	= "StuInfoDB";
	private final String		userName		= "sa";
	private final String		password		= "test";

	// Constructor
	public test_connection()
	{
	}

	private String getConnectionUrl()
	{
		// return url + serverName + ";databaseName=" + databaseName + ";";
		return "jdbc:sqlserver://BUNNY-PC\\SQLEXPRESS;Integrated Security=True;Connect Timeout=15;Encrypt=False;TrustServerCertificate=False";
	}

	private java.sql.Connection getConnection()
	{
		try
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(getConnectionUrl(), userName, password);
			if (con != null)
				System.out.println("Connection Successful!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Error Trace in getConnection() : " + e.getMessage());
		}
		return con;
	}

	/*
	 * Display the driver properties, database details
	 */

	public void displayDbProperties()
	{
		java.sql.DatabaseMetaData dm = null;
		java.sql.ResultSet rs = null;
		try
		{
			con = this.getConnection();
			if (con != null)
			{
				dm = con.getMetaData();
				System.out.println("Driver Information");
				System.out.println("\tDriver Name: " + dm.getDriverName());
				System.out.println("\tDriver Version: " + dm.getDriverVersion());
				System.out.println("\nDatabase Information ");
				System.out.println("\tDatabase Name: " + dm.getDatabaseProductName());
				System.out.println("\tDatabase Version: " + dm.getDatabaseProductVersion());
				System.out.println("Avalilable Catalogs ");
				rs = dm.getCatalogs();
				while (rs.next())
				{
					System.out.println("\tcatalog: " + rs.getString(1));
				}
				rs.close();
				rs = null;
			}
			else
				System.out.println("Error: No active Connection");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		dm = null;
	}

	private void closeConnection()
	{
		try
		{
			if (con != null)
				con.close();
			con = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception
	{
		test_connection myDbTest = new test_connection();
		myDbTest.displayDbProperties();
	}
}