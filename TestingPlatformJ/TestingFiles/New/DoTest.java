public class DoTest{
	
	public void test1(boolean a, boolean b)	{		
		int c = 0;
		do{
			c++;
		}while(a&&b);		
	}
	
	public void test2(boolean a, boolean b, boolean c, boolean d)	{		
		int c = 0;
		do{
			c++;
			do{
				c++;
			}while(a||b||c||d);	
			
			for(int i=0; (a||b||c)&&d; i++){
				d=false;
			}
			
		}while(a&&b);		
	}
}