import java.math.BigInteger;
import java.util.Scanner;

import javax.jws.Oneway;
import javax.swing.RootPaneContainer;

/*
 题目列表 > 长方形

 时间限制: 1000ms 内存限制: 256MB

 描述

 在 N × M 的网格上，放 K 枚石子，每个石子都只能放在网格的交叉点上。问在最优的摆放方式下，最多能找到多少四边平行于坐标轴的长方形，它的四个角上都恰好放着一枚石子。
 输入

 输入文件包含多组测试数据。

 第一行，给出一个整数T，为数据组数。接下来依次给出每组测试数据。

 每组数据为三个用空格隔开的整数 N，M，K。
 输出

 对于每组测试数据，输出一行"Case #X: Y"，其中X表示测试数据编号，Y表示最多能找到的符合条件的长方形数量。所有数据按读入顺序从1开始编号。
 数据范围

 1 ≤ T ≤ 100

 0 ≤ K ≤ N * M

 小数据：0 < N, M ≤ 30

 大数据：0 < N, M ≤ 30000


 样例输入

 3
 3 3 8
 4 5 13
 7 14 86

 样例输出

 Case #1: 5
 Case #2: 18
 Case #3: 1398
 */

public class Qualifier_Q2
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

		Scanner in = new Scanner(System.in).useDelimiter("\\n");

		int group_Quantity = Integer.parseInt(in.next().trim());

		String[] data = new String[group_Quantity];

		boolean adjust_Flag = false;

		for (int i = 0; i < data.length; i++)
		{
			data[i] = in.next();
			int N, M, K;
			N = Integer.parseInt(data[i].split(" ")[0].trim());
			M = Integer.parseInt(data[i].split(" ")[1].trim());
			K = Integer.parseInt(data[i].split(" ")[2].trim());

			// long max_Rectangle_Quantity = 0;
			BigInteger max_Rectangle_Quantity = new BigInteger("0");
			int short_stroke = Math.min(N, M);

			if (K >= 4)
			{
				max_Rectangle_Quantity.valueOf(1);
				// case full
				if (K == N * M)
				{
					// max_Rectangle_Quantity = N * M * (N - 1) * (M - 1) / 4;
					// bigdata
					max_Rectangle_Quantity = BigDataRectangleCal(N, M);
				}

				// case 2 one side full
				else if (K >= short_stroke * short_stroke)
				{

					int long_stroke = short_stroke;
					int left_point_Quantity = K - short_stroke * short_stroke;

					adjust_Flag = true;

					while (left_point_Quantity >= short_stroke)
					{
						long_stroke++;
						left_point_Quantity = left_point_Quantity - short_stroke;
						// adjust_Flag=false;
					}

					max_Rectangle_Quantity = BigDataRectangleCal(long_stroke, short_stroke);
					max_Rectangle_Quantity = max_Rectangle_Quantity.add(BigDataLeftCal(left_point_Quantity, long_stroke));
				}

				else
				{
					adjust_Flag = true;
					short_stroke = (int) Math.sqrt(K);
					int long_stroke = short_stroke;

					int left_point_Quantity = K - short_stroke * short_stroke;

					// case 3 two side neither not full, one side fill one time
					if (left_point_Quantity - 1 <= short_stroke)
					{
						if (left_point_Quantity - 1 == short_stroke)
							left_point_Quantity = short_stroke;
						max_Rectangle_Quantity = BigDataRectangleCal(long_stroke, short_stroke);
						max_Rectangle_Quantity = max_Rectangle_Quantity.add(BigDataLeftCal(left_point_Quantity, long_stroke));
					}
					else
					{

						left_point_Quantity = left_point_Quantity - short_stroke;
						long_stroke++;

						max_Rectangle_Quantity = BigDataRectangleCal(long_stroke, short_stroke);
						// case 4 one side fill two times
						if (Math.max(N, M) - short_stroke >= 2)
						{
							max_Rectangle_Quantity = max_Rectangle_Quantity.add(BigDataLeftCal(left_point_Quantity, long_stroke));
						}
						// two side neither not full, two side fill
						else
						{
							max_Rectangle_Quantity = max_Rectangle_Quantity.add(BigDataLeftCal(left_point_Quantity, short_stroke));
						}
					}
				}

				if (adjust_Flag)
				{
					int _short_S;
					int _long_S;
					BigInteger _max = new BigInteger("1");

					// adjust
					for (int j = 1; j < Math.min(N, M) / 3; j++)
					{
						_short_S = short_stroke - j;
						_long_S = (int) (K / short_stroke);

						if (_short_S * _long_S <= K && _short_S > 1 && _long_S <= Math.max(N, M) && _short_S <= Math.min(N, M))
						{
							int _left = K - _short_S * _long_S;

							int _L = _long_S;
							int _S = _short_S;
							int __left = _left;

							while (__left >= _S)
							{
								_L++;
								__left = __left - _S;
							}
							if (_L > Math.max(N, M))
								continue;
							if (__left > 0 && _L + 1 > Math.max(N, M))
								continue;

							_max = BigDataRectangleCal(_S, _L);
							_max = _max.add(BigDataLeftCal(__left, _L));
							if (_max.compareTo(max_Rectangle_Quantity) == 1)
							{
								max_Rectangle_Quantity = _max;
							}
						}
					}
				}
			}
			else
			{
				max_Rectangle_Quantity.valueOf(0);
			}
			int ii = i + 1;
			System.out.println("Case #" + ii + ": " + max_Rectangle_Quantity.toString());
		}
	}

	private static BigInteger BigDataLeftCal(int left_point_Quantity, int long_stroke)
	{
		String N = Integer.toString(left_point_Quantity);
		String M = Integer.toString(long_stroke);
		String div = Integer.toString(2);

		BigInteger result = new BigInteger("1");
		result = result.multiply(new BigInteger(N));
		N = Integer.toString(left_point_Quantity - 1);
		result = result.multiply(new BigInteger(N));
		result = result.divide(new BigInteger(div));
		result = result.multiply(new BigInteger(M));
		return result;
	}

	private static BigInteger BigDataRectangleCal(int n, int m)
	{
		String N = Integer.toString(n);
		String M = Integer.toString(m);
		String div = Integer.toString(4);

		BigInteger result = new BigInteger("1");
		result = result.multiply(new BigInteger(N));
		result = result.multiply(new BigInteger(M));
		N = Integer.toString(n - 1);
		M = Integer.toString(m - 1);
		result = result.multiply(new BigInteger(N));
		result = result.multiply(new BigInteger(M));
		result = result.divide(new BigInteger(div));
		return result;
	}
}