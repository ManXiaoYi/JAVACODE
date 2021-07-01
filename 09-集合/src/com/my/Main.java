package com.my;

import com.my.Times.Task;
import com.my.file.FileInfo;
import com.my.file.Files;
import com.my.set.ListSet;
import com.my.set.Set;
import com.my.set.TreeSet;
import com.my.set.Set.Visitor;

public class Main {
	public static void main(String[] args) {
		test3();

	}

	static void test() {
		Set<Integer> listSet = new ListSet<>();
		listSet.add(10);
		listSet.add(11);
		listSet.add(11);
		listSet.add(12);
		listSet.add(10);

		listSet.traversal(new Visitor<Integer>() {

			@Override
			public boolean visit(Integer element) {
				System.out.println(element);
				return false;
			}
		});
	}

	static void test2() {
		Set<Integer> treeSet = new TreeSet<>();
		treeSet.add(12);
		treeSet.add(10);
		treeSet.add(7);
		treeSet.add(11);
		treeSet.add(10);
		treeSet.add(11);
		treeSet.add(9);

		treeSet.traversal(new Visitor<Integer>() {

			@Override
			public boolean visit(Integer element) {
				System.out.println(element);
				return false;
			}
		});
	}

	static void testSet(Set<String> set, String[] words) {
		for (int i = 0; i < words.length; i++) {
			set.add(words[i]);
		}
		for (int i = 0; i < words.length; i++) {
			set.contains(words[i]);
		}
		for (int i = 0; i < words.length; i++) {
			set.remove(words[i]);
		}
	}

	static void test3() {
		FileInfo fileInfo = Files.read("/Users/manxiaoyi/Desktop/_算法Demo/JAVACODE", new String[] { "java" });

		System.out.println("文件数量：" + fileInfo.getFiles());
		System.out.println("代码行数：" + fileInfo.getLines());
		String[] words = fileInfo.words();
		System.out.println("单词数量：" + words.length);

		Times.test("ListSet", new Task() {
			public void execute() {
				testSet(new ListSet<>(), words);
			}
		});

		Times.test("TreeSet", new Task() {

			@Override
			public void execute() {
				testSet(new TreeSet<>(), words);
			}
		});

	}
}
