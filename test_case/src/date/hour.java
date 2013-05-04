package date;
import java.util.Random;

public class hour {
			String hour;

			public hour() {
				hour = "21";

			}
			 public String get(){
		        	return hour;
		        }

			String get_hour_betweenAB(int a, int b) {
				int temp;
				Random rand = new Random();
				temp = rand.nextInt(b - a) + a; // 水机生成a b之间的一个整数
				return ((Integer) temp).toString();

			}

			String get_hour_biggerA(int a) {
				int temp;
				Random rand = new Random();
				temp = rand.nextInt(1000) + a; // 水机生成a b之间的一个整数
				return ((Integer) temp).toString();
			}

			String get_hour_belowA(int a) {
				int temp;
				Random rand = new Random();
				temp = a - rand.nextInt(1000);
			 
				return ((Integer) temp).toString();
			}

			void set_hour(int x) {
				hour = ((Integer) x).toString();
			}
       
}