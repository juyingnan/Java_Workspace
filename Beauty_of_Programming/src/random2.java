import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;

public class random2
{

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException
	{
		File test = new File("D:\\test.txt");
		PrintStream out = new PrintStream(new FileOutputStream(test));
		System.setOut(out);
		
		int count = 10;
		Random r = new Random();
		System.out.println(count);
		for (int i = 0; i < count; i++)
		{
			int a = r.nextInt(50000) + 1;
			for (int j = 0; j < a; j++)
			{
				int c = r.nextInt(10);
				System.out.print(c);
			}
			System.out.println();
			int b = r.nextInt(a);
			for (int j = 0; j < b; j++)
			{
				int c = r.nextInt(10);
				System.out.print(c);
			}
			System.out.println();
		}

	}

}
