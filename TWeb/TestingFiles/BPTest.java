public class BPTest{
	
	public int test(int a, int b, int c){
		int result=a;
		for(int k=0;k<a;k++)//0
			if(result<b){//1
				for(int i=0;i<c;i++)//2
					result++;//3
			}
		return result;

	}
}