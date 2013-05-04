package cn.edu.pku.ss.test.java.jobs;

import java.io.FileWriter;

import cn.edu.pku.ss.test.java.jobs.visitors.ASTVisitor;
//import cn.edu.pku.ss.test.java.jobs.visitors.AstXmlVisitor;
import cn.edu.pku.ss.test.jobs.JobFlow;
import org.jdom.output.XMLOutputter;

public class ASTWorker extends JobFlow{

	public ASTWorker(){
		this.add(new JavaParser());
		this.add(new ASTVisitor());
//		this.add(new AstXmlVisitor());
//       try{
//       	XMLOutputter xmlOutPutter = new XMLOutputter();
//       	FileWriter writer = new FileWriter("C:/syntaxTreeFile.xml");
//       	xmlOutPutter.output(ASTVisitor.syntaxTreeDocument, writer);
//       	System.out.println("2222222222222222222@@@@@@@@@@@@@@@@@@@");
//       }
//       catch(java.io.IOException e){
//       	e.printStackTrace();
//       }
	}
}
