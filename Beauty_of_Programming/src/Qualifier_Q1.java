/**********************************
��Ŀ�б� > ������Ϸ

ʱ������: 1000ms �ڴ�����: 256MB

����

Alice��Bob����������λ��������һ���洫����Ϸ�������Ϸ���������еģ����ȣ�������Ϸ�߰�˳��վ��һ�ţ�Aliceվ��һλ��Bobվ���һλ��Ȼ��Alice��һ�仰���ĸ��ߵڶ�λ��Ϸ�ߣ��ڶ�λ��Ϸ�������ĵظ��ߵ���λ������λ�ָ��ߵ���λ�����Դ����ƣ�ֱ�������ڶ�λ����Bob����λ��Ϸ���ڴ����У�������������������Ҳ����ʹ��֫�嶯�������͡����Bob�����������Ļ����ߴ�ң�AliceҲ����ԭ������Ļ����ߴ�ҡ� 

���ڴ��������п��ܳ���һЩƫ���Ϸ��Խ�࣬Bob��������Ļ�����Alice�����Խ��ͬ��Bob�����Ļ���������һЩ�ܸ�Ц�Ķ��������Դ������ִ˲�ƣ������������Ϸ��Aliceע�⵽�����˴����У���Щ�ʻ����������ر�������ض��Ĵʻ㡣Alice�Ѿ��ռ�����������һ���ʻ�ת�����б�����֪�����Ļ�����Bobʱ����ʲô���ӣ�����д����������������
����

��������������ݡ���һ�������� T����ʾ�ж�����������ݡ�ÿ�����ݵ�һ�а����������� N �� M���ֱ��ʾ��Ϸ�ߵ������͵���ת���б��ȡ������ M �У�ÿ�а��������ÿո�����ĵ��� a �� b����ʾ���� a �ڴ�����һ������ b���������ݱ�֤û���ظ��� a�����һ�а������ɸ��õ����ո�����ĵ��ʣ���ʾAlice����ľ��ӣ������ܳ�������100���ַ������е��ʶ�ֻ����Сд��ĸ�����ҳ��Ȳ�����20��ͬһ�����ʵĲ�ͬʱ̬����Ϊ�ǲ�ͬ�ĵ��ʡ�����Լٶ������б��еĵ�����Զ����仯��
���

����ÿ��������ݣ��������һ�С�Case #c: s�������У�c Ϊ�������ݱ�ţ�s ΪBob�������ľ��ӡ�s �ĸ�ʽ������������Alice����ľ��Ӹ�ʽ��ͬ��
���ݷ�Χ

1 �� T �� 100

С���ݣ�2 �� N �� 10, 0 �� M �� 10 

�����ݣ�2 �� N �� 100, 0 �� M �� 100 


��������

    2
    4 3
    ship sheep
    sinking thinking
    thinking sinking
    the ship is sinking
    10 5
    tidy tiny
    tiger liar
    tired tire
    tire bear
    liar bear
    a tidy tiger is tired

�������

    Case #1: the sheep is thinking
    Case #2: a tiny bear is bear
*/

import java.io.StringWriter;
import java.util.Scanner;

public class Qualifier_Q1
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

		for (int i = 0; i < data.length; i++)
		{
			int people;
			int length;

			String temp = in.next();
			people = Integer.parseInt(temp.split(" ")[0].trim());
			length = Integer.parseInt(temp.split(" ")[1].trim());

			String[] Change_List = new String[length];

			for (int j = 0; j < Change_List.length; j++)
			{
				Change_List[j] = in.next();
			}

			String inputString = in.next();
			String outputString = "";

			// DO SOMETHING
			String[] input_Words = inputString.split(" ");
			for (int j = 0; j < people - 1; j++)
			{
				for (int k = 0; k < input_Words.length; k++)
				{
					for (int k2 = 0; k2 < Change_List.length; k2++)
					{
						if (input_Words[k].trim().contentEquals(
								Change_List[k2].split(" ")[0].trim()))
						{
							input_Words[k] = Change_List[k2].split(" ")[1]
									.trim();
							break;
						}
					}
				}
			}

			for (int j = 0; j < input_Words.length; j++)
			{
				outputString += input_Words[j] + " ";
			}

			outputString = outputString.trim();

			int ii = i + 1;
			System.out.println("Case #" + ii + ": " + outputString);
		}
	}
}