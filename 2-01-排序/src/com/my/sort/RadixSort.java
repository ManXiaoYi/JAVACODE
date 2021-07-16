package com.my.sort;

/**
 * 基数排序
 * 适用于整数排序
 *
 * 依次对 个位数、十位数、百位数、千位数、万位数...进行排序（从低位到高位）
 */
public class RadixSort extends Sort<Integer> {

	@Override
	protected void sort() {
		// 获取最值
		int max = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		
		// 个位数: array[i] / 1 % 10 = 3
		// 十位数：array[i] / 10 % 10 = 9
		// 百位数：array[i] / 100 % 10 = 5
		// 千位数：array[i] / 1000 % 10 = ...
		
		// 循环排序
		for (int divider = 1; divider <= max; divider *= 10) {
			countingSort(divider);
		}
	}

	// 计数排序 - 对 0~9 进行排序
	protected void countingSort(int divider) {
		// 创建元素的次数数组 - 以元素为索引，出现次数为值
		int[] counts = new int[10];
		// 统计每个整数出现的次数
		for (int i = 0; i < array.length; i++) { 
			counts[array[i] / divider % 10]++;
		} 

		// 累加次数
		for (int i = 1; i < counts.length; i++) {
			counts[i] += counts[i - 1];
		}

		// 从后往前遍历元素，将它放到有序数组中的合适位置
		int[] newArray = new int[array.length];
		for (int i = array.length - 1; i >= 0; i--) {
			newArray[--counts[array[i] / divider % 10]] = array[i];
		}

		// 将有序数组赋值到array
		for (int i = 0; i < newArray.length; i++) {
			array[i] = newArray[i];
		}
	}
}
