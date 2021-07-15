package com.my.sort;

import java.text.DecimalFormat;

import com.my.Student;
import com.my.sort.cmp.ShellSort;

@SuppressWarnings("rawtypes")

/**
 * Sort<E extends Comparable<E>>
 * E 必须是 可比较的
 *
 */
public abstract class Sort<T extends Comparable<T>> implements Comparable<Sort<T>> {
	// 数组
	protected T[] array;
	// 比较次数
	private int cmpCount;
	// 交换次数
	private int swapCount;
	// 消耗时间
	private long time;
	// 数字格式化 - 保留两位小数
	private DecimalFormat fmt = new DecimalFormat("#.00");

	// 排序
	public void sort(T[] array) {
		if (array == null || array.length < 2) {
			return;
		}

		this.array = array;
		
		long begin = System.currentTimeMillis();
		sort();
		time = System.currentTimeMillis() - begin;
	}

	// 排序
	protected abstract void sort();

	// 比较规则
	@Override
	public int compareTo(Sort<T> o) {
		int result = (int)(time - o.time);
		if (result != 0) return result;
		
		result = cmpCount - o.cmpCount;
		if (result != 0) return result;
		
		return swapCount - o.swapCount;
	}
	
	/**
	 * 比较前后两值 
	 * 返回值等于0，代表 array[i1] = array[i2] 
	 * 返回值大于0，代表 array[i1] > array[i2]
	 * 返回值小于0，代表 array[i1] < array[i2]
	 */
	protected int cmp(int i1, int i2) {
		cmpCount++;
		return array[i1].compareTo(array[i2]);
	}
	

	protected int cmp(T v1, T v2) {
		cmpCount++;
		return v1.compareTo(v2);
	}

	/**
	 * 交换前后两值位置
	 */
	protected void swap(int i1, int i2) {
		swapCount++;
		T tmp = array[i1];
		array[i1] = array[i2];
		array[i2] = tmp;
	}

	// 打印相关
	// --------------------------------------------------------------------------
	@Override
	public String toString() { 
		String timeStr = "耗时：" + (time / 1000.0) + "s(" + time + "ms)";
		String compareCountStr = "比较：" + numberString(cmpCount);
		String swapCountStr = "交换：" + numberString(swapCount);
		String stableStr = "稳定性：" + isStable();
		return "【" + getClass().getSimpleName() + "】\n" 
				+ stableStr + " \t"
				+ timeStr + " \t"
				+ compareCountStr + "\t "
				+ swapCountStr + "\n"
				+ "------------------------------------------------------------------";

	}
	
	// 数字单位
	private String numberString(int number) {
		if (number < 10000) return "" + number;
		
		if (number < 100000000) return fmt.format(number / 10000.0) + "万";
		return fmt.format(number / 100000000.0) + "亿";
	}
	
	private boolean isStable() {
//		if (this instanceof RadixSort) return true;
		if (this instanceof CountingSort) return true;
		if (this instanceof ShellSort) return false;
//		if (this instanceof SelectionSort) return false;
		Student[] students = new Student[20];
		for (int i = 0; i < students.length; i++) {
			students[i] = new Student(i * 10, 10);
		}
		sort((T[]) students);
		for (int i = 1; i < students.length; i++) {
			int score = students[i].score;
			int prevScore = students[i - 1].score;
			if (score != prevScore + 10) return false;
		}
		return true;
	}
}
