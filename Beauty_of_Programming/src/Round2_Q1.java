import java.util.Scanner;

/*
 * 
 ��Ŀ�б� > �޾��ı��

 ʱ������: 1000ms �ڴ�����: 256MB

 ����

 ��һ����·�ϣ���Ҫ���ν���N����������ÿ����������֮�󣬶�����һ��01����������š�������·����㵽�յ㣬���н����ı�Ŷ��ϸ����ֵ��������˳�������У���ÿ��һ���µĵط�����һ������ʱ�����ı�Żᰴ���¹���ȷ����

 1) ���Ҫ��ǰһ���������ֵ���󣬱Ⱥ�һ���������ֵ���С

 3) ���һ����1��β

 2) ���Ҫ�����̣ܶ����������ʱ���ֵ��򾡿���С

 �ʼʱ����·�������յ��ϸ���һ����������ŷֱ���0��1�����������θ���N������ a1, a2, ..., aN�����α�ʾ��һ��������Ҫ�����λ�ã����Ҫ�ʣ������н�������ʱ����Щ�����ı���ܳ����Ƕ��٣������ֳ����˶��ٸ��ַ�1�����н������ڹ�·�����յ�֮�䣬����û��������������ͬһ��λ�á�
 ����

 �����ļ���������������ݡ�

 ��һ�У�����һ������T��Ϊ�������������������θ���ÿ��������ݡ�

 ÿ�������е�һ��Ϊһ������ N����ʾ��Ҫ����Ľ����������ڶ������õ����ո������N��������ͬ������ a1, a2, ..., aN����ʾ���ν�Ҫ����Ľ������ڵ����ꡣ
 ���

 ����ÿ��������ݣ����һ��"Case #X: Y Z"������X��ʾ�������ݱ�ţ�Y��ʾ���н�������ܳ���Z��ʾ���б�����ַ�1�����������н������������յ���������������������ݰ�����˳���1��ʼ��š�
 ���ݷ�Χ

 С���ݣ�T �� 100, 0 < N �� 100, 0 �� ai �� 1000

 �����ݣ�T �� 10, 0 < N �� 50000, 0 �� ai �� 500000


 ��������

 1
 5
 1 2 3 4 5

 �������

 Case #1: 22 16

 * 
 */

public class Round2_Q1
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);

		int group_Quantity = in.nextInt();

		String[] data = new String[group_Quantity];

		for (int i = 0; i < data.length; i++)
		{
			int tower_Quantity = in.nextInt();
			int totalLength = 0;
			int totalOne = 0;

			String[] tower_No = new String[tower_Quantity];
			int t = tower_Quantity;
			int count=0;
			
			
			totalLength += 2;
			totalOne += 1;

			String outputString = Integer.toString(totalLength) + " " + Integer.toString(totalOne);

			int ii = i + 1;
			System.out.println("Case #" + ii + ": " + outputString);
		}
	}

	private static String GenerateMinBinaryString(String string, String string2)
	{
		int S1_length = string.length();
		String temp = "1";
		boolean isFound = false;
		while (!isFound)
		{
			String t = "0"+temp;
			if(t.compareTo(string)>0 && t.compareTo(string2)<0) return t;
			String t2 = "0"+temp;
			if(t2.compareTo(string)>0 && t2.compareTo(string2)<0) return t2;
			
		}

		return null;
	}

	public static String Int2BinaryString(int ori)
	{
		String result = Integer.toBinaryString(ori);
		int p = result.indexOf('1');
		result = result.substring(p);
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