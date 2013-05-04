package date;
import java.util.Random;

public class day {
			String day;

			public day() {
				day = "20";

			}
			 public String get(){
		        	return day;
		        }

			String get_day_betweenAB(int a, int b) {
				int temp;
				Random rand = new Random();
				temp = rand.nextInt(b - a) + a; // 水机生成a b之间的一个整数
				return ((Integer) temp).toString();

			}

			String get_day_biggerA(int a) {
				int temp;
				Random rand = new Random();
				temp = rand.nextInt(1000) + a; // 水机生成a b之间的一个整数
				return ((Integer) temp).toString();
			}

			String get_day_belowA(int a) {
				int temp;
				Random rand = new Random();
				temp = a - rand.nextInt(1000);
				 
				return ((Integer) temp).toString();
			}

			void set_day(int x) {
				day = ((Integer) x).toString();
			}
       
}