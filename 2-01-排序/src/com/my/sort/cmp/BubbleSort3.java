package com.my.sort.cmp;

import com.my.sort.Sort;

/*
 * 冒泡排序
 * 优化方案二：
 * 如果序列尾部已经局部有序，可以记录最后一次交换的位置，减少比较次数
 */
public class BubbleSort3<E extends Comparable<E>> extends Sort<E> {

	@Override
	protected void sort() {
		
		for (int end = array.length - 1; end > 0; end--) {
			// 最后一次交换位置
			int sortedIndex = 1;
		
			for (int begin = 1; begin <= end; begin++) {
				if (cmp(begin, begin - 1) < 0) {
					swap(begin, begin - 1);
					
					// 最后一次交换位置
					sortedIndex = begin;
				}
			}
			
			// 最后一次交换位置 之前的需要循环
			end = sortedIndex;
		}
		
	}

}
