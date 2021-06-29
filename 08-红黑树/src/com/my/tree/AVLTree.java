package com.my.tree;

import java.util.Comparator;

import com.my.tree.BinaryTree.Node;

public class AVLTree<E> extends BBST<E> {
	public AVLTree() {
		this(null);
	}

	public AVLTree(Comparator<E> comparator) {
		super(comparator);
	}

	@Override
	// 构造器
	protected Node<E> createNode(E element, Node<E> parent) {
		return new AVLNode<>(element, parent);
	}

	// 初始化
	private static class AVLNode<E> extends Node<E> {
		// 高度
		int height = 1;

		// 构造函数
		public AVLNode(E element, Node<E> parent) {
			super(element, parent);
		}

		// 计算平衡因子
		public int balanceFactor() {
			int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
			int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
			return leftHeight - rightHeight;
		}

		// 计算高度
		public void updateHeight() {
			int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
			int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
			height = 1 + Math.max(leftHeight, rightHeight);
		}

		// 高度最高的子节点
		public Node<E> tallerChild() {
			int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
			int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
			if (leftHeight > rightHeight)
				return left;
			if (leftHeight < rightHeight)
				return right;
			return isLeftChild() ? left : right;
		}

		@Override
		public String toString() {
			String parentString = "null";
			if (parent != null) {
				parentString = parent.element.toString();
			}
			return element + "_p(" + parentString + ")_h(" + height + ")";
		}
	}

	// 是否平衡
	private boolean isBalanced(Node<E> node) {
		return Math.abs(((AVLNode<E>) node).balanceFactor()) <= 1;
	}

	// 更新高度
	private void updateHeight(Node<E> node) {
		((AVLNode<E>) node).updateHeight();
	}

	// 恢复平衡 - 方法一
	private void rebalance1(Node<E> grandparent) {
		Node<E> parent = ((AVLNode<E>) grandparent).tallerChild();
		Node<E> node = ((AVLNode<E>) parent).tallerChild();
		if (parent.isLeftChild()) { // L
			if (node.isLeftChild()) { // LL
				rotateRight(grandparent);
			} else { // LR
				rotateLeft(parent);
				rotateRight(grandparent);
			}

		} else { // R
			if (node.isLeftChild()) { // RL
				rotateRight(parent);
				rotateLeft(grandparent);
			} else { // RR
				rotateLeft(grandparent);
			}
		}
	}

	@Override
	public void afterRotate(Node<E> grandparent, Node<E> parent, Node<E> child) {
		super.afterRotate(grandparent, parent, child);

		// 更新高度
		updateHeight(grandparent);
		updateHeight(parent);
	}

	@Override
	public void rotate(Node<E> r, Node<E> a, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f, Node<E> g) {
		super.rotate(r, a, b, c, d, e, f, g);

		updateHeight(b);
		updateHeight(f);
		updateHeight(d);
	}

	// 恢复平衡 - 方法二
	private void rebalance(Node<E> grandparent) {
		Node<E> parent = ((AVLNode<E>) grandparent).tallerChild();
		Node<E> node = ((AVLNode<E>) parent).tallerChild();
		if (parent.isLeftChild()) { // L
			if (node.isLeftChild()) { // LL
				rotate(grandparent, node.left, node, node.right, parent, parent.right, grandparent, grandparent.right);
			} else { // LR
				rotate(grandparent, parent.left, parent, node.left, node, node.right, grandparent, grandparent.right);
			}

		} else { // R
			if (node.isLeftChild()) { // RL
				rotate(grandparent, grandparent.left, grandparent, node.left, node, node.right, parent, parent.right);
			} else { // RR
				rotate(grandparent, grandparent.left, grandparent, parent.left, parent, node.left, node, node.right);
			}
		}
	}

	// 新添加节点之后的处理
	@Override
	protected void afterAdd(Node<E> node) {
		// node的父节点 不为null时 循环
		while ((node = node.parent) != null) {
			// 进入循环的都是node的父节点、祖父节点
			if (isBalanced(node)) {
				// 更新高度
				updateHeight(node);
			} else {
				// 恢复平衡 - 说明此节点 是不平衡的 & 是高度最低的不平衡节点
				rebalance(node);
				// 上面节点恢复平衡，整棵树恢复平衡
				break;
			}
		}
	}

	// 删除节点之后的处理
	@Override
	protected void afterRemove(Node<E> node) {
		// node的父节点 不为null时 循环
		while ((node = node.parent) != null) {
			// 进入循环的都是node的父节点、祖父节点
			if (isBalanced(node)) {
				// 更新高度
				updateHeight(node);
			} else {
				// 恢复平衡 - 说明此节点 是不平衡的 & 是高度最低的不平衡节点
				rebalance(node);
			}
		}
	}

}
