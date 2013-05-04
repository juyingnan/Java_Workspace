public class testCase_7
{

	public int ExceptionTest(String input)
	{
		int result = 0;
		try
		{
			result = Integer.parseInt(input);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			System.out.println("ERROR");
		}
		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		testCase_7 t = new testCase_7();
		int j = t.ExceptionTest("123");
		int k = t.ExceptionTest("sss");
		System.out.println(j);
		System.out.println(k);
	}

}
