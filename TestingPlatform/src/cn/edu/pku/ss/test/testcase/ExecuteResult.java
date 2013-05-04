package cn.edu.pku.ss.test.testcase;


public class ExecuteResult {
	private boolean isSuccess;
	private Object Return;
	private Object monitor;
	private Exception exception;
	
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public void setReturn(Object Return) {
		this.Return = Return;
	}
	public Object getReturn() {
		return Return;
	}
	public void setMonitor(Object monitor) {
		this.monitor = monitor;
	}
	public Object getMonitor() {
		return monitor;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	public Exception getException() {
		return exception;
	}
	
}
