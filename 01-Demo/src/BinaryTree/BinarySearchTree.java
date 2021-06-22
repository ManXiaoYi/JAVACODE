package BinaryTree;

import java.util.Comparator;

public class BinarySearchTree<E> {
	// 大小
	private int size;
	// 根节点
	private Node<E> root;
	// 比较器
	private Comparator<E> comparator;

	// 构造方法
	public BinarySearchTree() {
		this(null);
	}

	public BinarySearchTree(Comparator<E> comparator) {
		this.comparator = comparator;
	}

	private static class Node<E> {
		E element;
		Node<E> parent;
		Node<E> left;
		Node<E> right;

		public Node(E element, Node<E> parent) {
			this.element = element;
			this.parent = parent;
		}
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void clear() {

	}

	public void add(E element) {
		elementNotNullCheck(element);
		
		if (root == null) {
			root = new Node<>(element, null);
			size++;
			return;
		}
		
		Node<E> parent = root;
		Node<E> node = root;
		int comopare = 0;
		
		while (node != null) {
			comopare = compare(element, node.element);
			
			parent = node;
			
			if (comopare > 0) {
				node = node.right;
			} else if (comopare < 0) {
				node = node.left;
			} else {
				return;
			}
		}

		Node<E> newNode = new Node<>(element, parent);
		if (comopare > 0) {
			parent.right = newNode;
		} else {
			parent.left = newNode;
		}
		
		size++;
	}

	public void remove(E element) {

	}

	public boolean contains(E element) {
		return false;
	}

	// 元素比较
	private int compare(E element1, E element2) {
		if (comparator != null) {
			return comparator.compare(element1, element2);
		}
		
		return ((Comparable<E>) element1).compareTo(element2) ;
	}

	// 校验元素是否为null
	private void elementNotNullCheck(E element) {
		if (element == null) {
			throw new IllegalArgumentException("element must not be null");
		}
	}
}
