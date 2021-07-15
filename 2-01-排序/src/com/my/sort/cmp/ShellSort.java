package com.my.sort.cmp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.my.sort.Sort;

public class ShellSort<T extends Comparable<T>> extends Sort<T> {

	@Override
	protected void sort() {
		// 获得步长序列
		List<Integer> stepSequence = sedgewickStepSequence();

		// 执行希尔排序
		for (Integer step : stepSequence) {
			sort(step);
		}
	}

	/**
	 * 分成Step列进行排序
	 */
	private void sort(int step) {
		// col: 第几列，column的简称
		for (int col = 0; col < step; col++) {
			
			// 插入排序
			// 1. 从第2个 到 第最后一个 循环
			// col、col+step、col+2*step、col+3*step
			for (int begin = col + step; begin < array.length; begin += step) {
				// 取当前索引
				int cur = begin;

				// 2. 索引 大于0 且 索引的元素小于前一个元素
				while (cur > col && cmp(cur, cur - step) < 0) {
					// 3. 交换元素
					swap(cur, cur - step);
					// 往前对比
					cur -= step;
				}
			}
			
		}

	}

	/**
	 * 创建步长序列
	 */
	private List<Integer> shellStepSequence() {
		List<Integer> stepSqeuence = new ArrayList<>();

		int step = array.length;

		// 不断除以2，添加到步长序列中
		while ((step >>= 1) > 0) {
			stepSqeuence.add(step);
		}

		return stepSqeuence;
	}

	/**
	 * 最好的步长序列
	 */
	private List<Integer> sedgewickStepSequence() {
		List<Integer> stepSequence = new LinkedList<>();
		int k = 0, step = 0;
		while (true) {
			if (k % 2 == 0) {
				int pow = (int) Math.pow(2, k >> 1);
				step = 1 + 9 * (pow * pow - pow);
			} else {
				int pow1 = (int) Math.pow(2, (k - 1) >> 1);
				int pow2 = (int) Math.pow(2, (k + 1) >> 1);
				step = 1 + 8 * pow1 * pow2 - 6 * pow2;
			}
			if (step >= array.length)
				break;
			stepSequence.add(0, step);
			k++;
		}
		return stepSequence;
	}
}
