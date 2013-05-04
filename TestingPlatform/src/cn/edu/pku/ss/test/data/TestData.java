package cn.edu.pku.ss.test.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import cn.edu.pku.ss.test.util.Log;
import cn.edu.pku.ss.test.util.IOUtil;
import cn.edu.pku.ss.test.util.Tools;

/**
 * 定义基本的待测数据输入数据，包括源文件和附加文件
 * */
public class TestData
{
	// 源文件
	private String						sourcePath;
	// 源文件，文件对象
	private File						fileSource;
	// 附加文件
	private ArrayList<AttachementFile>	attachedFiles	= new ArrayList<AttachementFile>();	;
	// 额外的参数列表
	private Hashtable<Object, Object>	arguments		= new Hashtable<Object, Object>();

	private TestResult					result			= new TestResult();

	private String						sourceCode;

	public TestData()
	{

	}

	/**
	 * 构造一个TestData
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
	 * 获取附加文件列表
	 * */
	public ArrayList<AttachementFile> getAttachFiles()
	{
		return attachedFiles;
	}

	/**
	 * 增加一个附加文件
	 * */
	public void addAttachedFile(AttachementFile file)
	{
		this.attachedFiles.add(file);
	}

	/**
	 * 增加一个附加文件
	 * */
	public void addAttachedFile(String file)
	{
		this.attachedFiles.add(new AttachementFile(file));
	}

	/**
	 * 增加一个附加文件
	 * */
	public void addAttachedFile(String file, boolean isSource)
	{
		this.attachedFiles.add(new AttachementFile(file, isSource));
	}

	/**
	 * 清空附加文件列表
	 * */
	public void clearAttachedFiles()
	{
		this.attachedFiles.clear();
	}

	/**
	 * 移除一个附加文件
	 * */
	public void removeAttachedFile(AttachementFile file)
	{
		this.attachedFiles.remove(file);
	}

	/**
	 * 将一同需要拷贝的文件列表拷贝到目标路径, 源文件不会被拷贝
	 * */
	public void copyList(String newPath)
	{

		// 格式化目标路径名称
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
	 * 获取需要编译的文件列表
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
	 * 获取附加文件的相对路径
	 * 
	 * @param index
	 *            附加文件的索引值
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
	 * 获取源文件路径
	 * */
	public String getSourcePath()
	{
		return sourcePath;
	}

	/**
	 * 获取源文件路径
	 * */
	public File getSourceFile()
	{
		return fileSource;
	}

	/**
	 * 设定源文件路径
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
	 * 附加文件的数据结构
	 * */
	public class AttachementFile
	{
		// 文件路径
		private String	file;
		// 是否需要一同编译
		private boolean	needCompile;

		/**
		 * 构造一个空的附加文件
		 * */
		public AttachementFile()
		{
		}

		/**
		 * 构造一个附加文件,默认编译
		 * */
		public AttachementFile(String file)
		{
			this.setFile(file);
			this.setNeedCompile(true);
		}

		/**
		 * 构造一个附加文件
		 * */
		public AttachementFile(String file, boolean needCompile)
		{
			this.setFile(file);
			this.setNeedCompile(needCompile);
		}

		/**
		 * 获取文件路径
		 * */
		public String getFile()
		{
			return file;
		}

		/**
		 * 设定文件路径
		 * */
		public void setFile(String file)
		{
			this.file = file;
		}

		/**
		 * 获取是否需要编译
		 * */
		public boolean needCompile()
		{
			return needCompile;
		}

		/**
		 * 设定是否需要编译
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
