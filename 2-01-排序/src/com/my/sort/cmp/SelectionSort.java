package com.my.sort.cmp;

import com.my.sort.Sort;

public class SelectionSort<E extends Comparable<E>> extends Sort<E> {

	@Override
	protected void sort() {
		
		// 4. 忽略内层循环的最大元素，重复执行
		for (int end = array.length - 1; end > 0; end--) {
			// 最大元素索引
			int max = 0;

			// 1. 从头比较相邻元素 - 执行完毕，最末尾就是最大元素
			for (int begin = 1; begin <= end; begin++) {

				// 2. 最大元素 小于 当前元素，更新最大元素索引
				if (cmp(max, begin) < 0) {
					max = begin;
				}
			}

			// 3. 交换最大元素 到 最末尾的位置
			swap(max, end);
		}


//		// 4. 忽略内层循环的最大元素，重复执行
//		for (int end = array.length - 1; end > 0; end--) {
//			// 最大元素索引
//			int max = 0;
//
//			// 1. 从头比较相邻元素 - 执行完毕，最末尾就是最大元素
//			for (int begin = 1; begin <= end; begin++) {
//
//				// 2. 最大元素 小于 当前元素，更新最大元素索引
//				if (array[max] < array[begin]) {
//					max = begin;
//				}
//			}
//
//			// 3. 交换最大元素 到 最末尾的位置
//			int tmp = array[max];
//			array[max] = array[end];
//			array[end] = tmp;
//		}

	}

}
