package com.my.tree;

import java.util.Comparator;

@SuppressWarnings("unchecked")

public class BST<E> extends BinaryTree<E> {
	// 比较器
	private Comparator<E> comparator;

	// 构造方法
	public BST() {
		this(null);
	}

	public BST(Comparator<E> comparator) {
		this.comparator = comparator;
	}

	public void add(E element) {
		elementNotNullCheck(element);

		// 添加位置是第一个节点
		if (root == null) {
			root = createNode(element, null);
			
			size++;
			
			// 新添加节点之后的处理
			afterAdd(root);
			return;
		}

		// 添加位置不是第一个节点 - 找到父节点
		Node<E> parent = root;
		Node<E> node = root;
		int compare = 0;

		while (node != null) {
			compare = compare(element, node.element);

			parent = node;

			if (compare > 0) {
				node = node.right;
			} else if (compare < 0) {
				node = node.left;
			} else { // 相等 元素覆盖
				node.element = element;
				return;
			}
		}

		// 插入元素
		Node<E> newNode = createNode(element, parent);
		if (compare > 0) {
			parent.right = newNode;
		} else {
			parent.left = newNode;
		}

		size++;
		
		// 新添加节点之后的处理
		afterAdd(newNode);
	}
	
	// 添加node之后的调整
	protected void afterAdd(Node<E> node) {}
	
	// 删除node之后的调整
	protected void afterRemove(Node<E> node) {}

	public void remove(E element) {
		remove(node(element));
	}

	public boolean contains(E element) {
		return node(element) != null;
	}

	public void remove(Node<E> node) {
		if (node == null) {
			return;
		}
		size--;

		// node是度为2的节点
		if (node.hasTwoChildren()) {
			// 1. 找到后继节点
			Node<E> s = successor(node);
			// 2. 用后继节点的值 覆盖 度为2的节点的值
			node.element = s.element;
			// 3. 删除后继节点 - 赋值给node，最后删除node即可
			node = s;
		}

		// node是度为0或1的节点
		Node<E> replacement = node.left != null ? node.left : node.right;

		if (replacement != null) {
			// node是度为1的节点
			// 1. 更改parent
			replacement.parent = node.parent;
			// 2. 更改parent的left、right指向
			if (node.parent == null) {
				// 根节点 - root指向被替换节点
				root = replacement;
			} else if (node == node.parent.left) {
				node.parent.left = replacement;
			} else {
				node.parent.right = replacement;
			}

			// 删除节点之后的处理
			afterRemove(node);
		} else if (node.parent == null) {
			// node是叶子节点 且 为根节点
			root = null;

			// 删除节点之后的处理
			afterRemove(node);
		} else {
			// node是度为0的节点 - 叶子节点 但 不为根节点
			if (node == node.parent.left) {
				// node在 父节点的左边
				node.parent.left = null;
			} else {
				// node在 父节点的右边
				node.parent.right = null;
			}

			// 删除节点之后的处理
			afterRemove(node);
		}

		
	}

	private Node<E> node(E element) {
		Node<E> node = root;
		while (node != null) {
			// 比较两者大小
			int cmp = compare(element, node.element);
			// = 0 说明就是此元素
			if (cmp == 0) {
				return node;
			}
			if (cmp > 0) { // > 0 说明当前节点小，往右节点遍历
				node = node.right;
			} else { // < 0 说明当前节点大，往左节点遍历
				node = node.left;
			}
		}
		return null;
	}

	// 方法相关
	// --------------------------------------------------------------------------
	// 元素比较
	private int compare(E element1, E element2) {
		if (comparator != null) {
			return comparator.compare(element1, element2);
		}

		return ((Comparable<E>) element1).compareTo(element2);
	}

	// 校验元素是否为null
	private void elementNotNullCheck(E element) {
		if (element == null) {
			throw new IllegalArgumentException("element must not be null");
		}
	}

}
