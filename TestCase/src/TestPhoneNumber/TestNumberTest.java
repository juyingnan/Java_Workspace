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
		// 验证输出长度是否符合边界值要求
		String[] al = t.TestingCases_Scope_Boundry();
		assertTrue(al[0].length() == 10);
		assertTrue(al[1].length() == 11);
		assertTrue(al[2].length() == 12);
	}

	@Test
	public void testTestingCases_Scope_Partition()
	{
		// 验证输出长度是否符合等价类要求
		String[] al = t.TestingCases_Scope_Partition();
		assertTrue(al[0].length() == 11);
		assertTrue(al[1].length() != 11);
	}

	@Test
	public void testTestingCases_Scope_Random()
	{
		// 验证输出长度是否11
		String[] al = t.TestingCases_Scope_Random();
		assertTrue(al[0].length() == 11);
		assertTrue(al[1].length() == 11);
		assertTrue(al[2].length() == 11);
	}

}
