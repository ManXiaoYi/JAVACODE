package com.my.tree;

import java.util.Comparator;

public class BBST<E> extends BST<E> {

	public BBST() {
		this(null);
	}

	public BBST(Comparator<E> comparator) {
		super(comparator);
	}

	// 左旋转
	public void rotateLeft(Node<E> grandparent) {
		Node<E> parent = grandparent.right;
		Node<E> child = parent.left;

		grandparent.right = child;
		parent.left = grandparent;

		afterRotate(grandparent, parent, child);
	}

	// 右旋转
	public void rotateRight(Node<E> grandparent) {
		Node<E> parent = grandparent.left;
		Node<E> child = parent.right;

		grandparent.left = child;
		parent.right = grandparent;

		afterRotate(grandparent, parent, child);
	}

	public void afterRotate(Node<E> grandparent, Node<E> parent, Node<E> child) {
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

	}

	// 旋转
	public void rotate(Node<E> r, // 子树的根节点
			Node<E> a, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f, Node<E> g) {
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

		// r - f - g
		f.left = e;
		if (e != null) {
			e.parent = f;
		}
		f.right = g;
		if (g != null) {
			g.parent = f;
		}

		// b - d - f
		d.left = b;
		b.parent = d;
		d.right = f;
		f.parent = d;
	}

}
