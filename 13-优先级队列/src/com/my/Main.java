package com.my;

import com.my.queue.PriorityQueue;

public class Main {

	public static void main(String[] args) {

//		test();
	}
	
	private static void test() {
		PriorityQueue<Person> queue = new PriorityQueue<>();
		queue.enQueue(new Person("Jack", 2));
		queue.enQueue(new Person("Rose", 10));
		queue.enQueue(new Person("Jadan", 5));
		queue.enQueue(new Person("James", 15));

		while (!queue.isEmpty()) {
			System.out.println(queue.deQueue());
		}
	}

}
