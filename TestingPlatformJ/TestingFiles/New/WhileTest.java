public class WhileTest{
	
	public void test1(boolean a, boolean b)	{		
		int c = 0;
		while(a&&b){
			c++;
		}	
		
	}
	
	public void test2(boolean a, boolean b)	{		
		int c = 0;
		while(a && b || m()){
			c++;
			
			if(a||b){
				c++;
				for(int i=0; i<c || b; i++){
					b = !m();
					c--;
				}
			}
			
			for(int i=0; i<c || b; i++){
				b = !m();
				c--;
			}
		}	
		
	}
	
	public boolean m(){
		return true;
	}
	
	public void test3(boolean a, boolean b, boolean c, boolean d){
		while((a||b)&&(c||d)){
			break;
		}
	}
	
}