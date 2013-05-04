public class BPTest{                             
	                     
	public int test0(int a, int b, int c){        //3
		int result = a;                          //4 
		if (a > b                            //5
				&& b < c					//6
					|| a < c )                //7  
		{                                        //8
			result = b - 1;                      //9
			result = b - 2;                      //10
			result = b - 3;                      //11
			result--;                            //12
		}                                        //13
		else                                     //14
		{                                        //15 
			result = a + c;                      //16
			result = a - b;                      //17
		}                                        //18
		return result;                           //19
	}
	
	public int test(int a, int b, int c){        //3
		int result = a;                          //4 
		if (a > b                            //6
				&& b < c)                        //7  
		{                                        //8
			result = b - 1;						//9
			result = b - 2;                      //10
			result = b - 3;                      //11
			result--;							//12
		}                                        //13
		else                                     //14
		{                                        //15 
			result = a + c;                      //16
			result = a - b;                      //17
		}                                        //18
		return result;                           //19
	}                                            
	

		
	private int test1(boolean a,boolean b,boolean c,boolean d){
		int x =0;
		if((a||b||c)&&d){
			x++;
		}
		
		for(;(a||b||c)&&d;){
			d=!d;
		}
		return x;
	}
}