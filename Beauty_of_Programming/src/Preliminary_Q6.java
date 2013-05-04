import java.util.Scanner;

public class Preliminary_Q6
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		class Village
		{
			double	x	= 0;
			double	y	= 0;
		}

		Scanner in = new Scanner(System.in);

		int group_Quantity = in.nextInt();

		String[] data = new String[group_Quantity];

		for (int i = 0; i < data.length; i++)
		{
			int village_Quantity = in.nextInt();
			Village[] villages = new Village[village_Quantity];
			for (int j = 0; j < village_Quantity; j++)
			{
				villages[j] = new Village();
				villages[j].x = in.nextDouble();
				villages[j].y = in.nextDouble();
			}

			Village result = new Village();
			Village temp = new Village();
			Village MAX = new Village();
			double result_dis = 1000000;
			double temp_dis = 0;

			double max_x = -10000;
			double min_x = 10000;
			double max_y = -10000;
			double min_y = 10000;

			for (int j = 0; j < villages.length; j++)
			{
				if (villages[j].x > max_x)
				{
					max_x = villages[j].x;
				}

				if (villages[j].x < min_x)
				{
					min_x = villages[j].x;
				}

				if (villages[j].y > max_y)
				{
					max_y = villages[j].y;
				}

				if (villages[j].y < min_y)
				{
					min_y = villages[j].y;
				}
			}

			for (int j = 0; j < villages.length; j++)
			{
				double t = Math.sqrt(Math.pow(villages[j].x, 2) + Math.pow(villages[j].y, 2));
				if (t > temp_dis)
				{
					MAX.x = villages[j].x;
					MAX.y = villages[j].y;
					temp_dis = t;
				}
			}

			temp.x = MAX.x;
			temp_dis = Math.sqrt(Math.pow((MAX.x - temp.x), 2) + Math.pow((MAX.y - temp.y), 2));
			int direction;
			if (MAX.x > 0)
			{
				direction = -1;
			}
			else
			{
				direction = 1;
			}

			boolean needAdjust = false;
			for (int j2 = 0; j2 < villages.length; j2++)
			{
				double t = Math.sqrt(Math.pow((villages[j2].x - temp.x), 2) + Math.pow((villages[j2].y - temp.y), 2));
				if (t > temp_dis)
				{
					needAdjust = true;
				}
			}
			if (needAdjust)
			{
				boolean isFound = false;
				while (!isFound)
				{
					temp.x += (0.000001 * direction);
					temp_dis = Math.sqrt(Math.pow((MAX.x - temp.x), 2) + Math.pow((MAX.y - temp.y), 2));
					for (int j2 = 0; j2 < villages.length; j2++)
					{
						double t = Math.sqrt(Math.pow((villages[j2].x - temp.x), 2) + Math.pow((villages[j2].y - temp.y), 2));
						if (t > temp_dis)
						{
							break;
						}
					}
					isFound = true;
					result.x = temp.x;
					result_dis = temp_dis;
				}
			}
			// temp.x = (max_x + min_x) / 2;
			// for (int j2 = 0; j2 < villages.length; j2++)
			// {
			// double t = Math.sqrt(Math.pow((villages[j2].x - temp.x), 2) +
			// Math.pow((villages[j2].y - temp.y), 2));
			// if (t > temp_dis)
			// {
			// temp_dis = t;
			// MAX.x = villages[j2].x;
			// MAX.y = villages[j2].y;
			// }
			// }
			//
			// int direction;
			// if (MAX.x > temp.x)
			// {
			// direction = 1;
			// }
			// else
			// {
			// direction = -1;
			// }
			// result_dis = temp_dis;
			// result.x = temp.x;
			// result.y = temp.y;
			//
			// for (double j = temp.x; j <= MAX.x; j += (0.000001 * direction))
			// {
			// temp.x = j;
			// temp_dis = 0;
			// double t = 0;
			// for (int j2 = 0; j2 < villages.length; j2++)
			// {
			// t = Math.sqrt(Math.pow((villages[j2].x - temp.x), 2) +
			// Math.pow((villages[j2].y - temp.y), 2));
			// if (t > temp_dis)
			// {
			// temp_dis = t;
			// }
			// }
			//
			// if (temp_dis > result_dis)
			// {
			// break;
			// }
			// else
			// {
			// result_dis = temp_dis;
			// result.x = temp.x;
			// result.y = temp.y;
			// }
			// }

			// for (double j = min_x; j < max_x; j += 0.000001)
			// {
			// temp.x = j;
			// double t = 0;
			// for (int j2 = 0; j2 < villages.length; j2++)
			// {
			// t = Math.sqrt(Math.pow((villages[j2].x - temp.x), 2) +
			// Math.pow((villages[j2].y - temp.y), 2));
			// if (t > temp_dis)
			// {
			// temp_dis = t;
			// }
			// }
			// if (temp_dis < result_dis)
			// {
			// result_dis = temp_dis;
			// result.x = temp.x;
			// result.y = temp.y;
			// }
			// temp_dis = 0;
			// }

			String outputString = "";

			java.text.DecimalFormat myformat = new java.text.DecimalFormat("#0.000000");
			outputString = myformat.format(result.x);
			int ii = i + 1;
			System.out.println("Case #" + ii + ": " + outputString);
		}
	}
}
