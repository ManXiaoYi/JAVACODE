package com.my;

import com.my.file.Files;
import com.my.printer.BinaryTrees;
import com.my.tree.BST;

public class Main {

	// 删除
	static void test() {
		Integer data[] = new Integer[] { 7, 4, 9, 2, 5, 8, 11, 3, 12, 1 };
		
		BST<Integer> bst = new BST<>();

		for (int i = 0; i < data.length; i++) {
			bst.add(data[i]);
		}

		String str = BinaryTrees.printString(bst);
		str += "\n\n";
		Files.writeToFile("/Users/manxiaoyi/Desktop/tree.txt", str, false);

		bst.remove(1);
		bst.remove(9);
		bst.remove(12);

		String str2 = BinaryTrees.printString(bst);
		str2 += "\n\n";
		Files.writeToFile("/Users/manxiaoyi/Desktop/tree.txt", str2, true);

	}

	public static void main(String[] args) {
		test();

	}

}
