package com.my.sort;

/**
 * 二分搜索 
 * 设计范围时 [begin, end) 左闭右开更方便
 */

public class BinarySearch {
	
	/**
	 * 查找v在有序数组array中的 位置
	 */
	public static int indexOf(int[] array, int v) {
		if (array == null || array.length == 0) {
			return -1;
		}

		// 开始索引
		int begin = 0;
		// 结束索引
		int end = array.length;
		
		while (begin < end) {
			// 中间索引
			int mid = (begin + end) >> 1;

			if (v < array[mid]) {
				// v 小于 取值
				end = mid;
			} else if (v > array[mid]) {
				// v 大于 取值
				begin = mid + 1;
			} else {
				// v 等于 取值
				return mid;
			}
		}

		return -1;
	}

	
	/**
	 * 查找v在有序数组array中的 待插入位置
	 * 即 第1个 大于v 的元素位置
	 */
	public static int search(int[] array, int v) {
		if (array == null || array.length == 0) {
			return -1;
		}

		// 开始索引
		int begin = 0;
		// 结束索引
		int end = array.length;

		while (begin < end) {
			int mid = (begin + end) >> 1;
			
			if (v < array[mid]) {
				// v 小于 取值
				end = mid;
			} else {
				// v 大于等于 取值
				begin = mid + 1;
			}
		}
		
		return begin;
	}

}
