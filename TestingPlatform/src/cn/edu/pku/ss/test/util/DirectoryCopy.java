package cn.edu.pku.ss.test.util;

/**
 * 文件拷貝函數，可以拷一個指定文件夾下面的所有文件，及其子目錄中的文件
 * 以及子目錄中子目錄下面的內容
 * 本程序来自http://blog.csdn.net/fenglibing/archive/2006/04/13/661923.aspx
 */

import java.io.*;

public class DirectoryCopy {
	FileInputStream FIS;
	FileOutputStream FOS;

	public DirectoryCopy() {
	}

	// 文件夾拷貝核心函數

	public boolean copyDirectory(String SrcDirectoryPath,
			String DesDirectoryPath) {

		try {
			// 創建不存在的目錄
			File F0 = new File(DesDirectoryPath);
			if (!F0.exists()) {
				if (!F0.mkdir()) {
					System.out.println("目標文件夾不存，創建失敗!");
				}
			}
			File F = new File(SrcDirectoryPath);
			File[] allFile = F.listFiles(); // 取得當前目錄下面的所有文件，將其放在文件數組中
			int totalNum = allFile.length; // 取得當前文件夾中有多少文件（包括文件夾）
			String srcName = "";
			String desName = "";
			int currentFile = 0;
			// 一個一個的拷貝文件
			for (currentFile = 0; currentFile < totalNum; currentFile++) {
				if (!allFile[currentFile].isDirectory()) {
					// 如果是文件是采用處理文件的方式
					srcName = allFile[currentFile].toString();
					desName = DesDirectoryPath + "\\"
							+ allFile[currentFile].getName();
					IOUtil.copy(srcName, desName);
				}
				// 如果是文件夾就采用遞歸處理
				else {
					// 利用遞歸讀取文件夾中的子文件下的內容，再讀子文件夾下面的子文件夾下面的內容...
					if (copyDirectory(
							allFile[currentFile].getPath().toString(),
							DesDirectoryPath + "\\"
									+ allFile[currentFile].getName().toString())) {
						// System.out.println("D Copy Successfully!");
					} else {
						System.out.println("SubDirectory Copy Error!");
					}
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
