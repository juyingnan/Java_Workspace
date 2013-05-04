package cn.edu.pku.ss.test.java.jobs.visitors;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.sun.mirror.declaration.TypeDeclaration;
import java.util.List;

import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.java.util.ConstText;
import cn.edu.pku.ss.test.jobs.IJob;
import cn.edu.pku.ss.test.jobs.JobConst;
import japa.parser.ast.BlockComment;
import japa.parser.ast.Comment;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.LineComment;
import japa.parser.ast.Node;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.TypeParameter;
import japa.parser.ast.body.AnnotationDeclaration;
import japa.parser.ast.body.AnnotationMemberDeclaration;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.EmptyMemberDeclaration;
import japa.parser.ast.body.EmptyTypeDeclaration;
import japa.parser.ast.body.EnumConstantDeclaration;
import japa.parser.ast.body.EnumDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.InitializerDeclaration;
import japa.parser.ast.body.JavadocComment;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.ArrayAccessExpr;
import japa.parser.ast.expr.ArrayCreationExpr;
import japa.parser.ast.expr.ArrayInitializerExpr;
import japa.parser.ast.expr.AssignExpr;
import japa.parser.ast.expr.BinaryExpr;
import japa.parser.ast.expr.BooleanLiteralExpr;
import japa.parser.ast.expr.CastExpr;
import japa.parser.ast.expr.CharLiteralExpr;
import japa.parser.ast.expr.ClassExpr;
import japa.parser.ast.expr.ConditionalExpr;
import japa.parser.ast.expr.DoubleLiteralExpr;
import japa.parser.ast.expr.EnclosedExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.InstanceOfExpr;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.IntegerLiteralMinValueExpr;
import japa.parser.ast.expr.LongLiteralExpr;
import japa.parser.ast.expr.LongLiteralMinValueExpr;
import japa.parser.ast.expr.MarkerAnnotationExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.expr.NullLiteralExpr;
import japa.parser.ast.expr.ObjectCreationExpr;
import japa.parser.ast.expr.QualifiedNameExpr;
import japa.parser.ast.expr.SingleMemberAnnotationExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.expr.SuperExpr;
import japa.parser.ast.expr.ThisExpr;
import japa.parser.ast.expr.UnaryExpr;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.AssertStmt;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.BreakStmt;
import japa.parser.ast.stmt.CatchClause;
import japa.parser.ast.stmt.ContinueStmt;
import japa.parser.ast.stmt.DoStmt;
import japa.parser.ast.stmt.EmptyStmt;
import japa.parser.ast.stmt.ExplicitConstructorInvocationStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.ForStmt;
import japa.parser.ast.stmt.ForeachStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.stmt.LabeledStmt;
import japa.parser.ast.stmt.ReturnStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.stmt.SwitchEntryStmt;
import japa.parser.ast.stmt.SwitchStmt;
import japa.parser.ast.stmt.SynchronizedStmt;
import japa.parser.ast.stmt.ThrowStmt;
import japa.parser.ast.stmt.TryStmt;
import japa.parser.ast.stmt.TypeDeclarationStmt;
import japa.parser.ast.stmt.WhileStmt;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.type.ReferenceType;
import japa.parser.ast.type.Type;
import japa.parser.ast.type.VoidType;
import japa.parser.ast.type.WildcardType;
import japa.parser.ast.visitor.VoidVisitor;

public class AstXmlVisitor implements IJob,VoidVisitor<Object>{
	
	protected Document doc;
	protected Element compilationUnit;
	protected XMLOutputter xmlOutputter ;
	protected String xmlStr;

	@Override
	public boolean run(TestData data) {
		// TODO Auto-generated method stub
		if(data!=null){
			CompilationUnit unit = (CompilationUnit) data.get(JobConst.AST);
			if(unit!=null){
				unit.accept(this, null);
				data.put(JobConst.ASTVIEW,this.getXmL());
				return true;
			}
			else{
				data.getResult().addReport("AST is null!");
				return false;
			}
		}
		return false;
	}

	private String getXmL() {
		// TODO Auto-generated method stub
		doc = new Document(compilationUnit);
		Format format = Format.getPrettyFormat().setEncoding("UTF-8");
		xmlOutputter = new XMLOutputter(format);
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		try {
			xmlOutputter.output(doc, bo);
			xmlStr = bo.toString("UTF-8");
//			System.out.println(xmlStr);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xmlStr;
	}

	public void visit(Node arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	private Element createElement(Node n){
		Element e = new Element(n.getClass().getSimpleName());
		Attribute beginLine = new Attribute("beginLine",String.valueOf(n.getBeginLine()));
		Attribute endLine = new Attribute("endLine",String.valueOf(n.getEndLine()));
		Attribute beginColumn = new Attribute("beginColumn",String.valueOf(n.getBeginColumn()));
		Attribute endColumn = new Attribute("endColumn",String.valueOf(n.getEndColumn()));
		List<Attribute> attrs = new ArrayList<Attribute>();
		attrs.add(beginLine);
		attrs.add(endLine);
		attrs.add(beginColumn);
		attrs.add(endColumn);
		
		e.setAttributes(attrs);
		return e;
	}

	@Override
	public void visit(CompilationUnit n, Object arg) {
		// TODO Auto-generated method stub
		 compilationUnit = createElement(n);
		 
		arg = compilationUnit;
		
		if(n.getComments()!=null){
			for(Comment c:n.getComments()){
				c.accept(this, arg);
			}
		}
		 
		if(n.getPackage()!=null){
			n.getPackage().accept(this, arg);
		}
		if(n.getImports()!=null){
			for(ImportDeclaration i:n.getImports()){
				i.accept(this, arg);
			}
		}
		if(n.getTypes()!=null){
			for(japa.parser.ast.body.TypeDeclaration i:n.getTypes()){
				i.accept(this, arg);
			}
		}
	}

	@Override
	public void visit(PackageDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
		Element packageElement =createElement(n);
		((Element) arg).addContent(packageElement);
		
		arg = packageElement;
		
		if(n.getAnnotations()!=null){
			for(AnnotationExpr i:n.getAnnotations()){
				i.accept(this, arg);
			}
		}
		
		if(n.getName()!=null){
			n.getName().accept(this, arg);
		}
		}
	}

	@Override
	public void visit(ImportDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
		Element importElement = createElement(n);
		((Element) arg).addContent(importElement);
		
		arg = importElement;
		
		if(n.getName()!=null){
			n.getName().accept(this, arg);
		}
		}
	}

	@Override
	public void visit(TypeParameter n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element typeParametr = createElement(n);
			((Element) arg).addContent(typeParametr);
			
			arg = typeParametr;
			
			if(n.getTypeBound()!=null){
				for(Type t:n.getTypeBound()){
					t.accept(this, arg);
				}
			}
		}
	}

	@Override
	public void visit(LineComment n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element lineCom = createElement(n);
			((Element) arg).addContent(lineCom);
			
		}
	}

	@Override
	public void visit(BlockComment n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element blockComm = createElement(n);
			((Element) arg).addContent(blockComm);
		}
	}

	@Override
	public void visit(ClassOrInterfaceDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
		Element classOrInterfaceUnit = createElement(n);
		((Element)arg).addContent(classOrInterfaceUnit);
		
		arg = classOrInterfaceUnit;
		
		if(n.getImplements()!=null){
			Element implementsList = new Element("ImplementsList");
			classOrInterfaceUnit.addContent(implementsList);
			
			arg = implementsList;
			
			for(ClassOrInterfaceType i:n.getImplements()){
				i.accept(this, arg);
			}
		}
		
		if(n.getExtends()!=null){
			Element extendsList = new Element("ExtendsList");
			classOrInterfaceUnit.addContent(extendsList);
			
			arg = extendsList;
			
			for(ClassOrInterfaceType i:n.getExtends()){
				i.accept(this, arg);
			}
		}
		
		if(n.getMembers()!=null){
			for(BodyDeclaration i:n.getMembers()){
				i.accept(this, arg);
			}
		}
		}
	}

	@Override
	public void visit(EnumDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element enumDecl = createElement(n);
			((Element) arg).addContent(enumDecl);
			
			arg = enumDecl;
			
			if(n.getJavaDoc()!=null){
				n.getJavaDoc().accept(this, arg);
			}
			if(n.getAnnotations()!=null){
				for(AnnotationExpr a:n.getAnnotations()){
					a.accept(this, arg);
				}
			}
			if(n.getEntries()!=null){
				for(EnumConstantDeclaration e:n.getEntries()){
					e.accept(this, arg);
				}
			}
			if(n.getImplements()!=null){
				for(Type t:n.getImplements()){
					t.accept(this, arg);
				}
			}
			if(n.getMembers()!=null){
				for(BodyDeclaration b:n.getMembers()){
					b.accept(this, arg);
				}
			}
		}
	}

	@Override
	public void visit(EmptyTypeDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element emptyTypeDecl = createElement(n);
			((Element) arg).addContent(emptyTypeDecl);
			
			arg = emptyTypeDecl;
			
			if(n.getJavaDoc()!=null){
				n.getJavaDoc().accept(this, arg);
			}
			if(n.getMembers()!=null){
				for(BodyDeclaration b:n.getMembers()){
					b.accept(this, arg);
				}
			}
		}
	}

	@Override
	public void visit(EnumConstantDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element enumCons = createElement(n);
			((Element) arg).addContent(enumCons);
			
			arg = enumCons;
			
			if(n.getJavaDoc()!=null){
                 n.getJavaDoc().accept(this, arg);
			}
			if(n.getAnnotations()!=null){
				for(AnnotationExpr a:n.getAnnotations()){
					a.accept(this, arg);
				}
			}
			if(n.getArgs()!=null){
				for(Expression e:n.getArgs()){
					e.accept(this, arg);
				}
			}
			if(n.getClassBody()!=null){
				for(BodyDeclaration b:n.getClassBody()){
					b.accept(this, arg);
				}
			}
			
		}
	}

	@Override
	public void visit(AnnotationDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element annoDecl = createElement(n);
			((Element) arg).addContent(annoDecl);
			
			arg = annoDecl;
			
			if(n.getJavaDoc()!=null){
				n.getJavaDoc().accept(this, arg);
			}
			if(n.getAnnotations()!=null){
				for(AnnotationExpr a:n.getAnnotations()){
					a.accept(this, arg);
				}
			}
			if(n.getMembers()!=null){
				for(BodyDeclaration b:n.getMembers()){
					b.accept(this, arg);
				}
			}
		}
	}

	@Override
	public void visit(AnnotationMemberDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element annoMemDecl = createElement(n);
			((Element) arg).addContent(annoMemDecl);
			
			arg = annoMemDecl;
			
			if(n.getJavaDoc()!=null){
				n.getJavaDoc().accept(this, arg);
			}
			if(n.getAnnotations()!=null){
				for(AnnotationExpr a:n.getAnnotations()){
					a.accept(this, arg);
				}
			}
			n.getType().accept(this, arg);
			if(n.getDefaultValue()!=null){
				n.getDefaultValue().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(FieldDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element fieldDecl = createElement(n);
			((Element) arg).addContent(fieldDecl);
			
			arg = fieldDecl;
			
			if(n.getJavaDoc()!=null){
				n.getJavaDoc().accept(this, arg);
			}
			if(n.getAnnotations()!=null){
				for(AnnotationExpr a:n.getAnnotations()){
					a.accept(this, arg);
				}
			}
			n.getType().accept(this, arg);
			if(n.getVariables()!=null){
				for(VariableDeclarator v:n.getVariables()){
					v.accept(this, arg);
				}
			}
		}
	}

	@Override
	public void visit(VariableDeclarator n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element varDecl = createElement(n);
			((Element) arg).addContent(varDecl);
			
			arg = varDecl;
			
			n.getId().accept(this, arg);
			if(n.getInit()!=null){
				n.getInit().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(VariableDeclaratorId n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			((Element) arg).addContent(createElement(n));
		}
	}

	@Override
	public void visit(ConstructorDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element constructorDecl = createElement(n);
			((Element) arg).addContent(constructorDecl);
			
			arg = constructorDecl;
			
			if(n.getJavaDoc()!=null){
				n.getJavaDoc().accept(this, arg);
			}
			if(n.getAnnotations()!=null){
				for(AnnotationExpr a:n.getAnnotations()){
					a.accept(this, arg);
				}
			}
			if(n.getTypeParameters()!=null){
				for(TypeParameter t:n.getTypeParameters()){
					t.accept(this, arg);
				}
			}
			if(n.getParameters()!=null){
				for(Parameter p:n.getParameters()){
					p.accept(this, arg);
				}
			}
			if(n.getThrows()!=null){
				for(NameExpr i:n.getThrows()){
					i.accept(this, arg);
				}
			}
			n.getBlock().accept(this, arg);
			
		}
	}

	@Override
	public void visit(MethodDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
		Element methodDeclaration = createElement(n);
		((Element) arg).addContent(methodDeclaration);
		
		arg = methodDeclaration;
		
		if(n.getTypeParameters()!=null){
			for(TypeParameter t:n.getTypeParameters()){
				t.accept(this, arg);
			}
		}
		if(n.getType()!=null){
			methodDeclaration.addContent(createElement(n.getType()));
		}
		if(n.getThrows()!=null){
			for(NameExpr i:n.getThrows()){
				i.accept(this, arg);
			}
		}
		if(n.getParameters()!=null){
			for(Parameter p:n.getParameters()){
				p.accept(this, arg);
			}
		}
		if(n.getBody()!=null){
			n.getBody().accept(this, arg);
		}
		}
	}

	@Override
	public void visit(Parameter n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element parameter = createElement(n);
			((Element) arg).addContent(parameter);
			
			arg = parameter;
			
			if(n.getType()!=null){
				parameter.addContent(createElement(n.getType()));
			}
			if(n.getId()!=null){
				n.getId().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(EmptyMemberDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element emptyMemDecl = createElement(n);
			((Element) arg).addContent(emptyMemDecl);
			
			if(n.getJavaDoc()!=null){
				n.getJavaDoc().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(InitializerDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element iniDecl = createElement(n);
			((Element) arg).addContent(iniDecl);
			
			arg = iniDecl;
			
			if(n.getJavaDoc()!=null){
				n.getJavaDoc().accept(this, arg);
			}
			if(n.getBlock()!=null){
				n.getBlock().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(JavadocComment n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element javaDocComm = createElement(n);
			((Element) arg).addContent(javaDocComm);
		}
	}

	@Override
	public void visit(ClassOrInterfaceType n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element classOrInterfaceType = createElement(n);
			((Element) arg).addContent(classOrInterfaceType);
			
			arg = classOrInterfaceType;
			
			if(n.getScope()!=null){
				n.getScope().accept(this, arg);
			}
			if(n.getTypeArgs()!=null){
				for(Type t:n.getTypeArgs()){
				  t.accept(this, arg);
				}
			}
		}
	}

	@Override
	public void visit(PrimitiveType n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			((Element) arg).addContent(createElement(n));
		}
	}

	@Override
	public void visit(ReferenceType n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			((Element) arg).addContent(createElement(n));
		}
	}

	@Override
	public void visit(VoidType n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			((Element) arg).addContent(createElement(n));
		}
	}

	@Override
	public void visit(WildcardType n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element wildcard = createElement(n);
			((Element) arg).addContent(wildcard);
			
			arg = wildcard;
			
			if(n.getExtends()!=null){
				n.getExtends().accept(this, arg);
			}
			if(n.getSuper()!=null){
				n.getSuper().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(ArrayAccessExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element arrayAccess = createElement(n);
			((Element) arg).addContent(arrayAccess);
			
			arg = arrayAccess;
			
			if(n.getIndex()!=null){
				n.getIndex().accept(this, arg);
			}
			if(n.getName()!=null){
				n.getName().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(ArrayCreationExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element arrayCreaExpr = createElement(n);
			((Element) arg).addContent(arrayCreaExpr);
			
			arg = arrayCreaExpr;
			
			n.getType().accept(this, arg);
			if(n.getDimensions()!=null){
				for(Expression e:n.getDimensions()){
					e.accept(this, arg);
				}
			}
			else{
				n.getInitializer().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(ArrayInitializerExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element arrayIniExpr = createElement(n);
			((Element) arg).addContent(arrayIniExpr);
			
			arg = arrayIniExpr;
			
			if(n.getValues()!=null){
				for(Expression e:n.getValues()){
					e.accept(this, arg);
				}
			}
		}
	}

	@Override
	public void visit(AssignExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element assignExpr = createElement(n);
			((Element) arg).addContent(assignExpr);
			
			arg = assignExpr;
			
			n.getTarget().accept(this, arg);
			assignExpr.addContent(new Element(n.getOperator().getClass().getSimpleName()));
			n.getValue().accept(this, arg);
		}
	}

	@Override
	public void visit(BinaryExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element binary = createElement(n);
			((Element) arg).addContent(binary);
			
			arg = binary;
			
			n.getLeft().accept(this, arg);
			binary.addContent(new Element(n.getOperator().getClass().getSimpleName()));
			n.getRight().accept(this, arg);
		}
	}

	@Override
	public void visit(CastExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element castExp = createElement(n);
			((Element) arg).addContent(castExp);
			
			arg = castExp;
			
			n.getType().accept(this, arg);
			n.getExpr().accept(this, arg);
		}
	}

	@Override
	public void visit(ClassExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element classExpr = createElement(n);
			((Element) arg).addContent(classExpr);
			
			arg = classExpr;
			
			n.getType().accept(this, arg);
		}
	}

	@Override
	public void visit(ConditionalExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element conditionalExpr = createElement(n);
			((Element) arg).addContent(conditionalExpr);
			
			arg = conditionalExpr;
			
			n.getCondition().accept(this, arg);
			n.getThenExpr().accept(this, arg);
			n.getElseExpr().accept(this, arg);
		}
	}

	@Override
	public void visit(EnclosedExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element enclosedExpr = createElement(n);
			((Element) arg).addContent(enclosedExpr);
			
			arg = enclosedExpr;
			
			n.getInner().accept(this, arg);
		}
	}

	@Override
	public void visit(FieldAccessExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element fieldAccessExpr = createElement(n);
			((Element) arg).addContent(fieldAccessExpr);
			
			arg = fieldAccessExpr;
			
			n.getScope().accept(this, arg);
			if(n.getTypeArgs()!=null){
				for(Type t:n.getTypeArgs()){
					t.accept(this, arg);
				}
			}
		}
	}

	@Override
	public void visit(InstanceOfExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element instanceOfExpr = createElement(n);
			((Element) arg).addContent(instanceOfExpr);
			
			arg = instanceOfExpr;
			
			n.getExpr().accept(this, arg);
			n.getType().accept(this, arg);
		}
	}

	@Override
	public void visit(StringLiteralExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element stringLiter = createElement(n);
			((Element) arg).addContent(stringLiter);
		
		}
	}

	@Override
	public void visit(IntegerLiteralExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element integer = createElement(n);
			((Element) arg).addContent(integer);
			
		}
	}

	@Override
	public void visit(LongLiteralExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			((Element) arg).addContent(createElement(n));
		}
	}

	@Override
	public void visit(IntegerLiteralMinValueExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			((Element) arg).addContent(createElement(n));
		}
	}

	@Override
	public void visit(LongLiteralMinValueExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			((Element) arg).addContent(createElement(n));
		}
	}

	@Override
	public void visit(CharLiteralExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			((Element) arg).addContent(createElement(n));
		}
	}

	@Override
	public void visit(DoubleLiteralExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			((Element) arg).addContent(createElement(n));
		}
	}

	@Override
	public void visit(BooleanLiteralExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			((Element) arg).addContent(createElement(n));
		}
	}

	@Override
	public void visit(NullLiteralExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			((Element) arg).addContent(createElement(n));
		}
	}

	@Override
	public void visit(MethodCallExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element methodCall = createElement(n);
			((Element) arg).addContent(methodCall);
			
			arg = methodCall;
			
			if(n.getScope()!=null){
				n.getScope().accept(this, arg);
			}
			if(n.getTypeArgs()!=null){
				for(Type t:n.getTypeArgs()){
					t.accept(this, arg);
				}
			}
			if(n.getArgs()!=null){
				for(Expression e:n.getArgs()){
					e.accept(this, arg);
				}
			}
		}
	}

	@Override
	public void visit(NameExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
		 ((Element) arg).addContent(createElement(n));
		}
	}

	@Override
	public void visit(ObjectCreationExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element objectCre = createElement(n);
			((Element) arg).addContent(objectCre);
			
			arg = objectCre;
			
			if(n.getScope()!=null){
				n.getScope().accept(this, arg);
			}
			if(n.getTypeArgs()!=null){
				for(Type t:n.getTypeArgs()){
					t.accept(this, arg);
				}
			}
			n.getType().accept(this, arg);
			if(n.getArgs()!=null){
				for(Expression e:n.getArgs()){
					e.accept(this, arg);
				}
			}
			if(n.getAnonymousClassBody()!=null){
				for(BodyDeclaration b:n.getAnonymousClassBody()){
					b.accept(this, arg);
				}
			}
		}
	}

	@Override
	public void visit(QualifiedNameExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element quailfidName = createElement(n);
			((Element) arg).addContent(quailfidName);
			
			arg = quailfidName;
			
			if(n.getQualifier()!=null){
				n.getQualifier().accept(this, arg);
			}
		}
	}



	@Override
	public void visit(ThisExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element thisExpr = createElement(n);
			((Element) arg).addContent(thisExpr);
			
			arg = thisExpr;
			
			if(n.getClassExpr()!=null){
				n.getClassExpr().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(SuperExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element superExpr = createElement(n);
			((Element) arg).addContent(superExpr);
			
			arg = superExpr;
			
			if(n.getClassExpr()!=null){
				n.getClassExpr().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(UnaryExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element unaryExpr = createElement(n);
			((Element) arg).addContent(unaryExpr);
			
			arg = unaryExpr;
			
			if(n.getExpr()!=null){
				n.getExpr().accept(this, arg);
			}
			if(n.getOperator()!=null){
				unaryExpr.addContent(new Element(n.getOperator().getClass().getSimpleName()));
			}
		}
	}

	@Override
	public void visit(VariableDeclarationExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element variableDecl = createElement(n);
			((Element) arg).addContent(variableDecl);
			
			arg = variableDecl;
			
			if(n.getAnnotations()!=null){
				for(AnnotationExpr a:n.getAnnotations()){
					a.accept(this, arg);
				}
			}
			if(n.getType()!=null){
				n.getType().accept(this, arg);
			}
			if(n.getVars()!=null){
				for(VariableDeclarator v:n.getVars()){
					v.accept(this, arg);
				}
			}
		}
	}

	@Override
	public void visit(MarkerAnnotationExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element markerAnno = createElement(n);
			((Element) arg).addContent(markerAnno);
			
			arg = markerAnno;
			
			if(n.getName()!=null){
				n.getName().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(SingleMemberAnnotationExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element singleMem = createElement(n);
			((Element) arg).addContent(singleMem);
			
			arg = singleMem;
			
			if(n.getName()!=null){
				n.getName().accept(this, arg);
			}
			if(n.getMemberValue()!=null){
				n.getMemberValue().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(NormalAnnotationExpr n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element normalAnno = createElement(n);
			((Element) arg).addContent(normalAnno);
			
			arg = normalAnno;
			
			if(n.getName()!=null){
				n.getName().accept(this, arg);
			}
			if(n.getPairs()!=null){
				for(MemberValuePair m:n.getPairs()){
					m.accept(this, arg);
				}
			}
		}
	}

	@Override
	public void visit(MemberValuePair n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element memberVal = createElement(n);
			((Element) arg).addContent(memberVal);
			
			arg = memberVal;
			
			if(n.getValue()!=null){
				n.getValue().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(ExplicitConstructorInvocationStmt n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element explicitCons = createElement(n);
			((Element) arg).addContent(explicitCons);
			
			arg = explicitCons;
			
			if(!n.isThis()){
				if(n.getExpr()!=null){
					n.getExpr().accept(this, arg);
				}
			}
			if(n.getTypeArgs()!=null){
				for(Type t:n.getTypeArgs()){
					t.accept(this, arg);
				}
			}
			if(n.getArgs()!=null){
				for(Expression e:n.getArgs()){
					e.accept(this, arg);
				}
			}
		}
	}

	@Override
	public void visit(TypeDeclarationStmt n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element typeDecl = createElement(n);
			((Element) arg).addContent(typeDecl);
			
			arg = typeDecl;
			
			if(n.getTypeDeclaration()!=null){
				n.getTypeDeclaration().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(AssertStmt n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element assertStmt = createElement(n);
			((Element) arg).addContent(assertStmt);
			
			arg = assertStmt;
			
			if(n.getCheck()!=null){
				n.getCheck().accept(this, arg);
			}
			if(n.getMessage()!=null){
				n.getMessage().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(BlockStmt n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element blockStmt = createElement(n);
			((Element) arg).addContent(blockStmt);
			
			arg = blockStmt;
			
			if(n.getStmts()!=null){
				for(Statement s:n.getStmts()){
					s.accept(this,arg);
				}
			}
		}
	}

	@Override
	public void visit(LabeledStmt n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element labeledStmt = createElement(n);
			((Element) arg).addContent(labeledStmt);
			
			arg = labeledStmt;
			
			if(n.getStmt()!=null){
				n.getStmt().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(EmptyStmt n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element emptyStmt = createElement(n);
			((Element) arg).addContent(emptyStmt);
		}
	}

	@Override
	public void visit(ExpressionStmt n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element expr = createElement(n);
			((Element) arg).addContent(expr);
			
			arg = expr;
			
			if(n.getExpression()!=null){
				n.getExpression().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(SwitchStmt n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element switchStmt = createElement(n);
			((Element) arg).addContent(switchStmt);
			
			arg = switchStmt;
			
			if(n.getSelector()!=null){
				n.getSelector().accept(this, arg);
			}
			if(n.getEntries()!=null){
				for(SwitchEntryStmt s:n.getEntries()){
					s.accept(this, arg);
				}
			}
		}
	}

	@Override
	public void visit(SwitchEntryStmt n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element switchEntryStm = createElement(n);
			((Element) arg).addContent(switchEntryStm);
			
			arg = switchEntryStm;
			
			if(n.getLabel()!=null){
				n.getLabel().accept(this, arg);
			}
			if(n.getStmts()!=null){
				for(Statement s:n.getStmts()){
					s.accept(this, arg);
				}
			}
		}
	}

	@Override
	public void visit(BreakStmt n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element breakeStmt = createElement(n);
			((Element) arg).addContent(breakeStmt);
		}
	}

	@Override
	public void visit(ReturnStmt n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element returnStmt = createElement(n);
			((Element) arg).addContent(returnStmt);
			
			arg = returnStmt;
			if(n.getExpr()!=null){
				n.getExpr().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(IfStmt n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element ifStmt = createElement(n);
			((Element) arg).addContent(ifStmt);
			
			arg = ifStmt;
			
			if(n.getCondition()!=null){
				n.getCondition().accept(this, arg);
			}
			if(n.getThenStmt()!=null){
				n.getThenStmt().accept(this, arg);
			}
			if(n.getElseStmt()!=null){
				n.getElseStmt().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(WhileStmt n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element whileStmt = createElement(n);
			((Element) arg).addContent(whileStmt);
			
			arg = whileStmt;
			
			if(n.getCondition()!=null){
				n.getCondition().accept(this, arg);
			}
			if(n.getBody()!=null){
				n.getBody().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(ContinueStmt n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element contin = createElement(n);
			((Element) arg).addContent(contin);
	
		}
	}

	@Override
	public void visit(DoStmt n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element doStmt = createElement(n);
			((Element) arg).addContent(doStmt);
			
			arg = doStmt;
			
			if(n.getCondition()!=null){
				n.getCondition().accept(this, arg);
			}
			if(n.getBody()!=null){
				n.getBody().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(ForeachStmt n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element foreach = createElement(n);
			((Element) arg).addContent(foreach);
			
			arg = foreach;
			
			if(n.getVariable()!=null){
				n.getVariable().accept(this, arg);
			}
			if(n.getIterable()!=null){
				n.getIterable().accept(this, arg);
			}
			if(n.getBody()!=null){
				n.getBody().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(ForStmt n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element forStmt = createElement(n);
			((Element) arg).addContent(forStmt);
			
			arg = forStmt;
			
			if(n.getInit()!=null){
				for(Expression e:n.getInit()){
					e.accept(this, arg);
				}
			}
			if(n.getCompare()!=null){
				n.getCompare().accept(this, arg);
			}
			if(n.getUpdate()!=null){
				for(Expression e:n.getUpdate()){
					e.accept(this, arg);
				}
			}
			n.getBody().accept(this, arg);
		}
	}

	@Override
	public void visit(ThrowStmt n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element throwStmt = createElement(n);
			((Element) arg).addContent(throwStmt);
			
			arg = throwStmt;
			
			if(n.getExpr()!=null){
				n.getExpr().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(SynchronizedStmt n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element synchr = createElement(n);
			((Element) arg).addContent(synchr);
			
			arg = synchr;
			
			if(n.getExpr()!=null){
				n.getExpr().accept(this, arg);
			}
			if(n.getBlock()!=null){
				n.getBlock().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(TryStmt n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element tryStmt = createElement(n);
			((Element) arg).addContent(tryStmt);
			
			arg = tryStmt;
			
			if(n.getTryBlock()!=null){
				n.getTryBlock().accept(this, arg);
			}
			if(n.getCatchs()!=null){
				for(CatchClause c:n.getCatchs()){
					c.accept(this, arg);
				}
			}
			if(n.getFinallyBlock()!=null){
				n.getFinallyBlock().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(CatchClause n, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Element){
			Element catchCl = createElement(n);
			((Element) arg).addContent(catchCl);
			
			arg = catchCl;
			
			if(n.getCatchBlock()!=null){
				n.getCatchBlock().accept(this, arg);
			}
			if(n.getExcept()!=null){
				n.getExcept().accept(this, arg);
			}
		}
	}

}
