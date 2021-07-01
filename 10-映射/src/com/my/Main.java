package com.my;

import com.my.file.FileInfo;
import com.my.file.Files;
import com.my.map.Map;
import com.my.map.Map.Visitor;
import com.my.map.TreeMap;
import com.my.set.Set;
import com.my.set.TreeSet;

public class Main {
	public static void main(String[] args) {
//		test3();
	}
	
	static void test3() {
		Set<String> set = new TreeSet<>();
		set.add("a");
		set.add("c");
		set.add("b");
		set.add("c");
		set.add("c");
		
		set.traversal(new Set.Visitor<String>() {
			@Override
			public boolean visit(String element) {
				System.out.println(element);
				return false;
			}
		});
	}
	
	static void test2() {
		FileInfo fileInfo = Files.read("/Users/manxiaoyi/Desktop/_算法Demo/JAVACODE", new String[] { "java" });

		System.out.println("文件数量：" + fileInfo.getFiles());
		System.out.println("代码行数：" + fileInfo.getLines());
		String[] words = fileInfo.words();
		System.out.println("单词数量：" + words.length);
		
		Map<String, Integer> map = new TreeMap<>();
		for (int i = 0; i < words.length; i++) {
			Integer count = map.get(words[i]);
			count = count == null ? 1 : count + 1;
			map.put(words[i], count);
		}
		
		map.traversal(new Visitor<String, Integer>() {
			@Override
			public boolean visit(String key, Integer value) {
				System.out.println(key + "_" + value);
				return false;
			}
		});
	}

	static void test() {
		Map<String, Integer> map = new TreeMap<>();
		map.put("c", 2);
		map.put("a", 5);
		map.put("b", 6);
		map.put("a", 8);

		map.traversal(new Visitor<String, Integer>() {
			@Override
			public boolean visit(String key, Integer value) {
				System.out.println(key + "_" + value);
				return false;
			}
		});
	}
}
