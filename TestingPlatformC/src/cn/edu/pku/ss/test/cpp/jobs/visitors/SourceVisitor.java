package cn.edu.pku.ss.test.cpp.jobs.visitors;



import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTPointerOperator;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTTypeId;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier.ICPPASTBaseSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTArraySubscriptExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTBinaryExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTBreakStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCaseStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCastExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCatchHandler;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompoundStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTContinueStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDeclarationStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDefaultStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDoStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTElaboratedTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTExpressionList;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTExpressionStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFieldReference;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTForStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionCallExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTGotoStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTIdExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTIfStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTInitializerExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLabelStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLinkageSpecification;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLiteralExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTNamedTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTNullStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTParameterDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTPointer;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTProblem;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTProblemDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTProblemStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTReturnStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSwitchStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTryBlockStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTypeIdExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTUnaryExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTUsingDirective;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTWhileStatement;

import cn.edu.pku.ss.test.cpp.Const;
import cn.edu.pku.ss.test.cpp.ast.CppAST;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.util.Log;
import cn.edu.pku.ss.test.util.SourcePrinter;

public class SourceVisitor extends CppVisitor {

	protected SourcePrinter printer = new SourcePrinter();
	
		
	public static String getASTCode(IASTNode node){
		
		SourceVisitor sv = new SourceVisitor();
		node.accept(sv);
		return sv.getSource();
		
	}	

	public boolean run(TestData data){
		if(super.run(data)){			
			data.put(Const.SOURCE_WITH_MONITOR, this.getSource());
			return true;
		} else {
			return false;
		}
	}
	
	public String getSource() {
		return printer.getSource();
	}

	public SourcePrinter getPrinter() {
		return printer;
	}

	public SourceVisitor() {
		this(true);
	}

	public SourceVisitor(boolean visitNodes) {
		super(visitNodes);

	}	
	
	@Override
	public int visit(IASTTranslationUnit n) {		
		visitHeader(n);	
		printer.println();
		for(IASTDeclaration s: n.getDeclarations()){
			s.accept(this);
			printer.println();
		}
		
		check(null);
		return PROCESS_SKIP;
	}


	@Override
	public int visit(IASTPreprocessorStatement n) {
		String str = n.toString();
		if (str.startsWith("#")) {
			printer.print(str);
		} else {
			printer.print("#define " + n.toString());
		}
		printer.println();
		return PROCESS_CONTINUE;
	}

	/**
	 * 方法
	 * */
	@Override
	public int visit(CPPASTFunctionDefinition n) {
		printer.println();		
		n.getDeclSpecifier().accept(this);
		printer.print(" ");
		n.getDeclarator().accept(this);
		printer.print(" ");
		n.getBody().accept(this);
		//printer.printLn();	
		return PROCESS_SKIP;
	}

	@Override
	public int leave(CPPASTFunctionDefinition n) {
		//printer.printLn();
		return PROCESS_CONTINUE;

	}

	/**
	 * 简单声明
	 * */
	@Override
	public int visit(CPPASTSimpleDeclaration n) {
		n.getDeclSpecifier().accept(this);
		
		if(n.getDeclarators()!=null){
			printer.print(" ");
			IASTDeclarator[] s = n.getDeclarators();
			for(int i=0; i< s.length-1; i++){
				s[i].accept(this);
				printer.print(" ");
			}
			if(s.length>0){
				s[s.length-1].accept(this);
			}
			
		}
		
		printer.print(";");
		return PROCESS_SKIP;

	}

	@Override
	public int leave(CPPASTSimpleDeclaration n) {
		//if(enter){
		//	printer.printLn(";");
		//}/else{
		
		//	enter = true;
		//}
		return PROCESS_CONTINUE;
	}

	
	@Override
	public int visit(CPPASTParameterDeclaration n) {		
		n.getDeclSpecifier().accept(this);
		printer.print(" ");
		n.getDeclarator().accept(this);		
		return PROCESS_SKIP;

	}

	@Override
	public int visit(CPPASTUsingDirective n) {
		
		printer.println("using namespace " + n.getQualifiedName().toString()
				+ ";");
		
		return PROCESS_CONTINUE;

	}
	
	public int visit(CPPASTLinkageSpecification n){		
		printer.println("extern " + n.getLiteral()+ " {");
		return PROCESS_CONTINUE;
	}

	public int leave(CPPASTLinkageSpecification n){		
		printer.println("}");		
		return PROCESS_CONTINUE;
	}
	
	public int visit(CPPASTProblemDeclaration n){
		//try {
			//printer.print(n.getSyntax().getImage() + " ");
		//} catch (ExpansionOverlapsBoundaryException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		return PROCESS_CONTINUE;
	}
	
	public int visit(CPPASTProblem n){
		
		//try {
		//	printer.print(n.getSyntax().getImage()+ " " + n.);
		//}// catch (ExpansionOverlapsBoundaryException e) {
		//	// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		
		return PROCESS_CONTINUE;
	}
	
	@Override
	public int visit(CPPASTSimpleDeclSpecifier n) {
		printer.print(n.toString());
		return PROCESS_CONTINUE;

	}

	@Override
	public int visit(CPPASTElaboratedTypeSpecifier n) {
		if(n.getStorageClass()==1){
			printer.print("typedef ");
		}		
		printer.print(CppAST.ElaboratedTypeSpecifier[n.getKind()] + " ");		
		printer.print(n.getName().toString());		
		return PROCESS_CONTINUE;
	}
	

	@Override
	public int visit(CPPASTCompositeTypeSpecifier n) {
		ICPPASTBaseSpecifier[] bs = n.getBaseSpecifiers();
		for(int i=0; i < bs.length; i++){
			bs[i].accept(this);
		}
		
		//n.getKey();
		
		if(n.getStorageClass() == 0){
			printer.print("typedef ");
		}else {
			//printer.print("debug:xx n.getStorageClass()= " + n.getStorageClass());
		}
		//TODO: Complete the code
		//n.isConst()
		//n.isExplicit()
		//n.isFriend()
		//n.isInline()
		//n.isVirtual()
		//n.isVolatile()
		
		if(n.getKey()==1){
			printer.print("struct ");
		}else {
			
		}
		
		printer.print(n.getName().toString());
		printer.println("{ ");
		printer.indent();
		IASTDeclaration[]  m = n.getMembers();
		for(int i=0; i < m.length; i++){
			m[i].accept(this);
		}
		printer.unindent();
		printer.print("}");
			
		return PROCESS_SKIP;
	}


	@Override
	public int visit(CPPASTNamedTypeSpecifier n) {
		//n.getStorageClass()
//		switch(n.getStorageClass()){
//		
//		case 0:
//			/**
//			 * <code>sc_unspecified</code> undefined storage class
//			 */
//			printer.print(" undefined ");
//			break;
//		case 1:
//			/**
//			 * <code>sc_typedef</code> typedef
//			 */
//			printer.print(" typedef ");
//			break;
//		case 2:
//			/**
//			 * <code>sc_extern</code>extern
//			 */
//			printer.print(" extern ");
//			break;
//		case 3:
//			/**
//			 * <code>sc_static</code>static
//			 */
//			printer.print(" static ");
//			break;
//		case 4:
//			/**
//			 * <code>sc_auto</code>auto
//			 */
//			printer.print(" auto ");
//			break;
//		case 5:
//			/**
//			 * <code>sc_register</code>register
//			 */
//			printer.print(" register ");
//			break;
//		}		
		
		printer.print(n.getName().toString());
		return PROCESS_SKIP;
	}

	
	@Override
	public int visit(CPPASTDeclarator n) {
		
		if(n.getPointerOperators()!=null && n.getPointerOperators().length>0){
			for(IASTPointerOperator p : n.getPointerOperators()){
				p.accept(this);
			}
			printer.print(" ");
		}
		
		printer.print(n.getName().toString());
		
		if(n.getInitializer() != null) {
			n.getInitializer().accept(this);
		}
		
		//printer.print(n.getPointerOperators())
		//if (parameterCount == 0) {
			//printer.printLn(";");
		//}

		//if (parameterCount > 0) {
		//	parameterCount--;
		//	if (parameterCount == 0) {
		//		printer.print(")");
		//	} else {
		//		printer.print(", ");
		//	}
		//}
		return PROCESS_SKIP;

	}

	@Override
	public int visit(CPPASTFunctionDeclarator n) {
		//IASTTypeId[] ids= n.getExceptionSpecification();
		//for (int i = 0; i < ids.length; i++) {
		//	ids[i].accept(this);
		//}
		if(n.getPointerOperators()!=null && n.getPointerOperators().length>0){
			for(IASTPointerOperator p : n.getPointerOperators()){
				p.accept(this);
			}
			printer.print(" ");
		}
			
		printer.print(n.getName().toString() + "(");
		
		if(n.getParameters().length>0){
			IASTParameterDeclaration[] s = n.getParameters();
			s[0].accept(this);
			for(int i =1; i< s.length; i++){
				printer.print(", ");
				s[i].accept(this);
			}
			
		}		
		printer.print(")");
		
		if(n.getExceptionSpecification()!=null){
			for(IASTTypeId s : n.getExceptionSpecification()){
				s.accept(this);
				printer.print(" ");
			}
		}
		
		//}

		return PROCESS_SKIP;

	}


	@Override
	public int visit(CPPASTPointer n) {
		printer.print("*");
	    if(n.isConst()){
	    	printer.print(" const");
	    }else if(n.isVolatile()){
	    	printer.print(" volatile");
	    }
		return PROCESS_CONTINUE;
	}
	
	@Override
	public int visit(CPPASTInitializerExpression n) {
		printer.print(" = ");
		//needSemicolon = true;
		return PROCESS_CONTINUE;

	}

	@Override
	public int visit(CPPASTLiteralExpression n) {
		printer.print(new String(n.getValue()));
		return PROCESS_CONTINUE;

	}

	@Override
	public int visit(CPPASTBinaryExpression n) {
		//opStack.push(CppOperator.Operators[n.getOperator()]);
		n.getOperand1().accept(this);
		printer.print(" " + CppAST.Operators[n.getOperator()] + " ");		
		n.getOperand2().accept(this);
		return PROCESS_SKIP;
	}

	@Override
	public int visit(CPPASTIdExpression n) {
		printer.print(n.getName().toString());

		return PROCESS_CONTINUE;

	}

	@Override
	public int visit(CPPASTUnaryExpression n) {
		switch (n.getOperator()) {
		case 0:
			/**
			 * Prefix increment. <code>op_prefixIncr</code> ++exp
			 */
			printer.print("++");
			n.getOperand().accept(this);
			break;
		case 1:
			/**
			 * Prefix decrement. <code>op_prefixDecr</code> --exp
			 */
			printer.print("--");
			n.getOperand().accept(this);
			break;
		case 2:
			/**
			 * Operator plus. <code>op_plus</code> ==> + exp
			 */
			printer.print("+");
			n.getOperand().accept(this);
			break;
		case 3:
			/**
			 * Operator minus. <code>op_minux</code> ==> -exp
			 */
			printer.print("-");
			n.getOperand().accept(this);
			break;
		case 4:

			/**
			 * Operator star. <code>op_star</code> ==> *exp
			 */
			printer.print("*");
			n.getOperand().accept(this);
			break;

		case 5:
			/**
			 * Operator ampersand. <code>op_amper</code> ==> &exp
			 */
			printer.print("&");
			n.getOperand().accept(this);
			break;
		case 6:
			/**
			 * Operator tilde. <code>op_tilde</code> ==> ~exp
			 */
			printer.print("~");
			n.getOperand().accept(this);
			break;
		case 7:
			/**
			 * not. <code>op_not</code> ==> ! exp
			 */
			printer.print("!");
			n.getOperand().accept(this);
			break;
		case 8:
			/**
			 * sizeof. <code>op_sizeof</code> ==> sizeof exp
			 */
			printer.print("sizeof ");
			n.getOperand().accept(this);
			break;

		case 9:
			/**
			 * Postfix increment. <code>op_postFixIncr</code> ==> exp++
			 */
			n.getOperand().accept(this);
			printer.print("++");
			break;

		case 10:
			/**
			 * Postfix decrement. <code>op_bracketedPrimary</code> ==> exp--
			 */
			n.getOperand().accept(this);
			printer.print("--");
			break;

		case 11:
			/**
			 * Postfix decrement. <code>op_bracketedPrimary</code> ==>  ( exp )
			 */
			printer.print("(");
			n.getOperand().accept(this);
			printer.print(")");
			break;
		case 12:
			/**
			 * for c++, only. <code>op_throw</code> throw exp
			 */
			printer.print(" throw ");
			n.getOperand().accept(this);			
			break;
		case 13:
			/**
			 * for c++, only. <code>op_typeid</code> = typeid( exp )
			 */
			printer.print(" typeid(");
			n.getOperand().accept(this);
			printer.print(")");
			break;
		case 14:
			/**
			 * for gnu parsers, only. <code>op_typeof</code> is used for 
			 * typeof( unaryExpression ) type expressions.
			 */
			printer.print(" typeof(");
			n.getOperand().accept(this);
			printer.print(")");
			break;
		case 15:
			/**
			 * for gnu parsers, only. <code>op_typeof</code> is used for 
			 * __alignOf(unaryExpression ) type expressions.
			 */
			printer.print(" __alignOf(");
			n.getOperand().accept(this);
			printer.print(")");
			break;
		}
		
		//printer.print(n.getParent().getClass().toString());
		
		return PROCESS_SKIP;
	}


	@Override
	public int visit(CPPASTCastExpression n) {
		
		printer.print("(");
        n.getTypeId().accept(this);
        printer.print(") ");
        n.getOperand().accept(this);
		return PROCESS_SKIP;
	}
	

	@Override
	public int visit(CPPASTFunctionCallExpression n) {
		//ICPPFunction fun = n.getOperator();
		if(n.getFunctionNameExpression()!=null)
			n.getFunctionNameExpression().accept(this);
		printer.print("(");
		if(n.getParameterExpression()!=null){
			//Debug.println(n.getParameterExpression().getClass());
			n.getParameterExpression().accept(this);
		}
		printer.print(")");
		return PROCESS_SKIP;
	}

	@Override
	public int leave(CPPASTFunctionCallExpression n) {		
		//printer.print(")");
		return PROCESS_CONTINUE;
	}

	public int visit(CPPASTTypeIdExpression n){
		
		switch(n.getOperator()){
			case 0:
				/**
				 * <code>op_sizeof</code> sizeof( typeId ) expression
				 */
				printer.print("sizeof( ");
				n.getTypeId().accept(this);
				printer.print(" )");
				break;
			case 1:
				/**
				 * For c++, only.
				 */
				printer.print("( ");
				n.getTypeId().accept(this);
				printer.print(" )");
				break;
			case 2:
				/**
				 * For gnu-parsers, only.
				 * <code>op_alignOf</code> is used for __alignOf( typeId ) type expressions.
				 */
				printer.print("__alignOf( ");
				n.getTypeId().accept(this);
				printer.print(" )");
				break;	
				
			case 3:
				/**
				 * For gnu-parsers, only.
				 * <code>op_typeof</code> is used for typeof( typeId ) type expressions.
				 */
				printer.print("typeof( ");
				n.getTypeId().accept(this);
				printer.print(" )");
				break;				
		}
		
		return PROCESS_SKIP;
	}
	
	public int visit(CPPASTFieldReference n){
		n.getFieldOwner().accept(this);
		//printer.print(n.getParent().getClass().toString());
		if(n.isPointerDereference()){
			printer.print("->");
		}else{
			printer.print(".");
		}
		
		//printer.print(n.isTemplate()+ "");
		
		printer.print(n.getFieldName().toString());
		
		return PROCESS_SKIP;
	}
	
	
	public int visit(CPPASTExpressionList n){		
		IASTExpression [] exprs = n.getExpressions();
		for(int i=0;i< exprs.length -1; i++){
			exprs[i].accept(this);
			printer.print(" ,");
		}
		if(exprs.length>0){
			exprs[exprs.length -1].accept(this);
		}
		return PROCESS_SKIP;
		
	}
	
	@Override
	public int visit(CPPASTExpressionStatement n) {		
		return PROCESS_CONTINUE;
	}
	

	@Override
	public int leave(CPPASTExpressionStatement n) {
		printer.print(";");
		return PROCESS_CONTINUE;
	}

	
	public int visit(CPPASTArraySubscriptExpression n){	
		n.getArrayExpression().accept(this);
		printer.print("[");
		n.getSubscriptExpression().accept(this);
		printer.print("]");
		return PROCESS_SKIP;
	}
	
	
	
	
	@Override
	public int visit(CPPASTReturnStatement n) {
		 printer.print("return");
		 if(n.getReturnValue()!=null){
			 printer.print(" ");
			 n.getReturnValue().accept(this);
		 }
	     printer.print(";");
		return PROCESS_SKIP;
	}
	
	@Override
	public int leave(CPPASTReturnStatement n) {

		return PROCESS_CONTINUE;
	}

	
	@Override
	public int visit(CPPASTIfStatement n) {
		printer.print("if (");
		n.getConditionExpression().accept(this);
		printer.print(") ");
		// printer.printLn(n.getThenClause().getClass().toString());
		n.getThenClause().accept(this);

		if (n.getElseClause() != null) {
			if(n.getThenClause().getClass() != CPPASTCompoundStatement.class ){
				printer.println();
				printer.print("else ");
			} else {
				printer.print(" else ");
			}
			
			n.getElseClause().accept(this);
		}
		//printer.printLn();	
		return PROCESS_SKIP;
	}

	@Override
	public int leave(CPPASTIfStatement n) {
		return PROCESS_CONTINUE;
	}
	
	@Override
	public int visit(CPPASTWhileStatement n) {
		printer.print("while (");
		n.getCondition().accept(this);
		printer.print(") ");
		n.getBody().accept(this);
		//printer.printLn();	
		return PROCESS_SKIP;
	}
	
	@Override
	public int leave(CPPASTWhileStatement n) {
		return PROCESS_CONTINUE;
	}
	
	@Override
	public int visit(CPPASTForStatement n) {
		printer.print("for (");
		//enter = false;
		n.getInitializerStatement().accept(this);	
		n.getConditionExpression().accept(this);
		printer.print(";");
		n.getIterationExpression().accept(this);		
		printer.print(") ");
		n.getBody().accept(this);
		//printer.printLn();	
		return PROCESS_SKIP;
	}

	@Override
	public int leave(CPPASTForStatement n) {
		return PROCESS_CONTINUE;
	}


	@Override
	public int visit(CPPASTDoStatement n) {
		printer.print("do ");
		n.getBody().accept(this);
		printer.print(" while (");
		n.getCondition().accept(this);
		printer.print(");");
		return PROCESS_SKIP;
	}


	@Override
	public int leave(CPPASTDoStatement n) {
		// TODO Auto-generated method stub
		return PROCESS_CONTINUE;
	}
	

	
	@Override
	public int visit(CPPASTLabelStatement n) {
		 printer.print(n.getName().toString());
	     printer.print(": ");
	     n.getNestedStatement().accept(this);
		return PROCESS_CONTINUE;
	}

	@Override
	public int visit(CPPASTBreakStatement n) {
		printer.print("break;");
		return PROCESS_CONTINUE;
	}

	@Override
	public int visit(CPPASTCaseStatement n) {
		// TODO Auto-generated method stub
		return PROCESS_CONTINUE;

	}

	@Override
	public int visit(CPPASTCompoundStatement n) {
		printer.println("{");
		if(n.getStatements()!=null){
			 printer.indent();
			 visitStatements(n);
			 printer.unindent();			 
		}	        
	    printer.print("}");
		return PROCESS_SKIP;

	}

	protected void visitStatements(CPPASTCompoundStatement n) {
		for (IASTStatement s : n.getStatements()) {
		        s.accept(this);
		        printer.println();
		 }
	}

	@Override
	public int leave(CPPASTCompoundStatement n) {
		return PROCESS_CONTINUE;

	}

	@Override
	public int leave(CPPASTBreakStatement n) {
		// TODO Auto-generated method stub
		return PROCESS_CONTINUE;
	}

	@Override
	public int leave(CPPASTCaseStatement n) {
		// TODO Auto-generated method stub
		return PROCESS_CONTINUE;
	}

	@Override
	public int leave(CPPASTContinueStatement n) {
		// TODO Auto-generated method stub
		return PROCESS_CONTINUE;
	}

	@Override
	public int leave(CPPASTDeclarationStatement n) {
		// TODO Auto-generated method stub
		return PROCESS_CONTINUE;
	}

	@Override
	public int leave(CPPASTDefaultStatement n) {
		// TODO Auto-generated method stub
		return PROCESS_CONTINUE;
	}




	@Override
	public int leave(CPPASTGotoStatement n) {
		// TODO Auto-generated method stub
		return PROCESS_CONTINUE;
	}

	@Override
	public int leave(CPPASTLabelStatement n) {
		// TODO Auto-generated method stub
		return PROCESS_CONTINUE;
	}

	@Override
	public int leave(CPPASTNullStatement n) {
		// TODO Auto-generated method stub
		return PROCESS_CONTINUE;
	}

	@Override
	public int leave(CPPASTProblemStatement n) {
		// TODO Auto-generated method stub
		return PROCESS_CONTINUE;
	}



	@Override
	public int leave(CPPASTSwitchStatement n) {
		// TODO Auto-generated method stub
		return PROCESS_CONTINUE;
	}



	@Override
	public int leave(CPPASTCatchHandler n) {
		// TODO Auto-generated method stub
		return PROCESS_CONTINUE;
	}

	@Override
	public int leave(CPPASTTryBlockStatement n) {
		// TODO Auto-generated method stub
		return PROCESS_CONTINUE;
	}

	@Override
	public int visit(CPPASTContinueStatement n) {
		// TODO Auto-generated method stub
		return PROCESS_CONTINUE;
	}

	@Override
	public int visit(CPPASTDeclarationStatement n) {
		// TODO Auto-generated method stub
		return PROCESS_CONTINUE;
	}

	@Override
	public int visit(CPPASTDefaultStatement n) {
		// TODO Auto-generated method stub
		return PROCESS_CONTINUE;
	}


	@Override
	public int visit(CPPASTGotoStatement n) {
		// TODO Auto-generated method stub
		return PROCESS_CONTINUE;
	}

	@Override
	public int visit(CPPASTNullStatement n) {
		// TODO Auto-generated method stub
		return PROCESS_CONTINUE;
	}

	@Override
	public int visit(CPPASTProblemStatement n) {
		// TODO Auto-generated method stub
		return PROCESS_CONTINUE;
	}


	@Override
	public int visit(CPPASTSwitchStatement n) {
		// TODO Auto-generated method stub
		return PROCESS_CONTINUE;
	}



	@Override
	public int visit(CPPASTCatchHandler n) {
		// TODO Auto-generated method stub
		return PROCESS_CONTINUE;
	}

	@Override
	public int visit(CPPASTTryBlockStatement n) {
		// TODO Auto-generated method stub
		return PROCESS_CONTINUE;
	}




}
