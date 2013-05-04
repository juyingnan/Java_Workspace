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

import java.util.Iterator;
import java.util.List;

import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.jobs.IJob;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.util.SourcePrinter;

/**
 * @author shootsoft
 * 
 * SourceVisitor根据抽象的AST语法树重新构建源码。
 * 也可以用来对源码进行格式化操作。
 * 
 */

public class SourceVisitor<R, A> implements GenericVisitor<R, A>, IJob {

    protected SourcePrinter printer = new SourcePrinter();
    protected boolean fixModifiers;

	
    @Override
	public boolean run(TestData data) {
		if (data != null) {
			CompilationUnit unit = (CompilationUnit) data.get(
					JobConst.AST);
			if (unit != null) {
				visit(unit, null);
				data.put(JobConst.TARGET_SOURCE, this.getSource());
				return true;
			}
		}
		return false;
	}
    
    /**
     * 获取源码
     * */
    public String getSource() {
        return printer.getSource();
    }

    /**
     * 获取源码打印机
     * */
    public SourcePrinter getPrinter(){
    	return printer;
    }

    /**
     * 打印修饰符
     * */
    protected void printModifiers(int modifiers) {
    	 if (ModifierSet.isPrivate(modifiers)) {
         	if(isFixModifiers()){
         		printer.print("public ");
         	}
         	else{
         		printer.print("private ");
         	}
         }
         if (ModifierSet.isProtected(modifiers)) {
         	if(isFixModifiers()){
         		printer.print("public ");
         	}
         	else{
         		printer.print("protected ");
         	}

         }
         if (ModifierSet.isPublic(modifiers)) {
             printer.print("public ");
         }
         if (ModifierSet.isAbstract(modifiers)) {
             printer.print("abstract ");
         }
         if (ModifierSet.isStatic(modifiers)) {
             printer.print("static ");
         }
         if (ModifierSet.isFinal(modifiers)) {
             printer.print("final ");
         }
         if (ModifierSet.isNative(modifiers)) {
             printer.print("native ");
         }
         if (ModifierSet.isStrictfp(modifiers)) {
             printer.print("strictfp ");
         }
         if (ModifierSet.isSynchronized(modifiers)) {
             printer.print("synchronized ");
         }
         if (ModifierSet.isTransient(modifiers)) {
             printer.print("transient ");
         }
         if (ModifierSet.isVolatile(modifiers)) {
             printer.print("volatile ");
         }
    }

    /**
     * 打印所有成员
     * */
    protected void printMembers(List<BodyDeclaration> members, A arg) {
        for (BodyDeclaration member : members) {
            printer.println();
            member.accept(this, arg);
            printer.println();
        }
    }

    /**
     * 打印Annotation成员，每个成员换行
     * */
    protected void printMemberAnnotations(List<AnnotationExpr> annotations, A arg) {
        if (annotations != null) {
            for (AnnotationExpr a : annotations) {
                a.accept(this, arg);
                printer.println();
            }
        }
    }

    /**
     * 打印Annotation成员，每个成员之间空格
     * */
    protected void printAnnotations(List<AnnotationExpr> annotations, A arg) {
        if (annotations != null) {
            for (AnnotationExpr a : annotations) {
                a.accept(this, arg);
                printer.print(" ");
            }
        }
    }

    
    /**
     * 打印范型的类型列表
     */       
    protected void printTypeArgs(List<Type> args, A arg) {
        if (args != null) {
            printer.print("<");
            for (Iterator<Type> i = args.iterator(); i.hasNext();) {
                Type t = i.next();
                t.accept(this, arg);
                if (i.hasNext()) {
                    printer.print(", ");
                }
            }
            printer.print(">");
        }
    }

    /**
     * 打印范型的参数
     */ 
    protected void printTypeParameters(List<TypeParameter> args, A arg) {
        if (args != null) {
            printer.print("<");
            for (Iterator<TypeParameter> i = args.iterator(); i.hasNext();) {
                TypeParameter t = i.next();
                t.accept(this, arg);
                if (i.hasNext()) {
                    printer.print(", ");
                }
            }
            printer.print(">");
        }
    }

    /**
     * 访问Node，会出错
     */ 
    public R visit(Node n, A arg) {
        throw new IllegalStateException(n.getClass().getName());
    }

    /**
     * 访问根节点Node
     */ 
    public R visit(CompilationUnit n, A arg) {
        if (n.getPackage() != null) {
            n.getPackage().accept(this, arg);
        }
        if (n.getImports() != null) {
            for (ImportDeclaration i : n.getImports()) {
                i.accept(this, arg);
            }
            printer.println();
        }
        if (n.getTypes() != null) {
            for (Iterator<TypeDeclaration> i = n.getTypes().iterator(); i.hasNext();) {
                i.next().accept(this, arg);
                printer.println();
                if (i.hasNext()) {
                    printer.println();
                }
            }
        }
        return null;
    }

    /**
     * 访问包声明
     */ 
    public R visit(PackageDeclaration n, A arg) {
        printAnnotations(n.getAnnotations(), arg);
        printer.print("package ");
        n.getName().accept(this, arg);
       
        printer.println(";");
        printer.println();
        return null;
    }

    /**
     * 访问名称表达式
     */ 
    public R visit(NameExpr n, A arg) {
        printer.print(n.getName());
        return null;
    }

    public R visit(QualifiedNameExpr n, A arg) {
        n.getQualifier().accept(this, arg);
        printer.print(".");
        printer.print(n.getName());
        return null;
    }

    public R visit(ImportDeclaration n, A arg) {
        printer.print("import ");
        if (n.isStatic()) {
            printer.print("static ");
        }
        n.getName().accept(this, arg);
        if (n.isAsterisk()) {
            printer.print(".*");
        }
        printer.println(";");
        return null;
    }

    public R visit(ClassOrInterfaceDeclaration n, A arg) {
    	
    	visitClassOrInterfaceHeader(n,arg);

        printer.println(" {");
        printer.indent();
        if (n.getMembers() != null) {
            printMembers(n.getMembers(), arg);
        }
        printer.unindent();
        printer.print("}");
        return null;
    }

    public R visit(EmptyTypeDeclaration n, A arg) {
        if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        printer.print(";");
        return null;
    }

    public R visit(JavadocComment n, A arg) {
        printer.print("/**");
        printer.print(n.getContent());
        printer.println("*/");
        return null;
    }

    public R visit(ClassOrInterfaceType n, A arg) {
        if (n.getScope() != null) {
            n.getScope().accept(this, arg);
            printer.print(".");
        }
        printer.print(n.getName());
        printTypeArgs(n.getTypeArgs(), arg);
        return null;
    }

    public R visit(TypeParameter n, A arg) {
        printer.print(n.getName());
        if (n.getTypeBound() != null) {
            printer.print(" extends ");
            for (Iterator<ClassOrInterfaceType> i = n.getTypeBound().iterator(); i.hasNext();) {
                ClassOrInterfaceType c = i.next();
                c.accept(this, arg);
                if (i.hasNext()) {
                    printer.print(" & ");
                }
            }
        }
        return null;
    }

    public R visit(PrimitiveType n, A arg) {
        switch (n.getType()) {
            case Boolean:
                printer.print("boolean");
                break;
            case Byte:
                printer.print("byte");
                break;
            case Char:
                printer.print("char");
                break;
            case Double:
                printer.print("double");
                break;
            case Float:
                printer.print("float");
                break;
            case Int:
                printer.print("int");
                break;
            case Long:
                printer.print("long");
                break;
            case Short:
                printer.print("short");
                break;
        }
        return null;
    }

    public R visit(ReferenceType n, A arg) {
        n.getType().accept(this, arg);
        for (int i = 0; i < n.getArrayCount(); i++) {
            printer.print("[]");
        }
        return null;
    }

    public R visit(WildcardType n, A arg) {
        printer.print("?");
        if (n.getExtends() != null) {
            printer.print(" extends ");
            n.getExtends().accept(this, arg);
        }
        if (n.getSuper() != null) {
            printer.print(" super ");
            n.getSuper().accept(this, arg);
        }
        return null;
    }

    public R visit(FieldDeclaration n, A arg) {
        if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        printMemberAnnotations(n.getAnnotations(), arg);
        printModifiers(n.getModifiers());
        n.getType().accept(this, arg);

        printer.print(" ");
        for (Iterator<VariableDeclarator> i = n.getVariables().iterator(); i.hasNext();) {
            VariableDeclarator var = i.next();
            var.accept(this, arg);
            if (i.hasNext()) {
                printer.print(", ");
            }
        }

        printer.print(";");
        return null;
    }

    public R visit(VariableDeclarator n, A arg) {
        n.getId().accept(this, arg);
        if (n.getInit() != null) {
            printer.print(" = ");
            n.getInit().accept(this, arg);
        }
        return null;
    }

    public R visit(VariableDeclaratorId n, A arg) {
        printer.print(n.getName());
        for (int i = 0; i < n.getArrayCount(); i++) {
            printer.print("[]");
        }
        return null;
    }

    public R visit(ArrayInitializerExpr n, A arg) {
        printer.print("{");
        if (n.getValues() != null) {
            printer.print(" ");
            for (Iterator<Expression> i = n.getValues().iterator(); i.hasNext();) {
                Expression expr = i.next();
                expr.accept(this, arg);
                if (i.hasNext()) {
                    printer.print(", ");
                }
            }
            printer.print(" ");
        }
        printer.print("}");
        return null;
    }

    public R visit(VoidType n, A arg) {
        printer.print("void");
        return null;
    }

    public R visit(ArrayAccessExpr n, A arg) {
        n.getName().accept(this, arg);
        printer.print("[");
        n.getIndex().accept(this, arg);
        printer.print("]");
        return null;
    }

    public R visit(ArrayCreationExpr n, A arg) {
        printer.print("new ");
        n.getType().accept(this, arg);
        

        if (n.getDimensions() != null) {
            for (Expression dim : n.getDimensions()) {
                printer.print("[");
                dim.accept(this, arg);
                printer.print("]");
            }
            for (int i = 0; i < n.getArrayCount(); i++) {
                printer.print("[]");
            }
        } else {
            for (int i = 0; i < n.getArrayCount(); i++) {
                printer.print("[]");
            }
            printer.print(" ");
            n.getInitializer().accept(this, arg);
        }
        return null;
    }

    public R visit(AssignExpr n, A arg) {
        n.getTarget().accept(this, arg);
        printer.print(" ");
        switch (n.getOperator()) {
            case assign:
                printer.print("=");
                break;
            case and:
                printer.print("&=");
                break;
            case or:
                printer.print("|=");
                break;
            case xor:
                printer.print("^=");
                break;
            case plus:
                printer.print("+=");
                break;
            case minus:
                printer.print("-=");
                break;
            case rem:
                printer.print("%=");
                break;
            case slash:
                printer.print("/=");
                break;
            case star:
                printer.print("*=");
                break;
            case lShift:
                printer.print("<<=");
                break;
            case rSignedShift:
                printer.print(">>=");
                break;
            case rUnsignedShift:
                printer.print(">>>=");
                break;
        }
        printer.print(" ");
        n.getValue().accept(this, arg);
        return null;
    }

    public R visit(BinaryExpr n, A arg) {
        n.getLeft().accept(this, arg);
        printer.print(" ");
        switch (n.getOperator()) {
            case or:
                printer.print("||");
                break;
            case and:
                printer.print("&&");
                break;
            case binOr:
                printer.print("|");
                break;
            case binAnd:
                printer.print("&");
                break;
            case xor:
                printer.print("^");
                break;
            case equals:
                printer.print("==");
                break;
            case notEquals:
                printer.print("!=");
                break;
            case less:
                printer.print("<");
                break;
            case greater:
                printer.print(">");
                break;
            case lessEquals:
                printer.print("<=");
                break;
            case greaterEquals:
                printer.print(">=");
                break;
            case lShift:
                printer.print("<<");
                break;
            case rSignedShift:
                printer.print(">>");
                break;
            case rUnsignedShift:
                printer.print(">>>");
                break;
            case plus:
                printer.print("+");
                break;
            case minus:
                printer.print("-");
                break;
            case times:
                printer.print("*");
                break;
            case divide:
                printer.print("/");
                break;
            case remainder:
                printer.print("%");
                break;
        }
        printer.print(" ");
        n.getRight().accept(this, arg);
        return null;
    }

    public R visit(CastExpr n, A arg) {
        printer.print("(");
        n.getType().accept(this, arg);
        printer.print(") ");
        n.getExpr().accept(this, arg);
        return null;
    }

    public R visit(ClassExpr n, A arg) {
        n.getType().accept(this, arg);
        printer.print(".class");
        return null;
    }

    public R visit(ConditionalExpr n, A arg) {
        n.getCondition().accept(this, arg);
        printer.print(" ? ");
        n.getThenExpr().accept(this, arg);
        printer.print(" : ");
        n.getElseExpr().accept(this, arg);
        return null;
    }

    public R visit(EnclosedExpr n, A arg) {
        printer.print("(");
        n.getInner().accept(this, arg);
        printer.print(")");
        return null;
    }

    public R visit(FieldAccessExpr n, A arg) {
        n.getScope().accept(this, arg);
        printer.print(".");
        printer.print(n.getField());
        return null;
    }

    public R visit(InstanceOfExpr n, A arg) {
        n.getExpr().accept(this, arg);
        printer.print(" instanceof ");
        n.getType().accept(this, arg);
        return null;
    }

    public R visit(CharLiteralExpr n, A arg) {
        printer.print("'");
        printer.print(n.getValue());
        printer.print("'");
        return null;
    }

    public R visit(DoubleLiteralExpr n, A arg) {
        printer.print(n.getValue());
        return null;
    }

    public R visit(IntegerLiteralExpr n, A arg) {
        printer.print(n.getValue());
        return null;
    }

    public R visit(LongLiteralExpr n, A arg) {
        printer.print(n.getValue());
        return null;
    }

    public R visit(IntegerLiteralMinValueExpr n, A arg) {
        printer.print(n.getValue());
        return null;
    }

    public R visit(LongLiteralMinValueExpr n, A arg) {
        printer.print(n.getValue());
        return null;
    }

    public R visit(StringLiteralExpr n, A arg) {
        printer.print("\"");
        printer.print(n.getValue());
        printer.print("\"");
        return null;
    }

    public R visit(BooleanLiteralExpr n, A arg) {
        printer.print(String.valueOf(n.getValue()));
        return null;
    }

    public R visit(NullLiteralExpr n, A arg) {
        printer.print("null");
        return null;
    }

    public R visit(ThisExpr n, A arg) {
        if (n.getClassExpr() != null) {
            n.getClassExpr().accept(this, arg);
            printer.print(".");
        }
        printer.print("this");
        return null;
    }

    public R visit(SuperExpr n, A arg) {
        if (n.getClassExpr() != null) {
            n.getClassExpr().accept(this, arg);
            printer.print(".");
        }
        printer.print("super");
        return null;
    }

    public R visit(MethodCallExpr n, A arg) {
        if (n.getScope() != null) {
            n.getScope().accept(this, arg);
            printer.print(".");
        }
        printTypeArgs(n.getTypeArgs(), arg);
        printer.print(n.getName());
        printer.print("(");
        if (n.getArgs() != null) {
            for (Iterator<Expression> i = n.getArgs().iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
                if (i.hasNext()) {
                    printer.print(", ");
                }
            }
        }
        printer.print(")");
        return null;
    }

    public R visit(ObjectCreationExpr n, A arg) {
        if (n.getScope() != null) {
            n.getScope().accept(this, arg);
            printer.print(".");
        }

        printer.print("new ");

        printTypeArgs(n.getTypeArgs(), arg);
        n.getType().accept(this, arg);

        printer.print("(");
        if (n.getArgs() != null) {
            for (Iterator<Expression> i = n.getArgs().iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
                if (i.hasNext()) {
                    printer.print(", ");
                }
            }
        }
        printer.print(")");

        if (n.getAnonymousClassBody() != null) {
            printer.println(" {");
            printer.indent();
            printMembers(n.getAnonymousClassBody(), arg);
            printer.unindent();
            printer.print("}");
        }
        return null;
    }


    public R visit(UnaryExpr n, A arg) {
        switch (n.getOperator()) {
            case positive:
                printer.print("+");
                break;
            case negative:
                printer.print("-");
                break;
            case inverse:
                printer.print("~");
                break;
            case not:
                printer.print("!");
                break;
            case preIncrement:
                printer.print("++");
                break;
            case preDecrement:
                printer.print("--");
                break;
        }

        n.getExpr().accept(this, arg);

        switch (n.getOperator()) {
            case posIncrement:
                printer.print("++");
                break;
            case posDecrement:
                printer.print("--");
                break;
        }
        return null;
    }

    public R visit(ConstructorDeclaration n, A arg) {
        if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        printMemberAnnotations(n.getAnnotations(), arg);
        printModifiers(n.getModifiers());

        printTypeParameters(n.getTypeParameters(), arg);
        if (n.getTypeParameters() != null) {
            printer.print(" ");
        }
        printer.print(n.getName());

        printer.print("(");
        if (n.getParameters() != null) {
            for (Iterator<Parameter> i = n.getParameters().iterator(); i.hasNext();) {
                Parameter p = i.next();
                p.accept(this, arg);
                if (i.hasNext()) {
                    printer.print(", ");
                }
            }
        }
        printer.print(")");

        if (n.getThrows() != null) {
            printer.print(" throws ");
            for (Iterator<NameExpr> i = n.getThrows().iterator(); i.hasNext();) {
                NameExpr name = i.next();
                name.accept(this, arg);
                if (i.hasNext()) {
                    printer.print(", ");
                }
            }
        }
        printer.print(" ");
        n.getBlock().accept(this, arg);
        return null;
    }

    public R visit(MethodDeclaration n, A arg) {
        if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        printMemberAnnotations(n.getAnnotations(), arg);
        printModifiers(n.getModifiers());

        printTypeParameters(n.getTypeParameters(), arg);
        if (n.getTypeParameters() != null) {
            printer.print(" ");
        }

        n.getType().accept(this, arg);
        printer.print(" ");
        printer.print(n.getName());
     
        
        printer.print("(");
        if (n.getParameters() != null) {
            for (Iterator<Parameter> i = n.getParameters().iterator(); i.hasNext();) {
                Parameter p = i.next();
                p.accept(this, arg);
                if (i.hasNext()) {
                    printer.print(", ");
                }
            }
        }
        printer.print(")");

        for (int i = 0; i < n.getArrayCount(); i++) {
            printer.print("[]");
        }

        if (n.getThrows() != null) {
            printer.print(" throws ");
            for (Iterator<NameExpr> i = n.getThrows().iterator(); i.hasNext();) {
                NameExpr name = i.next();
                name.accept(this, arg);
                if (i.hasNext()) {
                    printer.print(", ");
                }
            }
        }
        if (n.getBody() == null) {
            printer.print(";");
        } else {
            printer.print(" ");
            n.getBody().accept(this, arg);
        }
        return null;
    }

    public R visit(Parameter n, A arg) {
        printAnnotations(n.getAnnotations(), arg);
        printModifiers(n.getModifiers());

        n.getType().accept(this, arg);
        if (n.isVarArgs()) {
            printer.print("...");
        }
        printer.print(" ");
        n.getId().accept(this, arg);
        return null;
    }

    public R visit(ExplicitConstructorInvocationStmt n, A arg) {
        if (n.isThis()) {
            printTypeArgs(n.getTypeArgs(), arg);
            printer.print("this");
        } else {
            if (n.getExpr() != null) {
                n.getExpr().accept(this, arg);
                printer.print(".");
            }
            printTypeArgs(n.getTypeArgs(), arg);
            printer.print("super");
        }
        printer.print("(");
        if (n.getArgs() != null) {
            for (Iterator<Expression> i = n.getArgs().iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
                if (i.hasNext()) {
                    printer.print(", ");
                }
            }
        }
        printer.print(");");
        return null;
    }

    public R visit(VariableDeclarationExpr n, A arg) {
        printAnnotations(n.getAnnotations(), arg);
        printModifiers(n.getModifiers());

        n.getType().accept(this, arg);
        printer.print(" ");

        for (Iterator<VariableDeclarator> i = n.getVars().iterator(); i.hasNext();) {
            VariableDeclarator v = i.next();
            v.accept(this, arg);
            if (i.hasNext()) {
                printer.print(", ");
            }
        }
        return null;
    }

    public R visit(TypeDeclarationStmt n, A arg) {
        n.getTypeDeclaration().accept(this, arg);
        return null;
    }

    public R visit(AssertStmt n, A arg) {
        printer.print("assert ");
        n.getCheck().accept(this, arg);
        if (n.getMessage() != null) {
            printer.print(" : ");
            n.getMessage().accept(this, arg);
        }
        printer.print(";");
        return null;
    }

    public R visit(BlockStmt n, A arg) {
        printer.println("{");
        if (n.getStmts() != null) {
            printer.indent();
            for (Statement s : n.getStmts()) {
                s.accept(this, arg);
                printer.println();
            }
            printer.unindent();
        }
        printer.print("}");
        return null;
    }

    public R visit(LabeledStmt n, A arg) {
        printer.print(n.getLabel());
        printer.print(": ");
        n.getStmt().accept(this, arg);
        return null;
    }

    public R visit(EmptyStmt n, A arg) {
        printer.print(";");
        return null;
    }

    public R visit(ExpressionStmt n, A arg) {
        n.getExpression().accept(this, arg);
        printer.print(";");
        return null;
    }

    public R visit(SwitchStmt n, A arg) {
        printer.print("switch(");
        n.getSelector().accept(this, arg);
        printer.println(") {");
        if (n.getEntries() != null) {
            printer.indent();
            for (SwitchEntryStmt e : n.getEntries()) {
                e.accept(this, arg);
            }
            printer.unindent();
        }
        printer.print("}");
        return null;
    }

    public R visit(SwitchEntryStmt n, A arg) {
        if (n.getLabel() != null) {
            printer.print("case ");
            n.getLabel().accept(this, arg);
            printer.print(":");
        } else {
            printer.print("default:");
        }
        printer.println();
        printer.indent();
        if (n.getStmts() != null) {
            for (Statement s : n.getStmts()) {
                s.accept(this, arg);
                printer.println();
            }
        }
        printer.unindent();
        return null;
    }

    public R visit(BreakStmt n, A arg) {
        printer.print("break");
        if (n.getId() != null) {
            printer.print(" ");
            printer.print(n.getId());
        }
        printer.print(";");
        return null;
    }

    public R visit(ReturnStmt n, A arg) {
        printer.print("return");
        if (n.getExpr() != null) {
            printer.print(" ");
            n.getExpr().accept(this, arg);
        }
        printer.print(";");
        return null;
    }

    public R visit(EnumDeclaration n, A arg) {
        if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        printMemberAnnotations(n.getAnnotations(), arg);
        printModifiers(n.getModifiers());

        printer.print("enum ");
        printer.print(n.getName());

        if (n.getImplements() != null) {
            printer.print(" implements ");
            for (Iterator<ClassOrInterfaceType> i = n.getImplements().iterator(); i.hasNext();) {
                ClassOrInterfaceType c = i.next();
                c.accept(this, arg);
                if (i.hasNext()) {
                    printer.print(", ");
                }
            }
        }

        printer.println(" {");
        printer.indent();
        if (n.getEntries() != null) {
            printer.println();
            for (Iterator<EnumConstantDeclaration> i = n.getEntries().iterator(); i.hasNext();) {
                EnumConstantDeclaration e = i.next();
                e.accept(this, arg);
                if (i.hasNext()) {
                    printer.print(", ");
                }
            }
        }
        if (n.getMembers() != null) {
            printer.println(";");
            printMembers(n.getMembers(), arg);
        } else {
            if (n.getEntries() != null) {
                printer.println();
            }
        }
        printer.unindent();
        printer.print("}");
        return null;
    }

    public R visit(EnumConstantDeclaration n, A arg) {
        if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        printMemberAnnotations(n.getAnnotations(), arg);
        printer.print(n.getName());

        if (n.getArgs() != null) {
            printer.print("(");
            for (Iterator<Expression> i = n.getArgs().iterator(); i.hasNext();) {
                Expression e = i.next();
                e.accept(this, arg);
                if (i.hasNext()) {
                    printer.print(", ");
                }
            }
            printer.print(")");
        }

        if (n.getClassBody() != null) {
            printer.println(" {");
            printer.indent();
            printMembers(n.getClassBody(), arg);
            printer.unindent();
            printer.println("}");
        }
        return null;
    }

    public R visit(EmptyMemberDeclaration n, A arg) {
        if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        printer.print(";");
        return null;
    }

    public R visit(InitializerDeclaration n, A arg) {
        if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        if (n.isStatic()) {
            printer.print("static ");
        }
        n.getBlock().accept(this, arg);
        return null;
    }

    public R visit(IfStmt n, A arg) {
        printer.print("if (");
        n.getCondition().accept(this, arg);
        printer.print(") ");
        n.getThenStmt().accept(this, arg);
        if (n.getElseStmt() != null) {
            printer.print(" else ");
            n.getElseStmt().accept(this, arg);
        }
        return null;
    }

    public R visit(WhileStmt n, A arg) {
        printer.print("while (");
        n.getCondition().accept(this, arg);
        printer.print(") ");
        n.getBody().accept(this, arg);
        return null;
    }

    public R visit(ContinueStmt n, A arg) {
        printer.print("continue");
        if (n.getId() != null) {
            printer.print(" ");
            printer.print(n.getId());
        }
        printer.print(";");
        return null;
    }

    public R visit(DoStmt n, A arg) {
        printer.print("do ");
        n.getBody().accept(this, arg);
        printer.print(" while (");
        n.getCondition().accept(this, arg);
        printer.print(");");
        return null;
    }

    public R visit(ForeachStmt n, A arg) {
        printer.print("for (");
        n.getVariable().accept(this, arg);
        printer.print(" : ");
        n.getIterable().accept(this, arg);
        printer.print(") ");
        n.getBody().accept(this, arg);
        return null;
    }

    public R visit(ForStmt n, A arg) {
    	
    	visitForStmtHeader(n,arg);    	
       
        n.getBody().accept(this, arg);
        return null;
    }

    public R visit(ThrowStmt n, A arg) {
        printer.print("throw ");
        n.getExpr().accept(this, arg);
        printer.print(";");
        return null;
    }

    public R visit(SynchronizedStmt n, A arg) {
        printer.print("synchronized (");
        n.getExpr().accept(this, arg);
        printer.print(") ");
        n.getBlock().accept(this, arg);
        return null;
    }

    public R visit(TryStmt n, A arg) {
        printer.print("try ");
        n.getTryBlock().accept(this, arg);
        if (n.getCatchs() != null) {
            for (CatchClause c : n.getCatchs()) {
                c.accept(this, arg);
            }
        }
        if (n.getFinallyBlock() != null) {
            printer.print(" finally ");
            n.getFinallyBlock().accept(this, arg);
        }
        return null;
    }

    public R visit(CatchClause n, A arg) {
        printer.print(" catch (");
        n.getExcept().accept(this, arg);
        printer.print(") ");
        n.getCatchBlock().accept(this, arg);
        return null;
    }

    public R visit(AnnotationDeclaration n, A arg) {
        if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        printMemberAnnotations(n.getAnnotations(), arg);
        printModifiers(n.getModifiers());

        printer.print("@interface ");
        printer.print(n.getName());
        printer.println(" {");
        printer.indent();
        if (n.getMembers() != null) {
            printMembers(n.getMembers(), arg);
        }
        printer.unindent();
        printer.print("}");
        return null;
    }

    public R visit(AnnotationMemberDeclaration n, A arg) {
        if (n.getJavaDoc() != null) {
            n.getJavaDoc().accept(this, arg);
        }
        printMemberAnnotations(n.getAnnotations(), arg);
        printModifiers(n.getModifiers());

        n.getType().accept(this, arg);
        printer.print(" ");
        printer.print(n.getName());
        printer.print("()");
        if (n.getDefaultValue() != null) {
            printer.print(" default ");
            n.getDefaultValue().accept(this, arg);
        }
        printer.print(";");
        return null;
    }

    public R visit(MarkerAnnotationExpr n, A arg) {
        printer.print("@");
        n.getName().accept(this, arg);
        return null;
    }

    public R visit(SingleMemberAnnotationExpr n, A arg) {
        printer.print("@");
        n.getName().accept(this, arg);
        printer.print("(");
        n.getMemberValue().accept(this, arg);
        printer.print(")");
        return null;
    }

    public R visit(NormalAnnotationExpr n, A arg) {
        printer.print("@");
        n.getName().accept(this, arg);
        printer.print("(");
        for (Iterator<MemberValuePair> i = n.getPairs().iterator(); i.hasNext();) {
            MemberValuePair m = i.next();
            m.accept(this, arg);
            if (i.hasNext()) {
                printer.print(", ");
            }
        }
        printer.print(")");
        return null;
    }

    public R visit(MemberValuePair n, A arg) {
        printer.print(n.getName());
        printer.print(" = ");
        n.getValue().accept(this, arg);
        return null;
    }

    public R visit(LineComment n, A arg) {
        printer.print("//");
        printer.println(n.getContent());
        return null;
    }

    public R visit(BlockComment n, A arg) {
        printer.print("/*");
        printer.print(n.getContent());
        printer.println("*/");
        return null;
    }

	public void setFixModifiers(boolean fixModifiers) {
		this.fixModifiers = fixModifiers;
	}

	public boolean isFixModifiers() {
		return fixModifiers;
	}
	
	protected void visitClassOrInterfaceHeader(ClassOrInterfaceDeclaration n, A arg){
		 if (n.getJavaDoc() != null) {
	            n.getJavaDoc().accept(this, arg);
	        }
	        printMemberAnnotations(n.getAnnotations(), arg);
	        printModifiers(n.getModifiers());

	        if (n.isInterface()) {
	            printer.print("interface ");
	        } else {
	            printer.print("class ");
	        }
	        
	        printer.print(n.getName());

	        printTypeParameters(n.getTypeParameters(), arg);

	        if (n.getExtends() != null) {
	            printer.print(" extends ");
	            for (Iterator<ClassOrInterfaceType> i = n.getExtends().iterator(); i.hasNext();) {
	                ClassOrInterfaceType c = i.next();
	                c.accept(this, arg);
	                if (i.hasNext()) {
	                    printer.print(", ");
	                }
	            }
	        }

	        if (n.getImplements() != null) {
	            printer.print(" implements ");
	            for (Iterator<ClassOrInterfaceType> i = n.getImplements().iterator(); i.hasNext();) {
	                ClassOrInterfaceType c = i.next();
	                c.accept(this, arg);
	                if (i.hasNext()) {
	                    printer.print(", ");
	                }
	            }
	        }

	      
	        
	}

	protected void visitForStmtHeader(ForStmt n, A arg) {
		printer.print("for (");

		visitForStmtHeaderInit(n, arg);
		visitForStmtHeaderCondition(n, arg);
		visitForStmtHeaderUpdate(n, arg);
		printer.print(") ");
	}

	protected void visitForStmtHeaderInit(ForStmt n, A arg) {
		if (n.getInit() != null) {
			for (Iterator<Expression> i = n.getInit().iterator(); i.hasNext();) {
				Expression e = i.next();
				e.accept(this, arg);
				if (i.hasNext()) {
					printer.print(", ");
				}
			}
		}
		printer.print("; ");
	}

	protected void visitForStmtHeaderCondition(ForStmt n, A arg) {
		if (n.getCompare() != null) {
			n.getCompare().accept(this, arg);
		}
		printer.print("; ");
	}

	protected void visitForStmtHeaderUpdate(ForStmt n, A arg) {
		if (n.getUpdate() != null) {
			for (Iterator<Expression> i = n.getUpdate().iterator(); i.hasNext();) {
				Expression e = i.next();
				e.accept(this, arg);
				if (i.hasNext()) {
					printer.print(", ");
				}
			}
		}
	}

	
}
