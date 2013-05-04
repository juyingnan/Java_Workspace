package bank.ss.pku.edu.cn;

public class BankService
{

	public boolean creditCardAuthorization(String creditCardNo)
	{
		if (creditCardNo.startsWith("A") && creditCardNo.length() == 6)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
