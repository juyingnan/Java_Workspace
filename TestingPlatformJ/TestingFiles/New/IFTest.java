
public class IFTest {

	public int test1(int a, int b){
		int c =0;
		if(a>0 && a<10 || b<0 && b>-10){			
			c++;
		} else {
			c--;
		}
		return c;
	}
	
	public int test2(int a, int b){
		int c =0;
		if(a>0 || a<10 && b<0 || b>-10){			
			c++;
		} else {
			c--;
		}
		return c;
	}
	
	public int test3(int a, int b){
		int c =0;
		if(a>0 && a<10 || b<0 && b>-10){			
			c++;
		} 
		return c;
	}
	
	public int test4(int a, int b){
		int c =0;
		if(a>0 && a<10 || b<0 && b>-10){			
			if(a>0 && a<10 || b<0 && b>-10){			
				c++;
			} else {
				c--;
			}
		} else {
			if(a>0 && a<10 || b<0 && b>-10){			
				c++;
			} else {
				c--;
			}
		}
		return c;
	}
	
	public int test5(int a, int b){
		int c =0;
		if(a>0 && a<10 || b<0 && b>-10){			
			if(a>0 && a<10 || b<0 && b>-10){			
				c++;
			} else {
				c--;
			}
		} else {
			if(a>0 && a<10 || b<0 && b>-10){			
				c++;
			} else {
				c--;
			}
			
			if(a>0 && a<10 || b<0 && b>-10){			
				if(a>0 && a<10 || b<0 && b>-10){			
					c++;
				} else {
					c--;
				}
			} else {
				if(a>0 && a<10 || b<0 && b>-10){			
					c++;
				} else {
					c--;
				}
			}
		}
		
		if(a>0 && a<10 || b<0 && b>-10){			
			if(a>0 && a<10 || b<0 && b>-10){			
				c++;
			} else {
				c--;
			}
		} else {
			if(a>0 && a<10 || b<0 && b>-10){			
				c++;
			} else {
				c--;
			}
		}
		return c;
	}
	
	
	public int test6(boolean a,boolean b,boolean c,boolean d){
		int x=0;
		if((a||b||c)&&d){
			x++;
		}
			
		return x;
	}
	
	public int test7(boolean a,boolean b,boolean c,boolean d){
		int x=0;
		
		if(a||b||c ||d){
			x++;
		}
		
		return x;
	}
	
	public int test8(boolean a,boolean b,boolean c,boolean d){
		int x=0;
		
		if((a || b) && (c || d) ){
			x++;
		}
		return x;
	}
	
	public int test9(boolean a,boolean b,boolean c,boolean d){
		int x=0;
		if((a||b||c)&&d){
			x++;
		}
		
		if(a||b||c ||d){
			x++;
		}
		
		if((a || b) && (c || d) ){
			x++;
		}
		return x;
	}
	
}
