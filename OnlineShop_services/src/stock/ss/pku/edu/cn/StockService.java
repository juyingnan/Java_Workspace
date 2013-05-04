package stock.ss.pku.edu.cn;

public class StockService
{
	public boolean stockInquiry(String tosterType, int quantity)
	{
		int[] q = {3,2,1};

		if (tosterType.startsWith("A"))
		{
			if (quantity <= q[0])
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if (tosterType.startsWith("B"))
		{
			if (quantity <= q[1])
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			if (quantity <= q[2])
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
}
