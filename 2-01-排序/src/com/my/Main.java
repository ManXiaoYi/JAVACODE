package com.my;

import java.util.Arrays;
import com.my.sort.Sort;
import com.my.sort.cmp.BubbleSort1;
import com.my.sort.cmp.BubbleSort2;
import com.my.sort.cmp.BubbleSort3;
import com.my.sort.cmp.HeapSort;
import com.my.sort.cmp.SelectionSort;
import com.my.tools.Asserts;
import com.my.tools.Integers;

@SuppressWarnings({"rawtypes", "unchecked"})

public class Main {
	public static void main(String[] args) {

		Integer[] array = Integers.random(10000, 1, 20000);

		testSorts(array, 
				new BubbleSort1(), 
				new BubbleSort2(), 
				new BubbleSort3(), 
				new SelectionSort(), 
				new HeapSort());
	}

	static void testSorts(Integer[] array, Sort... sorts) {
		for (Sort sort : sorts) {
			Integer[] newArray = Integers.copy(array);

			sort.sort(newArray);

			Asserts.test(Integers.isAscOrder(newArray));
		}

		Arrays.sort(sorts);

		for (Sort sort : sorts) {
			System.out.println(sort);
		}
	}
}
