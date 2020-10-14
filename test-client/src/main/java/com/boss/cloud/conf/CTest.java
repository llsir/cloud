package com.boss.cloud.conf;

import java.util.*;

/**
 * @author: lpb
 * @create: 2020-09-15 11:50
 */
public class CTest {

    public int lengthOfLongestSubstring(String s) {
        if(s == null || s.length() == 0){
            return 0;
        }
        int res = 0;
        Map<Character, Integer> map = new HashMap<>();
        for(int end = 0, start = 0; end < s.length();end ++ ){
            char c = s.charAt(end);
            if(map.containsKey(c)){
                start = Math.max(map.get(c) + 1, start);
            }
            res = Math.max(res , end - start + 1);
            map.put(c,end);
        }
        return res;
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int mid = (m + n) / 2;
        int[] cur = new int[2];
        int start = 0;
        int s1 = 0, s2 = 0;
        while(start < mid+1 && s1 < m && s2 < n){
            if(nums1[s1] < nums2[s2]){
                if(start == mid-1){
                    cur[0] = nums1[s1];
                }
                if(start == mid){
                    cur[1] = nums1[s1];
                }
                start++;
                s1++;
            }else{
                if(start == mid-1){
                    cur[0] = nums2[s2];
                }
                if(start == mid){
                    cur[1] = nums2[s2];
                }
                start++;
                s2++;
            }
        }
        if(start < mid + 1){
            while(s1 < m){
                if(start == mid-1){
                    cur[0] = nums1[s1];
                }
                if(start == mid){
                    cur[1] = nums1[s1];
                    break;
                }
                start++;
                s1++;
            }
            while(s2 < n){
                if(start == mid-1){
                    cur[0] = nums2[s2];
                }
                if(start == mid){
                    cur[1] = nums2[s2];
                    break;
                }
                start++;
                s2++;
            }
        }
        if((m+n)%2 == 0){
            return ((double) (cur[0]+cur[1]))/2.0d;
        }else {
            return cur[1];
        }

    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        List<Integer> combine = new ArrayList<Integer>();
        dfs(candidates, target, ans, combine, 0);
        return ans;
    }

    private void dfs(int[] candidates, int target, List<List<Integer>> ans, List<Integer> combine, int idx) {
        if (idx == candidates.length) {
            return;
        }
        if (target == 0) {
            ans.add(new ArrayList<Integer>(combine));
            return;
        }
        // 直接跳过
        dfs(candidates, target, ans, combine, idx + 1);
        // 选择当前数
        if (target - candidates[idx] >= 0) {
            combine.add(candidates[idx]);
            dfs(candidates, target - candidates[idx], ans, combine, idx);
            combine.remove(combine.size() - 1);
        }
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<Integer> combine = new ArrayList<>();
        HashSet<List<Integer>> res = new HashSet<>();
        dps(0, target, candidates, combine, res);
        return new ArrayList<>(res);
    }

    private void dps(int begin, int target, int[] candidates, List<Integer> combine, Set<List<Integer>> res){
        if(begin == candidates.length){
            if(target == 0){
                res.add(new ArrayList<Integer>(combine));
            }
            return;
        }
        if(target == 0){
            res.add(new ArrayList<Integer>(combine));
            return;
        }
        for(int i = begin;i < candidates.length;i++){
            if(target - candidates[i] < 0){
                break;
            }
            combine.add(candidates[i]);
//            System.out.println(combine.toString() + "，添加了：" + candidates[i]);
            dps(i+1, target-candidates[i], candidates, combine , res);
            combine.remove(combine.size()-1);
//            System.out.println(combine.toString() + "，移除了：" + candidates[i]);
        }
    }


    public static void main(String[] args) {
        CTest s = new CTest();
        int[] candidates = new int[]{2,5,2,1,2};
        List<List<Integer>> lists = s.combinationSum2(candidates, 5);
        System.out.println(lists);
//        int[] candidates = new int[]{2,3,6,7};
//        List<List<Integer>> lists = s.combinationSum(candidates, 7);
//        System.out.println(lists.toString());
//        int[] nums1 = new int[]{2};
//        int[] nums2 = new int[]{1,3,4};
//        double medianSortedArrays = s.findMedianSortedArrays(nums1, nums2);
//        System.out.println(medianSortedArrays);
//        System.out.println(s.lengthOfLongestSubstring("abba"));
    }
}
