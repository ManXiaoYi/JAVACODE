package com.my;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import com.my.printer.BinaryTreeInfo;

@SuppressWarnings("unchecked")

public class BinarySearchTree<E> implements BinaryTreeInfo {
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

		// 叶子节点
		public boolean isLeaf() {
			return left == null && right == null;
		}

		// 有两个子节点
		public boolean hasTwoChildren() {
			return left != null && right != null;
		}

	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void clear() {
		root = null;
		size = 0;
	}

	public void add(E element) {
		elementNotNullCheck(element);

		// 添加位置是第一个节点
		if (root == null) {
			root = new Node<>(element, null);
			size++;
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
		Node<E> newNode = new Node<>(element, parent);
		if (compare > 0) {
			parent.right = newNode;
		} else {
			parent.left = newNode;
		}

		size++;
	}

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
			
		} else if (node.parent == null) {
			// node是叶子节点 且 为根节点
			 root = null;
			
		} else {
			// node是度为0的节点 - 叶子节点 但 不为根节点
			if (node == node.parent.left) {
				// node在 父节点的左边
				node.parent.left = null;
			} else {
				// node在 父节点的右边
				node.parent.right = null;
			}
			
		}
		
	}
	
	private Node<E> node(E element) {
		Node<E> node = root;
		while (node != null) {
			// 比较两者大小
			int cmp = compare(element, node.element);
			// = 0 说明就是此元素
			if (cmp == 0) { return node; }
			if (cmp > 0) { // > 0 说明当前节点小，往右节点遍历
				node = node.right;
			} else { // < 0 说明当前节点大，往左节点遍历
				node = node.left;
			}
		}
		return null;
	}

	// 是否为完全二叉树
	// --------------------------------------------------------------------------
	public boolean isComplete() {
		if (root == null) {
			return false;
		}
		
		// 队列
		Queue<Node<E>> queue = new LinkedList<>();
		// 根节点 入队
		queue.offer(root);

		boolean leaf = false;
		while (!queue.isEmpty()) {			
			// 头结点 出队
			Node<E> node = queue.poll();
			if (leaf && !node.isLeaf()) {
				return false;
			}

			// 有左节点 入队
			if (node.left != null) {
				queue.offer(node.left);
			} else if (node.right != null) { // node.left == null && node.right != null
				return false;
			}

			// 有右节点 入队
			if (node.right != null) {
				queue.offer(node.right);
			} else { // node.right == null
				leaf = true;
			}
		}

		return true;
	}

	// 二叉树高度
	// --------------------------------------------------------------------------
	// 迭代方案 - 层序遍历
	public int height() {
		if (root == null) {
			return 0;
		}

		// 队列
		Queue<Node<E>> queue = new LinkedList<>();
		// 根节点 入队
		queue.offer(root);

		// 层数
		int height = 0;
		// 每层的节点数 - 默认根节点 =1
		int levelSize = 1;

		while (!queue.isEmpty()) {
			// 头结点 出队
			Node<E> node = queue.poll();

			// 出队 节点数 -1
			levelSize--;

			// 有左节点 入队
			if (node.left != null) {
				queue.offer(node.left);
			}

			// 有右节点 入队
			if (node.right != null) {
				queue.offer(node.right);
			}

			if (levelSize == 0) {
				// 即将访问下一层
				levelSize = queue.size();
				height++;
			}

		}

		return height;
	}

	// 递归方案
	public int height2() {
		return height(root);
	}

	private int height(Node<E> node) {
		if (node == null) {
			return 0;
		}

		return 1 + Math.max(height(node.left), height(node.right));
	}

	// 遍历相关
	// --------------------------------------------------------------------------
	/**
	 * 抽象类 可定义boolean类型
	 */
	public static abstract class Visitor<E> {
		boolean stop;

		// 返回为true，停止遍历
		public abstract boolean visit(E element);
	}

	/**
	 * 前序遍历
	 */
	public void preorder(Visitor<E> visitor) {
		if (visitor == null) {
			return;
		}
		preorder(root, visitor);
	}

	// 递归
	private void preorder(Node<E> node, Visitor<E> visitor) {
		if (node == null || visitor.stop) {
			return;
		}

		visitor.stop = visitor.visit(node.element);
		preorder(node.left, visitor);
		preorder(node.right, visitor);
	}

	/**
	 * 中序遍历
	 */
	public void inorder(Visitor<E> visitor) {
		if (visitor == null) {
			return;
		}
		inorder(root, visitor);
	}

	private void inorder(Node<E> node, Visitor<E> visitor) {
		// visitor.stop - 递归调用 终止
		if (node == null || visitor.stop) {
			return;
		}

		inorder(node.left, visitor);
		// 防止上面操作 visitor.stop = true
		if (visitor.stop) {
			return;
		}
		visitor.stop = visitor.visit(node.element);
		inorder(node.right, visitor);
	}

	/**
	 * 后序遍历
	 */
	public void postorder(Visitor<E> visitor) {
		if (visitor == null) {
			return;
		}
		postorder(root, visitor);
	}

	private void postorder(Node<E> node, Visitor<E> visitor) {
		// visitor.stop - 递归调用 终止
		if (node == null || visitor.stop) {
			return;
		}

		postorder(node.left, visitor);
		postorder(node.right, visitor);
		// 防止上面操作 visitor.stop = true
		if (visitor.stop) {
			return;
		}
		visitor.stop = visitor.visit(node.element);
	}

	/**
	 * 层序遍历
	 */
	public void levelorder(Visitor<E> visitor) {
		// visitor.stop - 递归调用 终止
		if (root == null || visitor == null) {
			return;
		}

		// 队列
		Queue<Node<E>> queue = new LinkedList<>();
		// 根节点 入队
		queue.offer(root);

		while (!queue.isEmpty()) {
			// 头结点 出队
			Node<E> node = queue.poll();

			// 停止就返回
			if (visitor.visit(node.element)) {
				return;
			}

			// 有左节点 入队
			if (node.left != null) {
				queue.offer(node.left);
			}

			// 有右节点 入队
			if (node.right != null) {
				queue.offer(node.right);
			}
		}

	}

	// 遍历相关 - 只是实现
	// --------------------------------------------------------------------------
//	/**
//	 * 前序遍历
//	 */
//	public void preorderTraversal() {
//		preorderTraverval(root);
//	}
//
//	// 递归
//	private void preorderTraverval(Node<E> node) {
//		if (node == null) {
//			return;
//		}
//
//		System.out.println(node.element);
//		preorderTraverval(node.left);
//		preorderTraverval(node.right);
//	}
//
//	/**
//	 * 中序遍历
//	 */
//	public void inorderTraverval() {
//		inorderTraverval(root);
//	}
//
//	private void inorderTraverval(Node<E> node) {
//		if (node == null) {
//			return;
//		}
//
//		inorderTraverval(node.left);
//		System.out.println(node.element);
//		inorderTraverval(node.right);
//	}
//
//	/**
//	 * 后序遍历
//	 */
//	public void postorderTraverval() {
//		postorderTraverval(root);
//	}
//
//	private void postorderTraverval(Node<E> node) {
//		if (node == null) {
//			return;
//		}
//
//		postorderTraverval(node.left);
//		postorderTraverval(node.right);
//		System.out.println(node.element);
//	}
//
//	/**
//	 * 层序遍历
//	 */
//	public void levelorderTraversal() {
//		if (root == null) {
//			return;
//		}
//
//		// 队列
//		Queue<Node<E>> queue = new LinkedList<>();
//		// 根节点 入队
//		queue.offer(root);
//
//		while (!queue.isEmpty()) {
//			// 头结点 出队
//			Node<E> node = queue.poll();
//			System.out.println(node.element);
//
//			// 有左节点 入队
//			if (node.left != null) {
//				queue.offer(node.left);
//			}
//
//			// 有右节点 入队
//			if (node.right != null) {
//				queue.offer(node.right);
//			}
//		}
//
//	}
	
	// 前驱节点 - 后继节点
	// --------------------------------------------------------------------------
	// 前驱结点：中序遍历时的前一个节点
	@SuppressWarnings("unused")
	private Node<E> predecessor(Node<E> node) {
		if (node == null) {
			return null;
		}
		
		// 前驱节点在左子树当中 .left.right.right.right...
		Node<E> p = node.left;
		if (p != null) {
			while (p.right != null) {
				p = p.right;
			}
			return p;
		}
		
		// 左子树为null && 父节点不为null && 是父节点的左子树(说明父节点大于node) parent.parent.parent...
		while (node.parent != null && node == node.parent.left) {
			node = node.parent;
		}
		
		// node.parent == null
		// node == node.parent.right
		
		return node.parent;
	}
	
	// 后继节点：中序遍历时的后一个节点
	private Node<E> successor(Node<E> node) {
		if (node == null) {
			return null;
		}
		
		// 前驱节点在右子树当中 .right.left.left.left...
		Node<E> p = node.right;
		if (p != null) {
			while (p.left != null) {
				p = p.left;
			}
			return p;
		}
		
		// 右子树为null && 父节点不为null && 是父节点的右子树(说明父节点小于node) parent.parent.parent...
		while (node.parent != null && node == node.parent.right) {
			node = node.parent;
		}
		
		// node.parent == null
		// node == node.parent.left
		
		return node.parent;
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

	// 打印相关
	// --------------------------------------------------------------------------
	@Override
	public Object root() {
		return root;
	}

	@Override
	public Object left(Object node) {
		return ((Node<E>) node).left;
	}

	@Override
	public Object right(Object node) {
		return ((Node<E>) node).right;
	}

	@Override
	public Object string(Object node) {
		Node<E> myNode = (Node<E>) node;
		String parentString = "null";
		if (myNode.parent != null) {
			parentString = myNode.parent.element.toString();
		}

		return myNode.element + "_p(" + parentString + ")";
	}
}