package com.my.sort.cmp;

import com.my.sort.Sort;

@SuppressWarnings({"unused", "unchecked"})

/*
 * 归并排序
 * 1. 不断得将当前序列平均分割成2个子序列
 * 	  直到不能再分割（序列中只剩1个元素)
 * 2. 不断的将2个子序列合并成一个有序序列
 * 	  直到最终只剩下1个有序序列
 */
public class MergeSort<T extends Comparable<T>>  extends Sort<T> {
	// 存储左边一般的元素
	private T[] leftArray;
	
	@Override
	protected void sort() {
		leftArray = (T[]) new Comparable[array.length >> 1];
		
		sort(0, array.length);
	}

	/**
	 * 对 [begin, end) 范围的数据进行归并排序
	 */
	private void sort(int begin, int end) {
		// 至少要有2个元素
		if (end - begin < 2) {
			return;
		}
		
		int mid = (begin + end) >> 1;
		// 分割左边
		sort(begin, mid);
		// 分割右边
		sort(mid, end);
		
		// 归并
		merge(begin, mid, end);
	}
	
	/**
	 * 将 [begin, mid) 和 [mid, end) 范围的序列合并成一个有序序列
	 */
	private void merge(int begin, int mid, int end) {
		int li = 0, le = mid - begin;
		int ri = mid, re = end;
		int ai = begin;
		
		// 备份左边数据
		for (int i = li; i < le; i++) {
			leftArray[i] = array[begin + i];
		}
		
		// 如果左边还没结束
		while (li < le) {
			// 右边没结束 且 右边小于左边
			if (ri < re && cmp(array[ri], leftArray[li]) < 0) {
				// 右边移动
				array[ai++] = array[ri++];
			} else {
				// 左边移动
				array[ai++] = leftArray[li++];
			}
		} 
	}
	
}


