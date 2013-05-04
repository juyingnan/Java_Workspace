import java.io.Console;


public class InsertionSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
int[] number ={2,4,1,5,6};

insertionSort(number, 5);

System.out.println(number[0]+""+number[1]+""+number[2]+""+number[3]+""+number[4]+"");
		
	}
	
	static void insertionSort(int numbers[], int array_size)
	{
	  int i, j, index;

	  for (i=1; i < array_size; i++)
	  {
	    index = numbers[i];
	    j = i;
	    
	    while ((j > 0) && 
	    		(numbers[j-1] > index))
	    {
	      numbers[j] = numbers[j-1];
	      j = j - 1;
	    }
	    numbers[j] = index;
	  }
	}


}
