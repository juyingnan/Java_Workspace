package order.ss.pku.edu.cn;

public class OrderIDGenerator
{
	public String orderIDGenerator(String customerID)
	{
		java.sql.Timestamp a = new java.sql.Timestamp(System.currentTimeMillis());
		long s = a.getTime();
		return customerID + s;
	}
}
