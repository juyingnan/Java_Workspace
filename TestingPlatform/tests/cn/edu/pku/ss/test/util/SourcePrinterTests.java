
package cn.edu.pku.ss.test.util;

import org.junit.Test;

public final class SourcePrinterTests extends junit.framework.TestCase {

	@Test
	public void testIndent() {
		SourcePrinter p = new SourcePrinter();
		p.println("0123456789");
		p.indent();
		p.println("0123456789");
		p.indent();
		p.println("0123456789");
		p.unindent();
		p.println("0123456789");
		p.unindent();
		p.println("0123456789");
		Log.println(p.getSource());
		assertEquals(76, p.getSource().length());
		
	}
}