package com.my;

public class Main {

	public static void main(String[] args) {
		// java.util.ArrayList

		ArrayList<Person> list = new ArrayList<Person>();
		list.add(new Person(10, "Jack"));
		list.add(new Person(20, "James"));
		list.add(null);
		list.add(new Person(30, "Rose"));

		// list.clear();

		// // 提醒JVM进行垃圾回收
		// System.gc();

		// Asserts.test(list.get(1) == null);

		System.out.println(list.toString());
		// System.out.println(list.indexOf(null));
	}
}
