package doubly;

import com.my.AbstractList;

public class CircleLinkedList<E> extends AbstractList<E> {
	private Node<E> first;
	private Node<E> last;
	private Node<E> current;

	private static class Node<E> {
		Node<E> prev;
		E element;
		Node<E> next;

		public Node(Node<E> prev, E element, Node<E> next) {
			this.prev = prev;
			this.element = element;
			this.next = next;
		}

		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();

			if (prev != null) {
				sb.append(prev.element);
			} else {
				sb.append("null");
			}

			sb.append("_").append(element).append("_");

			if (next != null) {
				sb.append(next.element);
			} else {
				sb.append("null");
			}

			return sb.toString();
		}
	}
	
	/**
	 * current指向头结点first
	 */
	public void reset() {
		current = first;
	}
	
	/**
	 * current下级节点
	 * @return
	 */
	public E next() {
		if (current == null) {
			return null;
		}
		
		current = current.next;
		return current.element;
	}
	
	/**
	 * 删除current指向的节点，之后并指向下一个节点
	 * @return
	 */
	public E remove() {
		if (current == null) {
			return null;
		}
		
		Node<E> next = current.next;
		E element = remove(current);
		if (size == 0) {
			current = null;
		} else {
			current = next;
		}
		
		return element;
	}

	@Override
	public void clear() {
		size = 0;
		first = null;
		last = null;
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

		if (index == size) {
			// 往最后添加元素
			Node<E> oldLast = last;
			last = new Node<>(oldLast, element, first);

			if (oldLast == null) {
				// 空的链表，添加第一个元素
				first = last;
				first.prev = last;
				first.next = last;

			} else {
				oldLast.next = last;
				first.prev = last;
			}

		} else {
			// 其他情况
			Node<E> next = node(index);
			Node<E> prex = next.prev;
			Node<E> node = new Node<>(prex, element, next);
			next.prev = node;
			prex.next = node;

			if (index == 0) {
				// 往最前添加元素
				first = node;
			}
		}

		size++;
	}

	@Override
	public E remove(int index) { // O(n)
		rangeCheck(index);
		return remove(node(index));
	}
	
	/**
	 * 删除node
	 * @param node
	 * @return
	 */
	private E remove(Node<E> node) {
		if (size == 1) {
			first = null;
			last = null;
		} else {
			Node<E> prev = node.prev;
			Node<E> next = node.next;
			prev.next = next;
			next.prev = prev;

			if (node == first) {
				first = next;
			}

			if (node == last) {
				last = prev;
			}
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

		if (index < (size >> 1)) {
			Node<E> node = first;
			for (int i = 0; i < index; i++) {
				node = node.next;
			}
			return node;
		} else {
			Node<E> node = last;
			for (int i = size - 1; i > index; i--) {
				node = node.prev;
			}
			return node;
		}

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

			string.append(node);

			node = node.next;
		}

		string.append("]");
		return string.toString();
	}
}
