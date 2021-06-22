package linkedList;

import java.util.Iterator;

import List.AbstractList;

public class SingleLinkedList<E> extends AbstractList<E> {

	private Node<E> first;

	public static class Node<E> {
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
	public E get(int index) {
		return node(index).element;
	}

	@Override
	public E set(int index, E element) {
		Node<E> node = node(index);
		E old = node.element;
		node.element = element;
		return old;
	}

	@Override
	public void add(int index, E element) {
		rangeCheckForAdd(index);
		
		if (index == 0) {
			first = new Node<>(element, first);
		} else {
			Node<E> prev = node(index - 1);
			prev.next = new Node<>(element, prev.next);
		}
		
		size++;
	}

	@Override
	public E remove(int index) {
		rangeCheck(index);
		
		Node<E> node = first;
		if (index == 0) {
			first = first.next;
		} else {
			Node<E> prev = node(index - 1);
			node = prev.next;
			prev.next = prev.next.next;
		}
		
		size--;
		
		
		return node.element;
	}

	@Override
	public int indexOf(E element) {
		Node<E> node = first;

		if (element == null) {
			for (int i = 0; i < size; i++) {
				if (node.element == null) {
					return i;
				}

				node = node.next;
			}
		} else {
			for (int i = 0; i < size; i++) {
				if (element.equals(node.element)) {
					return i;
				}

				node = node.next;
			}
		}

		return ELEMENT_NOT_FOUND;
	}

	private Node<E> node(int index) {
		rangeCheck(index);

		Node<E> node = first;
		for (int i = 0; i < index; i++) {
			node = node.next;
		}

		return node;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
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
