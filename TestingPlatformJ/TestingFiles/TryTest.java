
public class TryTest {
	
	public void test(int a, int b){
		
		try{
			a+=b;
			b=a-b;
			a=a-b;
		} catch(Exception e){
			e.printStackTrace();
			try{
				a+=b;
				b=a-b;
				a=a-b;
			} catch(Exception e){
				e.printStackTrace();
			} finally{
				a+=b;
				b=a-b;
				a=a-b;
			}
			
		}
		
		try{
			a+=b;
			b=a-b;
			a=a-b;
			
			if(a>0){
				a--;
			} else if(a<0){
				a++;
			} else {
				b--;
			}
			try{
				a+=b;
				b=a-b;
				a=a-b;
			} catch(Exception e){
				e.printStackTrace();
			}
			
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			a+=b;
			b=a-b;
			a=a-b;
		}
		
	}
}

