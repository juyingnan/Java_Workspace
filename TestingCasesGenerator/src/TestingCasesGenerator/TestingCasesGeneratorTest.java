package TestingCasesGenerator;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.rules.Timeout;
import org.omg.CORBA.PRIVATE_MEMBER;

public class TestingCasesGeneratorTest
{

	private static TestingCasesGenerator	t	= new TestingCasesGenerator();


	@Rule
    public Timeout  globalTimeout=  new Timeout(20);
	
	@Before
	public void setUp() throws Exception
	{
	}

	
	@Test
	public void testTestingCases_Scope_Boundry()
	{
		// 边界值，最小值
		int min = 1;
		int max = 8;
		ArrayList<String> result1 = t.TestingCases_Scope_Boundry(min, max, true);
		int[] el = { min, min + 1, min - 1, max, max + 1, max - 1 }; // 期待输出
		int[] al = new int[el.length]; // 实际输出
		for (int i = 0; i < al.length; i++) // 实际输出赋值
		{
			al[i] = result1.get(i).length();
		}
		assertArrayEquals(el, al); // 比较是否相同

		// 边界值，最小值-1
		min = 0;
		max = 8;
		result1 = t.TestingCases_Scope_Boundry(min, max, true);
		assertNull(result1);// 输出应为null

		// 等价类，最小值<0
		min = -4;
		max = 5;
		result1 = t.TestingCases_Scope_Boundry(min, max, true);
		assertNull(result1);// 输出应为null

		// 等价类，最大值小于最小值
		min = 4;
		max = 2;
		result1 = t.TestingCases_Scope_Boundry(min, max, true);
		assertNull(result1);// 输出应为null
		
		//input illegal
		result1 = t.TestingCases_Scope_Boundry(min, max, true);
	}

	@Test
	public void testTestingCases_Scope_Partition()
	{
		// 边界值，最小值
		int min = 1;
		int max = 8;
		ArrayList<String> result1 = t.TestingCases_Scope_Partition(min, max, true);
		int[] el = { (min + max) / 2, min + max };// 期待输出
		int[] al = new int[el.length];// 实际输出
		for (int i = 0; i < al.length; i++)// 实际输出赋值
		{
			al[i] = result1.get(i).length();
		}
		assertArrayEquals(el, al);

		// 边界值，最小值-1
		min = 0;
		max = 8;
		result1 = t.TestingCases_Scope_Partition(min, max, true);
		assertTrue(result1 == null);// 输出应为null

		// 等价类，最小值<0
		min = -4;
		max = 5;
		result1 = t.TestingCases_Scope_Partition(min, max, true);
		assertTrue(result1 == null);// 输出应为null

		// 等价类，最大值小于最小值
		min = 4;
		max = 2;
		result1 = t.TestingCases_Scope_Partition(min, max, true);
		assertTrue(result1 == null);// 输出应为null
	}

	@Test
	public void testTestingCases_Scope_Random()
	{
		// 边界值，最小值
		int min = 1;
		int max = 8;
		ArrayList<String> result3 = t.TestingCases_Scope_Random(min, max, true);
		// 长度应在随机长度范围之内
		for (int i = 0; i < result3.size(); i++)
		{
			assertTrue(result3.get(i).length() > 0 && result3.get(i).length() <= (max + max - min + 1));
		}

		// 边界值，最小值-1
		min = 0;
		max = 8;
		result3 = t.TestingCases_Scope_Boundry(min, max, true);
		assertTrue(result3 == null);// 输出应为null

		// 等价类，最小值<0
		min = -4;
		max = 5;
		result3 = t.TestingCases_Scope_Boundry(min, max, true);
		assertTrue(result3 == null);// 输出应为null

		// 等价类，最大值小于最小值
		min = 4;
		max = 2;
		result3 = t.TestingCases_Scope_Boundry(min, max, true);
		assertTrue(result3 == null);// 输出应为null
	}

	@Ignore
	public void testTestingCases_Scope_All()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testTestingCases_HttpIPURL_Boundry()
	{
		// 期待输出
		String[] el = { "http://0.0.0.0", "http://-1.-1.-1.-1", "http://1.1.1.1", "http://255.255.255.255",
				"http://254.254.254.254", "http://256.256.256.256" };
		String[] al = new String[el.length];
		ArrayList<String> result = t.TestingCases_HttpIPURL_Boundry();
		// 实际输出
		for (int i = 0; i < al.length; i++)
		{
			al[i] = result.get(i);
		}
		// 结果比较
		assertArrayEquals(el, al);
	}

	@Test
	public void testTestingCases_HttpIPURL_Partition()
	{
		ArrayList<String> result = t.TestingCases_HttpIPURL_Partition();
		// 第1个应为http://开头
		assertTrue(result.get(0).startsWith("http://"));
		// 第1个应没有http://开头
		assertTrue(!(result.get(1).startsWith("http://")));
		int[] n = new int[4];
		String string = result.get(2).substring(7);
		String[] s = new String[4];
		s = string.split("\\.");
		// 第3个所有数字应为0-255之间
		for (int i = 0; i < n.length; i++)
		{
			n[i] = Integer.parseInt(s[i]);
			assertTrue(n[i] <= 255 && n[i] >= 0);
		}

		// 第4个所有数字应大于255
		string = result.get(3).substring(7);
		s = string.split("\\.");
		for (int i = 0; i < n.length; i++)
		{
			n[i] = Integer.parseInt(s[i]);
			assertTrue(n[i] > 255);
		}

		// 第5个应该为四组数字
		string = result.get(4).substring(7);
		s = string.split("\\.");
		assertTrue(s.length == 4);

		// 第6个应该为不为四组数字
		string = result.get(5).substring(7);
		s = string.split("\\.");
		assertTrue(s.length != 4);

	}

	@Ignore
	public void testTestingCases_HttpIPURL_Random()
	{
		fail("Not yet implemented");
	}

	@Ignore
	public void testTestingCases_HttpIPURL_All()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testTestingCases_CharSets_Boundry()
	{
		// 等价类，输入字符数组不为空
		ArrayList<Character> result9 = t.TestingCases_CharSets_Boundry(new char[] { 'L', 'O', 'V', 'E' });
		// 比较结果
		assertTrue(result9.get(0) == 'L');
		assertTrue(result9.get(1) == 'E');

		// 等价类，输入字符数组为空
		result9 = t.TestingCases_CharSets_Boundry(new char[] {});
		assertTrue(result9 == null);

		// 边界值,最小值，输入字符数组大小为1
		result9 = t.TestingCases_CharSets_Boundry(new char[] { 'L' });
		// 比较结果
		assertTrue(result9.get(0) == 'L');
		assertTrue(result9.get(1) == 'L');
	}

	@Test
	public void testTestingCases_CharSets_Partition()
	{
		// 等价类，输入字符数组不为空
		ArrayList<Character> result9 = t.TestingCases_CharSets_Partition(new char[] { 'L', 'O', 'V', 'E' });
		assertTrue(result9.get(0) == 'L');
		assertTrue(result9.get(1) == null);
		assertTrue(result9.get(2) == 'E');
		assertTrue(result9.get(3) != 'L' && result9.get(3) != 'O' && result9.get(3) != 'V' && result9.get(3) != 'E');

		// 等价类，输入字符数组为空
		result9 = t.TestingCases_CharSets_Partition(new char[] {});
		// 比较结果
		assertTrue(result9 == null);

		// 边界值,最小值，输入字符数组大小为1
		result9 = t.TestingCases_CharSets_Partition(new char[] { 'L' });
		// 比较结果
		assertTrue(result9.get(0) == 'L');
		assertTrue(result9.get(1) == null);
		assertTrue(result9.get(2) == 'L');
		assertTrue(result9.get(3) != 'L');

	}

	@Test
	public void testTestingCases_CharSets_Random()
	{
		ArrayList<Character> result9 = t.TestingCases_CharSets_Random(new char[] { 'L', 'O', 'V', 'E' });
		for (Character character : result9)
		{
			assertTrue(character>=(char)0 && character<=(char)255);
		}
	}

	@Ignore
	public void testTestingCases_CharSets_All()
	{
		fail("Not yet implemented");
	}

}
