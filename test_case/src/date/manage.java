package date;

 

public class manage {
	
	
	   year y=new year();
	   mounth m =new mounth();
	   day d=new day();
	   hour h=new hour();
	   minunt mnt= new minunt();
	   second s= new second();
	
	   
	   public String Creat_right_case(){//该方法生成 正确格式的时间
		   
		   String temp=null;
		   
		   temp=y.get_year_betweenAB(1990, 2012)+"_"+m.get_mounth_betweenAB(1,12)+"_"+d.get_day_betweenAB(1, 31)+" "+h.get_hour_betweenAB(0, 23)+":"+m.get_mounth_betweenAB(0, 59)+":"+s.get_second_betweenAB(0,59);
		   
		   return temp;
		    
	   }
	    
	   
	   public String[]  TestingCases_Scope_Boundry(int max, int min){
		   
		        String  str[ ] = new String[2];  
		        y.set_year(max);
		        m.set_mounth(12);
		        d.set_day(31);
		        h.set_hour(0);
		        mnt.set_minunt(0);
		        s.set_second(0);
		       
		        str[0]="上边界值:    "+y.year+"_"+m.mounth+"_"+d.day+" "+h.hour+":"+mnt.minunt+":"+s.second;
		        y.set_year(min);
		        m.set_mounth(1);
		        d.set_day(1);
		        h.set_hour(0);
		        mnt.set_minunt(0);
		        s.set_second(0);
		        str[1]="下边界值:    "+y.year+"_"+m.mounth+"_"+d.day+" "+h.hour+":"+mnt.minunt+":"+s.second;
		    	
		        return str;
	   }
	   
	   public String[]  wrong(int min, int max){
		   
		     String  str[] = new String[4];
		     
		     str[0]="低于下边界的错误时间：  "+ y.get_year_belowA(min)+"_"+m.get_mounth_belowA(0)+"_"+d.get_day_belowA(0)+" "+h.get_hour_belowA(0)+":"+mnt.get_minunt_belowA(0)+":"+s.get_second_belowA(0);
		     str[1]="低于下边界的错误时间：  "+ y.get_year_belowA(min)+"_"+m.get_mounth_belowA(0)+"_"+d.get_day_belowA(0)+" "+h.get_hour_belowA(0)+":"+mnt.get_minunt_belowA(0)+":"+s.get_second_belowA(0);

		     str[2]="高于上边界的错误时间：  "+ y.get_year_belowA(max)+"_"+m.get_mounth_biggerA(12)+"_"+d.get_day_biggerA(31)+" "+h.get_hour_biggerA(23)+":"+mnt.get_minunt_biggerA(60)+":"+s.get_second_biggerA(60);
		     str[3]="高于上边界的错误时间：  "+ y.get_year_belowA(max)+"_"+m.get_mounth_biggerA(12)+"_"+d.get_day_biggerA(31)+" "+h.get_hour_biggerA(23)+":"+mnt.get_minunt_biggerA(60)+":"+s.get_second_biggerA(60);
		     
		     return str;
	   }
	   
	   
	   
	   
	   
	   
	   
	   
	public static void main(String [] args){
		
		// 假如用户要求输入一个 1990年到2012年之间的一个精确到秒的时间
		System.out.println("假如用户要求输入一个 1990年到2012年之间的一个精确到秒的时间" );
		manage m=new manage();
		
		//首先给出两个正确格式的时间： 
		for(int i=0 ;i<2;i++)   System.out.println(m.Creat_right_case());
		
		//得到边界值：
		String str[]=m.TestingCases_Scope_Boundry(2012, 1990);
		for(int i=0 ;i<2;i++)   System.out.println(str[i]);
		
		//得到正确时间范围之外的用例
	    String	str2[]=m.wrong(1990, 2012);
		for(int i=0 ;i<4;i++)   System.out.println(str2[i]);
		 
	}

}
