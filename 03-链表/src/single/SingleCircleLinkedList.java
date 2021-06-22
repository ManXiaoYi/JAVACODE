package single;

import com.my.AbstractList;

public class SingleCircleLinkedList<E> extends AbstractList<E> {
	private Node<E> first;

	private static class Node<E> {
		E element;
		Node<E> next;

		public Node(E element, Node<E> next) {
			this.element = element;
			this.next = next;
		}
		
		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append(element).append("_").append(next.element);
			return sb.toString();
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
			// 循环链表 - 尾部节点的next指向首部节点
			// new Node<>(element, first) 会改变first，所以先newFirst暂存
			Node<E> newFirst = new Node<>(element, first);
			Node<E> last = size == 0 ? newFirst : node(size - 1);
			last.next = newFirst;
			first = newFirst;
			
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
			// 循环链表 - 尾部节点的next指向首部节点
			if (size == 1) {
				first = null;
			} else {
				// node(size - 1)会改变first，所以第一句放在最前面
				Node<E> last = node(size - 1);
				first = first.next;
				last.next = first;
			}
			
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

			string.append(node);

			node = node.next;
		}

		string.append("]");
		return string.toString();
	}
}
