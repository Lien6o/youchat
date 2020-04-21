package com.youchat.common.leetcode.sort;

import java.util.Arrays;

/**
 * @author: Lien6o
 * @description:
 * @date: 2020/2/26 10:23 上午
 * @version: v1.0
 */
public class HighLevelSort {

    private static int[] array = {6, 35, 23, 1, 67, 20, 9, 18, 99, 0, 13, 7};
      static String rashomom ;
    public static void main(String[] args) {
        rashomom = "";
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

    public static void merge(int[] array, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        // 辅助数组的起始位置。
        int tempIndex = 0;
        // 左指针 左数组起始位置。
        int leftIndex = left;
        // 右指针 右数组起始位置。
        int rightIndex = mid + 1;
        // 把较小的数先移到新数组中
        while (leftIndex <= mid && rightIndex <= right) {
            temp[tempIndex++] = array[leftIndex] <= array[rightIndex]
                    ? array[leftIndex++]
                    : array[rightIndex++];
        }
        // 把左边剩余的数移入数组
        while (leftIndex <= mid) {
            temp[tempIndex++] = array[leftIndex++];
        }
        // 把右边边剩余的数移入数组
        while (rightIndex <= right) {
            temp[tempIndex++] = array[rightIndex++];
        }
        // 把新数组中的数覆盖nums数组
        for (int k2 = 0; k2 < temp.length; k2++) {
            array[k2 + left] = temp[k2];
        }
    }
}
