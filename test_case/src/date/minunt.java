package date;
import java.util.Random;

public class minunt {
			String minunt;

			public minunt() {
				minunt = "36";

			}
			
			 public String get(){
		        	return minunt;
		        }


			String get_minunt_betweenAB(int a, int b) {
				int temp;
				Random rand = new Random();
				temp = rand.nextInt(b - a) + a; // 水机生成a b之间的一个整数
				return ((Integer) temp).toString();

			}

			String get_minunt_biggerA(int a) {
				int temp;
				Random rand = new Random();
				temp = rand.nextInt(1000) + a; // 水机生成a b之间的一个整数
				return ((Integer) temp).toString();
			}

			String get_minunt_belowA(int a) {
				int temp;
				Random rand = new Random();
				temp = a - rand.nextInt(1000);
				 
				return ((Integer) temp).toString();
			}

			void set_minunt(int x) {
				minunt = ((Integer) x).toString();
			}
       
}