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
		// �߽�ֵ����Сֵ
		int min = 1;
		int max = 8;
		ArrayList<String> result1 = t.TestingCases_Scope_Boundry(min, max, true);
		int[] el = { min, min + 1, min - 1, max, max + 1, max - 1 }; // �ڴ����
		int[] al = new int[el.length]; // ʵ�����
		for (int i = 0; i < al.length; i++) // ʵ�������ֵ
		{
			al[i] = result1.get(i).length();
		}
		assertArrayEquals(el, al); // �Ƚ��Ƿ���ͬ

		// �߽�ֵ����Сֵ-1
		min = 0;
		max = 8;
		result1 = t.TestingCases_Scope_Boundry(min, max, true);
		assertNull(result1);// ���ӦΪnull

		// �ȼ��࣬��Сֵ<0
		min = -4;
		max = 5;
		result1 = t.TestingCases_Scope_Boundry(min, max, true);
		assertNull(result1);// ���ӦΪnull

		// �ȼ��࣬���ֵС����Сֵ
		min = 4;
		max = 2;
		result1 = t.TestingCases_Scope_Boundry(min, max, true);
		assertNull(result1);// ���ӦΪnull
		
		//input illegal
		result1 = t.TestingCases_Scope_Boundry(min, max, true);
	}

	@Test
	public void testTestingCases_Scope_Partition()
	{
		// �߽�ֵ����Сֵ
		int min = 1;
		int max = 8;
		ArrayList<String> result1 = t.TestingCases_Scope_Partition(min, max, true);
		int[] el = { (min + max) / 2, min + max };// �ڴ����
		int[] al = new int[el.length];// ʵ�����
		for (int i = 0; i < al.length; i++)// ʵ�������ֵ
		{
			al[i] = result1.get(i).length();
		}
		assertArrayEquals(el, al);

		// �߽�ֵ����Сֵ-1
		min = 0;
		max = 8;
		result1 = t.TestingCases_Scope_Partition(min, max, true);
		assertTrue(result1 == null);// ���ӦΪnull

		// �ȼ��࣬��Сֵ<0
		min = -4;
		max = 5;
		result1 = t.TestingCases_Scope_Partition(min, max, true);
		assertTrue(result1 == null);// ���ӦΪnull

		// �ȼ��࣬���ֵС����Сֵ
		min = 4;
		max = 2;
		result1 = t.TestingCases_Scope_Partition(min, max, true);
		assertTrue(result1 == null);// ���ӦΪnull
	}

	@Test
	public void testTestingCases_Scope_Random()
	{
		// �߽�ֵ����Сֵ
		int min = 1;
		int max = 8;
		ArrayList<String> result3 = t.TestingCases_Scope_Random(min, max, true);
		// ����Ӧ��������ȷ�Χ֮��
		for (int i = 0; i < result3.size(); i++)
		{
			assertTrue(result3.get(i).length() > 0 && result3.get(i).length() <= (max + max - min + 1));
		}

		// �߽�ֵ����Сֵ-1
		min = 0;
		max = 8;
		result3 = t.TestingCases_Scope_Boundry(min, max, true);
		assertTrue(result3 == null);// ���ӦΪnull

		// �ȼ��࣬��Сֵ<0
		min = -4;
		max = 5;
		result3 = t.TestingCases_Scope_Boundry(min, max, true);
		assertTrue(result3 == null);// ���ӦΪnull

		// �ȼ��࣬���ֵС����Сֵ
		min = 4;
		max = 2;
		result3 = t.TestingCases_Scope_Boundry(min, max, true);
		assertTrue(result3 == null);// ���ӦΪnull
	}

	@Ignore
	public void testTestingCases_Scope_All()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testTestingCases_HttpIPURL_Boundry()
	{
		// �ڴ����
		String[] el = { "http://0.0.0.0", "http://-1.-1.-1.-1", "http://1.1.1.1", "http://255.255.255.255",
				"http://254.254.254.254", "http://256.256.256.256" };
		String[] al = new String[el.length];
		ArrayList<String> result = t.TestingCases_HttpIPURL_Boundry();
		// ʵ�����
		for (int i = 0; i < al.length; i++)
		{
			al[i] = result.get(i);
		}
		// ����Ƚ�
		assertArrayEquals(el, al);
	}

	@Test
	public void testTestingCases_HttpIPURL_Partition()
	{
		ArrayList<String> result = t.TestingCases_HttpIPURL_Partition();
		// ��1��ӦΪhttp://��ͷ
		assertTrue(result.get(0).startsWith("http://"));
		// ��1��Ӧû��http://��ͷ
		assertTrue(!(result.get(1).startsWith("http://")));
		int[] n = new int[4];
		String string = result.get(2).substring(7);
		String[] s = new String[4];
		s = string.split("\\.");
		// ��3����������ӦΪ0-255֮��
		for (int i = 0; i < n.length; i++)
		{
			n[i] = Integer.parseInt(s[i]);
			assertTrue(n[i] <= 255 && n[i] >= 0);
		}

		// ��4����������Ӧ����255
		string = result.get(3).substring(7);
		s = string.split("\\.");
		for (int i = 0; i < n.length; i++)
		{
			n[i] = Integer.parseInt(s[i]);
			assertTrue(n[i] > 255);
		}

		// ��5��Ӧ��Ϊ��������
		string = result.get(4).substring(7);
		s = string.split("\\.");
		assertTrue(s.length == 4);

		// ��6��Ӧ��Ϊ��Ϊ��������
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
		// �ȼ��࣬�����ַ����鲻Ϊ��
		ArrayList<Character> result9 = t.TestingCases_CharSets_Boundry(new char[] { 'L', 'O', 'V', 'E' });
		// �ȽϽ��
		assertTrue(result9.get(0) == 'L');
		assertTrue(result9.get(1) == 'E');

		// �ȼ��࣬�����ַ�����Ϊ��
		result9 = t.TestingCases_CharSets_Boundry(new char[] {});
		assertTrue(result9 == null);

		// �߽�ֵ,��Сֵ�������ַ������СΪ1
		result9 = t.TestingCases_CharSets_Boundry(new char[] { 'L' });
		// �ȽϽ��
		assertTrue(result9.get(0) == 'L');
		assertTrue(result9.get(1) == 'L');
	}

	@Test
	public void testTestingCases_CharSets_Partition()
	{
		// �ȼ��࣬�����ַ����鲻Ϊ��
		ArrayList<Character> result9 = t.TestingCases_CharSets_Partition(new char[] { 'L', 'O', 'V', 'E' });
		assertTrue(result9.get(0) == 'L');
		assertTrue(result9.get(1) == null);
		assertTrue(result9.get(2) == 'E');
		assertTrue(result9.get(3) != 'L' && result9.get(3) != 'O' && result9.get(3) != 'V' && result9.get(3) != 'E');

		// �ȼ��࣬�����ַ�����Ϊ��
		result9 = t.TestingCases_CharSets_Partition(new char[] {});
		// �ȽϽ��
		assertTrue(result9 == null);

		// �߽�ֵ,��Сֵ�������ַ������СΪ1
		result9 = t.TestingCases_CharSets_Partition(new char[] { 'L' });
		// �ȽϽ��
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
