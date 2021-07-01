package com.my;

@SuppressWarnings("unchecked")

public class ArrayList<E> {
	/**
	 * 元素的数量
	 */
	private int size;

	/**
	 * 所有的元素
	 */
	private E[] elements;

	private static final int DEFAULT_CAPACITY = 10;
	private static final int ELEMENT_NOT_FOUND = -1;

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
	}

	/**
	 * 元素的数量
	 * 
	 * @return
	 */
	public int size() {
		return size;
	}

	/**
	 * 是否为空
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * 是否包含某个元素
	 * 
	 * @param element
	 * @return
	 */
	public boolean contains(E element) {
		return indexOf(element) != ELEMENT_NOT_FOUND;
	}

	/**
	 * 添加元素到尾部
	 * 
	 * @param element
	 */
	public void add(E element) {
		add(size, element);
	}

	/**
	 * 获取index位置的元素
	 * 
	 * @param index
	 * @return
	 */
	public E get(int index) {
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
	public E set(int index, E element) {
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
	public void add(int index, E element) {
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
	public E remove(int index) {
		rangeCheck(index);

		E old = elements[index];
		for (int i = index + 1; i < size; i++) {
			elements[i - 1] = elements[i];
		}

		// 最后一个清空
		elements[--size] = null;

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

	public int indexOf2(E element) {
		 for (int i = 0; i < size; i++) {
			if (valEquals(element, elements[i])) {
				return i;
			}
		}
		 return ELEMENT_NOT_FOUND;
	 }
	 
	// 判断两者是否相等
	private boolean valEquals(Object v1, Object v2) {
		return v1 == null ? v2 == null : v1.equals(v2);
	}

	/**
	 * 扩容
	 * 
	 * @param capaticy
	 */
	private void ensureCapacity(int capaticy) {
		int oldCapacity = elements.length;
		if (oldCapacity > capaticy)
			return;

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
	 * 校验代码
	 * 
	 * @param index
	 */
	private void rangeCheck(int index) {
		if (index < 0 || index >= size) {
			outOfBounds(index);
		}
	}

	private void rangeCheckForAdd(int index) {
		if (index < 0 || index > size) {
			outOfBounds(index);
		}
	}

	private void outOfBounds(int index) {
		throw new IndexOutOfBoundsException("index:" + index + ", size:" + size);
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
