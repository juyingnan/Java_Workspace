import java.util.Scanner;

public class Preliminary_Q2
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		
		Scanner in = new Scanner(System.in).useDelimiter("\\n");

		int group_Quantity = Integer.parseInt(in.next().trim());

		String[] data = new String[group_Quantity];

		long startTime=System.currentTimeMillis();
		
		for (int i = 0; i < data.length; i++)
		{

			String S1 = in.next();
			String S2 = in.next();
			S2 = S2.trim();

			int S1_Length = S1.length();
			int S2_length = S2.length();

			int dif = 99999;
			int temp = dif;

			// do something
			for (int j = 0; j < S1_Length - S2_length + 1; j++)
			{
				String S1_SubString = S1.substring(j, j + S2_length);
				// System.out.println(S1_SubString);

				temp = CompareDif(S1_SubString, S2);
				if (dif > temp)
					dif = temp;
				if (dif == 0)
					break;
			}

			String outputString = "";

			outputString = Integer.toString(dif);
			int ii = i + 1;
			System.out.println("Case #" + ii + ": " + outputString);
			
		}

		long endTime=System.currentTimeMillis(); 
		System.out.println("Duration£º "+(endTime-startTime)+"ms"); 
	}

	private static int CompareDif(String s1_SubString, String s2)
	{
		if (s1_SubString.length() != s2.length())
			return 99999;

		int result = 0;

		for (int i = 0; i < s1_SubString.length(); i++)
		{
			if (s1_SubString.charAt(i) != s2.charAt(i))
				result++;
		}

		return result;
	}

}
