package com.my.heap;

import java.util.Comparator;

@SuppressWarnings("unchecked")

/**
 * 二叉堆（最大堆)
 * 
 * @author manxiaoyi
 *
 * @param <E>
 */
public class BinaryHeap<E> extends AbstractHeap<E> {
	private E[] elements;
	private static final int DEFAULT_CAPACITY = 10;
	// 初始化
	// --------------------------------------------------------------------------
	public BinaryHeap(E[] elements, Comparator<E> comparator) {
		super(comparator);

		if (elements == null || elements.length == 0) {
			this.elements = (E[]) new Object[DEFAULT_CAPACITY];
		} else {
			size = elements.length;
			
			int capacity = Math.max(elements.length, DEFAULT_CAPACITY);
			this.elements = (E[]) new Object[capacity];
			
			for (int i = 0; i < elements.length; i++) {
				this.elements[i] = elements[i];
			}
			
			heapify();
		}
	}

	public BinaryHeap(E[] elements) {
		this(elements, null);
	}

	public BinaryHeap(Comparator<E> comparator) {
		this(null, comparator);
	}

	public BinaryHeap() {
		this(null, null);
	}

	// 暴露方法
	// --------------------------------------------------------------------------
	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	@Override
	public void add(E element) {
		elementNotNullCheck(element);
		ensureCapacity(size + 1);
		elements[size++] = element;
		siftUp(size - 1);
	}

	@Override
	public E get() {
		emptyCheck();
		return elements[0];
	}

	@Override
	public E remove() {
		emptyCheck();
		
		// 获取最后一个节点index
		int lastIndex = --size;
		// 获取根节点
		E root = elements[0];
		// 最后一个节点 覆盖 根节点
		elements[0] = elements[lastIndex];
		// 删除最后一个节点
		elements[lastIndex] = null;
		
		siftDown(0);
		
		return root;
	}

	@Override
	public E replace(E element) {
		elementNotNullCheck(element);
		
		E root = null;
		if (size == 0) {
			elements[0] = element;
			size++;
		} else {
			 root = elements[0];
			 elements[0] = element;
			 siftDown(0);
		}
		
		return root;
	}

	// 批量建堆
	private void heapify() {
//		// 自上而下的上滤
//		for (int i = 1; i < size; i++) {
//			siftUp(i);
//		}
		
		// 自下而上的下滤
		for (int i = (size >> 1) - 1; i >= 0 ; i--) {
			siftDown(i);
		}
	}
	
	// 方法
	// --------------------------------------------------------------------------
	// 让index位置的元素上滤
	private void siftUp(int index) {
		E element = elements[index];
		
		while (index > 0) {
			// 获取父节点
			int parentIndex = (index - 1) >> 1;
			E parent = elements[parentIndex];
			
			// 小于父节点
			if (compare(parent, element) >= 0) {
				break;
			}
			
			// 大于父节点 - 将父元素安排到index位置
			elements[index] = parent;
			
			// 重新赋值index
			index = parentIndex;
		}
		
		// 将元素安排到index位置
		elements[index] = element;
	}

	// 让index位置的元素下滤
	private void siftDown(int index) {
		E element = elements[index];
		
		// 第一个叶子节点的索引 == 非叶子节点的个数
		int half = size >> 1;

		// 必须保证index位置是非叶子节点
		// index < 第一个叶子节点的索引
		while (index < half) {
			// index的节点有2种情况
			// 1. 只有左节点  2. 同时有左右节点
			
			// 默认为左节点跟它进行比较
			int childIndex = (index << 1) + 1;
			E child = elements[childIndex];
			
			// 右子节点
			int rightIndex = childIndex + 1;
			
			// 选出左右节点最大的那一个
			if (rightIndex < size && compare(elements[rightIndex], child) > 0) {
				childIndex = rightIndex;
				child = elements[rightIndex];
			}
			
			// 元素 大于 最大子节点
			if (compare(element, child) >= 0) {
				return;
			}
			
			// 元素 小于 最大子节点
			// 将子节点放到index位置
			elements[index] = child;
			
			// 重新设置index
			index = childIndex;
		}
		
		elements[index] = element;
	}
	
	// 扩容
	private void ensureCapacity(int capacity) {
		int oldCapacity = elements.length;
		if (oldCapacity >= capacity)
			return;

		// 新容量为旧容量的1.5倍
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		E[] newElements = (E[]) new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[i];
		}
		elements = newElements;
	}

	// 空值判断 - 数组为空
	private void emptyCheck() {
		if (size == 0) {
			throw new IndexOutOfBoundsException("Heap is empty");
		}
	}

	// 空值判断 - 元素为空
	private void elementNotNullCheck(E element) {
		if (element == null) {
			throw new IllegalArgumentException("element must not be null");
		}
	}
}
