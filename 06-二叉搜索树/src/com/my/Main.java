package com.my;

import java.util.Comparator;

import com.my.file.Files;
import com.my.printer.BinaryTrees;
import com.my.BinarySearchTree.Visitor;

public class Main {
	// 构造器
	@SuppressWarnings("unused")
	private static class PersonComparator implements Comparator<Person> {
		public int compare(Person e1, Person e2) {
			return e1.getAge() - e2.getAge();
		}
	}

	static void test1() {
		Integer data[] = new Integer[] { 7, 4, 9, 2, 5, 8, 11, 3, 12, 1 };

		BinarySearchTree<Integer> bst = new BinarySearchTree<>();

		for (int i = 0; i < data.length; i++) {
			bst.add(data[i]);
		}

		BinaryTrees.println(bst);
	}

	static void test2() {
		Integer data[] = new Integer[] { 7, 4, 9, 2, 5, 8, 11, 3, 12, 1 };

		BinarySearchTree<Person> bst = new BinarySearchTree<>();

		for (int i = 0; i < data.length; i++) {
			bst.add(new Person(data[i]));
		}

		BinaryTrees.println(bst);
	}
	
	static void test3() {
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (int i = 0; i < 40; i++) {
			bst.add((int)(Math.random() * 100));
		}
		
		String str = BinaryTrees.printString(bst);
		str += "\n\n";
		Files.writeToFile("/Users/manxiaoyi/Desktop/tree.txt", str, false);
		
		System.out.println(bst.height());
		System.out.println(bst.isComplete());
	}
	
	// 前序
	static void test4() {
		Integer data[] = new Integer[] { 7, 4, 2, 1, 3, 5, 9, 8, 11, 10, 12 };

		BinarySearchTree<Integer> bst = new BinarySearchTree<>();

		for (int i = 0; i < data.length; i++) {
			bst.add(data[i]);
		}

		BinaryTrees.println(bst);
		
		// 前序遍历
//		bst.preorderTraversal();
		// 中序遍历
//		bst.inorderTraverval();
		// 后序遍历
//		bst.postorderTraverval();
		// 层序遍历
//		bst.levelorderTraversal();
		
	}
	
	static void test5() {
		Integer data[] = new Integer[] {
				7, 4, 9, 2, 1
		};
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (int i = 0; i < data.length; i++) {
			bst.add(data[i]);
		}
		BinaryTrees.println(bst);
		
		bst.preorder(new Visitor<Integer>() {
			@Override
			public boolean visit(Integer element) {
				System.out.print(element + " -> ");
				return element == 2;
			}
		});
		System.out.println();

		bst.inorder(new Visitor<Integer>() {
			@Override
			public boolean visit(Integer element) {
				System.out.print(element + " -> ");
				return element == 4;
			}
		});
		System.out.println();

		bst.postorder(new Visitor<Integer>() {
			@Override
			public boolean visit(Integer element) {
				System.out.print(element + " -> ");
				return element == 4;
			}
		});
		System.out.println();

		bst.levelorder(new Visitor<Integer>() {
			@Override
			public boolean visit(Integer element) {
				System.out.print(element + " -> ");
				return element == 9;
			}
		});
		System.out.println();
		
		System.out.println(bst.height());
		
	}
	
	// 是否完全二叉树
	static void test6() {
		Integer data[] = new Integer[] { 7, 4, 9, 2, 5};

		BinarySearchTree<Integer> bst = new BinarySearchTree<>();

		for (int i = 0; i < data.length; i++) {
			bst.add(data[i]);
		}

		BinaryTrees.println(bst);
		System.out.println(bst.isComplete());
		
	}
	
	// 删除
	static void test7() {
		Integer data[] = new Integer[] { 7, 4, 9, 2, 5, 8, 11, 3, 12, 1};

		BinarySearchTree<Integer> bst = new BinarySearchTree<>();

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
//		test1();
//		test2();
//		test3();
		// 遍历
//		test4();
//		test5();	
//		test6();
//		test7();
		
		
//		// 构造方法
//		BinarySearchTree<Person> bst2 = new BinarySearchTree<>(new PersonComparator());
//		bst2.add(new Person(12));
//		bst2.add(new Person(18));

	}

}
