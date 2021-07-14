package com.my.sort.cmp;

import com.my.sort.Sort;

/*
 * 选择排序
 * 优化方案：二分搜索
 * 【交换】转为【挪动】
 * 
 * 1.备份当前元素
 * 2.二分搜索查到 插入位置
 * 3.将插入位置及以后元素往后移动
 * 4.将当前元素插入合适位置
 * 
 */
public class InsertionSort3<T extends Comparable<T>>  extends Sort<T> {

	@Override
	protected void sort() {
		// TODO Auto-generated method stub
		
		for (int begin = 1; begin < array.length; begin++) {
			insert(begin, search(begin));
		}
	}
	
	/**
	 * 将source位置的元素 插入到 dest位置
	 */
	private void insert(int source, int dest) {
		T v = array[source];
		
		for (int i = source; i > dest; i--) {
			array[i] = array[i - 1]; 
		}
		
		array[dest] = v;
	}

	/**
	 * 利用二分搜索找到 index 位置元素的待插入位置
	 * 
	 * 已经排好序数组的取件范围是 [0, index)
	 */
	private int search(int index) {
		int begin = 0;
		int end = index;
		
		while (begin < end) {
			int mid = (begin + end) >> 1;
			if (cmp(array[index], array[mid]) < 0) {
				end = mid;
			} else {
				begin = mid + 1;
			}
		}
		
		return begin;
	}
}
