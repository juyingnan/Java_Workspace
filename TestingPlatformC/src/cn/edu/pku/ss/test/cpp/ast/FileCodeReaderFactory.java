package cn.edu.pku.ss.test.cpp.ast;

import org.eclipse.cdt.core.dom.ICodeReaderFactory;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.core.parser.CodeReader;
import org.eclipse.cdt.core.parser.ICodeReaderCache;
import org.eclipse.cdt.core.parser.IScanner;
import org.eclipse.cdt.internal.core.dom.parser.EmptyCodeReaderCache;

/**
 * @author jcamelon
 */
public class FileCodeReaderFactory implements ICodeReaderFactory {

    private static FileCodeReaderFactory instance;
	private ICodeReaderCache cache = null;

    private FileCodeReaderFactory(ICodeReaderCache cache)
    {
		this.cache = cache;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.cdt.core.dom.ICodeReaderFactory#getUniqueIdentifier()
     */
    public int getUniqueIdentifier() {
        return 3;
    }

    /* (non-Javadoc)
     * @see org.eclipse.cdt.core.dom.ICodeReaderFactory#createCodeReaderForTranslationUnit(java.lang.String)
     */
    public CodeReader createCodeReaderForTranslationUnit(String path) {
		return cache.get(path);
	}

    public CodeReader createCodeReaderForTranslationUnit(ITranslationUnit tu) {
		return new CodeReader(tu.getPath().toOSString(), tu.getContents());
    }
    
	@Override
	public CodeReader createCodeReaderForInclusion(String path) {
		// TODO Auto-generated method stub
		return null;
	}

    
    /**
     *     * 
     * @return
     */
    public static FileCodeReaderFactory getInstance() {
        if( instance == null )
            instance = new FileCodeReaderFactory(new EmptyCodeReaderCache());
        return instance;
    }

	public ICodeReaderCache getCodeReaderCache() {
		return cache;
	}

}
