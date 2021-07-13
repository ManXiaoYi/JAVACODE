package com.my.queue;

import java.util.Comparator;

import com.my.heap.BinaryHeap;

public class PriorityQueue<E> {
	BinaryHeap<E> heap;

	// 初始化
	// --------------------------------------------------------------------------
	public PriorityQueue(Comparator<E> comparator) {
		heap = new BinaryHeap<>(comparator); 
	}
	
	public PriorityQueue() {
		this(null);
	}
	
	// 暴露方法
	// --------------------------------------------------------------------------
	public int size() {
		return heap.size();
	}
	
	public boolean isEmpty() {
		return heap.isEmpty();
	}
	
	public void clear() {
		heap.clear();
	}
	
	public void enQueue(E element) {
		heap.add(element);
	}
	
	public E deQueue() {
		return heap.remove();
	}
	
	public E front() {
		return heap.get();
	}

}
