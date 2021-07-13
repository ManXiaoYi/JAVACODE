package com.my.sort.cmp;

import com.my.sort.Sort;

/*
 * 冒泡排序 
 * 优化方案一：
 * 如果序列已经完全有序，可以提前终止冒泡排序
 */
public class BubbleSort2<E extends Comparable<E>> extends Sort<E> {

	@Override
	protected void sort() {

		for (int end = array.length - 1; end > 0; end--) {
			// 序列完全有序
			boolean sorted = true;
			
			for (int begin = 1; begin <= end; begin++) {
				if (cmp(begin, begin - 1) < 0) {
					swap(begin, begin - 1);
					
					// 序列不完全有序
					sorted = false;
				}
			}
			
			// 序列完全有序，终止
			if (sorted) {
				break;
			}
		}
	}

}
