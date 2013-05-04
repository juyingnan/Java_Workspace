package cn.edu.pku.ss.test.cpp.jobs.visitors;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.cdt.core.dom.ICodeReaderFactory;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage;
import org.eclipse.cdt.core.parser.CodeReader;
import org.eclipse.cdt.core.parser.DefaultLogService;
import org.eclipse.cdt.core.parser.IParserLogService;
import org.eclipse.cdt.core.parser.IScannerInfo;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTranslationUnit;
import org.eclipse.core.runtime.CoreException;

import cn.edu.pku.ss.test.cpp.Const;
import cn.edu.pku.ss.test.cpp.ast.FileCodeReaderFactory;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.jobs.IJob;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.util.Log;
import cn.edu.pku.ss.test.util.IOUtil;

public class CppParser implements IJob{

	
	public static IASTTranslationUnit parse(String file){
		File f = new File(file);
		return parse(f);
	}
	
	public static IASTTranslationUnit parseCode(String code){
		IParserLogService log = new DefaultLogService();
		
        CodeReader reader = new CodeReader(code.toCharArray());
        Map definedSymbols = new HashMap();
        String[] includePaths = new String[0];
        IScannerInfo info = new ScannerInfo(definedSymbols, includePaths);
        ICodeReaderFactory readerFactory = FileCodeReaderFactory.getInstance();
        
        IASTTranslationUnit tu;
		try {
			tu = GPPLanguage.getDefault().
        		getASTTranslationUnit(reader, info,	readerFactory, null, log);
	
			return tu;
		} catch (CoreException ex) {
			ex.printStackTrace();
		}

		return null;
	}
	
	public static IASTTranslationUnit parse(File file){
		String code = IOUtil.read(file.getPath());
		return parseCode(code);		
	}
	
	public static CPPASTTranslationUnit parseTest(File file){
		return (CPPASTTranslationUnit)parse(file);
	}
	
	@Override
	public boolean run(TestData data) {
		//读取源码
        String code = IOUtil.read(data.getSourcePath());       
        data.put(Const.SOURCE_ORGINAL, code);		
		IASTTranslationUnit tu= parseCode(code);
		if(tu==null){ 
			return false;
		}
		else{		
			//将AST结果存入data
			data.put(JobConst.AST,tu);
			return true;
		}
	}

}
