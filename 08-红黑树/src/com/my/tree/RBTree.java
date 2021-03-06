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
		
		@Override
		public String toString() {
			String str = "";
			if (color == RED) {
				str = "R_";
			}
			return str + element.toString();
		}
	}
	
	@Override
	protected Node<E> createNode(E element, Node<E> parent) {
		return new RBNode<E>(element, parent);
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
		
		// 添加的是根节点 or 上溢到达了根节点
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
	protected void afterRemove(Node<E> node, Node<E> repalcement) {
		// 如果删除的节点是红色
		if (isRed(node)) {
			return;
		}
		
		// 用以取代node的子节点是红色
		if (isRed(repalcement)) {
			black(repalcement);
			return;
		}
		
		Node<E> parent = node.parent;
		
		// 删除的是根节点
		if (parent == null) {
			return;
		}
		
		// 删除的是黑色叶子节点【下溢】
		// 判断被删除的node是左还是右 - 删除操作之后，做此操作，只需判断被删节点是左右节点，可推导此节点
		boolean left = parent.left == null || node.isLeftChild();
		Node<E> sibling = left ? parent.right : parent.left;
		
		if (left) { 
			// 被删除的节点在 左边，兄弟节点在 右边
			if (isRed(sibling)) { // 兄弟节点是红色
				black(sibling);
				red(parent);
				rotateLeft(parent);
				// 更换兄弟
				sibling = parent.right;
			}
			
			// 兄弟节点必然是黑色
			if (isBlack(sibling.left) && isBlack(sibling.right)) {
				// 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
				boolean parentBlack = isBlack(parent);
				black(parent);
				red(sibling);
				// 父节点为黑，下溢
				if (parentBlack) {
					afterRemove(parent, null);
				}
				
			} else {
				// 兄弟节点至少有1个红色子节点，向兄弟节点借元素
				// 兄弟节点的左边是黑色，兄弟要先旋转
				if (isBlack(sibling.right)) {
					rotateRight(sibling);
					sibling = parent.right;
				}
				
				color(sibling, colorOf(parent));
				black(sibling.right);
				black(parent);
				rotateLeft(parent);
			}
			
			
		} else {
			// 被删除的节点在 右边，兄弟节点在 左边
			if (isRed(sibling)) { // 兄弟节点是红色
				black(sibling);
				red(parent);
				rotateRight(parent);
				// 更换兄弟
				sibling = parent.left;
			}
			
			// 兄弟节点必然是黑色
			if (isBlack(sibling.left) && isBlack(sibling.right)) {
				// 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
				boolean parentBlack = isBlack(parent);
				black(parent);
				red(sibling);
				// 父节点为黑，下溢
				if (parentBlack) {
					afterRemove(parent, null);
				}
				
			} else {
				// 兄弟节点至少有1个红色子节点，向兄弟节点借元素
				// 兄弟节点的左边是黑色，兄弟要先旋转
				if (isBlack(sibling.left)) {
					rotateLeft(sibling);
					sibling = parent.left;
				}
				
				color(sibling, colorOf(parent));
				black(sibling.left);
				black(parent);
				rotateRight(parent);
			}
			
		}
		
		
	}

}
