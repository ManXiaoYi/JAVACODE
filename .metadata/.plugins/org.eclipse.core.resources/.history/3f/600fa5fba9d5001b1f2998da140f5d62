package com.my.tree;

import java.util.Comparator;

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
		int height;
		
		public AVLNode(E element, Node<E> parent) {
			super(element, parent);
		}
			
		// 计算平衡因子
		private int balanceFactor() {
			int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
			int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
			return leftHeight - rightHeight;
		}
	}
	
	// 是否平衡
	private boolean isBalanced(Node<E> node) {
		return Math.abs(((AVLNode<E>)node).balanceFactor()) <= 1;
	}
	
	@Override
	// 新添加节点之后的处理
	protected void afterAdd(Node<E> node) {
		// node的父节点 不为null时 循环
		while ((node = node.parent) != null) {
			if (isBalanced(node)) {
				
			}
		}
		
		
	}
}
