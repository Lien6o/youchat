package com.youchat.common.leetcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MergerOverlapped {

    public static void main(String[] args) {
        int[][] intervals = {{1, 6}, {5, 6}, {2, 3}};


        List<int[]> ints = Arrays.asList(intervals);
        ints.sort(Comparator.comparing(x -> x[0]));
        ints.forEach(x -> {
            String s = Arrays.toString(x);
            System.out.println("s = " + s);
        });
    }
}
