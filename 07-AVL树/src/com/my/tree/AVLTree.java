package com.my.tree;

import java.util.Comparator;

import com.my.tree.BinaryTree.Node;

public class AVLTree<E> extends BST<E> {
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
			int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
			int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
			return leftHeight - rightHeight;
		}
		
		// 计算高度
		public void updateHeight() {
			int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
			int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
			height = 1 + Math.max(leftHeight, rightHeight);
		}
		
		// 高度最高的子节点
		public Node<E> tallerChild() {
			int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
			int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
			if (leftHeight > rightHeight) return left;
			if (leftHeight < rightHeight) return right;
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
		return Math.abs(((AVLNode<E>)node).balanceFactor()) <= 1;
	}
	
	// 更新高度
	private void updateHeight(Node<E> node) {
		((AVLNode<E>)node).updateHeight();
	}
	
	// 恢复平衡 - 方法一
	private void rebalance1(Node<E> grandparent) {
		Node<E> parent = ((AVLNode<E>)grandparent).tallerChild();
		Node<E> node = ((AVLNode<E>)parent).tallerChild();
		if (parent.isLeftChild()) { // L
			if (node.isLeftChild()) { // LL
				retateRight(grandparent);
			} else { // LR
				retateLeft(parent);
				retateRight(grandparent);
			}
			
		} else { // R
			if (node.isLeftChild()) { // RL
				retateRight(parent);
				retateLeft(grandparent);
			} else { // RR
				retateLeft(grandparent);
			}
		}
	}
	
	// 恢复平衡 - 方法二
	private void rebalance(Node<E> grandparent) {
		Node<E> parent = ((AVLNode<E>)grandparent).tallerChild();
		Node<E> node = ((AVLNode<E>)parent).tallerChild();
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
	
	// 左旋转
	private void retateLeft(Node<E> grandparent) {
		Node<E> parent = grandparent.right;
		Node<E> child = parent.left;
		
		grandparent.right = child;
		parent.left = grandparent;
		
		afterRotate(grandparent, parent, child);
	}
	
	// 右旋转
	private void retateRight(Node<E> grandparent) {
		Node<E> parent = grandparent.left;
		Node<E> child = parent.right;
		
		grandparent.left = child;
		parent.right = grandparent;
		
		afterRotate(grandparent, parent, child);
	}
	
	private void afterRotate(Node<E> grandparent, Node<E> parent, Node<E> child) {
		// 让parent成为 子树的根节点
		parent.parent = grandparent.parent;
		if (grandparent.isLeftChild()) {
			grandparent.parent.left = parent;
		} else if (grandparent.isRightChild()) {
			grandparent.parent.right = parent;
		} else { // grandparent是 root节点
			root = parent;
		}
		
		// 更新child的 parent
		if (child != null) {
			child.parent = grandparent;
		}

		// 更新grandparent的 parent
		grandparent.parent = parent;
		
		// 更新高度
		updateHeight(grandparent);
		updateHeight(parent);
	}
	
	// 旋转
	private void rotate(
			Node<E> r, // 子树的根节点
			Node<E> a, Node<E> b, Node<E> c,
			Node<E> d,
			Node<E> e, Node<E> f, Node<E> g) {
		// 让d成为这棵子树的根节点
		d.parent = r.parent;
		if (r.isLeftChild()) {
			r.parent.left = d;
		} else if (r.isRightChild()) {
			r.parent.right = d;
		} else {
			root = d;
		}
		
		// a - b - c
		b.left = a;
		if (a != null) {
			a.parent = b;
		}
		b.right = c;
		if (c != null) {
			c.parent = b;
		}
		updateHeight(b);
		
		// r - f - g
		f.left = e;
		if (e != null) {
			e.parent = f;
		}
		f.right = g;
		if (g != null) {
			g.parent = f;
		}
		updateHeight(f);

		// b - d - f
		d.left = b;
		b.parent = d;
		d.right = f;
		f.parent = d;
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
