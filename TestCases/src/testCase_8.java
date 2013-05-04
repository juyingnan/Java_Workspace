public class testCase_8
{

	public int ExceptionTest(String input) throws Exception
	{
		int result = 0;

		result = Integer.parseInt(input);

		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		testCase_8 t = new testCase_8();
		int j=0;
		int k=0;
		try
		{
			j = t.ExceptionTest("123");
			k = t.ExceptionTest("sss");
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(j);
		System.out.println(k);
	}

}
