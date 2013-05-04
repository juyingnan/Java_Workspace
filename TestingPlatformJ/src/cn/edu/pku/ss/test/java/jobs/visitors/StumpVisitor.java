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
 * StumpVisitor<R, A> ΪSourceVisitor<R, A> ����չ�������˲�׮�Ĺ���
 * */
public class StumpVisitor<R, A> extends SourceVisitor<R, A> implements IStumpManager {

	protected StumpTable stumpList = new StumpTable();
	// ����ʶ�����
	protected NamePosition namePosition;
	// ���ͺͷ����б�������ͬʱ���¼
	protected MethodTable methodTable;
	// Ҫ��׮�������б�
	protected ArrayList<String> interestes;
	// ��׮ʱÿ����ʹ�ò�ͬ�ı���,���� -- ������
	protected Hashtable<String, String> variableList;
	protected Hashtable<String, String> constructorInfo;
	// ��׮�������ͣ�����Import��Ϣ��StumpManager��������
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
	 * ��ʼ��������ÿ�α������ڵ�֮ǰ��Ҫ����һ�γ�ʼ������
	 * */
	public void initialize() {
		namePosition = new NamePosition();
		methodTable = new MethodTable();
		stumpList = new StumpTable();
		variableList = new Hashtable<String, String>();
		constructorInfo = new Hashtable<String, String>();
	}

	/**
	 * ע��Ҫ��׮������
	 * */
	public boolean register(String className) {
		if (!interestes.contains(className)) {
			interestes.add(className);
			return true;
		} else
			return false;

	}

	/**
	 * ���Ҫ��׮������
	 * */
	public boolean unregister(String className) {
		return interestes.remove(className);
	}

	/**
	 * ���Ҫ��׮������
	 * */
	public void clearRegistered() {
		interestes.clear();
		variableList.clear();
	}

	/**
	 * ��÷��������б�
	 * */
	public MethodTable getMethodTable() {
		return methodTable;
	}

	/**
	 * ���ò�׮��Ϣ
	 * */
	public StumpDeclarationType getStumpType() {
		return stumpType;
	}

	/**
	 * ��ȡStump�б�
	 * */
	public StumpTable getStumps() {
		return stumpList;
	}

	/**
	 * ��ȡ��׮�ı����б�
	 * */
	public Hashtable<String, String> getVariableList() {
		return variableList;
	}
	

	public Stump create(Object node, Stump stump) {
		if (stump != null) {
			// ����׮����ؽڵ�
			if (node != null)
				stump.setNode(node);
			// ���÷���
			stump.setCatalog(this.namePosition.getCurrentName());

			try {
				// ����׮�б��������׮���б��
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
	 * �������ڵ�
	 * */
	public R visit(CompilationUnit n, A arg) {
		// �������ڵ�֮ǰҪ��ʼ��
		initialize();
		return super.visit(n, arg);
	}

	/**
	 * �����������ڵ�
	 * */
	public R visit(PackageDeclaration n, A arg) {
		this.namePosition.setPackageName(n.getName().toString());
		super.visit(n, arg);
		return null;
	}

	/**
	 * ��ĸ��ڵ�
	 * */
	public R visit(ClassOrInterfaceDeclaration n, A arg) {

		// �����ڲ��࣬������ѹջ ,//ˢ������
		this.namePosition.enterClass(n.getName());
		// classNameStack.push(n.getName());

		// refreshClassName();
		// �����ǰ������һ������Ȥ��Ҫ��׮����
		if (interestes.contains(this.namePosition.getFullClassName())) {
			variableList.put(this.namePosition.getFullClassName(), Monitor
					.getInject());
			constructorInfo.put(this.namePosition.getFullClassName(), "");

			// System.out.println("debug: " + getFullClassName() + " - " +
			// StumpManager.getInject());
			// ����Import��Ϣ
			for (int i = 0; i < stumpType.getImports().size(); i++) {
				printer.println(ConstText.IMPORT
						+ stumpType.getImports().get(i) + ConstText.SEMICOLON);
			}

		}
		// ����ͷ��Ϣ
		this.visitClassOrInterfaceHeader(n, arg);

		printer.println(" {");
		printer.indent();
		if (n.getMembers() != null) {
			printMembers(n.getMembers(), arg);
		}

		if (interestes.contains(this.namePosition.getFullClassName())) {
			// ����ע��
			printer.println(ConstText.COMMENT);
			// ��������������Ϣ
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
	 * �����ĸ��ڵ�
	 * */
	public R visit(MethodDeclaration n, A arg) {
		methodTable.put(this.namePosition.getFullClassName(), n);
		this.namePosition.setMethodName(n.getName());
		super.visit(n, arg);
		this.namePosition.setMethodName("");

		return null;
	}



}
