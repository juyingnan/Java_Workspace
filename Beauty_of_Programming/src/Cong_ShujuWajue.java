import java.util.Scanner;


 public class Cong_ShujuWajue
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in).useDelimiter("\\n");
		
		while (in.hasNext())
		{
			String temp = in.next().trim();
			
			switch (temp.length())
			{
				case 4:
					temp="0101"+temp;
					break;
				case 5:
					temp="010"+temp;
					break;
				case 6:
					temp="01"+temp;
					break;
				case 7:
					temp="0"+temp;
					break;
				default:
					break;
			}
			
			System.out.println(temp);
			
		}

	}

}
