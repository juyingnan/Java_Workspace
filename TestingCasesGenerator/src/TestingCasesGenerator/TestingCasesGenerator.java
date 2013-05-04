package TestingCasesGenerator;

import java.awt.List;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class TestingCasesGenerator
{
	/**
	 * @param args
	 */
	// MAIN�������в���
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		// test
		TestingCasesGenerator t = new TestingCasesGenerator();

		// SCOPE���ԣ��ַ���
		// �߽�
		ArrayList<String> result1 = t.TestingCases_Scope_Boundry(2, 8, true);
		System.out.println("****************Boundry****************");
		for (String string : result1)
		{
			System.out.println(string);
		}

		// �ȼ���
		ArrayList<String> result2 = t.TestingCases_Scope_Partition(2, 8, true);
		System.out.println("****************Partition****************");
		for (String string : result2)
		{
			System.out.println(string);
		}

		// ���
		ArrayList<String> result3 = t.TestingCases_Scope_Random(2, 8, true);
		System.out.println("****************Random****************");
		for (String string : result3)
		{
			System.out.println(string);
		}

		// ����
		ArrayList<String> result4 = t.TestingCases_Scope_All(2, 8, true);
		System.out.println("****************ALL****************");
		for (String string : result4)
		{
			System.out.println(string);
		}

		// SCOPE���ԣ��ַ���
		// �߽�
		ArrayList<String> result5 = t.TestingCases_HttpIPURL_Boundry();
		System.out.println("****************Boundry****************");
		for (String string : result5)
		{
			System.out.println(string);
		}

		// �ȼ���
		ArrayList<String> result6 = t.TestingCases_HttpIPURL_Partition();
		System.out.println("****************Partition****************");
		for (String string : result6)
		{
			System.out.println(string);
		}

		// ���
		ArrayList<String> result7 = t.TestingCases_HttpIPURL_Random();
		System.out.println("****************Random****************");
		for (String string : result7)
		{
			System.out.println(string);
		}

		// ����
		ArrayList<String> result8 = t.TestingCases_HttpIPURL_All();
		System.out.println("****************ALL****************");
		for (String string : result8)
		{
			System.out.println(string);
		}

		// SCOPE���ԣ��ַ���
		// �߽�
		ArrayList<Character> result9 = t.TestingCases_CharSets_Boundry(new char[] { 'L', 'O', 'V', 'E' });
		System.out.println("****************Boundry****************");
		for (Character character : result9)
		{
			System.out.println(character);
		}

		// �ȼ���
		ArrayList<Character> result10 = t.TestingCases_CharSets_Partition(new char[] { 'L', 'O', 'V', 'E' });
		System.out.println("****************Partition****************");
		for (Character character : result10)
		{
			System.out.println(character);
		}

		// ���
		ArrayList<Character> result11 = t.TestingCases_CharSets_Random(new char[] { 'L', 'O', 'V', 'E' });
		System.out.println("****************Random****************");
		for (Character character : result11)
		{
			System.out.println(character);
		}

		// ����
		ArrayList<Character> result12 = t.TestingCases_CharSets_All(new char[] { 'L', 'O', 'V', 'E' });
		System.out.println("****************ALL****************");
		for (Character character : result12)
		{
			System.out.println(character);
		}
	}

	// ��Χ���ַ�������

	// ���ݳ��ȷ����ַ���
	private String stringGenerator(int length)
	{
		// ��ʼ����Ϊ0
		String result = "";

		// >0ʱ�����ַ���
		if (length > 0)
		{
			for (int i = 0; i < length; i++)
			{
				// �������33-126֮���ASCII
				int j = (int) (Math.random() * (126 - 33)) + 33;
				// int -> char
				char c = (char) j;
				result += c;
			}
		}
		// ����С�ڷ���null
		else if (length < 0)
		{
			result = null;
		}
		return result;
	}

	// �߽�ֵ
	public ArrayList<String> TestingCases_Scope_Boundry(int minLength, int maxLength, Boolean b)
	{
		// exception detection
		if (minLength <= 0 || maxLength <= 0 || maxLength < minLength)
			return null;

		// ���������ɱ߽�ֵ

		// ������С�����ַ�������С����+1�ַ�������С����-1�ַ���

		String minLengthString = "";
		String minlength_plus_1_String = "";
		String minlength_minus_1_String = "";

		// minLength
		minLengthString = stringGenerator(minLength);

		// minLength+1
		minlength_plus_1_String = stringGenerator(minLength + 1);

		// minLength-1
		minlength_minus_1_String = stringGenerator(minLength - 1);

		// ������󳤶��ַ�������󳤶�+1�ַ�������󳤶�-1�ַ���

		String maxLengthString = "";
		String maxlength_plus_1_String = "";
		String maxlength_minus_1_String = "";

		// maxLength
		maxLengthString = stringGenerator(maxLength);

		// maxLength+1
		maxlength_plus_1_String = stringGenerator(maxLength + 1);

		// maxLength-1
		maxlength_minus_1_String = stringGenerator(maxLength - 1);

		// Ȼ��ȫ���ŵ�һ��������

		ArrayList<String> result = new ArrayList<String>(); // ����Ҳ�����ô�С��ȷ����LIST
		result.add(minLengthString);
		result.add(minlength_plus_1_String);
		result.add(minlength_minus_1_String);
		result.add(maxLengthString);
		result.add(maxlength_plus_1_String);
		result.add(maxlength_minus_1_String);

		return result;

	}

	// �ȼۻ���
	public ArrayList<String> TestingCases_Scope_Partition(int minLength, int maxLength, Boolean b)
	{
		// exception detection
		if (minLength <= 0 || maxLength <= 0 || maxLength < minLength)
			return null;

		// ���������ɵȼ���Ĳ�������

		// ���磬�������Χ�ڵ�ĳֵ������ȡ�м�ֵ�����ֵ

		int median = (minLength + maxLength) / 2;
		String partitonString_in = stringGenerator(median);

		// ���磬 ���������Χ�ڵ�ĳֵ�����Ը����ض�����ȡҲ����ȡ���ֵ

		int outOfBoundry = maxLength + minLength;
		String partitonString_out = stringGenerator(outOfBoundry);

		// Ȼ��ȫ���ŵ�һ��������
		ArrayList<String> result = new ArrayList<String>();
		result.add(partitonString_in);
		result.add(partitonString_out);

		return result;

	}

	// ���
	public ArrayList<String> TestingCases_Scope_Random(int minLength, int maxLength, Boolean b)
	{
		// exception detection
		if (minLength <= 0 || maxLength <= 0 || maxLength < minLength)
			return null;

		// ��������������Ĳ�������

		// ���磬������ɶ�����������������Ƿ���Ҫ��ģ�Ҳ�����ǲ�����Ҫ���

		// ȫ���ŵ�һ��������
		ArrayList<String> result = new ArrayList<String>();

		// �������
		final int times = 10;
		for (int i = 0; i < times; i++)
		{
			// �˴�����ȡֵ��ΧΪ0 - max+scope
			int j = (int) (Math.random() * (maxLength + maxLength - minLength) + 1);

			result.add(stringGenerator(j));
		}

		return result;

	}

	// ȫ������
	public ArrayList<String> TestingCases_Scope_All(int minLength, int maxLength, Boolean b)
	{
		// ͨ������������Ե��øղ��������ַ���

		ArrayList<String> result1 = TestingCases_Scope_Partition(minLength, maxLength, true);

		ArrayList<String> result2 = TestingCases_Scope_Boundry(minLength, maxLength, true);

		ArrayList<String> result3 = TestingCases_Scope_Random(minLength, maxLength, true);

		// Ȼ������ֽ��ȫ���ŵ�һ��������
		ArrayList<String> result = new ArrayList<String>(); // ����Ҳ�����ô�С��ȷ����LIST
		result.addAll(result1);
		result.addAll(result2);
		result.addAll(result3);
		return result;

	}

	// ��ַ��IP��ַ

	// ���ݲ�������IP��ַ
	private String URLGenerator(boolean isHeaderExist, int min, int max, int groupNumber)
	{
		// TODO Auto-generated method stub
		String result = "";

		// http://
		if (isHeaderExist)
		{
			result += "http://";
		}

		// IP address generator
		for (int i = 1; i <= groupNumber; i++)
		{
			result += (int) (Math.random() * (max - min)) + min;
			if (i < groupNumber)
			{
				result += ".";
			}
		}
		return result;
	}

	// �߽�ֵ
	public ArrayList<String> TestingCases_HttpIPURL_Boundry()
	{
		// ���������ɱ߽�ֵ

		// IP��ַ�������ޣ�����-1������+1

		String thresholdIPAddressString = "";
		String threshold_minus_1_IPAddressString = "";
		String threshold_plus_1_IPAddressString = "";

		// ����
		thresholdIPAddressString = URLGenerator(true, 0, 0, 4);

		// ����+1
		threshold_plus_1_IPAddressString = URLGenerator(true, 1, 1, 4);

		// ����-1
		threshold_minus_1_IPAddressString = URLGenerator(true, -1, -1, 4);

		// IP��ַ�������ޣ�����-1������+1

		String floorIPAddressString = "";
		String floor_minus_1_IPAddressString = "";
		String floor_plus_1_IPAddressString = "";

		// ����
		floorIPAddressString = URLGenerator(true, 255, 255, 4);

		// ����+1
		floor_plus_1_IPAddressString = URLGenerator(true, 256, 256, 4);

		// ����-1
		floor_minus_1_IPAddressString = URLGenerator(true, 254, 254, 4);

		// Ȼ��ȫ���ŵ�һ��������

		ArrayList<String> result = new ArrayList<String>(); // ����Ҳ�����ô�С��ȷ����LIST
		result.add(thresholdIPAddressString);
		result.add(threshold_minus_1_IPAddressString);
		result.add(threshold_plus_1_IPAddressString);
		result.add(floorIPAddressString);
		result.add(floor_minus_1_IPAddressString);
		result.add(floor_plus_1_IPAddressString);

		return result;

	}

	// �ȼۻ���
	public ArrayList<String> TestingCases_HttpIPURL_Partition()
	{
		// ���������ɵȼ���Ĳ�������

		// ��http:// �� ���� http://

		String iPAssresswithHTTPString = URLGenerator(true, 0, 255, 4);
		String iPAssresswithoutHTTPString = URLGenerator(false, 0, 255, 4);

		// �ڷ�Χ�� �� ���� ��Χ��
		String iPAssresswithinBoundryString = URLGenerator(true, 0, 255, 4);
		String iPAssresswithoutBoundryString = URLGenerator(true, 256, 512, 4);

		// ���� Ϊ4����ȷ�� �� ��Ϊ4������ȷ��
		String iPAssressinRightGroupString = URLGenerator(true, 0, 255, 4);
		String iPAssressinWrongGroupString = URLGenerator(true, 0, 255, 3);

		// Ȼ��ȫ���ŵ�һ��������
		ArrayList<String> result = new ArrayList<String>();
		result.add(iPAssresswithHTTPString);
		result.add(iPAssresswithoutHTTPString);
		result.add(iPAssresswithinBoundryString);
		result.add(iPAssresswithoutBoundryString);
		result.add(iPAssressinRightGroupString);
		result.add(iPAssressinWrongGroupString);

		return result;
	}

	// ���
	public ArrayList<String> TestingCases_HttpIPURL_Random()
	{
		// ��������������Ĳ�������

		// ���磬������ɶ�����������������Ƿ���Ҫ��ģ�Ҳ�����ǲ�����Ҫ���

		// ȫ���ŵ�һ��������
		ArrayList<String> result = new ArrayList<String>();

		// �������
		final int times = 10;
		for (int i = 0; i < times; i++)
		{
			boolean b = (Math.random() > 0.5) ? true : false;

			int g = (int) (Math.random() * 6);

			String j = URLGenerator(b, 0, 300, g);

			result.add(j);
		}

		return result;
	}

	// ȫ������
	public ArrayList<String> TestingCases_HttpIPURL_All()
	{
		// ͨ������������Ե��øղ��������ַ���

		ArrayList<String> result1 = TestingCases_HttpIPURL_Partition();

		ArrayList<String> result2 = TestingCases_HttpIPURL_Boundry();

		ArrayList<String> result3 = TestingCases_HttpIPURL_Random();

		// Ȼ������ֽ��ȫ���ŵ�һ��������
		ArrayList<String> result = new ArrayList<String>(); // ����Ҳ�����ô�С��ȷ����LIST
		result.addAll(result1);
		result.addAll(result2);
		result.addAll(result3);
		return result;

	}

	// CHAR����

	// �߽�ֵ
	public ArrayList<Character> TestingCases_CharSets_Boundry(char[] SET)
	{
		// exception detection
		if (SET.length <= 0)
			return null;

		// ���������ɱ߽�ֵ

		Character c1 = null;
		Character c2 = null;
		Character c3 = null;

		if (SET != null)
		{
			c1 = SET[0];
			c2 = SET[SET.length - 1];
		}
		// Ȼ��ȫ���ŵ�һ��������

		ArrayList<Character> result = new ArrayList<Character>();
		result.add(c1);
		result.add(c2);

		return result;
	}

	// �ȼۻ���
	public ArrayList<Character> TestingCases_CharSets_Partition(char[] SET)
	{
		// exception detection
		if (SET.length <= 0)
			return null;

		// ���������ɵȼ���Ĳ�������

		Character c1 = null;
		Character c2 = null;
		Character c3 = null;
		Character c4 = null;

		if (SET != null)
		{
			// Ϊ�յĺͲ�Ϊ�յ�

			c1 = SET[0];
			c2 = null;

			// �ڷ�Χ�� �� ���� ��Χ��

			c3 = SET[SET.length - 1];
			// �ַ���ȫ��
			ArrayList<Character> fullCHArrayList = new ArrayList<Character>();
			for (int i = 0; i < 255; i++)
			{
				fullCHArrayList.add((char) i);
			}
			// �����Ӽ�
			ArrayList<Character> setArrayList = new ArrayList<Character>();
			for (Character character : SET)
			{
				setArrayList.add(character);
			}
			// ȫ��ȥ�������Ӽ���Ϊ���в��ڷ�Χ�ڵ�CHAR����
			fullCHArrayList.removeAll(setArrayList);
			// ���ѡȡһ��
			c4 = fullCHArrayList.get((int) (Math.random() * fullCHArrayList.size()));
		}

		// Ȼ��ȫ���ŵ�һ��������
		ArrayList<Character> result = new ArrayList<Character>();
		result.add(c1);
		result.add(c2);
		result.add(c3);
		result.add(c4);
		return result;
	}

	// ���
	public ArrayList<Character> TestingCases_CharSets_Random(char[] SET)
	{
		// exception detection
		if (SET.length <= 0)
			return null;

		// ��������������Ĳ�������

		// ���磬������ɶ�����������������Ƿ���Ҫ��ģ�Ҳ�����ǲ�����Ҫ���

		// ȫ���ŵ�һ��������
		ArrayList<Character> result = new ArrayList<Character>();

		// �������
		final int times = 10;
		for (int i = 0; i < times; i++)
		{
			int j = (int) (Math.random() * 255);
			char c = (char) j;
			result.add(c);
		}

		return result;
	}

	// ȫ������
	public ArrayList<Character> TestingCases_CharSets_All(char[] SET)
	{
		// ͨ������������Ե��øղ��������ַ���

		ArrayList<Character> result1 = TestingCases_CharSets_Boundry(SET);

		ArrayList<Character> result2 = TestingCases_CharSets_Partition(SET);

		ArrayList<Character> result3 = TestingCases_CharSets_Random(SET);

		// Ȼ������ֽ��ȫ���ŵ�һ��������
		ArrayList<Character> result = new ArrayList<Character>(); // ����Ҳ�����ô�С��ȷ����LIST
		result.addAll(result1);
		result.addAll(result2);
		result.addAll(result3);
		return result;

	}

}
