package com.my;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		test();
	}
	
	// 哈希值
	private static void test() {
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
