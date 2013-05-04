package cn.edu.pku.ss.test.cpp.ast;

import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTNodeLocation;

public class ASTHelper {
	
	public class Position{
		public int X =0;
		public int Y =0;
	}
	
	public static int getPosition(IASTNode n){
		//Position p = new Position();
		IASTNodeLocation[] l = n.getNodeLocations();
		if(l.length>0){	
			return l[0].getNodeOffset();
		} else return 0;
		
	}
}
