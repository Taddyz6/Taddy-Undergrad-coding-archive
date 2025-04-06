#include <stdio.h>

int main() {
    int i = 5;
    int* ip = &i;
printf("%p\n", (void*) ip); // (a)
printf("%p\n", (void*) (ip + 1)); // (b)
printf("%p\n", (void*) (ip + 2)); // (c)
printf("%p\n", (void*) (ip + 3)); // (d)
printf("%p\n", (void*) (ip + 4)); // (e)
    return 0;
}
