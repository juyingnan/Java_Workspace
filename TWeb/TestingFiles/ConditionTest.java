public class ConditionTest{

	public int test(int a, int b){

		int c=0;
		
		if(a>0 && a<10 || b>0){
			c++;
		}
		
		for(int i=0; i< a; i++){
			c++;
		}
		
		while(b>0){
			b--;
		}		
		
		do{
			c++;
		}while(c<100);
		
		return c;
	
	}

}

//public class ConditionTest{ public int test3(int a, int b){int c=0;if(a>0 && a<10 || b>0){c++;}if(a>0 && a<10 || b>0){c++;}return c;}}