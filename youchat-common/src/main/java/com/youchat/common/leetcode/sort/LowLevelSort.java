package com.youchat.common.leetcode.sort;

import java.util.Arrays;

/**
 * @author: Lien6o
 * @description:
 * @date: 2020/2/25 6:29 下午
 * @version: v1.0
 */
public class LowLevelSort {

    private static int[] array = {6, 35, 23, 1, 67, 20, 9, 18};

    public static int[] insertSort(int[] array) {
        int length = array.length;
        // 次数控制
        for (int i = 0; i < length; i++) {

            // 和前面的对比 假设前面都是排好的
            for (int j = i; j > 0; j--) {
                if (array[j] < array[j - 1]) {
                    // swap
                    int temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                } else {
                    break;
                }
            }
        }
        return array;
    }



    public static int[] selectSort(int[] array) {
        int length = array.length;
        for (int j = 0; j < length; j++) {
            int min = array[j];
            int curr = j;
            // 找最小
            for (int i = j; i < length; i++) {
                if (min > array[i] ) {
                    min = array[i];
                    curr = i;
                }
            }
            // swap 交换
            int temp = array[j];
            array[j] = min;
            array[curr] = temp;
        }
        return array;
    }


    public static int[] bubbleSort(int[] array) {
        int length = array.length;
        // 提前退出冒泡循环的标志位
        boolean flag = false;
        // 外层控制次数
        for (int j = 0; j < length; j++) {
            // 内层是一次所有的两两互换  大的数字一点一点的向后移动 像不像水泡从水底冒出。。。哈哈哈哈
            for (int i = 0; i < length - 1; i++) {
                if (array[i] > array[i + 1]) {
                    int temp = array[i + 1];
                    array[i + 1] = array[i];
                    array[i] = temp;
                    flag = true; // 表示有数据交换
                }
            }
            if (!flag) break; // 没有数据交换，提前退出
        }
        return array;
    }




    public static void main(String[] args) {
       System.out.println("bubbleSort "+Arrays.toString(insertSort(array)));
        //  System.out.println("selectSort "+ Arrays.toString(insertSort(array)));
    }



}
