public class BPTest{                             
	                                             
	public int test(int a, int b, int c){        //3
		int result = a;                          //4 
		if (a > b                            //5
				&& b < c)                        //6  
		{                                        //7
			result = b - 1;                      //8
			result = b - 2;                      //9
			result = b - 3;                      //10
			result--;                            //11
		}                                        //12
		else                                     //13
		{                                        //14 
			result = a + c;                      //15
			result = a - b;                      //16
		}                                        //17
		return result;                           //18
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