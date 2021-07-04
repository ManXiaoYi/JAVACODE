package com.my;

import com.my.map.HashMap;
import com.my.map.Map;
import com.my.model.Key;
import com.my.model.Person;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
  
		
		test5();
	}
	
	private static void test6() {
		
	}
	
	private static void test5() {
		HashMap<Object, Integer> map = new HashMap<>();
		for (int i = 1; i <= 19; i++) {
			map.put(new Key(1), i);
		}
		System.out.println(map.get(new Key(1)));
		map.print();
	}
	
	private static void test4() {
		Person p1 = new Person(10, 10.6f, "jack");
		Person p2 = new Person(10, 10.6f, "jack");
		
		Map<Object, Integer> map = new HashMap<>();
		map.put(p1, 1);
		map.put(p2, 2);
		map.put("jack", 3);
		map.put("rose", 4);
		map.put("jack", 5);
		map.put(null, 6);
		
//		map.traversal(new Visitor<Object, Integer>() {
//			@Override
//			public boolean visit(Object key, Integer value) {
//				System.out.println(key + "_" + value);
//				return false;
//			}
//		});
	
		System.out.println(map.containsKey(p1));
		System.out.println(map.containsKey(null));
		System.out.println(map.containsValue(6));
		System.out.println(map.containsValue(1));

//		System.out.println(map.get("jack"));
//		System.out.println(map.get("rose"));
//		System.out.println(map.get(null));
//		System.out.println(map.get(p1));
//		
//		System.out.println(map.size());
//		System.out.println(map.remove("jack"));
//		System.out.println(map.size());
//		System.out.println(map.get("jack"));
	}
	
	private static void test3() {
		Person p1 = new Person(10, 10.6f, "jack");
		Person p2 = new Person(10, 10.6f, "jack");
		System.out.println(p1.hashCode());
		System.out.println(p2.hashCode());
		
		Map<Object, Object> map = new HashMap<>();
		map.put(p1, "abc");
		map.put("test", "ccc");
		map.put(p2, "bcd");
		System.out.println(map.size());
	}
	
	// 哈希值
	private static void test2() {
		Integer a = 110;
		Float b = 10.6f;
		Long c = 156l;
		Double d = 10.9;
		String e = "jack";
		
		System.out.println(a.hashCode());
		System.out.println(b.hashCode());
		System.out.println(c.hashCode());
		System.out.println(d.hashCode());
		System.out.println(e.hashCode());
		
	}
	
	// 计算字符串的hashCode
	private static void test1() {
		String string = "jack"; // 3254239
		int length = string.length();
		int hashCode = 0;
		for (int i = 0; i < length; i++) {
			char c = string.charAt(i);
			// hashCode = hashCode * 31 + c;
			hashCode = (hashCode << 5) - hashCode + c;
		}
		System.out.println(hashCode);
		
		// 官方方法
		System.out.println(string.hashCode());
	}

}
