package com.my;

import com.my.file.Files;
import com.my.printer.BinaryTrees;
import com.my.tree.AVLTree;
import com.my.tree.BST;
import com.my.tree.RBTree;

public class Main {

	// 删除
	static void test() {
		Integer data[] = new Integer[] { 85, 19, 69, 3, 7, 99, 95, 2, 1, 70, 44, 58, 11, 21, 14, 93, 57, 4, 56 };
		
		AVLTree<Integer> avl = new AVLTree<>();

		for (int i = 0; i < data.length; i++) {
			avl.add(data[i]);
		
			System.out.println("【" + data[i] + "】");
			BinaryTrees.println(avl);
			System.out.println("------------------------------");
		}

		for (int i = 0; i < data.length; i++) {
			avl.remove(data[i]);
		
			System.out.println("【" + data[i] + "】");
			BinaryTrees.println(avl);
			System.out.println("------------------------------");
		}
		
	}
	
	static void test1() {
		Integer data[] = new Integer[] { 55, 87, 56, 74, 96, 22, 62, 20, 70, 68, 90, 50 };
		
		RBTree<Integer> rb = new RBTree<>();

		for (int i = 0; i < data.length; i++) {
			rb.add(data[i]);
		
			System.out.println("【" + data[i] + "】");
			BinaryTrees.println(rb);
			System.out.println("------------------------------");
		}
	}
	
	static void test2() {
		Integer data[] = new Integer[] { 55, 87, 56, 74, 96, 22, 62, 20, 70, 68, 90, 50 };
		
		RBTree<Integer> rb = new RBTree<>();

		for (int i = 0; i < data.length; i++) {
			rb.add(data[i]);
		} 
		
		BinaryTrees.println(rb);
		
		for (int i = 0; i < data.length; i++) {
			rb.remove(data[i]);
		
			System.out.println("【" + data[i] + "】");
			BinaryTrees.println(rb);
			System.out.println("------------------------------");
		}
	}

	public static void main(String[] args) {
		test2();

	}

}
