package com.my.tree;

import java.util.Comparator;

public class RBTree<E> extends BBST<E> {
	private static final boolean RED = false;
	private static final boolean BLACK = true;

	// ----------------------  初始化  ----------------------
	public RBTree() {
		this(null);
	}

	public RBTree(Comparator<E> comparator) {
		super(comparator);
	}

	// 初始化
	private static class RBNode<E> extends Node<E> {
		// 默认RED，能够让红黑树的性质尽快满足，仅性质4不满足
		boolean color = RED;

		public RBNode(E element, Node<E> parent) {
			super(element, parent);
			// TODO Auto-generated constructor stub
		}
	}

	// ----------------------  颜色  ----------------------
	// 染色
	private Node<E> color(Node<E> node, boolean color) {
		if (node == null) {
			return node;
		}
		((RBNode<E>) node).color = color;
		return node;
	}

	// 染红
	private Node<E> red(Node<E> node) {
		return color(node, RED);
	}

	// 染黑
	private Node<E> black(Node<E> node) {
		return color(node, BLACK);
	}

	// 节点颜色
	private boolean colorOf(Node<E> node) {
		return node == null ? BLACK : ((RBNode<E>)node).color;
	}

	// 是否红色
	private boolean isRed(Node<E> node) {
		return colorOf(node) == RED;
	}

	// 是否黑色
	private boolean isBlack(Node<E> node) {
		return colorOf(node) == BLACK;
	}
	
	// ----------------------  方法  ----------------------
	@Override
	protected void afterAdd(Node<E> node) {
		Node<E> parent = node.parent;
		
		// 添加的是根节点
		if (parent == null) {
			black(node);
			return;
		}
		
		// 1、如果父节点是黑色，直接返回
		if (isBlack(parent)) {
			return;
		}
		
		// 叔父节点
		Node<E> uncle = parent.sibling();
		// 祖父节点
		Node<E> grand = parent.parent;
		
		if (isRed(uncle)) {
			// 叔父节点是红色
			black(parent);
			black(uncle);
			// 把祖父节点当做是 新添加的节点 - 递归调用
			afterAdd(red(grand));
			return;
			
		} 

		// 叔父节点 不是红色
		if (parent.isLeftChild()) { // L
			red(grand);
			if (node.isLeftChild()) { // LL
				black(parent);
			} else { // LR
				black(node);
				rotateLeft(parent);
			}
			rotateRight(grand);
			
		} else { // R
			red(grand);
			if (node.isLeftChild()) { // RL
				black(node);
				rotateRight(parent);				
			} else { // RR
				black(parent);
			}
			rotateLeft(grand);
		}
		
	}

	@Override
	protected void afterRemove(Node<E> node) {
		// TODO Auto-generated method stub
		super.afterRemove(node);
	}
}
