package TestInt;

import java.io.Console;

public class TestInt
{

	public int[] TestingCases_Scope_Boundry(int min, int max)
	{
		//
		if (min > max)
			return null;
		//
		int[] result = { min - 1, min, min + 1, max - 1, max, max + 1 };
		return result;
	}

	public int[] TestingCases_Scope_Partition(int min, int max)
	{
		//
		if (min > max)
			return null;
		//
		int PartitionIn = (int) (Math.random() * (max - min)) + min;
		int PartitionOut = max + 100;
		int[] result = { PartitionIn, PartitionOut };
		return result;
	}

	public int[] TestingCases_Scope_Random(int min, int max)
	{
		//
		if (min > max)
			return null;
		//
		int randomIn = (int) (Math.random() * (max - min)) + min;// 之间随机数
		int randomMin = (int) (Math.random() * 100) + (min - 100);// min之前100和min之间的随机
		int randomMax = (int) (Math.random() * 100) + max;// max和max之后100之间的随机
		int[] result = { randomIn, randomMin, randomMax };
		return result;
	}

	public int[] TestingCases_Scope_All(int max, int min)
	{
		int[] result1 = TestingCases_Scope_Partition(min, max);
		int[] result2 = TestingCases_Scope_Boundry(min, max);
		int[] result3 = TestingCases_Scope_Random(min, max);
		int[] result = new int[20];
		return result;
	}

	public static void main(String[] args)
	{
		TestInt t = new TestInt();
		int[] r1 = t.TestingCases_Scope_Boundry(2, 8);
		int[] r2 = t.TestingCases_Scope_Partition(2, 8);
		int[] r3 = t.TestingCases_Scope_Random(2, 8);

		System.out.println("*****************************");
		for (int i : r1)
		{
			System.out.print(i);
		}
		System.out.println();
		System.out.println("*****************************");
		for (int i : r2)
		{
			System.out.print(i);
		}
		System.out.println();
		System.out.println("*****************************");
		for (int i : r3)
		{
			System.out.print(i);
		}
		System.out.println();
		System.out.println("*****************************");
	}
}
