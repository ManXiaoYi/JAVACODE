package com.my.sort.cmp;

import com.my.sort.Sort;

/*
 * 冒泡排序
 */
public class BubbleSort1<E extends Comparable<E>> extends Sort<E> {

	@Override
	protected void sort() {
		
		// 3. 忽略内层循环的最大元素，重复执行
		for (int end = array.length - 1; end > 0; end--) {
			// 1. 从头比较相邻元素 - 执行完毕，最末尾就是最大元素
			for (int begin = 1; begin <= end; begin++) {
				// 2. 后一个 小于 前一个，交换位置
				if (cmp(begin, begin - 1) < 0) {
					swap(begin, begin - 1);
				}
			}
		}
		

//		// 3. 忽略内层循环的最大元素，重复执行
//		for (int end = array.length - 1; end > 0; end--) {
//			// 1. 从头比较相邻元素 - 执行完毕，最末尾就是最大元素
//			for (int begin = 1; begin <= end; begin++) {
//				// 2. 第一个 大于 第二个，交换位置
//				if (array[begin] < array[begin - 1]) {
//					int tmp = array[begin];
//					array[begin] = array[begin - 1];
//					array[begin - 1] = tmp;
//				}
//			}
//		}

	}
}
