import java.awt.List;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class BinaryDictGenerator
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		String[] result = new String[100];
		String[][] tempStrings = new String[10][];
		
		String[] tempList = new String[10000];
		String[] tt;
		int count = 0;
		int i = 1;
		String t;

		for (int j = 0; j < 10; j++)
		{
			tempStrings[j]=new String[(int)Math.pow(2, j)];
			count=0;
			for (int j2 = 1; j2 <= tempStrings[j].length; j2 += 2)
			{
				if (Int2BinaryString(j2).length() !=j )
				{
					if (count < tempList.length)
					{
						t = Int2BinaryString(j2);
						while (t.length() < j+1)
						{
							t = "0" + t;
						}
						t = t.substring(t.length() - j, t.length());
						tempStrings[j-2][count] = t;
						count++;
					}
				}
			}
		}
			
			
		
		System.out.println("Before sorted, the ary is: " + Arrays.toString(tempList));
		Arrays.sort(tempList);
		System.out.println("After sorted, the ary is: " + Arrays.toString(tempList));
		
		int[] StringLength = new int[tempList.length];
		int[] stat = new int [20];
		for (int j = 0; j < StringLength.length; j++)
		{
			StringLength[j]=tempList[j].length();
			stat[StringLength[j]]++;
		}
		System.out.println("After sorted, the ary is: " + Arrays.toString(stat));
	}

	public static String Int2BinaryString(int ori)
	{
		String result = Integer.toBinaryString(ori);
		return result;
	}

	public static int OneQuantity(String bin)
	{
		int result = 0;

		for (int i = 0; i < bin.length(); i++)
		{
			if (bin.charAt(i) == '1')
			{
				result++;
			}
		}
		return result;
	}

}
