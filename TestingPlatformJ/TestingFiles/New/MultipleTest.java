public class MultipleTest{
	public int test1(int a, int b, int c){
		int result=a;
		for(int k=0;k<a;k++)//0
			if(result<b){//1
				for(int i=0;i<c;i++)//2
					result++;//3
			}
		return result;

	}
	
	public int test2(int a, int b, int c){
		int result=a;
		for(int k=0;k<a;k++)//0
			if(result<b && a>10){//1
				for(int i=0;i<c;i++)//2
					result++;//3
			}
		
		if(a>0 || b>0 || c>0){}
		return result;

	}
}