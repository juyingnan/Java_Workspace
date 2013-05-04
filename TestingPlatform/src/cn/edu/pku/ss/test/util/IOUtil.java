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

import cn.edu.pku.ss.test.jobs.JobConst;

public class IOUtil {
	
	private static String tempPath = null;
	
	public static String formatDirPath(String path){
		if(!path.endsWith(File.pathSeparator)){
			path+=File.pathSeparator;
		}
		return path;
	}
	
	public static String getTempPath(String prefix){
		if(tempPath==null){
			if(PropertiesUtil.exist()){
				tempPath = PropertiesUtil.getInstance().readValue(PropertiesUtil.TEMP_PATH);
			}
			if(tempPath==null || tempPath.isEmpty()){
				tempPath = System.getProperty("java.io.tmpdir");
				tempPath = formatDirPath(tempPath); 
				tempPath += "temppath" + File.separator;
			}
			
			File path = new File(tempPath);
			if(!path.exists())path.mkdirs();
		}		
		String dir = prefix + Tools.RANDOM.nextInt(Integer.MAX_VALUE);
		return tempPath +dir + File.separatorChar ;
	}

	
	public static String getTempPath(){
		return getTempPath(JobConst.PREFIX);
	}
	
	private static String basePath = new File("").getAbsolutePath() + "\\";
	
	public static String getBasePath(String relativePath){
		return basePath + relativePath;
	}
	
	public static String getBasePath(){
		return basePath;
	}
	
	/**
	 * 读取一个文件到字符串
	 * @param path 文件路径
	 * */
	public static String read(String path) {
		
		String line = null;
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(path));
			while ((line = in.readLine()) != null) {
				sb.append(line + JobConst.ENTER);
			}
			in.close();
		} catch (Exception e) {

			e.printStackTrace();
		}

		return sb.toString();

	}
	
	/**
	 * 写入一个字符串到文件
	 * @param path 文件路径
	 * @param content 字符串内容
	 * @return true 写入成功，false 写入失败
	 * */
	public static boolean write(String path, String content){
		File input = new File(path);
		return write(input, content);
	}


	public static boolean write(File input, String content) {
		try {
			if(!input.getParentFile().exists()){				
				input.getParentFile().mkdirs();
			}
			PrintWriter pw = new PrintWriter(input);
			pw.write(content);
			pw.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * 文件移动
	 * 
	 * @param input 源文件
	 * @param output 目标文件
	 * 
	 */
	public static void move(String input, String output) {
		File inputFile = new File(input);
		File outputFile = new File(output);
		inputFile.renameTo(outputFile);
	}

	public static boolean mkdirs(String dir){
		File file = new File(dir);
		if(file.exists()) return true;
		else{
			try{
				return file.mkdirs();
			}catch(Exception ex){
				ex.printStackTrace();
				return false;
			}
			
		}
	}
	
	public static boolean delete(String input){
		File file = new File(input);
		if(file.exists()){
			try{
				return file.delete();
			}catch(Exception ex){
				ex.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 文件拷贝
	 * 
	 * @param input 源文件
	 * @param output 目标文件
	 * 
	 */
	public static boolean copy(String input, String output){
		int BUFSIZE = 65536;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			mkdirs(new File(output).getParent());
			fis = new FileInputStream(input);
			fos = new FileOutputStream(output);			
			
			int s;
			byte[] buf = new byte[BUFSIZE];
			while ((s = fis.read(buf)) > -1) {
				fos.write(buf, 0, s);
			}
			
			
		} catch (Exception ex) {			
			ex.printStackTrace();
			return false;
		} finally{
			if(fis!=null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {					
					e.printStackTrace();
					return false;
				}
			}
		}
		
		return true;
	}

	/**
	 * 拷贝目录 包括子目录和文件
	 * 
	 * @param srcDir 源目录
	 * @param desDir 目标目录
	 */
	public static boolean copyDir(String srcDir,
			String desDir) {
		//为保证线程安全
		DirectoryCopy dc = new DirectoryCopy();
		return dc.copyDirectory(srcDir, desDir);
		
	}
	
	/**
	 * 删除目录.包括目录下所有文件和目录
	 * 
	 * @param directory 要删除的目录.
	 */
	public static void recursiveRemoveDir(File directory) throws Exception {
		if (!directory.exists())
			throw new IOException(directory.toString() + " do not exist!");
		String[] filelist = directory.list();
		File tmpFile = null;
		for (int i = 0; i < filelist.length; i++) {
			tmpFile = new File(directory.getAbsolutePath(), filelist[i]);
			if (tmpFile.isDirectory()) {
				recursiveRemoveDir(tmpFile);
			} else if (tmpFile.isFile()) {
				tmpFile.delete();
			}
		}
		directory.delete();
	}
	
	public static String listJarFiles(String path, String separator){
		File file = new File(path);
		StringBuilder sb = new StringBuilder();
		if(!file.isDirectory()) return path;
		File[] files = file.listFiles();
		if(files==null){
			return path + separator;
		}
		for(int i=0; i< files.length;i++){
			if(files[i].isDirectory()){
				sb.append( listJarFiles(files[i].toString(),separator));
			}
			else if(files[i].toString().toLowerCase().endsWith(".jar")){
				sb.append(files[i].toString() + separator);
			}
		}
		return sb.toString();
		
	}

}
