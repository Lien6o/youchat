package com.youchat.common.leetcode;

/**
 * @author: Lien6o
 * @description:
 * @date: 2020/3/2 6:12 下午
 * @version: v1.0
 */
public class BinarySearch {
    /**
     * 折线
     */
    private static int[] polylineArray = {4, 5, 6, 1, 2, 3};

    public static int polylineSearch(int[] polylineArray, int value) {
        int low = 0;
        int high = polylineArray.length - 1;
        while (low <= high) {

            int mid = low + (high - low) >>> 1;
            if (mid == value) {
                return mid;
            } else if (polylineArray[mid - 1] < value && polylineArray[mid + 1] > value) {
                low = mid + 1;
                high = mid - 1;
            } else if (polylineArray[mid - 1] > value && polylineArray[mid + 1] > value) {
                return -1;
            } else if (polylineArray[mid - 1] < value && polylineArray[mid + 1] < value) {
                return -1;
            }
            low = mid + 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        int i = polylineSearch(polylineArray,5);
        System.out.println(i);
    }
}
