package com.youchat.common.sort;

import java.util.Arrays;

/**
 * @author: Lien6o
 * @description:
 * @date: 2020/2/26 10:23 上午
 * @version: v1.0
 */
public class HighLevelSort {

    private static int[] array = {6, 35, 23, 1, 67, 20, 9, 18, 99, 0, 13, 7};

    public static void main(String[] args) {
        System.out.println(Arrays.toString(mergeSort(array, 0, array.length - 1)));
    }

    public static int[] mergeSort(int[] array, int low, int high) {

        int mid = low + (high - low)/2;
        if (low < high) {
            // 左边
            mergeSort(array, low, mid);
            // 右边
            mergeSort(array, mid + 1, high);
            // 左右归并
            merge(array, low, mid, high);
        }
        return array;
    }

    public static void merge(int[] array, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;// 左指针
        int j = mid + 1;// 右指针
        int k = 0;
        // 把较小的数先移到新数组中
        while (i <= mid && j <= high) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }
        // 把左边剩余的数移入数组
        while (i <= mid) {
            temp[k++] = array[i++];
        }
        // 把右边边剩余的数移入数组
        while (j <= high) {
            temp[k++] = array[j++];
        }
        // 把新数组中的数覆盖nums数组
        for (int k2 = 0; k2 < temp.length; k2++) {
            array[k2 + low] = temp[k2];
        }
    }
}
