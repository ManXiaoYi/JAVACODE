package com.my.sort.cmp;

import com.my.sort.Sort;

/*
 * 选择排序
 */
public class InsertionSort1<T extends Comparable<T>>  extends Sort<T> {

	@Override
	protected void sort() {
		// TODO Auto-generated method stub
		
		// 1. 从第2个 到 第最后一个 循环
		for (int begin = 1; begin < array.length; begin++) {
			// 取当前索引
			int cur = begin;
			
			// 2. 索引 大于0 且 索引的元素小于前一个元素
			while (cur > 0 && cmp(cur, cur - 1) < 0) {
				// 3. 交换元素
				swap(cur, cur - 1);
				// 往前对比
				cur--;
			}
		}
		
//		// 1. 从第2个 到 第最后一个 循环
//		for (int begin = 1; begin < array.length; begin++) {
//			// 2. 从开始 往前对比
//			for (int i = begin; i > 0; i--) {
//				// 3. 比前一个小，就交换元素，否则跳出循环
//				if (cmp(i, i - 1) < 0) {
//					swap(i, i - 1);
//				} else {
//					break;
//				}
//			}
//		}
		
	}

}
