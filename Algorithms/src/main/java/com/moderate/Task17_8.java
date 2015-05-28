package com.moderate;

/**
 * You are given an array of integers (both positive and negative). Find the contiguous
 * sequence with the largest sum. Return the sum
 */
public class Task17_8 {

	public static int largestContiguosSum(int[] nums) {
		int prev;
		int max = prev = nums[0];
		for (int i = 1; i < nums.length; i ++) {
			prev = Math.max(prev + nums[i], nums[i]);
			max = Math.max(max, prev);
		}
		return max;
	}
	
	public static void main(String[] args) {
		//int[] nums = {3, 6, -2, 1};		
		//int[] nums = {3, 6, -2, 3};
		//int[] nums = {6, 3, -8, 4, 7, -2};		
		
		int[] nums = {2, -8, 3, -2, 4, -10};
		
		
		System.out.println(largestContiguosSum(nums));
	}
}
