import java.util.Scanner;

public class Preliminary_Q3
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		class NODE
		{
			int		position		= 0;
			int		left_distance	= 0;
			boolean	object			= false;
			boolean	chest			= false;
			boolean	acc				= false;
			boolean	magic			= false;
			boolean	isOn			= false;
		}

		Scanner in = new Scanner(System.in);

		int group_Quantity = Integer.parseInt(in.next().trim());

		String[] data = new String[group_Quantity];

		for (int i = 0; i < data.length; i++)
		{
			int node_Quantity = in.nextInt();
			int line_Quantity = in.nextInt();
			int[] Lines = new int[line_Quantity];
			for (int j = 0; j < Lines.length; j++)
			{
				if (in.nextInt() == j + 1 && in.nextInt() == j + 2)
					Lines[j] = in.nextInt();
			}

			NODE[] nodes = new NODE[node_Quantity];
			for (int j = 0; j < nodes.length; j++)
			{
				nodes[j] = new NODE();
			}

			int object_Quantity = in.nextInt();
			int[] Objects = new int[object_Quantity];
			int last_object = 0;
			if (object_Quantity != 0)
			{
				for (int j = 0; j < Objects.length; j++)
				{
					Objects[j] = in.nextInt();
					nodes[Objects[j] - 1].object = true;
				}
				last_object = Objects[object_Quantity - 1];
			}

			int chest_Quantity = in.nextInt();
			int[] Chests = new int[chest_Quantity];
			int last_chest = 0;
			if (chest_Quantity != 0)
			{
				for (int j = 0; j < Chests.length; j++)
				{
					Chests[j] = in.nextInt();
					nodes[Chests[j] - 1].chest = true;
				}
				last_chest = Chests[chest_Quantity - 1];
			}

			int acc_Quantity = in.nextInt();
			int[] Acc = new int[acc_Quantity];
			if (acc_Quantity != 0)
			{
				for (int j = 0; j < Acc.length; j++)
				{
					Acc[j] = in.nextInt();
					nodes[Acc[j] - 1].acc = true;
				}
			}

			int magic_Quantity = in.nextInt();
			int[] Magics = new int[magic_Quantity];
			if (magic_Quantity != 0)
			{
				for (int j = 0; j < Magics.length; j++)
				{
					Magics[j] = in.nextInt();
					nodes[Magics[j] - 1].magic = true;
				}
			}

			int start_Node = in.nextInt();
			nodes[start_Node - 1].isOn = true;

			int speed = 30;

			for (int j = 0; j < nodes.length; j++)
			{
				nodes[j].position = j + 1;

				for (int j2 = j; j2 < Lines.length; j2++)
				{
					nodes[j].left_distance += Lines[j2];
				}
				if (last_chest > last_object)
				{
					for (int j2 = last_object - 1; j2 < Lines.length; j2++)
					{
						nodes[j].left_distance += Lines[j2];
					}
				}
			}

			// do something
			double time = 0;
			if (last_chest <= last_object)
			{
				for (int j = 0; j < last_object; j++)
				{
					if (j > 0)
					{
						time += (double) Lines[j - 1] / (double) speed;
					}
					if (nodes[j].acc)
						speed += 5;
					if (nodes[j].magic)
					{
						if (j + 1 != last_object)
						{
							speed += 5;
							time += 3;
						}
					}
				}
			}
			else
			{
				for (int j = 0; j < last_chest; j++)
				{
					if (j > 0)
					{
						time += (double) Lines[j - 1] / (double) speed;
					}
					if (nodes[j].acc)
						speed += 5;
					if (nodes[j].magic)
					{
						speed += 5;
						time += 3;
					}
				}
				time += (double) nodes[last_chest - 1].left_distance / (double) speed;
			}

			String outputString = "";

			java.text.DecimalFormat myformat = new java.text.DecimalFormat("#0.00000");
			outputString = myformat.format(time);
			int ii = i + 1;
			System.out.println("Case #" + ii + ": " + outputString);
		}
	}
}
