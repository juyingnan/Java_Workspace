
public class TestObject {
	//Field
	public int result = 0;
	//Method
	public int getResult(){
		return result;
	}
	//Self
	public boolean equals(Object o){	
		return new Integer(result).equals(o);		
	}
	//Invoke method
	public int add(int a, int b){
		result = a-b;
		return a+b;
	}
}
