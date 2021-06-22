package Circle;

@SuppressWarnings("unchecked")

public class CircleDeQueue<E> {
	private int front;
	private int size;
	private E[] elements = (E[]) new Object[DEFAULT_CAPACITY];
	private static final int DEFAULT_CAPACITY = 10;

	public CircleDeQueue() {

	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void clear() {
		front = 0;
		for (int i = 0; i < size; i++) {
			elements[index(i)] = null;
		}
		size = 0;
	}

	// 从对头入队
	public void enQueueFront(E element) {
		ensureCapacity(size + 1);

		// 重置front
		front = index(-1);
		elements[front] = element;
		size++;
	}

	// 从队尾入队
	public void enQueueRear(E element) {
		ensureCapacity(size + 1);

		elements[index(size)] = element;
		size++;
	}

	// 从对头出队
	public E deQueueFront() {
		E real = elements[front];
		elements[front] = null;
		front = index(1);
		size--;

		return real;
	}

	// 从对尾出队
	public E deQueueRear() {
		int realIndex = index(size - 1);
		E real = elements[realIndex];
		elements[realIndex] = null;
		size--;

		return real;
	}

	public E front() {
		return elements[front];
	}

	public E Rear() {
		return elements[index(size - 1)];
	}

	// 索引映射
	private int index(int index) {
		index += front;

		if (index < 0) {
			// 下标小于0
			return index + elements.length;
		}

		// 下标大于等于0
		return index % elements.length;
	}

	// 扩容
	private void ensureCapacity(int capacity) {
		int oldCapacity = elements.length;
		if (oldCapacity >= capacity) {
			return;
		}

		int newCapacity = oldCapacity + (oldCapacity >> 1);
		E[] newElements = (E[]) new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[index(i)];
		}
		elements = newElements;

		// 重置front
		front = 0;

		System.out.println(oldCapacity + "扩容为" + newCapacity);
	}
 
	@Override
	public String toString() {
		StringBuffer string = new StringBuffer();

		string.append("capacity = ").append(elements.length).append(", size = ").append(size).append(", front = ")
				.append(front).append(", [");
		for (int i = 0; i < elements.length; i++) {
			if (i != 0) {
				string.append(", ");
			}
			string.append(elements[i]);
		}
		string.append("]");

		return string.toString();
	}

}