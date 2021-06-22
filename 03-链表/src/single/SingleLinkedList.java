package single;

import com.my.AbstractList;

public class SingleLinkedList<E> extends AbstractList<E> {
	private Node<E> first;

	private static class Node<E> {
		E element;
		Node<E> next;

		public Node(E element, Node<E> next) {
			this.element = element;
			this.next = next;
		}
	}

	@Override
	public void clear() {
		size = 0;
		first = null;
	}

	@Override
	public E get(int index) { // O(n)
		return node(index).element;
	}

	@Override
	public E set(int index, E element) { // O(n)
		Node<E> node = node(index);
		E old = node.element;

		node.element = element;
		return old;
	}

	@Override
	public void add(int index, E element) { // O(n)
		rangeCheckForAdd(index);
		
		if (index == 0) {
			first = new Node<>(element, first);
		} else {
			Node<E> pref = node(index - 1);
			pref.next = new Node<>(element, pref.next);
		}

		size++;
	}

	@Override
	public E remove(int index) { // O(n)
		rangeCheck(index);
		
		Node<E> node = first;
		if (index == 0) {
			first = first.next;
		} else {
			Node<E> pref = node(index - 1);
			node = pref.next;
			pref.next = node.next;
		}
		size--;

		return node.element;
	}

	@Override
	public int indexOf(E element) {
		if (element == null) {
			// 为null判断
			Node<E> node = first;
			for (int i = 0; i < size; i++) {
				if (node.element == null) {
					return i;
				}

				node = node.next;
			}
		} else {
			// 非null判断
			Node<E> node = first;
			for (int i = 0; i < size; i++) {
				if (element.equals(node.element)) {
					return i;
				}

				node = node.next;
			}
		}

		return ELEMENT_NOT_FOUND;
	}

	/**
	 * 获取index位置对应的节点对象
	 * 
	 * @param index
	 * @return
	 */
	private Node<E> node(int index) {
		rangeCheck(index);

		Node<E> node = first;
		for (int i = 0; i < index; i++) {
			node = node.next;
		}
		return node;
	}
	
	/**
	 * 重写打印方法
	 */
	@Override
	public String toString() {
		StringBuffer string = new StringBuffer();
		string.append("size=").append(size).append(", [");

		Node<E> node = first;
		for (int i = 0; i < size; i++) {
			if (i != 0) {
				string.append(", ");
			}
			
			string.append(node.element);
			
			node = node.next;
		}

		string.append("]");
		return string.toString();
	}
}
