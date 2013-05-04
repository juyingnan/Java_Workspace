package date;
import java.util.Random;

public class second {
			String second;

			public second() {
				second = "36";

			}

			 public String get(){
		        	return second;
		        }


			String get_second_betweenAB(int a, int b) {
				int temp;
				Random rand = new Random();
				temp = rand.nextInt(b - a) + a; // 水机生成a b之间的一个整数
				return ((Integer) temp).toString();

			}

			String get_second_biggerA(int a) {
				int temp;
				Random rand = new Random();
				temp = rand.nextInt(1000) + a; // 水机生成a b之间的一个整数
				return ((Integer) temp).toString();
			}

			String get_second_belowA(int a) {
				int temp;
				Random rand = new Random();
				temp = a - rand.nextInt(1000);
				 
				return ((Integer) temp).toString();
			}

			void set_second(int x) {
				second = ((Integer) x).toString();
			}
       
}