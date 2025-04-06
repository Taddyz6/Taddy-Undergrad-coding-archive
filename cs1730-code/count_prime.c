 #include <stdio.h>

int main()
{
    int n = 10;
    int count = 0;
    if (n>=3) {
        count = 1;
        for (int i=3; i<n; i+=2){
            int isPrime = 1; // 1 is false
            for (int j=2; j<i/2+1; j++){
                if(i%j == 0){
                    isPrime = 0; // 0 is false
                    break;
                } //if
            } //for
            if(isPrime) {
                count++;
            } // if
        } // for
    } // if
printf("Prime Count = %d\n", count);
}
