import cn.edu.pku.ss.test.util.IDValuePairArray;
import cn.edu.pku.ss.test.inject.monitors.Monitor;

public class $Name$ extends Monitor {

	private IDValuePairArray<Integer, Boolean> pathRecorder = new IDValuePairArray<Integer, Boolean>();
	
	static {
		System.loadLibrary("$LibName$");
	}
	
	public boolean visit(int id, boolean value){		
		pathRecorder.add(id,value);		
		return true;
	}
	
	@Override
	public Object getResult() {
		return pathRecorder;
	}
	
	$Methods$

}
