package com.ww.leetcode.ask001;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaohua
 * @date 2020-12-19 8:31
 */
public class Aask001Excellent01 {

    public static int[] towSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                return new int[] { map.get(nums[i]), i };
            }

            map.put(target - nums[i], i);
        }

        return null;
    }
}
