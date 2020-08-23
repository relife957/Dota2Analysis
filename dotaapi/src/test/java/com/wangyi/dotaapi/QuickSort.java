package com.wangyi.dotaapi;

import org.junit.Ignore;

import java.util.Arrays;

/**
 * @author wangyi
 * @create 2019-02-28 10:28 PM
 **/
@Ignore
public class QuickSort {
    static int repartition(int[] nums,int start ,int end){
        int base = nums[start] ;
        while(start < end){
            while(start  < end && nums[end] >= base ){
                end -- ;
            }
            nums[start] = nums[end] ;

            while(start < end && nums[start] <= base){
                start ++ ;
            }
            nums[end] = nums[start];
        }

        nums[start] = base ;
        return start ;

    }


    static void sort(int[] nums,int start ,int end ){
        if(start >= end){
            return ;
        }
        int mid = repartition(nums,start,end);

        sort(nums,0,mid-1);
        sort(nums,mid+1,end);
    }


    public static void main(String[] args) {
        int[] nums = {4,51,3,10,7,22,43,1,23};
        sort(nums,0,nums.length-1);
        System.out.println(Arrays.toString(nums));
    }
}
