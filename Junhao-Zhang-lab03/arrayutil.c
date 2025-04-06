#include "arrayutil.h"
#include <stdio.h>
#include <stdlib.h>

int getMax(int arr[], int length) {
    int max = arr[0];
    for (int i = 1; i < length; i++) {
        if (arr[i] > max) {
            max = arr[i];
        }
    }
    return max;
}

double getMean(int arr[], int length) {
    int sum = 0;
    for (int i = 0; i < length; i++) {
        sum += arr[i];
    }
    return (double)sum / length;
}

double getVar(int arr[], int length) {
    double mean = getMean(arr, length);
    double variance = 0.0;
    for (int i = 0; i < length; i++) {
        variance += (arr[i] - mean) * (arr[i] - mean);
    }
    return variance / length;
}

void sortArray(int arr[], int length) {
    for (int i = 0; i < length - 1; i++) {
        for (int j = 0; j < length - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }
}
