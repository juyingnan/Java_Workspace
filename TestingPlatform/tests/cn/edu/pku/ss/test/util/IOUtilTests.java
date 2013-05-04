package cn.edu.pku.ss.test.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import cn.edu.pku.ss.test.jobs.JobConst;

public class IOUtilTests {
	
	@Test
	public void testListJarFiles(){
		
		String expected = "E:\\Programs\\Java\\workspace\\TestingPlatform\\lib\\jdom.jar;E:\\Programs\\Java\\workspace\\TestingPlatform\\lib\\jgap.jar;E:\\Programs\\Java\\workspace\\TestingPlatform\\lib\\log4j-1.2.14.jar;E:\\Programs\\Java\\workspace\\TestingPlatform\\lib\\tools.jar;";		
		String actual = IOUtil.listJarFiles(IOUtil.getBasePath() + "lib\\", ";");
		Assert.assertEquals(expected, actual);
	}

}
