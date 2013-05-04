package TestPhoneNumber;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestNumberTest
{

	TestNumber	t	= new TestNumber();

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testTestingCases_Scope_Boundry()
	{
		// ��֤��������Ƿ���ϱ߽�ֵҪ��
		String[] al = t.TestingCases_Scope_Boundry();
		assertTrue(al[0].length() == 10);
		assertTrue(al[1].length() == 11);
		assertTrue(al[2].length() == 12);
	}

	@Test
	public void testTestingCases_Scope_Partition()
	{
		// ��֤��������Ƿ���ϵȼ���Ҫ��
		String[] al = t.TestingCases_Scope_Partition();
		assertTrue(al[0].length() == 11);
		assertTrue(al[1].length() != 11);
	}

	@Test
	public void testTestingCases_Scope_Random()
	{
		// ��֤��������Ƿ�11
		String[] al = t.TestingCases_Scope_Random();
		assertTrue(al[0].length() == 11);
		assertTrue(al[1].length() == 11);
		assertTrue(al[2].length() == 11);
	}

}
