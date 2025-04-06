#include <stdio.h>
#include "arrayutil.h"

int main() {
    int size;

    printf("Please enter array size: ");
    scanf("%d", &size);

    int arr[size];

    printf("Please enter %d integers: ", size);
    for (int i = 0; i < size; i++) {
        scanf("%d", &arr[i]);
    }

    int max = getMax(arr, size);
    double mean = getMean(arr, size);
    double variance = getVar(arr, size);

    printf("Max = %d\n", max);
    printf("Mean = %.6lf\n", mean);
    printf("Variance = %.6lf\n", variance);

    sortArray(arr, size);

    printf("Sorted Array = [");
    for (int i = 0; i < size; i++) {
        printf("%d", arr[i]);
        if (i < size - 1) {
            printf(", ");
        }
    }
    printf("]\n");

    return 0;
}
