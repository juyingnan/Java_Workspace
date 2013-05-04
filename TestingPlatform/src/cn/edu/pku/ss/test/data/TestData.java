package cn.edu.pku.ss.test.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import cn.edu.pku.ss.test.util.Log;
import cn.edu.pku.ss.test.util.IOUtil;
import cn.edu.pku.ss.test.util.Tools;

/**
 * ��������Ĵ��������������ݣ�����Դ�ļ��͸����ļ�
 * */
public class TestData
{
	// Դ�ļ�
	private String						sourcePath;
	// Դ�ļ����ļ�����
	private File						fileSource;
	// �����ļ�
	private ArrayList<AttachementFile>	attachedFiles	= new ArrayList<AttachementFile>();	;
	// ����Ĳ����б�
	private Hashtable<Object, Object>	arguments		= new Hashtable<Object, Object>();

	private TestResult					result			= new TestResult();

	private String						sourceCode;

	public TestData()
	{

	}

	/**
	 * ����һ��TestData
	 * */
	public TestData(String file)
	{
		setSourcePath(file);
	}

	public TestData(String file, ArrayList<AttachementFile> attachements)
	{
		setSourcePath(file);
		this.attachedFiles = attachements;
	}

	/**
	 * ��ȡ�����ļ��б�
	 * */
	public ArrayList<AttachementFile> getAttachFiles()
	{
		return attachedFiles;
	}

	/**
	 * ����һ�������ļ�
	 * */
	public void addAttachedFile(AttachementFile file)
	{
		this.attachedFiles.add(file);
	}

	/**
	 * ����һ�������ļ�
	 * */
	public void addAttachedFile(String file)
	{
		this.attachedFiles.add(new AttachementFile(file));
	}

	/**
	 * ����һ�������ļ�
	 * */
	public void addAttachedFile(String file, boolean isSource)
	{
		this.attachedFiles.add(new AttachementFile(file, isSource));
	}

	/**
	 * ��ո����ļ��б�
	 * */
	public void clearAttachedFiles()
	{
		this.attachedFiles.clear();
	}

	/**
	 * �Ƴ�һ�������ļ�
	 * */
	public void removeAttachedFile(AttachementFile file)
	{
		this.attachedFiles.remove(file);
	}

	/**
	 * ��һͬ��Ҫ�������ļ��б�����Ŀ��·��, Դ�ļ����ᱻ����
	 * */
	public void copyList(String newPath)
	{

		// ��ʽ��Ŀ��·������
		if (!newPath.endsWith(File.separator))
		{
			newPath += File.separator;
		}
		for (int i = 0; i < this.attachedFiles.size(); i++)
		{
			String newFilePath = newPath + getRelativePath(i);
			File orgFile = new File(attachedFiles.get(i).getFile());
			File newFile = new File(newFilePath);
			// Debug.println("new file: " + newFile);
			if (orgFile.isDirectory())
			{
				IOUtil.copyDir(attachedFiles.get(i).getFile(), newFile.getPath());
			}
			else
				IOUtil.copy(attachedFiles.get(i).getFile(), newFile.getPath());
		}
	}

	/**
	 * ��ȡ��Ҫ������ļ��б�
	 * */
	public ArrayList<String> getCompiledList()
	{

		ArrayList<String> list = new ArrayList<String>();
		File file = new File(sourcePath);
		list.add(file.getName());

		for (int i = 0; i < this.attachedFiles.size(); i++)
		{
			if (this.attachedFiles.get(i).needCompile())
			{
				list.add(getRelativePath(i));
			}
		}

		return list;
	}

	/**
	 * ��ȡ�����ļ������·��
	 * 
	 * @param index
	 *            �����ļ�������ֵ
	 * */
	protected String getRelativePath(int index)
	{
		if (index < this.attachedFiles.size())
		{
			if (this.attachedFiles.get(index).getFile().startsWith(fileSource.getParent()))
			{
				return this.attachedFiles.get(index).getFile().substring(fileSource.getParent().length() + 1);
			}
			else
			{
				return new File(this.attachedFiles.get(index).getFile()).getName();
			}
		}
		else
			return null;
	}

	/**
	 * ��ȡԴ�ļ�·��
	 * */
	public String getSourcePath()
	{
		return sourcePath;
	}

	/**
	 * ��ȡԴ�ļ�·��
	 * */
	public File getSourceFile()
	{
		return fileSource;
	}

	/**
	 * �趨Դ�ļ�·��
	 * */
	public void setSourcePath(String sourcePath)
	{
		this.sourcePath = sourcePath;
		fileSource = new File(sourcePath);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("src: " + this.sourcePath + "\r\n");

		for (int i = 0; i < this.attachedFiles.size(); i++)
		{
			sb.append("attach : " + attachedFiles.get(i).toString() + "\r\n");
		}
		return sb.toString();
	}

	public void setArguments(Hashtable<Object, Object> arguments)
	{
		this.arguments = arguments;
	}

	public Hashtable<Object, Object> getArguments()
	{
		return arguments;
	}

	public String getStr(Object key)
	{
		if (this.arguments.containsKey(key))
		{
			return this.arguments.get(key).toString();
		}
		else
			return "";
	}

	public String getStr(Object key, String defValue)
	{
		if (this.arguments.containsKey(key))
		{
			return this.arguments.get(key).toString();
		}
		else
		{
			this.arguments.put(key, defValue);
			return defValue;
		}
	}

	public boolean containsArg(Object key)
	{
		return this.arguments.containsKey(key);
	}

	public Object get(Object key)
	{
		return this.arguments.get(key);
	}

	public Object get(Object key, Object defValue)
	{
		if (this.arguments.containsKey(key))
		{
			return this.arguments.get(key);
		}
		else
		{
			this.arguments.put(key, defValue);
			return defValue;
		}

	}

	public void put(Object key, Object value)
	{
		if (key == null || value == null)
		{
			Log.println("Null key or value");
			Log.println(key);
			Log.println(value);

		}
		this.arguments.put(key, value);
	}

	public void setResult(TestResult result)
	{
		this.result = result;
	}

	public TestResult getResult()
	{
		return result;
	}

	public void setSourceCode(String sourceCode, String fileName)
	{
		String temp = IOUtil.getTempPath();
		String file = temp + fileName;
		IOUtil.write(file, sourceCode);
		this.setSourcePath(file);
		this.sourceCode = sourceCode;
	}

	public String getSourceCode()
	{
		return sourceCode;
	}

	/**
	 * �����ļ������ݽṹ
	 * */
	public class AttachementFile
	{
		// �ļ�·��
		private String	file;
		// �Ƿ���Ҫһͬ����
		private boolean	needCompile;

		/**
		 * ����һ���յĸ����ļ�
		 * */
		public AttachementFile()
		{
		}

		/**
		 * ����һ�������ļ�,Ĭ�ϱ���
		 * */
		public AttachementFile(String file)
		{
			this.setFile(file);
			this.setNeedCompile(true);
		}

		/**
		 * ����һ�������ļ�
		 * */
		public AttachementFile(String file, boolean needCompile)
		{
			this.setFile(file);
			this.setNeedCompile(needCompile);
		}

		/**
		 * ��ȡ�ļ�·��
		 * */
		public String getFile()
		{
			return file;
		}

		/**
		 * �趨�ļ�·��
		 * */
		public void setFile(String file)
		{
			this.file = file;
		}

		/**
		 * ��ȡ�Ƿ���Ҫ����
		 * */
		public boolean needCompile()
		{
			return needCompile;
		}

		/**
		 * �趨�Ƿ���Ҫ����
		 * */
		public void setNeedCompile(boolean needCompile)
		{
			this.needCompile = needCompile;
		}

		@Override
		public String toString()
		{
			return this.file + "  " + this.needCompile;
		}
	}

}
