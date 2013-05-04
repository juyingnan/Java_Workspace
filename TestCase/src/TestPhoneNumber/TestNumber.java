package TestPhoneNumber;

import TestInt.TestInt;

public class TestNumber
{
	public String[] TestingCases_Scope_Boundry()
	{
		long random1 = (long) (Math.random() * 10);
		long random2 = (long) (Math.random() * 10);
		long random3 = (long) (Math.random() * 10);
		long random4 = (long) (Math.random() * 10);
		long random5 = (long) (Math.random() * 10);
		long random6 = (long) (Math.random() * 10);
		long random7 = (long) (Math.random() * 10);
		long random8 = (long) (Math.random() * 10);
		long random9 = (long) (Math.random() * 10);
		long random10 = (long) (Math.random() * 10);
		long random11 = (long) (Math.random() * 10);
		long[] random = { random1, random2, random3, random4, random5, random6, random7, random8, random9, random10,
				random11 };
		String number1, number2, number3;
		long temp = 1;
		for (int i = 1; i < 9; i++)
		{

			temp = temp * 10 + random[i];

		}
		number1 = 1 + Long.toString(temp);

		temp = 1;
		for (int i = 1; i < 10; i++)
		{
			temp = temp * 10 + random[i];
		}
		number2 = 1 + Long.toString(temp);

		temp = 1;
		for (int i = 1; i < 11; i++)
		{
			temp = temp * 10 + random[i];
		}
		number3 = 1 + Long.toString(temp);
		String[] result = { number1, number2, number3 };
		return result;
	}

	public String[] TestingCases_Scope_Partition()
	{
		long random1 = (long) (Math.random() * 10);
		long random2 = (long) (Math.random() * 10);
		long random3 = (long) (Math.random() * 10);
		long random4 = (long) (Math.random() * 10);
		long random5 = (long) (Math.random() * 10);
		long random6 = (long) (Math.random() * 10);
		long random7 = (long) (Math.random() * 10);
		long random8 = (long) (Math.random() * 10);
		long random9 = (long) (Math.random() * 10);
		long random10 = (long) (Math.random() * 10);
		long random11 = (long) (Math.random() * 10);
		long[] random = { random1, random2, random3, random4, random5, random6, random7, random8, random9, random10,
				random11 };
		String numberError, number2, number3;
		long temp = 1;
		for (int i = 1; i < 10; i++)
		{
			temp = temp * 10 + random[i];
		}
		number2 = 1 + Long.toString(temp);
		numberError = Long.toString((long) (Math.random() * 10 + 2));
		String[] result = { number2, numberError };
		return result;
	}

	public String[] TestingCases_Scope_Random()
	{
		long random1 = (long) (Math.random() * 10);
		long random2 = (long) (Math.random() * 10);
		long random3 = (long) (Math.random() * 10);
		long random4 = (long) (Math.random() * 10);
		long random5 = (long) (Math.random() * 10);
		long random6 = (long) (Math.random() * 10);
		long random7 = (long) (Math.random() * 10);
		long random8 = (long) (Math.random() * 10);
		long random9 = (long) (Math.random() * 10);
		long random10 = (long) (Math.random() * 10);
		long random11 = (long) (Math.random() * 10);
		long[] random = { random1, random2, random3, random4, random5, random6, random7, random8, random9, random10,
				random11 };
		String number1, number2, number3;
		long temp = 1;
		for (int i = 1; i < 10; i++)
		{
			temp = temp * 10 + random[i];
		}
		number1 = random1 + Long.toString(temp);
		number2 = random2 + Long.toString(temp);
		number3 = random3 + Long.toString(temp);
		// numberError = Long.toString((long) (Math.random() * 10 + 2));
		String[] result = { number1, number2, number3 };
		return result;
	}

	public String[] TestingCases_Scope_All()
	{
		String[] result1 = TestingCases_Scope_Partition();
		String[] result2 = TestingCases_Scope_Boundry();
		String[] result3 = TestingCases_Scope_Random();
		String[] result = new String[20];
		return result;
	}

	public static void main(String[] args)
	{
		TestNumber t = new TestNumber();
		String[] r1 = t.TestingCases_Scope_Boundry();
		String[] r2 = t.TestingCases_Scope_Partition();
		String[] r3 = t.TestingCases_Scope_Random();

		System.out.println("*****************************");
		for (String i : r1)
		{
			System.out.print(i + " ");
		}
		System.out.println();
		System.out.println("*****************************");
		for (String i : r2)
		{
			System.out.print(i + " ");
		}
		System.out.println();
		System.out.println("*****************************");
		for (String i : r3)
		{
			System.out.print(i + " ");
		}
		System.out.println();
		System.out.println("*****************************");
	}
}
