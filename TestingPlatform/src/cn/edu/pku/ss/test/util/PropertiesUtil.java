package cn.edu.pku.ss.test.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

public class PropertiesUtil {

	public static final String PRO_FILE = "TestingPlatform.properity";
	public static final String PRO_HOME = "user.home";

	public static final String CLASS_PATH = "ClassPath";
	public static final String UPLOAD_PATH = "UploadPath";
	public static final String TEMP_PATH = "TempPath";
	public static final String TEMPLATE_PATH = "TemplatePath";
	public static final String TESTING_PATH = "TestingPath";
	
	private Properties props = null;

	private static PropertiesUtil instance = null;

	
	
	private PropertiesUtil() {
		props = new Properties();
		try {
			
			String filePath = System.getProperty(PRO_HOME)+ "/" + PRO_FILE;
			
			//if(filePath == null){
			//	JOptionPane.showMessageDialog(null, "没有读取到路径信息!\r\nSystem.getProperty(\"user.home\")=" +  
			//			System.getProperty(PRO_HOME), "系统错误", JOptionPane.ERROR_MESSAGE);				
			//} 
			//if(!new File(filePath).exists()){
			//	JOptionPane.showMessageDialog(null, "配置文件不存在!\r\n路径:" + filePath, "系统错误",
			//			JOptionPane.ERROR_MESSAGE);
			//}
			
			
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static boolean exist(){
		return new File(System.getProperty(PRO_HOME)+ "/" + PRO_FILE).exists();
	}
	
	public static PropertiesUtil getInstance() {
		if (instance == null) {
			instance = new PropertiesUtil();
		}
		return instance;
	}
	
	public static Properties parse(String porperties){
		Properties p = new Properties();
		if(porperties!=null)
		{
			try {
				
				InputStream in =  new ByteArrayInputStream(porperties.getBytes());
				p.load(in);
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		}
		
		return p;
	}

	// 根据key读取value
	public String readValue(String key) {
		String value = props.getProperty(key);
		return value;
	}

	
}
