package com.my;

import java.util.Arrays;

import com.my.sort.BinarySearch;
import com.my.sort.Sort;
import com.my.sort.cmp.BubbleSort1;
import com.my.sort.cmp.BubbleSort2;
import com.my.sort.cmp.BubbleSort3;
import com.my.sort.cmp.HeapSort;
import com.my.sort.cmp.InsertionSort1;
import com.my.sort.cmp.InsertionSort2;
import com.my.sort.cmp.InsertionSort3;
import com.my.sort.cmp.SelectionSort;
import com.my.tools.Asserts;
import com.my.tools.Integers;

@SuppressWarnings({"rawtypes", "unchecked"})

public class Main {
	public static void main(String[] args) {
		test1();
		test2();
		
		Integer[] array = Integers.random(10000, 1, 20000);

		testSorts(array, 
//				new BubbleSort1(), 
//				new BubbleSort2(), 
				new BubbleSort3(), 
				new SelectionSort(), 
				new HeapSort(),
				new InsertionSort1<>(),
				new InsertionSort2<>(),
				new InsertionSort3<>()
				);
	}

	static void testSorts(Integer[] array, Sort... sorts) {
		for (Sort sort : sorts) {
			Integer[] newArray = Integers.copy(array);

//			Integers.println(newArray);
			sort.sort(newArray);
//			Integers.println(newArray);

			Asserts.test(Integers.isAscOrder(newArray));
		}

		Arrays.sort(sorts);

		for (Sort sort : sorts) {
			System.out.println(sort);
		}
	}
	
	static void test1() {
		int[] array1 = {2, 4, 6, 8, 10};
		Asserts.test(BinarySearch.indexOf(array1, 2) == 0);
		Asserts.test(BinarySearch.indexOf(array1, 10) == 4);
		Asserts.test(BinarySearch.indexOf(array1, 6) == 2);
		Asserts.test(BinarySearch.indexOf(array1, 123) == -1);
	}
	
	static void test2() {
		int[] array1 = {2, 4, 8, 8, 8, 12, 14};
		Asserts.test(BinarySearch.search(array1, 5) == 2);
		Asserts.test(BinarySearch.search(array1, 1) == 0);
		Asserts.test(BinarySearch.search(array1, 15) == 7);
		Asserts.test(BinarySearch.search(array1, 8) == 5);
	}
	
}
