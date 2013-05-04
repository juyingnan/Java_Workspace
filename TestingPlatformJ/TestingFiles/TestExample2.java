public class TestExample2
{
	public int fun(int a,int b)
	{
		while(a > 0||
				b < 0)
//		while(a > 0&&
//				b < 0)
		{
			a--;
			b++;
		}
		return a+b;
	}
}