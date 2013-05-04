package date;
import java.util.Random;

// 年类
	public class year {
		String year;

		public year() {
			year = "2001";
            
		}
        public String get(){
        	return year;
        }
		String get_year_betweenAB(int a, int b) {
			int temp;
			Random rand = new Random();
			temp = rand.nextInt(b - a) + a; // 水机生成a b之间的一个整数
			return ((Integer) temp).toString();

		}

		String get_year_biggerA(int a) {
			int temp;
			Random rand = new Random();
			temp = rand.nextInt(1000) + a; // 水机生成a b之间的一个整数
			return ((Integer) temp).toString();
		}

		String get_year_belowA(int a) {
			int temp;
			Random rand = new Random();
			temp = a - rand.nextInt(1000);
			if (temp < 0)
				temp = 0; // 如果小于零 则让它等于零
			return ((Integer) temp).toString();
		}

		void set_year(int x) {
			year = ((Integer) x).toString();
		}
}