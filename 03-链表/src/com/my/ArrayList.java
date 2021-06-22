package com.my;

@SuppressWarnings("unchecked")

public class ArrayList<E> extends AbstractList<E> {

	/**
	 * 所有的元素
	 */
	private E[] elements;

	private static final int DEFAULT_CAPACITY = 10;

	/**
	 * 初始化
	 */
	public ArrayList() {
		this(DEFAULT_CAPACITY);
	}

	public ArrayList(int capaticy) {
		capaticy = capaticy < DEFAULT_CAPACITY ? DEFAULT_CAPACITY : capaticy;
		elements = (E[]) new Object[capaticy];
	}

	/**
	 * 清除所有元素
	 */
	public void clear() {
		// 泛型的内存销毁，数组不销毁
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
		
		// 缩容
		if (elements != null && elements.length > DEFAULT_CAPACITY) {
			elements = (E[]) new Object[DEFAULT_CAPACITY];
		}
		
	}

	/**
	 * 获取index位置的元素
	 * 
	 * @param index
	 * @return
	 */
	public E get(int index) { // O(1)
		rangeCheck(index);

		return elements[index]; 
	}

	/**
	 * 设置index位置的元素
	 * 
	 * @param index
	 * @param element
	 * @return 原来的元素
	 */
	public E set(int index, E element) { // O(1)
		rangeCheck(index);

		E old = elements[index];
		elements[index] = element; 
		return old;
	}

	/**
	 * 在index位置插入一个的元素
	 * 
	 * @param index
	 * @param element
	 */
	public void add(int index, E element) { // O(n)
		rangeCheckForAdd(index);

		ensureCapacity(size + 1);

		for (int i = size; i > index; i--) {
			elements[i] = elements[i - 1];
		}

		elements[index] = element;
		size++;
	}

	/**
	 * 删除index位置的元素
	 * 
	 * @param index
	 * @return
	 */
	public E remove(int index) { // O(n)
		rangeCheck(index);

		E old = elements[index];
		for (int i = index + 1; i < size; i++) {
			elements[i - 1] = elements[i];
		}

		// 最后一个清空
		elements[--size] = null;
		
		// 缩容
		trim();

		return old;
	}

	/**
	 * 查看元素的索引
	 * 
	 * @param element
	 * @return
	 */
	public int indexOf(E element) {
		if (element == null) {
			// 为null判断
			for (int i = 0; i < size; i++) {
				if (elements[i] == null)
					return i;
			}
		} else {
			// 非null判断
			for (int i = 0; i < size; i++) {
				if (element.equals(elements[i]))
					return i;
			}
		}

		return ELEMENT_NOT_FOUND;
	}

	/**
	 * 扩容
	 * 
	 * @param capaticy
	 */
	private void ensureCapacity(int capaticy) {
		int oldCapacity = elements.length;
		if (oldCapacity > capaticy) return;

		// 新容量为旧容量的1.5倍
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		E[] newElements = (E[]) new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[i];
		}
		elements = newElements;

		System.out.println(oldCapacity + "扩容" + newCapacity);
	}
	
	/**
	 * 缩容
	 * 缩容和扩容系数相乘不要为1，防止 复杂度震荡
	 */
	private void trim() {
		int oldCapacity = elements.length;
		int newCapacity = oldCapacity >> 1;
		// size大于一半 or size小于等于默认
		if (size >= newCapacity || size <= DEFAULT_CAPACITY )  return;
		
		// 扩容
		E[] newElements = (E[]) new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[i];
		}
		elements = newElements;
		
		System.out.println(oldCapacity + "缩容" + newCapacity);
	}

	/**
	 * 重写打印方法
	 */
	@Override
	public String toString() {
		StringBuffer string = new StringBuffer();
		string.append("size=").append(size).append(", [");

		for (int i = 0; i < size; i++) {
			if (i != 0) {
				string.append(", ");
			}

			string.append(elements[i]);
		}

		string.append("]");
		return string.toString();
	}

	// /**
	// * 重写equals方法
	// */
	// @Override
	// public boolean equals(Object obj) {
	// if (obj == null) return false;
	// if (obj instanceof Person) {
	// Person person = (Person)obj;
	// return this.age == person.age;
	// }
	// return false;
	// }
}
