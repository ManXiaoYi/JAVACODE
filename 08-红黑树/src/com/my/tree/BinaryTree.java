package com.my.tree;

import java.util.LinkedList;
import java.util.Queue;
import com.my.printer.BinaryTreeInfo;

@SuppressWarnings("unchecked")

public class BinaryTree<E> implements BinaryTreeInfo {
	// 大小
	protected int size;
	// 根节点
	protected Node<E> root;
	
	protected static class Node<E> {
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

		// 是否是左节点
		public boolean isLeftChild() {
			return parent != null && this == parent.left;
		}
		
		// 是否是右节点
		public boolean isRightChild() {
			return parent != null && this == parent.right;
		}
		
		// 兄弟节点
		public Node<E> sibling() {
			if (isLeftChild()) {
				return parent.right;
			}
			if (isRightChild()) {
				return parent.left;
			}
			return null;
		}
		
	}
	
	// 创建通用构造器 
	protected Node<E> createNode(E element, Node<E> parent) {
			return new Node<>(element, parent);
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
	
	// 前驱节点 - 后继节点
	// --------------------------------------------------------------------------
	// 前驱结点：中序遍历时的前一个节点
	protected Node<E> predecessor(Node<E> node) {
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
	protected Node<E> successor(Node<E> node) {
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
		return node;
	}
}
