package com.my.sort;

public class CountingSort extends Sort<Integer> {

	@Override
	protected void sort() {
		// 获取最大值
		int max = 0;
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		
		// 创建元素的次数数组 - 以元素为索引，出现次数为值
		int[] counts = new int[max + 1];
		for (int i = 0; i < array.length; i++) {
			counts[array[i]]++;
		}
		
		// 按照顺序赋值
		int index = 0;
		for (int i = 0; i < counts.length; i++) {
			while (counts[i]-- > 0) {
				array[index++] = i;
			}
		}
		
	}

}
