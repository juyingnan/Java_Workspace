package date;
import java.util.Random;

public class mounth {
			String mounth;

			public mounth() {
				mounth = "3";

			}
			
			 public String get(){
		        	return mounth;
		        }


			String get_mounth_betweenAB(int a, int b) {
				int temp;
				Random rand = new Random();
				temp = rand.nextInt(b - a) + a; // ˮ������a b֮���һ������
				return ((Integer) temp).toString();

			}

			String get_mounth_biggerA(int a) {
				int temp;
				Random rand = new Random();
				temp = rand.nextInt(1000) + a; // ˮ������a b֮���һ������
				return ((Integer) temp).toString();
			}

			String get_mounth_belowA(int a) {
				int temp;
				Random rand = new Random();
				temp = a - rand.nextInt(1000);
				 
				return ((Integer) temp).toString();
			}

			void set_mounth(int x) {
				mounth = ((Integer) x).toString();
			}

			 
}