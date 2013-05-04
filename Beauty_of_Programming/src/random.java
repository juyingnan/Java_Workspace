import java.util.Random;


public class random
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		int count  =5;
		Random r = new  Random();
		System.out.println(count);
		for (int i=0; i < count; i++)
		{
			int a=r.nextInt(30000)+1;
			int b=r.nextInt(30000)+1;
			int c=r.nextInt(a*b)+1;
			System.out.println(a+" "+b+" "+c);
		}
	}
}
