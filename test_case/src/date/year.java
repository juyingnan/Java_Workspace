package date;
import java.util.Random;

// ����
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
			temp = rand.nextInt(b - a) + a; // ˮ������a b֮���һ������
			return ((Integer) temp).toString();

		}

		String get_year_biggerA(int a) {
			int temp;
			Random rand = new Random();
			temp = rand.nextInt(1000) + a; // ˮ������a b֮���һ������
			return ((Integer) temp).toString();
		}

		String get_year_belowA(int a) {
			int temp;
			Random rand = new Random();
			temp = a - rand.nextInt(1000);
			if (temp < 0)
				temp = 0; // ���С���� ������������
			return ((Integer) temp).toString();
		}

		void set_year(int x) {
			year = ((Integer) x).toString();
		}
}