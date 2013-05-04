package cn.edu.pku.ss.test.cpp.jobs.visitors;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTArrayModifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTInitializer;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTPointerOperator;
import org.eclipse.cdt.core.dom.ast.IASTProblem;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTTypeId;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator;
import org.eclipse.cdt.internal.core.dom.parser.ASTAmbiguousNode;

import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.jobs.IJob;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.util.SourcePrinter;

public class ASTViewVisitor extends ASTVisitor implements IJob{
	
	private SourcePrinter printer = new SourcePrinter();
	
	public ASTViewVisitor() {
		this(true);
	}
	
	public ASTViewVisitor(boolean visitNodes) {
		super(visitNodes);

	}	
	
	
	@Override
	public boolean run(TestData data) {
		Object astObj = data.get(JobConst.AST);
		
		if(astObj==null){
			data.getResult().addReport("Null AST!");
			return false;
		}
		
		IASTNode n = (IASTNode) astObj;
		if (n == null) {
			data.getResult().addReport("Null AST!");
			return false;
		}

		n.accept(this);
		
		data.put(JobConst.ASTVIEW, printer.getSource());
		
		return true;

	}
	
	
	public String getView(String path){
		printer = new SourcePrinter();
		CppParser p = new CppParser();
		p.parse(path).accept(this);		
		return printer.getSource();
	}
	
	// visit methods
	public int visit(IASTTranslationUnit n) {			
		printer.println(n.getClass().getSimpleName());
		printer.indent();
		return PROCESS_CONTINUE;
	}

	public int visit(IASTName n) {
		printer.println(n.getClass().getSimpleName());
		printer.indent();
		return PROCESS_CONTINUE;
	}

	public int visit(IASTDeclaration n) {
		printer.println(n.getClass().getSimpleName());
		printer.indent();
		return PROCESS_CONTINUE;
	}

	public int visit(IASTInitializer n) {
		printer.println(n.getClass().getSimpleName());
		printer.indent();
		return PROCESS_CONTINUE;
	}

	public int visit(IASTParameterDeclaration n) {
		printer.println(n.getClass().getSimpleName());
		printer.indent();
		return PROCESS_CONTINUE;
	}

	public int visit(IASTDeclarator n) {
		printer.println(n.getClass().getSimpleName());
		printer.indent();
		return PROCESS_CONTINUE;
	}

	public int visit(IASTDeclSpecifier n) {
		printer.println(n.getClass().getSimpleName());
		printer.indent();
		return PROCESS_CONTINUE;
	}

	/**
	 * @since 5.1
	 */
	public int visit(IASTArrayModifier n) {
		printer.println(n.getClass().getSimpleName());
		printer.indent();
		return PROCESS_CONTINUE;
	}

	/**
	 * @since 5.1
	 */
	public int visit(IASTPointerOperator n) {
		printer.println(n.getClass().getSimpleName());
		printer.indent();
		return PROCESS_CONTINUE;
	}

	public int visit(IASTExpression n) {
		printer.println(n.getClass().getSimpleName());
		printer.indent();
		return PROCESS_CONTINUE;
	}

	public int visit(IASTStatement n) {
		printer.println(n.getClass().getSimpleName());
		printer.indent();
		return PROCESS_CONTINUE;
	}

	public int visit(IASTTypeId n) {
		printer.println(n.getClass().getSimpleName());
		printer.indent();
		return PROCESS_CONTINUE;
	}

	public int visit(IASTEnumerator n) {
		printer.println(n.getClass().getSimpleName());
		printer.indent();
		return PROCESS_CONTINUE;
	}
	
	public int visit( IASTProblem n ){
		printer.println(n.getClass().getSimpleName());
		printer.indent();
		return PROCESS_CONTINUE;
	}
	
	// leave methods
	public int leave(IASTTranslationUnit tu) {
		printer.unindent();
		return PROCESS_CONTINUE;
	}

	public int leave(IASTName name) {
		printer.unindent();
		return PROCESS_CONTINUE;
	}

	public int leave(IASTDeclaration declaration) {
		printer.unindent();
		return PROCESS_CONTINUE;
	}

	public int leave(IASTInitializer initializer) {
		printer.unindent();
		return PROCESS_CONTINUE;
	}

	public int leave(IASTParameterDeclaration parameterDeclaration) {
		printer.unindent();
		return PROCESS_CONTINUE;
	}

	public int leave(IASTDeclarator declarator) {
		printer.unindent();
		return PROCESS_CONTINUE;
	}

	public int leave(IASTDeclSpecifier declSpec) {
		printer.unindent();
		return PROCESS_CONTINUE;
	}

	/**
	 * @since 5.1
	 */
	public int leave(IASTArrayModifier arrayModifier) {
		printer.unindent();
		return PROCESS_CONTINUE;
	}
	/**
	 * @since 5.1
	 */
	public int leave(IASTPointerOperator ptrOperator) {
		printer.unindent();
		return PROCESS_CONTINUE;
	}

	public int leave(IASTExpression expression) {
		printer.unindent();
		return PROCESS_CONTINUE;
	}

	public int leave(IASTStatement statement) {
		printer.unindent();
		return PROCESS_CONTINUE;
	}

	public int leave(IASTTypeId typeId) {
		printer.unindent();
		return PROCESS_CONTINUE;
	}

	public int leave(IASTEnumerator enumerator) {
		printer.unindent();
		return PROCESS_CONTINUE;
	}
	
	public int leave(IASTProblem problem){
		printer.unindent();
		return PROCESS_CONTINUE;
	}
	


	public int visit(ASTAmbiguousNode astAmbiguousNode) {
		printer.unindent();
		return PROCESS_CONTINUE;
	}


}
