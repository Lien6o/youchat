package com.youchat.common.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpiralOrder {

    public static  List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> list = new ArrayList<>();
        //当二维数组是空或任何一个维度是0，直接返回
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return list;
        }
        //m是矩阵的行数
        int m = matrix.length;
        //n是矩阵的列数
        int n = matrix[0].length;
        //大循环，从外向内逐层遍历矩阵
        /*
         *
         * ----------->
         * ^          |
         * |          |
         * |          |
         * <--------- v
         *
         */
        for (int c = 0; c < (Math.min(m, n) + 1) / 2; c++) {
            //从左到右遍历“上边”
            for (int j = c; j < n - c; j++) {
                // 行不变 c 不变，j 变
                list.add(matrix[c][j]);
            }
            //从上到下遍历“右边” int j = c + 1 : 去掉拐角
            for (int j = c + 1; j < m - c; j++) {
                list.add(matrix[j][(n - 1) - c]);
            }
            //从右到左遍历“下边”
            for (int j = c + 1; j < n - c; j++) {
                list.add(matrix[(m - 1) - c][(n - 1) - j]);
            }
            //从下到上遍历“左边”
            for (int j = c + 1; j < m - 1 - c; j++) {
                list.add(matrix[(m - 1) - j][c]);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3, 4, 5},
                {14, 15, 16, 17, 6},
                {13, 20, 19, 18, 7},
                {12, 11, 10, 9, 8}
        };
        int[][] matrix2 = {
                {1, 2, 3, 4, 5},
                {16, 17,18, 19, 6},
                {15, 24, 25, 20, 7},
                {14, 23, 22, 21, 8},
                {13, 12, 11, 10, 9}
        };
        List<Integer> resultList1 = spiralOrder(matrix);
        System.out.println(Arrays.toString(resultList1.toArray()));
        List<Integer> resultList2 = spiralOrder(matrix2);
        System.out.println(Arrays.toString(resultList2.toArray()));
    }
}
