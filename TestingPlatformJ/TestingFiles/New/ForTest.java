
public class ForTest {
	
	public int test1(int a, int b){
		int c =0;
		for(int i=0; i < a || i<b && b>a; i++){
			c++;
		}		
		return c;
	}
	
	public int test2(int a, int b){
		int c =0;
		for(int i=0; i < a || i<b && b>a; i++){
			c++;
			for(int i=0; i < a || i<b && b>a; i++){
				c++;
			}	
		}		
		return c;
	}
	
	public int test3(int a, int b){
		int c =0;
		for(int i=0; i < a || i<b && b>a; i++){
			c++;
			for(int i=0; i < a || i<b && b>a; i++){
				c++;
			}	
		}	
		
		for(int i=0; i < a || i<b && b>a; i++){
			c++;
			for(int i=0; i < a || i<b && b>a; i++){
				c++;
			}	
		}	
		return c;
	}
	
	public int test4(int a, int b){
		int c =0;
		for(int i=0; i < a || i<b && b>a; i++){
			c++;
			if(a>0 && b<0){
				c++;
			} else {
				c--;
			}
		}	
		
		for(int i=0; i < a || i<b && b>a; i++){
			c++;
			for(int i=0; i < a || i<b && b>a; i++){
				c++;
			}	
		}	
		return c;
	}
	
	public int test5(boolean a,boolean b,boolean c,boolean d){
		int x=0;
		for(int i=0; (a||b||c)&&d ; i++){
			x++;
			d=false;
		}			
		return x;
	}
	
	public int test6(boolean a,boolean b,boolean c,boolean d){
		int x=0;
		
		if(a||b){
			x++;
		}
		
		for(int i=0; (a||b||c)&&d ; i++){
			x++;
			d=false;
			for(int i=0; (a||b||c)&&d ; i++){
				x++;
				d=false;
				
			}	
		}			
		return x;
	}
	
	public int test7(boolean a,boolean b,boolean c,boolean d){
		int x=0;
		
		if(a||b){
			x++;
		}
		
		for(int i=0; (a||b||c)&&d ; i++){
			x++;
			d=false;
			for(int i=0; (a||b||c)&&d ; i++){
				x++;
				d=false;
				for(int i=0; (a||b||c)&&d ; i++){
					x++;
					d=false;
					for(int i=0; (a||b||c)&&d ; i++){
						x++;
						d=false;
					}	
				}		
			}	
		}			
		return x;
	}
	public int test8(boolean a,boolean b,boolean c,boolean d,boolean d){
		int x=0;
		
		if(a||b&&c||d){
			x++;
		}
		
		for(int i=0; (a||b&&c||d)&&d ; i++){
			x++;
			d=false;
			for(int i=0; (a||b&&c||d)&&d ; i++){
				x++;
				d=false;
				for(int i=0; (a||b&&c||d)&&d ; i++){
					x++;
					d=false;
					for(int i=0; (a||b&&c||d)&&d ; i++){
						x++;
						d=false;
					}	
				}		
			}	
		}			
		return x;
	}
	
}
