public class testCase_9
{

	public int ExceptionTest(String input)
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
		testCase_9 t = new testCase_9();
		int j = 0;
		int k = 0;
		j = t.ExceptionTest("123");
		k = t.ExceptionTest("sss");
		System.out.println(j);
		System.out.println(k);
	}

}
