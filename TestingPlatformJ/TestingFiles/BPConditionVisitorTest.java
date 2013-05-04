public class BPConditionVisitorTest {

	public int testMethod1(int a, int b){
		int c =0;
		if(a>0 && a<10 || b<0 && b>-10){			
			c++;
		} else {
			c--;
		}
		return c;
	}
	
	public void testMethod(int a, int b) {

		for (int i = 0; i < a && a > b; i++) {
			b++;

			while (b < a) {
				b++;
				if (a > 0 && b < -10) {
					break;
				} else if (b < 0) {
					continue;
				}

				if (b < 100) {
					b++;
					do {
						a--;
					} while (a > 0);
				}

				break;

			}
		}

		if (a > b) {
			return a - b;
		} else
			return b - a;
	}

}
