package cn.edu.pku.ss.test.java.jobs.visitors;
import japa.parser.ast.BlockComment;
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
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;
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
import japa.parser.ast.visitor.GenericVisitor;
import japa.parser.ast.visitor.VoidVisitor;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.jobs.IJob;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.util.Log;
import cn.edu.pku.ss.test.util.SourcePrinter;
import org.jdom.*;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * @author shootsoft
 * 
 * ASTVisitor用于打印AST语法树
 * 
 */

public class ASTVisitor implements IJob, VoidVisitor<Object>  {

    protected SourcePrinter printer = new SourcePrinter();
    protected boolean fixModifiers;
//    private Element rootElement;
//    public static Element parentElement = null;
    private Document syntaxTreeDocument;
    private String xmlStr;
    @Override
	public boolean run(TestData data) {
//    	System.out.println("%%%%%%%%%%%%%%%%%%%%%");
		if (data != null) {
			CompilationUnit unit = (CompilationUnit) data.get(JobConst.AST);
			if (unit != null) {
				visit(unit, null);
//				data.put(JobConst.ASTVIEW, this.getSource());
				data.put(JobConst.ASTVIEW,this.getXmL());
//				System.out.println("~~~~~~~~~~~~~~"+this.getXmL());
//		    try{
//	         	XMLOutputter xmlOutPutter = new XMLOutputter();
//	        	FileWriter writer = new FileWriter("C:/inetpub/wwwroot/NewClient/syntaxTreeFile.xml");
////	        	FileWriter writer = new FileWriter("http://localhost/NewClient/syntaxTreeFile.xml");
//	        	
//	        	xmlOutPutter.output(syntaxTreeDocument, writer);
//	       	    System.out.println("2222222222222222222@@@@@@@@@@@@@@@@@@@");
//	       }
//	        catch(java.io.IOException e){
//	        	System.out.println("3333333333333333333###################");
//	       	    e.printStackTrace();
//	       }
				return true;
			} else {
				data.getResult().addReport("AST null!");
				return false;
			}
		} else {
			return false;
		}
	}
    
    private String getXmL() {
		// TODO Auto-generated method stub
		Format format = Format.getPrettyFormat().setEncoding("UTF-8");
		XMLOutputter xmlOutputter = new XMLOutputter(format);
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		try {
			xmlOutputter.output(syntaxTreeDocument, bo);
			xmlStr = bo.toString("UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xmlStr;
	}
    
    /**
     * 获取源码
     * */
    public String getSource() {
        return printer.getSource();
    }


    private void printMembers(List<BodyDeclaration> members, Object arg) { 
//    	Element currElement = createElement(members.getClass().getSimpleName(),arg);
//		arg = currElement;
        for (BodyDeclaration member : members) {
        	int level = printer.getLevel();            
            member.accept(this, arg);
            while(printer.getLevel()>level){
            	printer.unindent();
            }
        }
    }

    private void printMemberAnnotations(List<AnnotationExpr> annotations, Object arg) {
        if (annotations != null) {
//        	Element currElement = createElement(annotations.getClass().getSimpleName(),arg);
//    		arg = currElement;
            for (AnnotationExpr a : annotations) {
                a.accept(this, arg);
            }
        }
    }

    private void printAnnotations(List<AnnotationExpr> annotations, Object arg) {
        if (annotations != null) {
//        	Element currElement = createElement(annotations.getClass().getSimpleName(),arg);
//    		arg = currElement;
            for (AnnotationExpr a : annotations) {
                a.accept(this, arg);              
            }
        }
    }

    private void printTypeArgs(List<Type> args, Object arg) { 
        if (args != null) {
            ////printer.print("<");
//        	Element currElement = createElement(args.getClass().getSimpleName(),arg);
//    		arg = currElement;
            for (Iterator<Type> i = args.iterator(); i.hasNext();) {
                Type t = i.next();
                t.accept(this, arg);
                if (i.hasNext()) {
                    ////printer.print(", ");
                }
            }
           // //printer.print(">");
        }
    }

    private void printTypeParameters(List<TypeParameter> args, Object arg) { 
        if (args != null) {
            //printer.print("<");
//        	Element currElement = createElement(args.getClass().getSimpleName(),arg);
//    		arg = currElement;
            for (Iterator<TypeParameter> i = args.iterator(); i.hasNext();) {
                TypeParameter t = i.next();
                t.accept(this, arg);
                if (i.hasNext()) {
                    //printer.print(", ");
                }
            }
            //printer.print(">");
        }
    }

    public void visit(Node n, Object arg) { 
    	Element nodeElement = new Element("node");
    	SourcePrinter.parentElement = nodeElement;
    	print(n,nodeElement);
        //throw new IllegalStateException(n.getClass().getName());
    }

    private void print(Node n, Element element){
    	//Debug.println("xxx");
//    	rootElement = new Element("node");
//        syntaxTreeDocument = new Document(rootElement);
//        rootElement.setAttribute(new Attribute("label", n.getClass().getSimpleName()));
    	
//    	Element nodeElement = new Element("node");
    	
//    	printer.println(n.getClass().getSimpleName(), element);
//    	printer.indent();
    	
    	
//    	SourcePrinter.parentElement = nodeElement;
    }
    
    
    
    public void visit(CompilationUnit n, Object arg) {
		Element currElement = createElement(n.getClass().getSimpleName(),arg);
		setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
		
    	if (n.getPackage() != null) {
            n.getPackage().accept(this, arg);
        }
        if (n.getImports() != null) {
            for (ImportDeclaration i : n.getImports()) {
            	i.accept(this, arg);
            }
        }
        if (n.getTypes() != null) {
            for (Iterator<TypeDeclaration> i = n.getTypes().iterator(); i.hasNext();) {
                i.next().accept(this, arg);
            }
        }
    }

    private void setElementAttribute(Element currElement, int beginLine, int beginColumn,
			int endLine, int endColumn) {
		// TODO Auto-generated method stub
    	currElement.setAttribute("beginLine", String.valueOf(beginLine));
		currElement.setAttribute("beginColumn", String.valueOf(beginColumn));
		currElement.setAttribute("endLine", String.valueOf(endLine));
		currElement.setAttribute("endColumn", String.valueOf(endColumn));
	}

	private Element createElement(String elementLabel, Object arg) {
		// TODO Auto-generated method stub
    	Element element = new Element("node");
    	Element parentElement;
    	element.setAttribute(new Attribute("label", elementLabel));
    	if(arg == null){
    		syntaxTreeDocument = new Document(element);
    	}
    	else{
    		parentElement = (Element)arg;
    		parentElement.addContent(element);
    	}
    	return element;
	}

	public void visit(PackageDeclaration n, Object arg) { 
        printAnnotations(n.getAnnotations(), arg);
        Element currElement = createElement(n.getClass().getSimpleName(),arg);
        setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
        //printer.print("package ");
        n.getName().accept(this, arg);
        //printer.println(";");
        //printer.println();
    }

    public void visit(NameExpr n, Object arg) { 
    	Element NameExprElement = new Element("node");
    	printer.println(n.getClass().getSimpleName(),NameExprElement);
    	SourcePrinter.parentElement = NameExprElement;
    }

    public void visit(QualifiedNameExpr n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
    	arg = currElement;
    	n.getQualifier().accept(this, arg);
        //printer.print(".");
        //printer.print(n.getName());
    }

    public void visit(ImportDeclaration n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	//printer.print("import ");
        if (n.isStatic()) {
            //printer.print("static ");
        }
        n.getName().accept(this, arg);
        if (n.isAsterisk()) {
            //printer.print(".*");
        }
        //printer.println(";");
    }

    public void visit(ClassOrInterfaceDeclaration n, Object arg) { 
//    	Element classOrInterfaceDeclarationElement = new Element("node");
//    	classOrInterfaceDeclarationElement.setAttribute("label", n.getClass().getSimpleName());
//    	rootElement.addContent(classOrInterfaceDeclarationElement);
    	
//    	 try{
//         	XMLOutputter xmlOutPutter = new XMLOutputter();
//         	FileWriter writer = new FileWriter("C:/syntaxTreeFile.xml");
//         	xmlOutPutter.output(syntaxTreeDocument, writer);
//         	System.out.println("2222222222222222222@@@@@@@@@@@@@@@@@@@");
//         }
//         catch(java.io.IOException e){
//         	e.printStackTrace();
//         }
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        printMemberAnnotations(n.getAnnotations(), arg);
        
        //printer.println("Modifier");
   
        if (n.isInterface()) {
            //printer.print("interface ");
        } else {
            //printer.print("class ");
        }

        //printer.print(n.getName());

        printTypeParameters(n.getTypeParameters(), arg);

        if (n.getExtends() != null) {
            //printer.print(" extends ");
        	currElement = createElement(n.getExtends().getClass().getSimpleName(),arg);
        	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
    		arg = currElement;
            for (Iterator<ClassOrInterfaceType> i = n.getExtends().iterator(); i.hasNext();) {
                ClassOrInterfaceType c = i.next();
                c.accept(this, arg);
                if (i.hasNext()) {
                    //printer.print(", ");
                }
            }
        }

        if (n.getImplements() != null) {
            //printer.print(" implements ");
        	currElement = createElement(n.getImplements().getClass().getSimpleName(),arg);
        	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
    		arg = currElement;
            for (Iterator<ClassOrInterfaceType> i = n.getImplements().iterator(); i.hasNext();) {
                ClassOrInterfaceType c = i.next();
                c.accept(this, arg);
                if (i.hasNext()) {
                    //printer.print(", ");
                }
            }
        }

        //printer.println(" {");
        printer.indent();
        if (n.getMembers() != null) {
            printMembers(n.getMembers(), arg);
        }
        printer.unindent();
        //printer.print("}");
    }

    public void visit(EmptyTypeDeclaration n, Object arg) {
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        //printer.print(";");
    }

    public void visit(JavadocComment n, Object arg) { Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("/**");
        //printer.print(n.getContent());
        //printer.println("*/");
    }

    public void visit(ClassOrInterfaceType n, Object arg) { 
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	if (n.getScope() != null) {
            n.getScope().accept(this, arg);
        }
        printTypeArgs(n.getTypeArgs(), arg);
    }

    public void visit(TypeParameter n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print(n.getName());
        if (n.getTypeBound() != null) {
            //printer.print(" extends ");
        	Element currElement = createElement(n.getTypeBound().getClass().getSimpleName(),arg);
        	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
    		arg = currElement;
            for (Iterator<ClassOrInterfaceType> i = n.getTypeBound().iterator(); i.hasNext();) {
                ClassOrInterfaceType c = i.next();
                c.accept(this, arg);
                if (i.hasNext()) {
                    //printer.print(" & ");
                }
            }
        }
    }

    public void visit(PrimitiveType n, Object arg) { Element PrimitiveTypeElement = new Element("node");print(n,PrimitiveTypeElement);SourcePrinter.parentElement = PrimitiveTypeElement;
        switch (n.getType()) {
            case Boolean:
                //printer.print("boolean");
                break;
            case Byte:
                //printer.print("byte");
                break;
            case Char:
                //printer.print("char");
                break;
            case Double:
                //printer.print("double");
                break;
            case Float:
                //printer.print("float");
                break;
            case Int:
                //printer.print("int");
                break;
            case Long:
                //printer.print("long");
                break;
            case Short:
                //printer.print("short");
                break;
        }
    }

    public void visit(ReferenceType n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        Element currElement = createElement(n.getClass().getSimpleName(),arg);
        setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
	    arg = currElement;
        n.getType().accept(this, arg);
        for (int i = 0; i < n.getArrayCount(); i++) {
            //printer.print("[]");
        }
    }

    public void visit(WildcardType n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("?");
        Element currElement = createElement(n.getClass().getSimpleName(),arg);
        setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
	    arg = currElement;
        if (n.getExtends() != null) {
            //printer.print(" extends ");
            n.getExtends().accept(this, arg);
        }
        if (n.getSuper() != null) {
            //printer.print(" super ");
            n.getSuper().accept(this, arg);
        }
    }

    public void visit(FieldDeclaration n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        printMemberAnnotations(n.getAnnotations(), arg);
        //printer.println("Modifier");
        n.getType().accept(this, arg);

        //printer.print(" ");
        currElement = createElement(n.getVariables().getClass().getSimpleName(),arg);
        setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
        for (Iterator<VariableDeclarator> i = n.getVariables().iterator(); i.hasNext();) {
            VariableDeclarator var = i.next();
            var.accept(this, arg);
            if (i.hasNext()) {
                //printer.print(", ");
            }
        }

        //printer.print(";");
    }

    public void visit(VariableDeclarator n, Object arg) {
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	n.getId().accept(this, arg);
        if (n.getInit() != null) {
            //printer.print(" = ");
            n.getInit().accept(this, arg);
        }
    }

    public void visit(VariableDeclaratorId n, Object arg) { Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print(n.getName());
        for (int i = 0; i < n.getArrayCount(); i++) {
            //printer.print("[]");
        }
    }

    public void visit(ArrayInitializerExpr n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("{");
        if (n.getValues() != null) {
            //printer.print(" ");
        	Element currElement = createElement(n.getValues().getClass().getSimpleName(),arg);
        	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
    		arg = currElement;
            for (Iterator<Expression> i = n.getValues().iterator(); i.hasNext();) {
                Expression expr = i.next();
                expr.accept(this, arg);
                if (i.hasNext()) {
                    //printer.print(", ");
                }
            }
            //printer.print(" ");
        }
        //printer.print("}");
    }

    public void visit(VoidType n, Object arg) { Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("void");
    }

    public void visit(ArrayAccessExpr n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	n.getName().accept(this, arg);
        //printer.print("[");
        n.getIndex().accept(this, arg);
        //printer.print("]");
    }

    public void visit(ArrayCreationExpr n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("new ");
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
        n.getType().accept(this, arg);
        

        if (n.getDimensions() != null) {
        	currElement = createElement(n.getDimensions().getClass().getSimpleName(),arg);
        	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
    		arg = currElement;
            for (Expression dim : n.getDimensions()) {
                //printer.print("[");
                dim.accept(this, arg);
                //printer.print("]");
            }
            for (int i = 0; i < n.getArrayCount(); i++) {
                //printer.print("[]");
            }
        } else {
        	currElement = createElement(n.getClass().getSimpleName(),arg);
        	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
    		arg = currElement;
            for (int i = 0; i < n.getArrayCount(); i++) {
                //printer.print("[]");
            }
            //printer.print(" ");
            n.getInitializer().accept(this, arg);
        }
    }

    public void visit(AssignExpr n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	n.getTarget().accept(this, arg);
        //printer.print(" ");
        switch (n.getOperator()) {
            case assign:
                //printer.print("=");
                break;
            case and:
                //printer.print("&=");
                break;
            case or:
                //printer.print("|=");
                break;
            case xor:
                //printer.print("^=");
                break;
            case plus:
                //printer.print("+=");
                break;
            case minus:
                //printer.print("-=");
                break;
            case rem:
                //printer.print("%=");
                break;
            case slash:
                //printer.print("/=");
                break;
            case star:
                //printer.print("*=");
                break;
            case lShift:
                //printer.print("<<=");
                break;
            case rSignedShift:
                //printer.print(">>=");
                break;
            case rUnsignedShift:
                //printer.print(">>>=");
                break;
        }
        //printer.print(" ");
        n.getValue().accept(this, arg);
    }

    public void visit(BinaryExpr n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	n.getLeft().accept(this, arg);
        //printer.print(" ");
        switch (n.getOperator()) {
            case or:
                //printer.print("||");
                break;
            case and:
                //printer.print("&&");
                break;
            case binOr:
                //printer.print("|");
                break;
            case binAnd:
                //printer.print("&");
                break;
            case xor:
                //printer.print("^");
                break;
            case equals:
                //printer.print("==");
                break;
            case notEquals:
                //printer.print("!=");
                break;
            case less:
                //printer.print("<");
                break;
            case greater:
                //printer.print(">");
                break;
            case lessEquals:
                //printer.print("<=");
                break;
            case greaterEquals:
                //printer.print(">=");
                break;
            case lShift:
                //printer.print("<<");
                break;
            case rSignedShift:
                //printer.print(">>");
                break;
            case rUnsignedShift:
                //printer.print(">>>");
                break;
            case plus:
                //printer.print("+");
                break;
            case minus:
                //printer.print("-");
                break;
            case times:
                //printer.print("*");
                break;
            case divide:
                //printer.print("/");
                break;
            case remainder:
                //printer.print("%");
                break;
        }
        //printer.print(" ");
        n.getRight().accept(this, arg);
    }

    public void visit(CastExpr n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("(");
        Element currElement = createElement(n.getClass().getSimpleName(),arg);
        setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
	    arg = currElement;
        n.getType().accept(this, arg);
        //printer.print(") ");
        n.getExpr().accept(this, arg);
    }

    public void visit(ClassExpr n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	n.getType().accept(this, arg);
        //printer.print(".class");
    }

    public void visit(ConditionalExpr n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	n.getCondition().accept(this, arg);
        //printer.print(" ? ");
        n.getThenExpr().accept(this, arg);
        //printer.print(" : ");
        n.getElseExpr().accept(this, arg);
    }

    public void visit(EnclosedExpr n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("(");
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	n.getInner().accept(this, arg);
        //printer.print(")");
    }

    public void visit(FieldAccessExpr n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	n.getScope().accept(this, arg);
        //printer.print(".");
        //printer.print(n.getField());
    }

    public void visit(InstanceOfExpr n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	n.getExpr().accept(this, arg);
        //printer.print(" instanceof ");
        n.getType().accept(this, arg);
    }

    public void visit(CharLiteralExpr n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("'");
        //printer.print(n.getValue());
        //printer.print("'");
    }

    public void visit(DoubleLiteralExpr n, Object arg) { Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print(n.getValue());
    }

    public void visit(IntegerLiteralExpr n, Object arg) { Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print(n.getValue());
    }

    public void visit(LongLiteralExpr n, Object arg) { Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print(n.getValue());
    }

    public void visit(IntegerLiteralMinValueExpr n, Object arg) { Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print(n.getValue());
    }

    public void visit(LongLiteralMinValueExpr n, Object arg) { Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print(n.getValue());
    }

    public void visit(StringLiteralExpr n, Object arg) { Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("\"");
        //printer.print(n.getValue());
        //printer.print("\"");
    }

    public void visit(BooleanLiteralExpr n, Object arg) { 
    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print(n.getValue().toString());
    }

    public void visit(NullLiteralExpr n, Object arg) { Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("null");
    }

    public void visit(ThisExpr n, Object arg) {
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	if (n.getClassExpr() != null) {
        	
            n.getClassExpr().accept(this, arg);
            //printer.print(".");
        }
        //printer.print("this");
    }

    public void visit(SuperExpr n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	if (n.getClassExpr() != null) {
        	
            n.getClassExpr().accept(this, arg);
            //printer.print(".");
        }
        //printer.print("super");
    }

    public void visit(MethodCallExpr n, Object arg) { 
//    	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~methodcallexp");
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	if (n.getScope() != null) {
        	
            n.getScope().accept(this, arg);
            //printer.print(".");
        }
        printTypeArgs(n.getTypeArgs(), arg);
        //printer.print(n.getName());
        //printer.print("(");
        if (n.getArgs() != null) {
        	currElement = createElement(n.getArgs().getClass().getSimpleName(),arg);
        	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
    		arg = currElement;
            for (Iterator<Expression> i = n.getArgs().iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
                if (i.hasNext()) {
                    //printer.print(", ");
                }
            }
        }
        //printer.print(")");
    }

    public void visit(ObjectCreationExpr n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	if (n.getScope() != null) {
            n.getScope().accept(this, arg);
            //printer.print(".");
        }

        //printer.print("new ");

        printTypeArgs(n.getTypeArgs(), arg);
        n.getType().accept(this, arg);

        //printer.print("(");
        if (n.getArgs() != null) {
        	currElement = createElement(n.getArgs().getClass().getSimpleName(),arg);
        	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
    		arg = currElement;
            for (Iterator<Expression> i = n.getArgs().iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
                if (i.hasNext()) {
                    //printer.print(", ");
                }
            }
        }
        //printer.print(")");

        if (n.getAnonymousClassBody() != null) {
            //printer.println(" {");
            printer.indent();
            printMembers(n.getAnonymousClassBody(), arg);
            printer.unindent();
            //printer.print("}");
        }
    }



    public void visit(UnaryExpr n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	switch (n.getOperator()) {
            case positive:
                //printer.print("+");
                break;
            case negative:
                //printer.print("-");
                break;
            case inverse:
                //printer.print("~");
                break;
            case not:
                //printer.print("!");
                break;
            case preIncrement:
                //printer.print("++");
                break;
            case preDecrement:
                //printer.print("--");
                break;
        }

        n.getExpr().accept(this, arg);

        switch (n.getOperator()) {
            case posIncrement:
                //printer.print("++");
                break;
            case posDecrement:
                //printer.print("--");
                break;
        }
    }

    public void visit(ConstructorDeclaration n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        printMemberAnnotations(n.getAnnotations(), arg);
        //printer.println("Modifier");

        printTypeParameters(n.getTypeParameters(), arg);
        if (n.getTypeParameters() != null) {
            //printer.print(" ");
        }
        //printer.print(n.getName());

        //printer.print("(");
        if (n.getParameters() != null) {
        	currElement = createElement(n.getParameters().getClass().getSimpleName(),arg);
        	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
    		arg = currElement;
            for (Iterator<Parameter> i = n.getParameters().iterator(); i.hasNext();) {
                Parameter p = i.next();
                p.accept(this, arg);
                if (i.hasNext()) {
                    //printer.print(", ");
                }
            }
        }
        //printer.print(")");

        if (n.getThrows() != null) {
            //printer.print(" throws ");
        	currElement = createElement(n.getThrows().getClass().getSimpleName(),arg);
        	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
    		arg = currElement;
            for (Iterator<NameExpr> i = n.getThrows().iterator(); i.hasNext();) {
                NameExpr name = i.next();
                name.accept(this, arg);
                if (i.hasNext()) {
                    //printer.print(", ");
                }
            }
        }
        //printer.print(" ");
        currElement = createElement(n.getClass().getSimpleName(),arg);
        setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
        n.getBlock().accept(this, arg);
    }

    public void visit(MethodDeclaration n, Object arg) { 
//    	Element MethodDeclarationElement = new Element("node");print(n,MethodDeclarationElement);SourcePrinter.parentElement = MethodDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        printMemberAnnotations(n.getAnnotations(), arg);
        //printer.println("Modifier");

        printTypeParameters(n.getTypeParameters(), arg);
        if (n.getTypeParameters() != null) {
            //printer.print(" ");
        }

        n.getType().accept(this, arg);
        //printer.print(" ");
        //printer.print(n.getName());

        //printer.print("(");
        if (n.getParameters() != null) {
//        	currElement = createElement(n.getParameters().getClass().getSimpleName(),arg);
//    		arg = currElement;
            for (Iterator<Parameter> i = n.getParameters().iterator(); i.hasNext();) {
                Parameter p = i.next();
                p.accept(this, arg);
                if (i.hasNext()) {
                    //printer.print(", ");
                }
            }
        }
        //printer.print(")");

        for (int i = 0; i < n.getArrayCount(); i++) {
            //printer.print("[]");
        }

        if (n.getThrows() != null) {
            //printer.print(" throws ");
        	currElement = createElement(n.getThrows().getClass().getSimpleName(),arg);
        	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
    		arg = currElement;
            for (Iterator<NameExpr> i = n.getThrows().iterator(); i.hasNext();) {
                NameExpr name = i.next();
                name.accept(this, arg);
                if (i.hasNext()) {
                    //printer.print(", ");
                }
            }
        }
        if (n.getBody() == null) {
            //printer.print(";");
        } else {
            //printer.print(" ");
//        	currElement = createElement(n.getClass().getSimpleName(),arg);
//    		arg = currElement;
            n.getBody().accept(this, arg);
        }
    }

    public void visit(Parameter n, Object arg) {
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	printAnnotations(n.getAnnotations(), arg);
        //printer.println("Modifier");

        n.getType().accept(this, arg);
        if (n.isVarArgs()) {
            //printer.print("...");
        }
        //printer.print(" ");
        n.getId().accept(this, arg);
    }

    public void visit(ExplicitConstructorInvocationStmt n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	if (n.isThis()) {
            printTypeArgs(n.getTypeArgs(), arg);
            //printer.print("this");
        } else {
            if (n.getExpr() != null) {
                n.getExpr().accept(this, arg);
                //printer.print(".");
            }
            printTypeArgs(n.getTypeArgs(), arg);
            //printer.print("super");
        }
        //printer.print("(");
        if (n.getArgs() != null) {
        	currElement = createElement(n.getArgs().getClass().getSimpleName(),arg);
        	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
    		arg = currElement;
            for (Iterator<Expression> i = n.getArgs().iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
                if (i.hasNext()) {
                    //printer.print(", ");
                }
            }
        }
        //printer.print(");");
    }

    public void visit(VariableDeclarationExpr n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	printAnnotations(n.getAnnotations(), arg);
        //printer.println("Modifier");

        n.getType().accept(this, arg);
        //printer.print(" ");

        for (Iterator<VariableDeclarator> i = n.getVars().iterator(); i.hasNext();) {
//        	currElement = createElement(n.getVars().getClass().getSimpleName(),arg);
//    		arg = currElement;
            VariableDeclarator v = i.next();
            v.accept(this, arg);
            if (i.hasNext()) {
                //printer.print(", ");
            }
        }
    }

    public void visit(TypeDeclarationStmt n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	n.getTypeDeclaration().accept(this, arg);
    }

    public void visit(AssertStmt n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("assert ");
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	n.getCheck().accept(this, arg);
        if (n.getMessage() != null) {
            //printer.print(" : ");
            n.getMessage().accept(this, arg);
        }
        //printer.print(";");
    }

    public void visit(BlockStmt n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.println("{");
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
        if (n.getStmts() != null) {
//        	currElement = createElement(n.getStmts().getClass().getSimpleName(),arg);
//    		arg = currElement;
            for (Statement s : n.getStmts()) {
            	int level = printer.getLevel();
                s.accept(this, arg);
                printer.setLevel(level);
            }
           
        }
        //printer.print("}");

    }

    public void visit(LabeledStmt n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print(n.getLabel());
        //printer.print(": ");
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
        n.getStmt().accept(this, arg);
    }

    public void visit(EmptyStmt n, Object arg) { Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print(";");
    }

    public void visit(ExpressionStmt n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	n.getExpression().accept(this, arg);
        //printer.print(";");
    }

    public void visit(SwitchStmt n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("switch(");
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
        n.getSelector().accept(this, arg);
        //printer.println(") {");
        if (n.getEntries() != null) {
//            printer.indent();
        	currElement = createElement(n.getEntries().getClass().getSimpleName(),arg);
        	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
    		arg = currElement;
            for (SwitchEntryStmt e : n.getEntries()) {
                e.accept(this, arg);
            }
            printer.unindent();
        }
        //printer.print("}");

    }

    public void visit(SwitchEntryStmt n, Object arg) {
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	if (n.getLabel() != null) {
            //printer.print("case ");
            n.getLabel().accept(this, arg);
            //printer.print(":");
        } else {
            //printer.print("default:");
        }
        //printer.println();
        printer.indent();
        if (n.getStmts() != null) {
        	currElement = createElement(n.getStmts().getClass().getSimpleName(),arg);
        	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
    		arg = currElement;
            for (Statement s : n.getStmts()) {
                s.accept(this, arg);
                //printer.println();
            }
        }
        printer.unindent();
    }

    public void visit(BreakStmt n, Object arg) { Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("break");
        if (n.getId() != null) {
            //printer.print(" ");
            //printer.print(n.getId());
        }
        //printer.print(";");
    }

    public void visit(ReturnStmt n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("return");
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	if (n.getExpr() != null) {
            //printer.print(" ");
            n.getExpr().accept(this, arg);
        }
        //printer.print(";");
    }

    public void visit(EnumDeclaration n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        printMemberAnnotations(n.getAnnotations(), arg);
        //printer.println("Modifier");

        //printer.print("enum ");
        //printer.print(n.getName());

        if (n.getImplements() != null) {
            //printer.print(" implements ");
        	currElement = createElement(n.getImplements().getClass().getSimpleName(),arg);
        	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
    		arg = currElement;
            for (Iterator<ClassOrInterfaceType> i = n.getImplements().iterator(); i.hasNext();) {
                ClassOrInterfaceType c = i.next();
                c.accept(this, arg);
                if (i.hasNext()) {
                    //printer.print(", ");
                }
            }
        }

        //printer.println(" {");
        printer.indent();
        if (n.getEntries() != null) {
            //printer.println();
        	currElement = createElement(n.getEntries().getClass().getSimpleName(),arg);
        	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
    		arg = currElement;
            for (Iterator<EnumConstantDeclaration> i = n.getEntries().iterator(); i.hasNext();) {
                EnumConstantDeclaration e = i.next();
                e.accept(this, arg);
                if (i.hasNext()) {
                    //printer.print(", ");
                }
            }
        }
        if (n.getMembers() != null) {
            //printer.println(";");
            printMembers(n.getMembers(), arg);
        } else {
            if (n.getEntries() != null) {
                //printer.println();
            }
        }
        printer.unindent();
        //printer.print("}");
    }

    public void visit(EnumConstantDeclaration n, Object arg) {
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        printMemberAnnotations(n.getAnnotations(), arg);
        //printer.print(n.getName());

        if (n.getArgs() != null) {
            //printer.print("(");
        	currElement = createElement(n.getArgs().getClass().getSimpleName(),arg);
        	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
    		arg = currElement;
            for (Iterator<Expression> i = n.getArgs().iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
                if (i.hasNext()) {
                    //printer.print(", ");
                }
            }
            //printer.print(")");
        }

        if (n.getClassBody() != null) {
            //printer.println(" {");
            printer.indent();
            printMembers(n.getClassBody(), arg);
            printer.unindent();
            //printer.println("}");
        }
    }

    public void visit(EmptyMemberDeclaration n, Object arg) {
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        //printer.print(";");
    }

    public void visit(InitializerDeclaration n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        if (n.isStatic()) {
            //printer.print("static ");
        }
        n.getBlock().accept(this, arg);
    }

    public void visit(IfStmt n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("if (");
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	n.getCondition().accept(this, arg);
        //printer.print(") ");
        n.getThenStmt().accept(this, arg);
        if (n.getElseStmt() != null) {
            //printer.print(" else ");
            n.getElseStmt().accept(this, arg);
        }
    }

    public void visit(WhileStmt n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("while (");
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
        n.getCondition().accept(this, arg);
        //printer.print(") ");
        n.getBody().accept(this, arg);
    }

    public void visit(ContinueStmt n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("continue");
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	if (n.getId() != null) {
            //printer.print(" ");
            //printer.print(n.getId());
        }
        //printer.print(";");
    }

    public void visit(DoStmt n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("do ");
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	n.getBody().accept(this, arg);
        //printer.print(" while (");
        n.getCondition().accept(this, arg);
        //printer.print(");");
    }

    public void visit(ForeachStmt n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("for (");
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	n.getVariable().accept(this, arg);
        //printer.print(" : ");
        n.getIterable().accept(this, arg);
        //printer.print(") ");
        n.getBody().accept(this, arg);
    }

    public void visit(ForStmt n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("for (");
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
    	Element arg1 = currElement;
    	if (n.getInit() != null) {
//    		currElement = createElement(n.getInit().getClass().getSimpleName(),arg1);
//    		Element arg2 = currElement;
            for (Iterator<Expression> i = n.getInit().iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg1);
                if (i.hasNext()) {
                    //printer.print(", ");
                }
            }
        }
        //printer.print("; ");
//    	currElement = createElement(n.getClass().getSimpleName(),arg);
//		arg = currElement;
        if (n.getCompare() != null) {
            n.getCompare().accept(this, arg1);
        }
        //printer.print("; ");
        if (n.getUpdate() != null) {
//        	currElement = createElement(n.getUpdate().getClass().getSimpleName(),arg1);
//    		Element arg3 = currElement;
            for (Iterator<Expression> i = n.getUpdate().iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg1);
                if (i.hasNext()) {
                    //printer.print(", ");
                }
            }
        }
        //printer.print(") ");
//        currElement = createElement(n.getClass().getSimpleName(),arg);
//		arg = currElement;
        n.getBody().accept(this, arg1);
    }

    public void visit(ThrowStmt n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("throw ");
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	n.getExpr().accept(this, arg);
        //printer.print(";");
    }

    public void visit(SynchronizedStmt n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("synchronized (");
        
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;n.getExpr().accept(this, arg);
        //printer.print(") ");
        n.getBlock().accept(this, arg);
    }

    public void visit(TryStmt n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("try ");
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
        n.getTryBlock().accept(this, arg);
        if (n.getCatchs() != null) {
        	currElement = createElement(n.getCatchs().getClass().getSimpleName(),arg);
        	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
    		arg = currElement;
            for (CatchClause c : n.getCatchs()) {
                c.accept(this, arg);
            }
        }
        if (n.getFinallyBlock() != null) {
            //printer.print(" finally ");
        	currElement = createElement(n.getClass().getSimpleName(),arg);
        	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
    		arg = currElement;
            n.getFinallyBlock().accept(this, arg);
        }
    }

    public void visit(CatchClause n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print(" catch (");
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	n.getExcept().accept(this, arg);
        //printer.print(") ");
        n.getCatchBlock().accept(this, arg);

    }

    public void visit(AnnotationDeclaration n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        printMemberAnnotations(n.getAnnotations(), arg);
        //printer.println("Modifier");

        //printer.print("@interface ");
        //printer.print(n.getName());
        //printer.println(" {");
        printer.indent();
        if (n.getMembers() != null) {
            printMembers(n.getMembers(), arg);
        }
        printer.unindent();
        //printer.print("}");
    }

    public void visit(AnnotationMemberDeclaration n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        printMemberAnnotations(n.getAnnotations(), arg);
        //printer.println("Modifier");

        n.getType().accept(this, arg);
        //printer.print(" ");
        //printer.print(n.getName());
        //printer.print("()");
        if (n.getDefaultValue() != null) {
            //printer.print(" default ");
            n.getDefaultValue().accept(this, arg);
        }
        //printer.print(";");
    }

    public void visit(MarkerAnnotationExpr n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("@");
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	n.getName().accept(this, arg);
    }

    public void visit(SingleMemberAnnotationExpr n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("@");
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	n.getName().accept(this, arg);
        //printer.print("(");
        n.getMemberValue().accept(this, arg);
        //printer.print(")");
    }

    public void visit(NormalAnnotationExpr n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("@");
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	n.getName().accept(this, arg);
        //printer.print("(");
    	
    	currElement = createElement(n.getPairs().getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
        for (Iterator<MemberValuePair> i = n.getPairs().iterator(); i.hasNext();) {
            MemberValuePair m = i.next();
            m.accept(this, arg);
            if (i.hasNext()) {
                //printer.print(", ");
            }
        }
        //printer.print(")");
    }

    public void visit(MemberValuePair n, Object arg) { 
//    	Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print(n.getName());
        //printer.print(" = ");
    	Element currElement = createElement(n.getClass().getSimpleName(),arg);
    	setElementAttribute(currElement,n.getBeginLine(),n.getBeginColumn(),n.getEndLine(),n.getEndColumn());
		arg = currElement;
    	n.getValue().accept(this, arg);
    }

    public void visit(LineComment n, Object arg) { Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("//");
        //printer.println(n.getContent());
    }

    public void visit(BlockComment n, Object arg) { Element PackageDeclarationElement = new Element("node");print(n,PackageDeclarationElement);SourcePrinter.parentElement = PackageDeclarationElement;
        //printer.print("/*");
        //printer.print(n.getContent());
        //printer.println("*/");
    }

    /**
     * 获取源码打印机
     * */
    public SourcePrinter getPrinter(){
    	return printer;
    }

    public void visitX(Node n){ 
    	Log.println(n);
    	Element NodeElement = new Element("node");
    	printer.println(n.getClass().getSimpleName(), NodeElement);
    	SourcePrinter.parentElement = NodeElement;
    	
    	Method[] m = n.getClass().getMethods(); 
    	for(int i=0; i< m.length; i++){
    		Class type = m[i].getReturnType();
    		Object o = null;
			try {
				if(m[i].getParameterTypes().length==0 && 
						m[i].getName().startsWith("get")){
					//Debug.println(m[i].getName());
					o = m[i].invoke(n);
					//Debug.println(o);
				}								
				if(o == null) continue;				
			} catch (Exception e) {				
				e.printStackTrace();
			} 
			//Debug.println(type.toString());
    		if(type == List.class){
    			List list=(List)o;
    			//Debug.println(1);
    			if(list.size()==0) continue;
    			Log.println(2);
    			boolean isMember = isMember(list.get(0).getClass());
    			if(isMember){

	    			for(Object obj : list){

	    			}
    			}
    		} else {
    			Log.println(5);
        		if(isMember(type)){  
        			printer.indent();
        			//visit((Node)o);
        		}
    		}
    		
    	}
    	
    }
    
    private void testMethod(Node n){
    	Field[] f = n.getClass().getFields(); 
    	for(int i=0; i< f.length; i++){
    		Class type = f[i].getType();
    		Object o = null;
			try {
				o = f[i].get(n);				
				if(o == null) continue;				
			} catch (Exception e) {				
				e.printStackTrace();
			} 
			
    		if(type == List.class){
    			List list=(List)o;
    			boolean isMember = isMember(list.get(0).getClass());
    			if(isMember){
	    			for(Object obj : list){
	    				printer.indent();
	        			//visit((Node)obj);
	        			printer.unindent();
	    			}
    			}
    		} else {
        		if(isMember(type)){  
        			printer.indent();
        			//visit((Node)o);
        		}
    		}
    		
    	}
    }
    
    private boolean isMember(Class clazz){
    	boolean isMember = false;  
    	Class c = clazz.getSuperclass();
    	while(c!= Object.class){
    		Log.println(c.toString());
    		if(c == Node.class){
    			isMember = true;
    			break;
    		}    		
    		c = c.getSuperclass();
    	}

		return isMember;
    }
 
	
}
