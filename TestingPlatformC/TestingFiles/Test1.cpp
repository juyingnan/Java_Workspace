#include <iostream>
#include "cstdlib"
#define AAA==234
#define BBB=="ccc"
#ifdef AAAB
#else
#endif
#define swap(x,y)==x = x + y;y = x - y;x = x - y;
#define MACRO(arg1,arg2)==do { stmt1; stmt2; } while(0)

vector<DocNode> copy(vector<DocNode> nodes);
using namespace std;

int xx = 0;
int yy;
int test2(int ccc, int cc);
int test3();

long test1(int a, int b){
    long c = 0;
    if (b == 10){
        ++c;
    }
    if (a < 0 && a > -10 || (c > 1 || a < 10) && b > 10){
        c++;
    }
    if (b > 0 && b < 10){
        c++;
    }
    return c;
}


int main(){
    int xx = 0;
    cout << "ÄãºÃEclipse!" << xx << endl;
    cout << 234 << endl;
    cout << "ÄãºÃEclipse!\n" << xx << endl;
    return 0;
}


int test2(int ccc, int cc){
    while( ccc > 0 ){
        ccc--;
    }
    test3();
    ccc = test1(ccc ,cc);
    if (ccc){
    }
    do{
        ccc++;
    }while (ccc < 10);
    for( int i = 0;i < 10; i++ ){
        ccc += cc;
    }
    return ccc;
}


int test3(){
}
