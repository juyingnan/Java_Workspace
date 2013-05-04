package TestInt;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestIntTest
{

	TestInt	t	= new TestInt();

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testTestingCases_Scope_Boundry()
	{
		// �ȼ��࣬min<max
		int min = 2;
		int max = 8;
		int[] al = t.TestingCases_Scope_Boundry(min, max);
		int[] el = { min - 1, min, min + 1, max - 1, max, max + 1 };
		assertArrayEquals(el, al);

		// �ȼ��࣬min>max
		min = 8;
		max = 2;
		al = t.TestingCases_Scope_Boundry(min, max);
		assertTrue(al == null);

		// �߽�ֵ����ΧΪ0
		min = 2;
		max = 2;
		al = t.TestingCases_Scope_Boundry(min, max);
		int[] el2 = { min - 1, min, min + 1, max - 1, max, max + 1 };
		assertArrayEquals(el2, al);

		// �߽�ֵ����ΧΪ0+1
		min = 2;
		max = 3;
		al = t.TestingCases_Scope_Boundry(min, max);
		int[] el3 = { min - 1, min, min + 1, max - 1, max, max + 1 };
		assertArrayEquals(el3, al);
	}

	@Test
	public void testTestingCases_Scope_Partition()
	{
		// �ȼ��࣬min<max
		int min = 2;
		int max = 8;
		int[] al = t.TestingCases_Scope_Partition(min, max);
		assertTrue(al[0] >= min && al[0] <= max);
		assertTrue(al[1] > min || al[0] > max);

		// �ȼ��࣬min>max
		min = 8;
		max = 2;
		al = t.TestingCases_Scope_Partition(min, max);
		assertTrue(al == null);

		// �߽�ֵ����ΧΪ0
		min = 2;
		max = 2;
		al = t.TestingCases_Scope_Partition(min, max);
		assertTrue(al[0] >= min && al[0] <= max);
		assertTrue(al[1] > min || al[0] > max);

		// �߽�ֵ����ΧΪ0+1
		min = 2;
		max = 3;
		al = t.TestingCases_Scope_Partition(min, max);
		assertTrue(al[0] >= min && al[0] <= max);
		assertTrue(al[1] > min || al[0] > max);
	}

	@Test
	public void testTestingCases_Scope_Random()
	{
		// �ȼ��࣬min<max
		int min = 2;
		int max = 8;
		int[] al = t.TestingCases_Scope_Random(min, max);
		assertTrue(al[0] >= min && al[0] <= max);
		assertTrue(al[1] >= min - 100 && al[0] <= max);
		assertTrue(al[2] >= min && al[0] <= max + 100);

		// �ȼ��࣬min>max
		min = 8;
		max = 2;
		al = t.TestingCases_Scope_Random(min, max);
		assertTrue(al == null);

		// �߽�ֵ����ΧΪ0
		min = 2;
		max = 2;
		al = t.TestingCases_Scope_Random(min, max);
		assertTrue(al[0] >= min && al[0] <= max);
		assertTrue(al[1] >= min - 100 && al[0] <= max);
		assertTrue(al[2] >= min && al[0] <= max + 100);

		// �߽�ֵ����ΧΪ0+1
		min = 2;
		max = 3;
		al = t.TestingCases_Scope_Random(min, max);
		assertTrue(al[0] >= min && al[0] <= max);
		assertTrue(al[1] >= min - 100 && al[0] <= max);
		assertTrue(al[2] >= min && al[0] <= max + 100);
	}

}
