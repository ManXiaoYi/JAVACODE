package com.my.sort.cmp;

import com.my.sort.Sort;

/*
 * 选择排序
 * 优化方案：
 * 【交换】转为【挪动】
 * 
 * 1.备份当前元素
 * 2.前面的 比当前元素 大的往后移动
 * 3.将当前元素插入合适位置
 */
public class InsertionSort2<T extends Comparable<T>>  extends Sort<T> {

	@Override
	protected void sort() {
		// TODO Auto-generated method stub
		
		// 1. 从第2个 到 第最后一个 循环
		for (int begin = 1; begin < array.length; begin++) {
			// 取当前索引
			int cur = begin;
			
			// 1. 备份当前元素
			T v = array[cur];
			
			// 2. 索引 大于0 且 索引的元素小于前一个元素
			while (cur > 0 && cmp(v, array[cur - 1]) < 0) {
				// 3. 往后移动一个位置
				array[cur] = array[cur - 1];
				
				// 往前对比
				cur--;
			}
			
			// 插入当前元素
			array[cur] = v;
		}
		
	}
}
