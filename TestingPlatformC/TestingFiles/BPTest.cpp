int testFunc(int a, int b, int c){
	if(a>10)
		for(int i=0; i< a ; i++)
			b+=c;
	if(b>100){
		for(int i=0; i< a ; i++){
			b+=c;
			if(b>1000){
				a +=b;
			} else
				b += a;
			a +=b;
			for(int i=0; i< a ; i++)
				b+=c;
		}
	} else a-=b;

	//while(a>100){
	//	a-=10;
	//	if(a<100){
	//		break;
	//	} else continue;
	//}

	return a+b+c;


}
