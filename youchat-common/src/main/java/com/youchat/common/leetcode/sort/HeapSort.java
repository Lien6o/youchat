package com.youchat.common.leetcode.sort;

import java.util.Arrays;

/**
 * @author: Lien6o
 * @description: 给出一个整数数组，堆化操作就是把它变成一个最小堆数组。
 * 对于堆数组A，A[0]是堆的根，并对于每个A[i]，A [i * 2 + 1]是A[i]的左儿子并且A[i * 2 + 2]是A[i]的右儿子。
 * 说明 什么是堆？
 * 堆是一种数据结构，它通常有三种方法：push， pop 和 top。
 * 其中，“push”添加新的元素进入堆，“pop”删除堆中最小/最大元素，“top”返回堆中最小/最大元素。 什么是堆化？
 * 把一个无序整数数组变成一个堆数组。
 * <p>
 * 如果是最小堆，每个元素A[i]，我们将得到A[i * 2 + 1] >= A[i]和A[i * 2 + 2] >= A[i] 如果有很多种堆化的结果？
 * <p>
 * 返回其中任何一个。 样例 给出 [3,2,1,4,5]，返回[1,2,3,4,5] 或者任何一个合法的堆数组
 * @date: 2020/3/24 3:07 下午
 * @version: v1.0
 */
public class HeapSort {

    public static void main(String[] args) {
        int[] a = {3, 2, 1, 4, 5};
        heapify(a);
        System.out.println(Arrays.toString(a));
    }

    private static void siftdown(int[] array, int k) {
        while (k < array.length) {
            int smallest = k;
            if ((k * 2 + 1) < array.length && array[k * 2 + 1] < array[smallest]) {
                smallest = (k * 2 + 1);
            }
            if ((k * 2 + 2) < array.length && array[k * 2 + 2] < array[smallest]) {
                smallest = (k * 2 + 2);
            }
            if (smallest == k) {
                break;
            }
            int temp = array[smallest];
            array[smallest] = array[k];
            array[k] = temp;

            k = smallest;
        }
    }

    public static void heapify(int[] array) {
        for (int i = array.length / 2; i >= 0; i--) {
            siftdown(array, i);
        }
    }
}
