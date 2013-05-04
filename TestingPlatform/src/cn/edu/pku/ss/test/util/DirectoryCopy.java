package cn.edu.pku.ss.test.util;

/**
 * �ļ���ؐ���������Կ�һ��ָ���ļ��A����������ļ���������Ŀ��е��ļ�
 * �Լ���Ŀ�����Ŀ�����ă���
 * ����������http://blog.csdn.net/fenglibing/archive/2006/04/13/661923.aspx
 */

import java.io.*;

public class DirectoryCopy {
	FileInputStream FIS;
	FileOutputStream FOS;

	public DirectoryCopy() {
	}

	// �ļ��A��ؐ���ĺ���

	public boolean copyDirectory(String SrcDirectoryPath,
			String DesDirectoryPath) {

		try {
			// ���������ڵ�Ŀ�
			File F0 = new File(DesDirectoryPath);
			if (!F0.exists()) {
				if (!F0.mkdir()) {
					System.out.println("Ŀ���ļ��A���棬����ʧ��!");
				}
			}
			File F = new File(SrcDirectoryPath);
			File[] allFile = F.listFiles(); // ȡ�î�ǰĿ�����������ļ�����������ļ����M��
			int totalNum = allFile.length; // ȡ�î�ǰ�ļ��A���ж����ļ��������ļ��A��
			String srcName = "";
			String desName = "";
			int currentFile = 0;
			// һ��һ���Ŀ�ؐ�ļ�
			for (currentFile = 0; currentFile < totalNum; currentFile++) {
				if (!allFile[currentFile].isDirectory()) {
					// ������ļ��ǲ���̎���ļ��ķ�ʽ
					srcName = allFile[currentFile].toString();
					desName = DesDirectoryPath + "\\"
							+ allFile[currentFile].getName();
					IOUtil.copy(srcName, desName);
				}
				// ������ļ��A�Ͳ����f�w̎��
				else {
					// �����f�w�xȡ�ļ��A�е����ļ��µă��ݣ����x���ļ��A��������ļ��A����ă���...
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
