package cn.edu.pku.ss.test.util;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log {
	
	static Logger log = null; 
	static final String log4jConfig = "TestingPlatform_log4j.propertity";
	static final String log4jFile = System.getProperty(PropertiesUtil.PRO_HOME)+ "/" + log4jConfig;
	public static boolean EnableLog = true;
	
	static {
		PropertyConfigurator.configure(log4jFile) ;
		log = Logger.getLogger(Log.class);
	}
	
	public static void println(Object msg){
		if(EnableLog){
			log.debug(msg);
		}		
		System.out.println(msg);
	}
	
	public static void Debug(Object msg){	
		System.out.println(msg);
	}
	
	public static void main(String[] args){
		Log.println("wocao");
	}
}
