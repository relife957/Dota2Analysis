package com.wangyi;

import javax.xml.transform.Result;

/**
 * 树状数组
 *
 * @author wangyi
 * @create 2018-12-16 4:21 PM
 **/
public class BIT {
    private int[] original ;         //原始数组元素从零开始

    private int[] BITree = new int[original.length+1] ;            //BIT数组元素从1开始

    public BIT(int[] original) {
        this.original = original;
        System.arraycopy(original,0,BITree,1,original.length);

        /*
         * 解释一下这个循环,我们Bitree每一个元素(除树中最高的哪一个)都会被别的元素所使用,(使用的意思就是此处的值是被使用元素之和)
         * 而每一个元素只能被一个元素所使用,所以我们从第一个元素开始,每次更新上一层的元素,所谓更新,就是使上层元素加上此处的值,
         */
        for (int i = 1 ; i < BITree.length;i++){                   //从1开始初始化BIT
            int j = i + lowbit(i) ;
            if(j < BITree.length){
                BITree[j] += BITree[i] ;                           //在这里更新第i个元素的下一个使用它的元素,例如,i = 2 , lowbit(2) = 2,然后就更新第i+2=4个元素
            }
        }
    }

    /**
     * 求i二进制数结尾连续的0的个数+1
     * @param i
     * @return
     */
    private int lowbit(int i){
        return i & -i ;
    }

    /**
     * 更新i处的值
     * @param i index ,原始数组中的下标,从0开始的
     * @param increment 增量,
     */
    public void update(int i , int increment){
        i += 1;                                 //BITree是从1开始的
        while (i < this.BITree.length){         //循环修改所有直接(1)间接使用i处元素的元素
            this.BITree[i] += increment;
            i = i + lowbit(i) ;
        }
    }

    /**
     * 求1-index+1区间的1和
     * @param index 原始数组下标,使用需加一
     * @return 和
     */
    public long prefixSum(int index){
        index += 1 ;
        long res = 0 ;
        while(index > 0 ){
            /*
            抛开树型数组,最简单的求和就是原始数组中从1累加到index+1.我们使用树型数组,就是在1-index+1这些元素中,
            有一些元素,他的值"寄托"在别的元素中,我们的目的是找到index+1下,那些最大的被寄托的元素,也就是说分多段求和,
             而每一段的和已经被求出,这个分段的和寄托在这一段中下标最大处的元素,
            有个理论,index+1二进制数中1的个数就是分段数,怎么寻找这些分段中的被寄托的点,就是技术活了,
            上面我们求出了每个元素的使用它的元素,使用的是i+lowbit(i),而为什么使用它的元素是i+lowbit(i)
            却没有说,实际上,lowbit的值是一个数二进制最低位1的的位置,比如lowbit(6)=2,而6=0110,最低位1在倒数第二位,二进制中就是2,再比如说
            lowbit(8)=8,8=1000,最低位1在倒数第四位,二进制中就是8.
            之后我们使用i+lowbit(i),这个式子代表什么呢,很简单,就是使最低位进一位,进一位是因为我们的树型数组就是这样定义的,
             */
            res += this.BITree[index] ;
            index -= index - lowbit(index) ;
        }
        return res ;
    }

    /**
     * 得到start-end的和
     * @param start 开始
     * @param end 结束
     * @return 和
     */
    public long rangeSum(int start,int end){
        return prefixSum(end) - prefixSum(start) ;
    }

}
