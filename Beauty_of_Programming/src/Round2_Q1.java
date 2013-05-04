import java.util.Scanner;

/*
 * 
 题目列表 > 无尽的编号

 时间限制: 1000ms 内存限制: 256MB

 描述

 在一条公路上，将要依次建造N座建筑。在每个建筑建成之后，都会用一个01串来给它编号。整条公路从起点到终点，所有建筑的编号都严格按照字典序递增的顺序来排列，而每在一个新的地方建起一个建筑时，它的编号会按以下规则确定：

 1) 编号要比前一个建筑的字典序大，比后一个建筑的字典序小

 3) 编号一定以1结尾

 2) 编号要尽可能短，满足该条件时，字典序尽可能小

 最开始时，公路的起点和终点上各有一个建筑，编号分别是0和1。接下来依次给出N个坐标 a1, a2, ..., aN，依次表示下一个建筑将要建造的位置，最后要问，当所有建筑建成时，这些建筑的编号总长度是多少，其中又出现了多少个字符1。所有建筑都在公路起点和终点之间，并且没有两个建筑建在同一个位置。
 输入

 输入文件包含多组测试数据。

 第一行，给出一个整数T，为数据组数。接下来依次给出每组测试数据。

 每组数据中第一行为一个整数 N，表示将要建造的建筑数量，第二行是用单个空格隔开的N个互不相同的整数 a1, a2, ..., aN，表示依次将要建造的建筑所在的坐标。
 输出

 对于每组测试数据，输出一行"Case #X: Y Z"，其中X表示测试数据编号，Y表示所有建筑编号总长，Z表示所有编号中字符1的数量。所有建筑包括起点和终点的这两个建筑。所有数据按读入顺序从1开始编号。
 数据范围

 小数据：T ≤ 100, 0 < N ≤ 100, 0 ≤ ai ≤ 1000

 大数据：T ≤ 10, 0 < N ≤ 50000, 0 ≤ ai ≤ 500000


 样例输入

 1
 5
 1 2 3 4 5

 样例输出

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