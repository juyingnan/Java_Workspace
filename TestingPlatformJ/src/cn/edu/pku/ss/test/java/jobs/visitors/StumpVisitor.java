package cn.edu.pku.ss.test.java.jobs.visitors;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.Node;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.TypeDeclaration;

import java.util.ArrayList;
import java.util.Hashtable;



import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.inject.IStumpManager;
import cn.edu.pku.ss.test.inject.Stump;
import cn.edu.pku.ss.test.inject.StumpDeclarationType;
import cn.edu.pku.ss.test.inject.StumpTable;
import cn.edu.pku.ss.test.inject.monitors.Monitor;
import cn.edu.pku.ss.test.java.Const;
import cn.edu.pku.ss.test.java.util.ConstText;
import cn.edu.pku.ss.test.java.util.MethodTable;
import cn.edu.pku.ss.test.java.util.NamePosition;
import cn.edu.pku.ss.test.jobs.JobConst;

/**
 * StumpVisitor<R, A> 为SourceVisitor<R, A> 的扩展，增加了插桩的功能
 * */
public class StumpVisitor<R, A> extends SourceVisitor<R, A> implements IStumpManager {

	protected StumpTable stumpList = new StumpTable();
	// 名称识别相关
	protected NamePosition namePosition;
	// 类型和方法列表，遍历的同时会记录
	protected MethodTable methodTable;
	// 要插桩的类名列表
	protected ArrayList<String> interestes;
	// 插桩时每个类使用不同的变量,类名 -- 变量名
	protected Hashtable<String, String> variableList;
	protected Hashtable<String, String> constructorInfo;
	// 插桩声明类型，包括Import信息和StumpManager类型声明
	protected StumpDeclarationType stumpType;

	public StumpVisitor() {
		this.setFixModifiers(true);
		interestes = new ArrayList<String>();
		variableList = new Hashtable<String, String>();
		stumpType = new StumpDeclarationType();
		initialize();
	}

	@Override	
	public boolean run(TestData data){
		if(super.run(data)){
			data.put(Const.METHODTABLE, this.getMethodTable());
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 初始化操作，每次遍历根节点之前都要进行一次初始化操作
	 * */
	public void initialize() {
		namePosition = new NamePosition();
		methodTable = new MethodTable();
		stumpList = new StumpTable();
		variableList = new Hashtable<String, String>();
		constructorInfo = new Hashtable<String, String>();
	}

	/**
	 * 注册要插桩的类名
	 * */
	public boolean register(String className) {
		if (!interestes.contains(className)) {
			interestes.add(className);
			return true;
		} else
			return false;

	}

	/**
	 * 解除要插桩的类名
	 * */
	public boolean unregister(String className) {
		return interestes.remove(className);
	}

	/**
	 * 清空要插桩的类名
	 * */
	public void clearRegistered() {
		interestes.clear();
		variableList.clear();
	}

	/**
	 * 获得方法类型列表
	 * */
	public MethodTable getMethodTable() {
		return methodTable;
	}

	/**
	 * 设置插桩信息
	 * */
	public StumpDeclarationType getStumpType() {
		return stumpType;
	}

	/**
	 * 获取Stump列表
	 * */
	public StumpTable getStumps() {
		return stumpList;
	}

	/**
	 * 获取插桩的变量列表
	 * */
	public Hashtable<String, String> getVariableList() {
		return variableList;
	}
	

	public Stump create(Object node, Stump stump) {
		if (stump != null) {
			// 设置桩的相关节点
			if (node != null)
				stump.setNode(node);
			// 设置分类
			stump.setCatalog(this.namePosition.getCurrentName());

			try {
				// 加入桩列表，并对这个桩进行编号
				stumpList.put(this.namePosition.getCurrentName(), stump);

			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return stump;
	}

	public String getStumpCode(Stump stump) {
		stump.setFlushed(true);
		return variableList.get(this.namePosition.getFullClassName())
				+ ConstText.DOT + stump.getMethodStumpCode();
	}

	/**
	 * 遍历根节点
	 * */
	public R visit(CompilationUnit n, A arg) {
		// 遍历根节点之前要初始化
		initialize();
		return super.visit(n, arg);
	}

	/**
	 * 包名的申明节点
	 * */
	public R visit(PackageDeclaration n, A arg) {
		this.namePosition.setPackageName(n.getName().toString());
		super.visit(n, arg);
		return null;
	}

	/**
	 * 类的根节点
	 * */
	public R visit(ClassOrInterfaceDeclaration n, A arg) {

		// 处理内部类，将类名压栈 ,//刷新类名
		this.namePosition.enterClass(n.getName());
		// classNameStack.push(n.getName());

		// refreshClassName();
		// 如果当前的类是一个感兴趣的要插桩的类
		if (interestes.contains(this.namePosition.getFullClassName())) {
			variableList.put(this.namePosition.getFullClassName(), Monitor
					.getInject());
			constructorInfo.put(this.namePosition.getFullClassName(), "");

			// System.out.println("debug: " + getFullClassName() + " - " +
			// StumpManager.getInject());
			// 插入Import信息
			for (int i = 0; i < stumpType.getImports().size(); i++) {
				printer.println(ConstText.IMPORT
						+ stumpType.getImports().get(i) + ConstText.SEMICOLON);
			}

		}
		// 处理头信息
		this.visitClassOrInterfaceHeader(n, arg);

		printer.println(" {");
		printer.indent();
		if (n.getMembers() != null) {
			printMembers(n.getMembers(), arg);
		}

		if (interestes.contains(this.namePosition.getFullClassName())) {
			// 插入注释
			printer.println(ConstText.COMMENT);
			// 插入类型声明信息
			printer.println(ConstText.PUBLIC + stumpType.getStumpManagerName()
					+ ConstText.SPACE
					+ variableList.get(this.namePosition.getFullClassName())
					+ ConstText.DECLARATION + stumpType.getStumpManagerName()
					+ ConstText.BRACKET_LEFT
					+ constructorInfo.get(this.namePosition.getFullClassName())
					+ ConstText.BRACKET_RIGHT + ConstText.SEMICOLON);

		}

		printer.unindent();
		printer.print("}");
		this.namePosition.existClass();

		return null;
	}

	/**
	 * 方法的根节点
	 * */
	public R visit(MethodDeclaration n, A arg) {
		methodTable.put(this.namePosition.getFullClassName(), n);
		this.namePosition.setMethodName(n.getName());
		super.visit(n, arg);
		this.namePosition.setMethodName("");

		return null;
	}



}
