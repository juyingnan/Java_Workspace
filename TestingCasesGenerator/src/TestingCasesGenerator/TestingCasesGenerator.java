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
	// MAIN函数进行测试
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		// test
		TestingCasesGenerator t = new TestingCasesGenerator();

		// SCOPE测试，字符串
		// 边界
		ArrayList<String> result1 = t.TestingCases_Scope_Boundry(2, 8, true);
		System.out.println("****************Boundry****************");
		for (String string : result1)
		{
			System.out.println(string);
		}

		// 等价类
		ArrayList<String> result2 = t.TestingCases_Scope_Partition(2, 8, true);
		System.out.println("****************Partition****************");
		for (String string : result2)
		{
			System.out.println(string);
		}

		// 随机
		ArrayList<String> result3 = t.TestingCases_Scope_Random(2, 8, true);
		System.out.println("****************Random****************");
		for (String string : result3)
		{
			System.out.println(string);
		}

		// 所有
		ArrayList<String> result4 = t.TestingCases_Scope_All(2, 8, true);
		System.out.println("****************ALL****************");
		for (String string : result4)
		{
			System.out.println(string);
		}

		// SCOPE测试，字符串
		// 边界
		ArrayList<String> result5 = t.TestingCases_HttpIPURL_Boundry();
		System.out.println("****************Boundry****************");
		for (String string : result5)
		{
			System.out.println(string);
		}

		// 等价类
		ArrayList<String> result6 = t.TestingCases_HttpIPURL_Partition();
		System.out.println("****************Partition****************");
		for (String string : result6)
		{
			System.out.println(string);
		}

		// 随机
		ArrayList<String> result7 = t.TestingCases_HttpIPURL_Random();
		System.out.println("****************Random****************");
		for (String string : result7)
		{
			System.out.println(string);
		}

		// 所有
		ArrayList<String> result8 = t.TestingCases_HttpIPURL_All();
		System.out.println("****************ALL****************");
		for (String string : result8)
		{
			System.out.println(string);
		}

		// SCOPE测试，字符串
		// 边界
		ArrayList<Character> result9 = t.TestingCases_CharSets_Boundry(new char[] { 'L', 'O', 'V', 'E' });
		System.out.println("****************Boundry****************");
		for (Character character : result9)
		{
			System.out.println(character);
		}

		// 等价类
		ArrayList<Character> result10 = t.TestingCases_CharSets_Partition(new char[] { 'L', 'O', 'V', 'E' });
		System.out.println("****************Partition****************");
		for (Character character : result10)
		{
			System.out.println(character);
		}

		// 随机
		ArrayList<Character> result11 = t.TestingCases_CharSets_Random(new char[] { 'L', 'O', 'V', 'E' });
		System.out.println("****************Random****************");
		for (Character character : result11)
		{
			System.out.println(character);
		}

		// 所有
		ArrayList<Character> result12 = t.TestingCases_CharSets_All(new char[] { 'L', 'O', 'V', 'E' });
		System.out.println("****************ALL****************");
		for (Character character : result12)
		{
			System.out.println(character);
		}
	}

	// 范围：字符串长度

	// 根据长度返回字符串
	private String stringGenerator(int length)
	{
		// 初始长度为0
		String result = "";

		// >0时返回字符串
		if (length > 0)
		{
			for (int i = 0; i < length; i++)
			{
				// 随机生成33-126之间的ASCII
				int j = (int) (Math.random() * (126 - 33)) + 33;
				// int -> char
				char c = (char) j;
				result += c;
			}
		}
		// 长度小于返回null
		else if (length < 0)
		{
			result = null;
		}
		return result;
	}

	// 边界值
	public ArrayList<String> TestingCases_Scope_Boundry(int minLength, int maxLength, Boolean b)
	{
		// exception detection
		if (minLength <= 0 || maxLength <= 0 || maxLength < minLength)
			return null;

		// 在这里生成边界值

		// 生成最小长度字符串，最小长度+1字符串，最小长度-1字符串

		String minLengthString = "";
		String minlength_plus_1_String = "";
		String minlength_minus_1_String = "";

		// minLength
		minLengthString = stringGenerator(minLength);

		// minLength+1
		minlength_plus_1_String = stringGenerator(minLength + 1);

		// minLength-1
		minlength_minus_1_String = stringGenerator(minLength - 1);

		// 生成最大长度字符串，最大长度+1字符串，最大长度-1字符串

		String maxLengthString = "";
		String maxlength_plus_1_String = "";
		String maxlength_minus_1_String = "";

		// maxLength
		maxLengthString = stringGenerator(maxLength);

		// maxLength+1
		maxlength_plus_1_String = stringGenerator(maxLength + 1);

		// maxLength-1
		maxlength_minus_1_String = stringGenerator(maxLength - 1);

		// 然后全都放到一个数组里

		ArrayList<String> result = new ArrayList<String>(); // 这里也可以用大小不确定的LIST
		result.add(minLengthString);
		result.add(minlength_plus_1_String);
		result.add(minlength_minus_1_String);
		result.add(maxLengthString);
		result.add(maxlength_plus_1_String);
		result.add(maxlength_minus_1_String);

		return result;

	}

	// 等价划分
	public ArrayList<String> TestingCases_Scope_Partition(int minLength, int maxLength, Boolean b)
	{
		// exception detection
		if (minLength <= 0 || maxLength <= 0 || maxLength < minLength)
			return null;

		// 在这里生成等价类的测试用例

		// 例如，在这个范围内的某值，可以取中间值或随机值

		int median = (minLength + maxLength) / 2;
		String partitonString_in = stringGenerator(median);

		// 例如， 不在这个范围内的某值，可以根据特定规则取也可以取随机值

		int outOfBoundry = maxLength + minLength;
		String partitonString_out = stringGenerator(outOfBoundry);

		// 然后全都放到一个数组里
		ArrayList<String> result = new ArrayList<String>();
		result.add(partitonString_in);
		result.add(partitonString_out);

		return result;

	}

	// 随机
	public ArrayList<String> TestingCases_Scope_Random(int minLength, int maxLength, Boolean b)
	{
		// exception detection
		if (minLength <= 0 || maxLength <= 0 || maxLength < minLength)
			return null;

		// 在这里生成随机的测试用例

		// 例如，随机生成多个测试用例，可以是符合要求的，也可以是不符合要求的

		// 全都放到一个数组里
		ArrayList<String> result = new ArrayList<String>();

		// 随机次数
		final int times = 10;
		for (int i = 0; i < times; i++)
		{
			// 此处设置取值范围为0 - max+scope
			int j = (int) (Math.random() * (maxLength + maxLength - minLength) + 1);

			result.add(stringGenerator(j));
		}

		return result;

	}

	// 全部调用
	public ArrayList<String> TestingCases_Scope_All(int minLength, int maxLength, Boolean b)
	{
		// 通过这个方法可以调用刚才所有三种方法

		ArrayList<String> result1 = TestingCases_Scope_Partition(minLength, maxLength, true);

		ArrayList<String> result2 = TestingCases_Scope_Boundry(minLength, maxLength, true);

		ArrayList<String> result3 = TestingCases_Scope_Random(minLength, maxLength, true);

		// 然后把三种结果全都放到一个数组里
		ArrayList<String> result = new ArrayList<String>(); // 这里也可以用大小不确定的LIST
		result.addAll(result1);
		result.addAll(result2);
		result.addAll(result3);
		return result;

	}

	// 网址：IP地址

	// 根据参数生成IP地址
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

	// 边界值
	public ArrayList<String> TestingCases_HttpIPURL_Boundry()
	{
		// 在这里生成边界值

		// IP地址数字下限，下限-1，下限+1

		String thresholdIPAddressString = "";
		String threshold_minus_1_IPAddressString = "";
		String threshold_plus_1_IPAddressString = "";

		// 下限
		thresholdIPAddressString = URLGenerator(true, 0, 0, 4);

		// 下限+1
		threshold_plus_1_IPAddressString = URLGenerator(true, 1, 1, 4);

		// 下限-1
		threshold_minus_1_IPAddressString = URLGenerator(true, -1, -1, 4);

		// IP地址数字上限，上限-1，上限+1

		String floorIPAddressString = "";
		String floor_minus_1_IPAddressString = "";
		String floor_plus_1_IPAddressString = "";

		// 上限
		floorIPAddressString = URLGenerator(true, 255, 255, 4);

		// 上限+1
		floor_plus_1_IPAddressString = URLGenerator(true, 256, 256, 4);

		// 上限-1
		floor_minus_1_IPAddressString = URLGenerator(true, 254, 254, 4);

		// 然后全都放到一个数组里

		ArrayList<String> result = new ArrayList<String>(); // 这里也可以用大小不确定的LIST
		result.add(thresholdIPAddressString);
		result.add(threshold_minus_1_IPAddressString);
		result.add(threshold_plus_1_IPAddressString);
		result.add(floorIPAddressString);
		result.add(floor_minus_1_IPAddressString);
		result.add(floor_plus_1_IPAddressString);

		return result;

	}

	// 等价划分
	public ArrayList<String> TestingCases_HttpIPURL_Partition()
	{
		// 在这里生成等价类的测试用例

		// 带http:// 和 不带 http://

		String iPAssresswithHTTPString = URLGenerator(true, 0, 255, 4);
		String iPAssresswithoutHTTPString = URLGenerator(false, 0, 255, 4);

		// 在范围内 和 不在 范围内
		String iPAssresswithinBoundryString = URLGenerator(true, 0, 255, 4);
		String iPAssresswithoutBoundryString = URLGenerator(true, 256, 512, 4);

		// 组数 为4（正确） 和 不为4（不正确）
		String iPAssressinRightGroupString = URLGenerator(true, 0, 255, 4);
		String iPAssressinWrongGroupString = URLGenerator(true, 0, 255, 3);

		// 然后全都放到一个数组里
		ArrayList<String> result = new ArrayList<String>();
		result.add(iPAssresswithHTTPString);
		result.add(iPAssresswithoutHTTPString);
		result.add(iPAssresswithinBoundryString);
		result.add(iPAssresswithoutBoundryString);
		result.add(iPAssressinRightGroupString);
		result.add(iPAssressinWrongGroupString);

		return result;
	}

	// 随机
	public ArrayList<String> TestingCases_HttpIPURL_Random()
	{
		// 在这里生成随机的测试用例

		// 例如，随机生成多个测试用例，可以是符合要求的，也可以是不符合要求的

		// 全都放到一个数组里
		ArrayList<String> result = new ArrayList<String>();

		// 随机次数
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

	// 全部调用
	public ArrayList<String> TestingCases_HttpIPURL_All()
	{
		// 通过这个方法可以调用刚才所有三种方法

		ArrayList<String> result1 = TestingCases_HttpIPURL_Partition();

		ArrayList<String> result2 = TestingCases_HttpIPURL_Boundry();

		ArrayList<String> result3 = TestingCases_HttpIPURL_Random();

		// 然后把三种结果全都放到一个数组里
		ArrayList<String> result = new ArrayList<String>(); // 这里也可以用大小不确定的LIST
		result.addAll(result1);
		result.addAll(result2);
		result.addAll(result3);
		return result;

	}

	// CHAR数组

	// 边界值
	public ArrayList<Character> TestingCases_CharSets_Boundry(char[] SET)
	{
		// exception detection
		if (SET.length <= 0)
			return null;

		// 在这里生成边界值

		Character c1 = null;
		Character c2 = null;
		Character c3 = null;

		if (SET != null)
		{
			c1 = SET[0];
			c2 = SET[SET.length - 1];
		}
		// 然后全都放到一个数组里

		ArrayList<Character> result = new ArrayList<Character>();
		result.add(c1);
		result.add(c2);

		return result;
	}

	// 等价划分
	public ArrayList<Character> TestingCases_CharSets_Partition(char[] SET)
	{
		// exception detection
		if (SET.length <= 0)
			return null;

		// 在这里生成等价类的测试用例

		Character c1 = null;
		Character c2 = null;
		Character c3 = null;
		Character c4 = null;

		if (SET != null)
		{
			// 为空的和不为空的

			c1 = SET[0];
			c2 = null;

			// 在范围内 和 不在 范围内

			c3 = SET[SET.length - 1];
			// 字符串全集
			ArrayList<Character> fullCHArrayList = new ArrayList<Character>();
			for (int i = 0; i < 255; i++)
			{
				fullCHArrayList.add((char) i);
			}
			// 已有子集
			ArrayList<Character> setArrayList = new ArrayList<Character>();
			for (Character character : SET)
			{
				setArrayList.add(character);
			}
			// 全集去掉已有子集即为所有不在范围内的CHAR集合
			fullCHArrayList.removeAll(setArrayList);
			// 随机选取一个
			c4 = fullCHArrayList.get((int) (Math.random() * fullCHArrayList.size()));
		}

		// 然后全都放到一个数组里
		ArrayList<Character> result = new ArrayList<Character>();
		result.add(c1);
		result.add(c2);
		result.add(c3);
		result.add(c4);
		return result;
	}

	// 随机
	public ArrayList<Character> TestingCases_CharSets_Random(char[] SET)
	{
		// exception detection
		if (SET.length <= 0)
			return null;

		// 在这里生成随机的测试用例

		// 例如，随机生成多个测试用例，可以是符合要求的，也可以是不符合要求的

		// 全都放到一个数组里
		ArrayList<Character> result = new ArrayList<Character>();

		// 随机次数
		final int times = 10;
		for (int i = 0; i < times; i++)
		{
			int j = (int) (Math.random() * 255);
			char c = (char) j;
			result.add(c);
		}

		return result;
	}

	// 全部调用
	public ArrayList<Character> TestingCases_CharSets_All(char[] SET)
	{
		// 通过这个方法可以调用刚才所有三种方法

		ArrayList<Character> result1 = TestingCases_CharSets_Boundry(SET);

		ArrayList<Character> result2 = TestingCases_CharSets_Partition(SET);

		ArrayList<Character> result3 = TestingCases_CharSets_Random(SET);

		// 然后把三种结果全都放到一个数组里
		ArrayList<Character> result = new ArrayList<Character>(); // 这里也可以用大小不确定的LIST
		result.addAll(result1);
		result.addAll(result2);
		result.addAll(result3);
		return result;

	}

}
