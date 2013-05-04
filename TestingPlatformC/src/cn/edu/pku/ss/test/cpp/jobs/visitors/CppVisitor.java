package cn.edu.pku.ss.test.cpp.jobs.visitors;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTArrayModifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTInitializer;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTPointerOperator;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorStatement;
import org.eclipse.cdt.core.dom.ast.IASTProblem;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTTypeId;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator;
import org.eclipse.cdt.internal.core.dom.parser.ASTAmbiguousNode;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTTypeIdInitializerExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTASMDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTAmbiguousBinaryVsCastExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTAmbiguousCastVsFunctionCallExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTAmbiguousCondition;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTAmbiguousDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTAmbiguousExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTAmbiguousStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTArraySubscriptExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTBinaryExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTBreakStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCaseStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCastExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCatchHandler;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompoundStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompoundStatementExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTConditionalExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTConstructorChainInitializer;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTConstructorInitializer;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTContinueStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDeclarationStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDefaultStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDeleteExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDoStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTElaboratedTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTEnumerationSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTExplicitTemplateInstantiation;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTExpressionList;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTExpressionStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFieldDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFieldReference;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTForStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionCallExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTGotoStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTIdExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTIfStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTInitializerExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTInitializerList;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLabelStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLinkageSpecification;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLiteralExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTNamedTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTNamespaceAlias;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTNamespaceDefinition;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTNewExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTNullStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTParameterDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTPointer;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTPointerToMember;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTProblem;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTProblemDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTProblemStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTReferenceOperator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTReturnStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleTypeConstructorExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSwitchStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTemplateDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTemplateSpecialization;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTryBlockStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTypeIdExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTypenameExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTUnaryExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTUsingDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTUsingDirective;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTVisibilityLabel;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTWhileStatement;

import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.jobs.IJob;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.util.Log;

public abstract class CppVisitor extends ASTVisitor implements IJob {

	ArrayList<IASTPreprocessorStatement> preProcessorArr = new ArrayList<IASTPreprocessorStatement>();
	
	public CppVisitor() {
		this(true);
	}
	
	public CppVisitor(boolean visitNodes) {
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
		preProcessorArr.clear();
		
		n.accept(this);
		
		
		
		return true;
	}

	

	// visit methods
	public int visit(IASTTranslationUnit n) {		
		visitHeader(n);	
		
		for(IASTDeclaration s: n.getDeclarations()){
			s.accept(this);
		}
		
		check(null);
		return PROCESS_SKIP;
	}

	public void visitHeader(IASTTranslationUnit n) {
		IASTDeclaration[] nodes = n.getDeclarations();
		int startOffset = -1;
	    if(nodes.length>0){
	    	startOffset = nodes[0].getNodeLocations()[0].getNodeOffset();
	    }
		
		//包括include信息，宏定义以及预处理定义
		IASTPreprocessorStatement[] stats = n.getAllPreprocessorStatements();
		for(int i=0; i< stats.length; i++){
			if(stats[i].getNodeLocations()[0].getNodeOffset() < startOffset){
				visit(stats[i]);	
			}
			else {
				preProcessorArr.add(stats[i]);
			}
		}
	}
	
	
	public void check(IASTNode n){
		if(preProcessorArr.size()==0 || 
				n!=null && n.getNodeLocations().length==0) return;
	
		while((n==null && preProcessorArr.size()>0) || 
				(preProcessorArr.size()>0 && n.getNodeLocations().length>0 &&
				n.getNodeLocations()[0].getNodeOffset() > preProcessorArr.get(0).getNodeLocations()[0].getNodeOffset())){
			visit(preProcessorArr.get(0));	
			preProcessorArr.remove(0);
			
		}
		
	}
	

	public int visit(IASTDeclaration n) {		
		check(n);
		
		Class clazz = n.getClass();
		if (clazz == CPPASTASMDeclaration.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTFunctionDefinition.class) {
			return visit((CPPASTFunctionDefinition)n);
		} else if (clazz == CPPASTProblemDeclaration.class) {
			return visit((CPPASTProblemDeclaration)n);
		} else if (clazz == CPPASTSimpleDeclaration.class) {
			return visit((CPPASTSimpleDeclaration)n);
		} else if (clazz == CPPASTExplicitTemplateInstantiation.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTLinkageSpecification.class) {
			return visit((CPPASTLinkageSpecification)n);
		} else if (clazz == CPPASTNamespaceAlias.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTNamespaceDefinition.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTTemplateDeclaration.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTTemplateSpecialization.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTUsingDeclaration.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTUsingDirective.class) {
			return visit((CPPASTUsingDirective)n);
		} else if (clazz == CPPASTVisibilityLabel.class) {
			//TODO: Complete code cpp by shootsoft
		}
		

		//Debug.println(n.getClass() + "-----" );
		//Debug.println(n.get
		return PROCESS_CONTINUE;
	}	



	public int visit(IASTInitializer n) {

		check(n);
		
		Class clazz = n.getClass();
		if (clazz == CPPASTInitializerExpression.class) {
			return visit((CPPASTInitializerExpression)n);
		} else if (clazz == CPPASTInitializerList.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTConstructorChainInitializer.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTConstructorInitializer.class) {
			//TODO: Complete code cpp by shootsoft
		} 
		
		
		//Debug.println(n.getClass());
		return PROCESS_CONTINUE;
	}

	public int visit(IASTParameterDeclaration n) {
		check(n);		
		Class clazz = n.getClass();
		if (clazz == CPPASTParameterDeclaration.class) {
			return visit((CPPASTParameterDeclaration)n);
		}		
		
		//Debug.println(n.getClass());
		return PROCESS_CONTINUE;
	}

	public int visit(IASTDeclarator n) {
		
		check(n);
		
		Class clazz = n.getClass();
		if (clazz == CPPASTDeclarator.class) {
			return visit((CPPASTDeclarator)n);
		} else if (clazz == CPPASTAmbiguousDeclarator.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTFieldDeclarator.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTFunctionDeclarator.class) {
			return visit((CPPASTFunctionDeclarator)n);
		} 
		
		//Debug.println(n.getClass());
		return PROCESS_CONTINUE;
	}

	public int visit(IASTDeclSpecifier n) {
		check(n);
		
		Class clazz = n.getClass();
		if (clazz == CPPASTCompositeTypeSpecifier.class) {
			return visit((CPPASTCompositeTypeSpecifier)n);	
		} else if (clazz == CPPASTElaboratedTypeSpecifier.class) {			
			return visit((CPPASTElaboratedTypeSpecifier)n);	
		} else if (clazz == CPPASTEnumerationSpecifier.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTNamedTypeSpecifier.class) {
			return visit((CPPASTNamedTypeSpecifier)n);		
		} else if (clazz == CPPASTSimpleDeclSpecifier.class) {
			return visit((CPPASTSimpleDeclSpecifier)n);			
		} else if (clazz == CPPASTCompositeTypeSpecifier.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTElaboratedTypeSpecifier.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTNamedTypeSpecifier.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTSimpleDeclSpecifier.class) {
			//TODO: Complete code cpp by shootsoft
		} 
		
		//Debug.println(n.getClass());
		return PROCESS_CONTINUE;
	}

	public int visit(IASTArrayModifier n) {
		//Debug.println(n.getClass());
		return PROCESS_CONTINUE;
	}

	public int visit(IASTPointerOperator n) {
		check(n);
		
		Class clazz = n.getClass();
		if (clazz == CPPASTPointer.class) {
			return visit((CPPASTPointer)n);	
		} else if (clazz == CPPASTPointerToMember.class) {			
			//TODO: Complete code cpp by shootsoft	
		} else if (clazz == CPPASTReferenceOperator.class) {
			//TODO: Complete code cpp by shootsoft
		} 
		return PROCESS_CONTINUE;
	}

	public int visit(IASTExpression n) {
		
		check(n);
		
		Class clazz = n.getClass();
		if (clazz == CPPASTAmbiguousCondition.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTAmbiguousBinaryVsCastExpression.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTAmbiguousCastVsFunctionCallExpression.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTAmbiguousExpression.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTArraySubscriptExpression.class) {
			return visit((CPPASTArraySubscriptExpression)n);			
		} else if (clazz == CPPASTBinaryExpression.class) {
			return visit((CPPASTBinaryExpression)n);
		} else if (clazz == CPPASTCastExpression.class) {
			return visit((CPPASTCastExpression)n);
		} else if (clazz == CPPASTConditionalExpression.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTExpressionList.class) {			
			return visit((CPPASTExpressionList)n);
		} else if (clazz == CPPASTFieldReference.class) {
			return visit((CPPASTFieldReference)n);
		}  else if (clazz == CPPASTFunctionCallExpression.class) {
			return visit((CPPASTFunctionCallExpression)n);
		}  else if (clazz == CPPASTIdExpression.class) {
			return visit((CPPASTIdExpression)n);
		}  else if (clazz == CPPASTLiteralExpression.class) {
			return visit((CPPASTLiteralExpression)n);			
		}  else if (clazz == CPPASTTypeIdExpression.class) {
			return visit((CPPASTTypeIdExpression)n);
		}  else if (clazz == CASTTypeIdInitializerExpression.class) {
			//TODO: Complete code cpp by shootsoft
		}  else if (clazz == CPPASTUnaryExpression.class) {
			return visit((CPPASTUnaryExpression)n);
		}  else if (clazz == CPPASTDeleteExpression.class) {
			//TODO: Complete code cpp by shootsoft
		}  else if (clazz == CPPASTNewExpression.class) {
			//TODO: Complete code cpp by shootsoft
		}  else if (clazz == CPPASTSimpleTypeConstructorExpression.class) {
			//TODO: Complete code cpp by shootsoft
		}  else if (clazz == CPPASTTypenameExpression.class) {
			//TODO: Complete code cpp by shootsoft
		}  else if (clazz == CPPASTCompoundStatementExpression.class) {
			//TODO: Complete code cpp by shootsoft
		} 		 
		
		//Debug.println(n.getClass());
		return PROCESS_CONTINUE;
	}

	public int visit(IASTStatement n) {
		check(n);
		
		if (n.getClass() == CPPASTAmbiguousStatement.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (n.getClass() == CPPASTBreakStatement.class) {
			return visit((CPPASTBreakStatement)n);
		} else if (n.getClass() == CPPASTCaseStatement.class) {
			return visit((CPPASTCaseStatement)n);
		} else if (n.getClass() == CPPASTCompoundStatement.class) {
			return visit((CPPASTCompoundStatement) n);
		} else if (n.getClass() == CPPASTContinueStatement.class) {
			return visit((CPPASTContinueStatement) n);
		} else if (n.getClass() == CPPASTDeclarationStatement.class) {
			return visit((CPPASTDeclarationStatement) n);
		} else if (n.getClass() == CPPASTDefaultStatement.class) {
			return visit((CPPASTDefaultStatement) n);
		} else if (n.getClass() == CPPASTDoStatement.class) {
			return visit((CPPASTDoStatement) n);
		} else if (n.getClass() == CPPASTExpressionStatement.class) {
			visit((CPPASTExpressionStatement) n);
		} else if (n.getClass() == CPPASTForStatement.class) {
			return visit((CPPASTForStatement) n);
		} else if (n.getClass() == CPPASTGotoStatement.class) {
			return visit((CPPASTGotoStatement) n);
		} else if (n.getClass() == CPPASTIfStatement.class) {
			return visit((CPPASTIfStatement) n);
		} else if (n.getClass() == CPPASTLabelStatement.class) {
			return visit((CPPASTLabelStatement) n);
		} else if (n.getClass() == CPPASTNullStatement.class) {
			return visit((CPPASTNullStatement) n);
		} else if (n.getClass() == CPPASTProblemStatement.class) {
			return visit((CPPASTProblemStatement) n);			
		} else if (n.getClass() == CPPASTReturnStatement.class) {
			return visit((CPPASTReturnStatement) n);
		} else if (n.getClass() == CPPASTSwitchStatement.class) {
			return visit((CPPASTSwitchStatement) n);
		} else if (n.getClass() == CPPASTWhileStatement.class) {
			return visit((CPPASTWhileStatement) n);
		} else if (n.getClass() == CPPASTCatchHandler.class) {
			return visit((CPPASTCatchHandler) n);
		} else if (n.getClass() == CPPASTTryBlockStatement.class) {
			return visit((CPPASTTryBlockStatement) n);
		}
		
		
		//Debug.println(n.getClass());		
		return PROCESS_CONTINUE;
	}
	


	public int visit(IASTTypeId n) {
		//Debug.println(n.getClass());
		return PROCESS_CONTINUE;
	}

	public int visit(IASTEnumerator n) {
		//Debug.println(n.getClass());
		return PROCESS_CONTINUE;
	}
	
	public int visit( IASTProblem n ){
		if (n.getClass() == CPPASTProblem.class) {
			return visit((CPPASTProblem)n);
		}

		return PROCESS_CONTINUE;
	}
	
	/*--------------------------------------------------------------*/
	
	public int leave(IASTDeclaration n) {
		check(n);
		Class clazz = n.getClass();
		if (clazz == CPPASTASMDeclaration.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTFunctionDefinition.class) {
			return leave((CPPASTFunctionDefinition)n);
		} else if (clazz == CPPASTProblemDeclaration.class) {
			 //
		} else if (clazz == CPPASTSimpleDeclaration.class) {
			return leave((CPPASTSimpleDeclaration)n);
		} else if (clazz == CPPASTExplicitTemplateInstantiation.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTLinkageSpecification.class) {
			return leave((CPPASTLinkageSpecification)n);			
		} else if (clazz == CPPASTNamespaceAlias.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTNamespaceDefinition.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTTemplateDeclaration.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTTemplateSpecialization.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTUsingDeclaration.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTUsingDirective.class) {
			//return visit((CPPASTUsingDirective)n);
		} else if (clazz == CPPASTVisibilityLabel.class) {
			//TODO: Complete code cpp by shootsoft
		}
		
		return PROCESS_CONTINUE;
	}

	public int leave(IASTInitializer n) {
		return PROCESS_CONTINUE;
	}

	public int leave(IASTParameterDeclaration n) {
		return PROCESS_CONTINUE;
	}

	public int leave(IASTDeclarator n) {
		return PROCESS_CONTINUE;
	}

	public int leave(IASTDeclSpecifier n) {
		return PROCESS_CONTINUE;
	}

	public int leave(IASTArrayModifier n) {
		return PROCESS_CONTINUE;
	}

	public int leave(IASTPointerOperator n) {
		
		return PROCESS_CONTINUE;
	}

	public int leave(IASTExpression n) {
		Class clazz = n.getClass();
		if (clazz == CPPASTAmbiguousCondition.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTAmbiguousBinaryVsCastExpression.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTAmbiguousCastVsFunctionCallExpression.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTAmbiguousExpression.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTArraySubscriptExpression.class) {
			//TODO: Complete code cpp by shootsoft	
		} else if (clazz == CPPASTBinaryExpression.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTCastExpression.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTConditionalExpression.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTExpressionList.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (clazz == CPPASTFieldReference.class) {
			//TODO: Complete code cpp by shootsoft
		}  else if (clazz == CPPASTFunctionCallExpression.class) {
			return leave((CPPASTFunctionCallExpression)n);
		}  else if (clazz == CPPASTIdExpression.class) {
			//TODO: Complete code cpp by shootsoft
		}  else if (clazz == CPPASTLiteralExpression.class) {
			//TODO: Complete code cpp by shootsoft
		}  else if (clazz == CPPASTTypeIdExpression.class) {
			//TODO: Complete code cpp by shootsoft
		}  else if (clazz == CASTTypeIdInitializerExpression.class) {
			//TODO: Complete code cpp by shootsoft
		}  else if (clazz == CPPASTUnaryExpression.class) {
			return visit((CPPASTUnaryExpression)n);
			//TODO: Complete code cpp by shootsoft
		}  else if (clazz == CPPASTDeleteExpression.class) {
			//TODO: Complete code cpp by shootsoft
		}  else if (clazz == CPPASTNewExpression.class) {
			//TODO: Complete code cpp by shootsoft
		}  else if (clazz == CPPASTSimpleTypeConstructorExpression.class) {
			//TODO: Complete code cpp by shootsoft
		}  else if (clazz == CPPASTTypenameExpression.class) {
			//TODO: Complete code cpp by shootsoft
		}  else if (clazz == CPPASTCompoundStatementExpression.class) {
			//TODO: Complete code cpp by shootsoft
		} 
		
		return PROCESS_CONTINUE;
	}

	public int leave(IASTStatement n) {
		if (n.getClass() == CPPASTAmbiguousStatement.class) {
			//TODO: Complete code cpp by shootsoft
		} else if (n.getClass() == CPPASTBreakStatement.class) {
			return leave((CPPASTBreakStatement)n);
		} else if (n.getClass() == CPPASTCaseStatement.class) {
			return leave((CPPASTCaseStatement)n);
		} else if (n.getClass() == CPPASTCompoundStatement.class) {
			return leave((CPPASTCompoundStatement) n);
		} else if (n.getClass() == CPPASTContinueStatement.class) {
			return leave((CPPASTContinueStatement) n);
		} else if (n.getClass() == CPPASTDeclarationStatement.class) {
			return leave((CPPASTDeclarationStatement) n);
		} else if (n.getClass() == CPPASTDefaultStatement.class) {
			return leave((CPPASTDefaultStatement) n);
		} else if (n.getClass() == CPPASTDoStatement.class) {
			return leave((CPPASTDoStatement) n);
		} else if (n.getClass() == CPPASTExpressionStatement.class) {
			return leave((CPPASTExpressionStatement) n);
		} else if (n.getClass() == CPPASTForStatement.class) {
			return leave((CPPASTForStatement) n);
		} else if (n.getClass() == CPPASTGotoStatement.class) {
			return leave((CPPASTGotoStatement) n);
		} else if (n.getClass() == CPPASTIfStatement.class) {
			return leave((CPPASTIfStatement) n);
		} else if (n.getClass() == CPPASTLabelStatement.class) {
			return leave((CPPASTLabelStatement) n);
		} else if (n.getClass() == CPPASTNullStatement.class) {
			return leave((CPPASTNullStatement) n);
		} else if (n.getClass() == CPPASTProblemStatement.class) {
			return leave((CPPASTProblemStatement) n);			
		} else if (n.getClass() == CPPASTReturnStatement.class) {
			return leave((CPPASTReturnStatement) n);
		} else if (n.getClass() == CPPASTSwitchStatement.class) {
			return leave((CPPASTSwitchStatement) n);
		} else if (n.getClass() == CPPASTWhileStatement.class) {
			return leave((CPPASTWhileStatement) n);
		} else if (n.getClass() == CPPASTCatchHandler.class) {
			return leave((CPPASTCatchHandler) n);
		} else if (n.getClass() == CPPASTTryBlockStatement.class) {
			return leave((CPPASTTryBlockStatement) n);
		}
		
		return PROCESS_CONTINUE;
	}

	public int leave(IASTTypeId n) {
		return PROCESS_CONTINUE;
	}

	public int leave(IASTEnumerator n) {
		return PROCESS_CONTINUE;
	}
	
	public int leave(IASTProblem n){
		return PROCESS_CONTINUE;
	}
	
	public int visit(ASTAmbiguousNode n) {
		return PROCESS_CONTINUE;
	}
	
	
	/**
	 * 头文件以及预处理宏定义
	 * */
	public abstract int visit(IASTPreprocessorStatement n);
	
	//声明定义
	/**
	 * 方法
	 * */
	public abstract int visit(CPPASTFunctionDefinition n);
	public abstract int leave(CPPASTFunctionDefinition n);
	/**
	 * 简单声明
	 * */
	public abstract int visit(CPPASTSimpleDeclaration n);
	public abstract int leave(CPPASTSimpleDeclaration n);
	public abstract int visit(CPPASTLinkageSpecification n);
	public abstract int leave(CPPASTLinkageSpecification n);
	public abstract int visit(CPPASTProblemDeclaration n);
	/**
	 * using
	 * */
	public abstract int visit(CPPASTUsingDirective n);
		
	public abstract int visit(CPPASTSimpleDeclSpecifier n);
	public abstract int visit(CPPASTElaboratedTypeSpecifier n);
	public abstract int visit(CPPASTCompositeTypeSpecifier n);
	public abstract int visit(CPPASTNamedTypeSpecifier n);	
	
	public abstract int visit(CPPASTDeclarator n);
	public abstract int visit(CPPASTFunctionDeclarator n);	
	public abstract int visit(CPPASTParameterDeclaration n);
	
	public abstract int visit(CPPASTPointer n);	
	public abstract int visit(CPPASTInitializerExpression n);	
	public abstract int visit(CPPASTLiteralExpression n);
	public abstract int visit(CPPASTBinaryExpression n);
	public abstract int visit(CPPASTIdExpression n);
	public abstract int visit(CPPASTUnaryExpression n);
	public abstract int visit(CPPASTCastExpression n);
	public abstract int visit(CPPASTFunctionCallExpression n);
	public abstract int leave(CPPASTFunctionCallExpression n);
	public abstract int visit(CPPASTTypeIdExpression n);
	public abstract int visit(CPPASTFieldReference n);
	public abstract int visit(CPPASTArraySubscriptExpression n);		
	public abstract int visit(CPPASTExpressionList n);
	
	//Statements
	public abstract int visit(CPPASTBreakStatement n);
	public abstract int visit(CPPASTCaseStatement n);
	public abstract int visit(CPPASTCompoundStatement n);
	public abstract int visit(CPPASTContinueStatement n);
	public abstract int visit(CPPASTDeclarationStatement n);
	public abstract int visit(CPPASTDefaultStatement n);
	public abstract int visit(CPPASTDoStatement n);
	public abstract int visit(CPPASTExpressionStatement n);
	public abstract int visit(CPPASTForStatement n);
	public abstract int visit(CPPASTGotoStatement n);
	public abstract int visit(CPPASTIfStatement n);
	public abstract int visit(CPPASTLabelStatement n);
	public abstract int visit(CPPASTNullStatement n);
	public abstract int visit(CPPASTProblemStatement n);
	public abstract int visit(CPPASTReturnStatement n);
	public abstract int visit(CPPASTSwitchStatement n);
	public abstract int visit(CPPASTWhileStatement n);
	public abstract int visit(CPPASTCatchHandler n);
	public abstract int visit(CPPASTTryBlockStatement n);
	
	
	//Statements
	public abstract int leave(CPPASTBreakStatement n);
	public abstract int leave(CPPASTCaseStatement n);
	public abstract int leave(CPPASTCompoundStatement n);
	public abstract int leave(CPPASTContinueStatement n);
	public abstract int leave(CPPASTDeclarationStatement n);
	public abstract int leave(CPPASTDefaultStatement n);
	public abstract int leave(CPPASTDoStatement n);
	public abstract int leave(CPPASTExpressionStatement n);
	public abstract int leave(CPPASTForStatement n);
	public abstract int leave(CPPASTGotoStatement n);
	public abstract int leave(CPPASTIfStatement n);
	public abstract int leave(CPPASTLabelStatement n);
	public abstract int leave(CPPASTNullStatement n);
	public abstract int leave(CPPASTProblemStatement n);
	public abstract int leave(CPPASTReturnStatement n);
	public abstract int leave(CPPASTSwitchStatement n);
	public abstract int leave(CPPASTWhileStatement n);
	public abstract int leave(CPPASTCatchHandler n);
	public abstract int leave(CPPASTTryBlockStatement n);

	
	public abstract int visit(CPPASTProblem n);

	
}
