package com.youchat.common.leetcode.sort;

import java.util.Arrays;

public class QuickSort {

    private static int[] array = {6, 35, 23, 1, 67, 20, 9, 18, 99, 0, 13, 7};

    public static void main(String[] args) {

        int[] arr = Arrays.copyOf(array, array.length);

        int partition = partition(arr, 0, arr.length - 1, 1);


//        for (int i = 0; i < 10; i++) {
//
//            int[] arr = Arrays.copyOf(array, array.length);
//            int partition = partition(arr, 0, arr.length - 1, i);
//            System.out.println("partition=" + partition + " i=" + i + " value=" + array[i] + " max=" + (array.length - partition));
//        }
//        sortTraditional(array,0,array.length-1);
//        System.out.println("Arrays.toString(array) = " + Arrays.toString(array));

    }

    /**
     * l 和 r 是数组中待排序的区间范围，ind 是本轮 partition 操作后基准值的位置。
     * 当找到基准值的位置以后，对于右侧从 ind + 1 到 r 位置，我们就正常调用递归函数。
     * 然后，我们通过将 r 设置为 ind - 1，直接利用本层 while 循环逻辑，继续对左侧进行 partition 等相关排序操作。
     */
    private static void sort(int[] array, int l, int r) {
        while (l < r) {
            // 进行一轮 partition 操作
            // 获得基准值的位置
            int i = partition(array, l, r, l);
            // 右侧正常调用递归函数
            sort(array, i + 1, r);
            // 用本层处理左侧的排序
            r = i - 1;
        }
    }

    /**
     * 普通递归
     * @param array
     * @param l
     * @param r
     */
    private static void sortTraditional(int[] array, int l, int r) {
        if (l < r) {
            int i = partition(array, l, r, l);
            sortTraditional(array, i + 1, r); // 递归调用
            sortTraditional(array, l, i -1);
        }
    }

    private static int partition(int[] arr, int startIndex, int endIndex, int pivotIndex) {
        System.out.println("Arrays.toString(arr) = " + Arrays.toString(arr));
        // 取第一个位置的元素作为基准元素
        int pivot = arr[pivotIndex];

        int left = startIndex;
        int right = endIndex;
        while (left != right) {
            //控制right指针比较并左移
            while (left < right && arr[right] > pivot) {
                right--;
            }
            //控制right指针比较并右移 注意符号 <=
            while (left < right && arr[left] <= pivot) {
                left++;
            }
            //交换left和right指向的元素
            if (left < right) {
                int p = arr[left];
                arr[left] = arr[right];
                arr[right] = p;
            }
            System.out.println("Arrays.toString(arr) = " + Arrays.toString(arr)+ "left = " + left+ " right = " + right);
        }
        //pivot和指针重合点交换
        System.out.println("left = " + left);
        System.out.println("pivotIndex = " + pivotIndex);
        int p = arr[left];
        arr[left] = arr[pivotIndex];
        arr[pivotIndex] = p;
        System.out.println("1 Arrays.toString(arr) = " + Arrays.toString(arr));
        return left;
    }

    private static int getIndex(int[] arr, int low, int high) {
        // 基准数据
        int tmp = arr[low];
        while (low < high) {
            // 当队尾的元素大于等于基准数据时,向前挪动high指针
            while (low < high && arr[high] >= tmp) {
                high--;
            }
            // 如果队尾元素小于tmp了,需要将其赋值给low
            arr[low] = arr[high];
            // 当队首元素小于等于tmp时,向前挪动low指针
            while (low < high && arr[low] <= tmp) {
                low++;
            }
            // 当队首元素大于tmp时,需要将其赋值给high
            arr[high] = arr[low];

        }
        // 跳出循环时low和high相等,此时的low或high就是tmp的正确索引位置
        // 由原理部分可以很清楚的知道low位置的值并不是tmp,所以需要将tmp赋值给arr[low]
        arr[low] = tmp;
        return low; // 返回tmp的正确位置
    }
}
