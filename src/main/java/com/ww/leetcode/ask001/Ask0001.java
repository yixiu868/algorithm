package com.ww.leetcode.ask001;

import com.ww.commons.In;

import java.util.*;

/**
 * @author xiaohua
 * @date 2020-12-19 7:35
 */
public class Ask0001 {

    public static int[] towSum(int[] nums, int target) {
        // key为期望值，value为索引集合
        Map<String, List<Integer>> map = new HashMap<>();
        int expectValue = 0;
        for (int i = 0; i < nums.length; i++) {
            expectValue = target - nums[i];
            if (map.containsKey(String.valueOf(expectValue))) {
                map.get(String.valueOf(expectValue)).add(i);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                map.put(String.valueOf(expectValue), list);
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(String.valueOf(nums[i]))) {
                List<Integer> list = map.get(String.valueOf(nums[i]));
                for (Integer index : list) {
                    if (i != index) {
                        return new int[]{i, index};
                    }
                }
            }
        }

        return null;
    }

    public static void main(String[] args) {
        int[] nums = {3, 3};
        int target = 6;

        int[] result = towSum(nums, target);
        for (int e : result) {
            System.out.println(e);
        }
    }
}
