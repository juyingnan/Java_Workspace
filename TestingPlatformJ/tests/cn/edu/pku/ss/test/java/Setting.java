package cn.edu.pku.ss.test.java;

import java.io.File;

import cn.edu.pku.ss.test.util.IOUtil;

public class Setting {
	public static String Base = IOUtil.getBasePath() + "TestingFiles" + File.separator;
	public static String CONDITION_FILE = "ConditionTest";
}
