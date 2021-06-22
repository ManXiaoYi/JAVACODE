package com.my;

import java.util.Iterator;

import Circle.CircleDeQueue;
import Circle.CircleQueue;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		Queue<Integer> queue = new Queue<>();
//		queue.enQueue(11);
//		queue.enQueue(22);
//		queue.enQueue(33);
//		queue.enQueue(44);
//		queue.enQueue(55);
//		
//		while (!queue.isEmpty()) {
//			System.out.println(queue.deQueue());
//		}

//		deQueue<Integer> queue = new deQueue<>();
//		queue.enQueueFront(11);
//		queue.enQueueFront(22);
//		queue.enQueueRear(33);
//		queue.enQueueRear(44);
//		
//		while (!queue.isEmpty()) {
//			System.out.println(queue.deQueueFront());
//		}

//		test2();
//		tes3();
	}

	static void tes3() {
		CircleDeQueue<Integer> queue = new CircleDeQueue<>();
		// 头 5 4 3 2 1 100 101 102 103 104 尾 -> 扩容
		// 头 5 4 3 2 1 100 101 102 103 104 105 106 8 7 6 尾 -> 扩容
		// 头 8 7 6 5 4 3 2 1 100 101 102 103 104 105 106 107 108 109 null null 10 9 尾
		for (int i = 0; i < 10; i++) {
			queue.enQueueFront(i + 1);
			queue.enQueueRear(i + 100);
		}

		// 头 null 7 6 5 4 3 2 1 100 101 102 103 104 105 106 null null null null null null null 尾
		for (int i = 0; i < 3; i++) {
			queue.deQueueFront();
			queue.deQueueRear();
		}

		// 头 11 7 6 5 4 3 2 1 100 101 102 103 104 105 106 12 null null null null null null 尾
		queue.enQueueFront(11);
		queue.enQueueFront(12);
		System.out.println(queue);

		while (!queue.isEmpty()) {
			System.out.println(queue.deQueueFront());
		}
	}

	static void test2() {
		CircleQueue<Integer> queue = new CircleQueue<>();
		for (int i = 0; i < 10; i++) {
			queue.enQueue(i);
		}

		for (int i = 0; i < 5; i++) {
			queue.deQueue();
		}

		for (int i = 15; i < 23; i++) {
			queue.enQueue(i);
		}
		System.out.println(queue);

		while (!queue.isEmpty()) {
			System.out.println(queue.deQueue());
		}
	}

}
