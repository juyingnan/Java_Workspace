
public class BPVisitorTest {
	
	public void testMethod(int a, int b){
		
		for(int i=0; i< a; i++){
			b++;
			
			while(b<a){
				b++;
				if(a>0 && b<-10){
					break;
				} else if(b<0) {
					continue;
				}
				
				if(b<100){
					b++;
					do{
						a--;
					} while(a>0);
				}
				
				break;
				
			}
		}	
		
		switch(a) {
		case 1:
			b++;
			a++;
			break;
		case 2:
			b++;
			a++;
			break;
		case 3:
		case 4:
			b++;
			a++;
			break;
		default:
			b++;				
			
		}
		
		
		if(a>b){
			return a-b;
		}
		else return b-a;
			
	}
	
	class InnerClass{
		public void innerMethod(int a, int b){
			if(a>b){
				return a-b;
			}
			else return b-a;
		}
		
		
		class InnerInnerClass{
			public void innerInnerMethod(int a, int b){
				if(a>b){
					System.out.println("a>b");
					return a-b;
				}
				else{
					System.out.println("b>a");
					return b-a;
				}
			}
		}
	}
}

class OutterClassTest{
	public void OutterClassMethod(int a, int b){
		if(a>b){
			return a-b;
		}
		else return b-a;
			
	}
	
}