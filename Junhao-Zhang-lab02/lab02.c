#include <stdio.h>
#include<stdlib.h>

int main() {
    unsigned int k;
    printf("Enter a value for k: ");
    scanf("%u", &k);

    if (k <= 0 || k > 32) {
        printf("Invalid value of k. It must be between 1 and 32.\n");
        return 1;
    }

    unsigned int x = 0;
    printf("Enter %u unsigned integers separated by spaces: ", k);

    for (unsigned int i = 0; i < k; ++i) {
        unsigned int num;
        scanf("%u", &num);

        if (num >= (1u << (32 / k))) {
            printf("Error: One of the integers is too large to fit into the given bits.\n");
            return 1;
        }

        x <<= (32 / k);  // Shift left to make room for the new integer
        x |= num;        // Bitwise OR to insert the new integer
    }

    printf("Overall value of x: %u\n", x);

    return 0;
}
