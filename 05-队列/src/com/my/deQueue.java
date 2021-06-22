package com.my;

import com.my.list.LinkedList;

public class deQueue<E> {
	LinkedList<E> list = new LinkedList<>();

	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public void clear() {
		list.clear();
	}

	// 从对头入队
	public void enQueueFront(E element) {
		list.add(0, element);
	}

	// 从队尾入队
	public void enQueueRear(E element) {
		list.add(element);
	}

	// 从对头出队
	public E deQueueFront() {
		return list.remove(0);
	}

	// 从对尾出队
	public E deQueueRear() {
		return list.remove(list.size() - 1);
	}

	public E front() {
		return list.get(0);
	}
	
	public E Rear() {
		return list.get(list.size() - 1);
	}

}
