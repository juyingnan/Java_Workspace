int testFunc(int a, int b, int x){

	int c=0;
		
	if(a>0 && a<10 || b>0){
		c++;
	}

	if(a>0 && (a<10 || b>0)){
		c++;
	}

	return c;
	
}


