package com.my.map;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class TreeMap<K, V> implements Map<K, V> {
	private static final boolean RED = false;
	private static final boolean BLACK = true;
	private int size;
	private Node<K, V> root;

	// 比较器
	private Comparator<K> comparator;

	// 构造方法
	public TreeMap() {
		this(null);
	}

	public TreeMap(Comparator<K> comparator) {
		this.comparator = comparator;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void clear() {
		root = null;
		size = 0;
	}

	@Override
	public V put(K key, V value) {
		keyNotNullCheck(key);

		// 添加位置是第一个节点
		if (root == null) {
			root = new Node<>(key, value, null);

			size++;

			// 新添加节点之后的处理
			afterPut(root);
			return null;
		}

		// 添加位置不是第一个节点 - 找到父节点
		Node<K, V> parent = root;
		Node<K, V> node = root;
		int compare = 0;

		while (node != null) {
			compare = compare(key, node.key);

			parent = node;

			if (compare > 0) {
				node = node.right;
			} else if (compare < 0) {
				node = node.left;
			} else { // 相等 元素覆盖
				node.key = key;
				V oldValue = node.value;
				node.value = value;
				return oldValue;
			}
		}

		// 插入元素
		Node<K, V> newNode = new Node<>(key, value, parent);
		if (compare > 0) {
			parent.right = newNode;
		} else {
			parent.left = newNode;
		}

		size++;

		// 新添加节点之后的处理
		afterPut(newNode);

		return null;
	}

	@Override
	public V get(K key) {
		Node<K, V> node = node(key);
		return node != null ? node.value : null;
	}

	@Override
	public V remove(K key) {
		return remove(node(key));
	}

	private V remove(Node<K, V> node) {
		if (node == null) {
			return null;
		}

		size--;

		V oldValue = node.value;

		// node是度为2的节点
		if (node.hasTwoChildren()) {
			// 1. 找到后继节点
			Node<K, V> s = successor(node);
			// 2. 用后继节点的值 覆盖 度为2的节点的值
			node.key = s.key;
			node.value = s.value;
			// 3. 删除后继节点 - 赋值给node，最后删除node即可
			node = s;
		}

		// node是度为0或1的节点
		Node<K, V> replacement = node.left != null ? node.left : node.right;

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
			afterRemove(node, replacement);
		} else if (node.parent == null) {
			// node是叶子节点 且 为根节点
			root = null;

			// 删除节点之后的处理
			afterRemove(node, null);
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
			afterRemove(node, null);
		}

		return oldValue;
	}

	@Override
	public boolean containsKey(K key) {
		return node(key) != null;
	}

	@Override 
	// 层序遍历
	public boolean containsValue(V value) {
		if (root == null) {
			return false;
		}
		
		Queue<Node<K, V>> queue = new LinkedList<>();
		queue.offer(root);
		
		while (!queue.isEmpty()) {
			Node<K, V> node = queue.poll();
			
			if (valEquals(node.value, value)) {
				return true;
			}
			
			if (node.left != null) {
				queue.offer(node.left);
			}
			
			if (node.right != null) {
				queue.offer(node.right);
			}
		}

		return false;
	}

	@Override
	// 中序遍历
	public void traversal(Visitor<K, V> visitor) {
		if (visitor == null) {
			return;
		}
		traversal(root, visitor);
	}
	
	public void traversal(Node<K, V> node, Visitor<K, V> visitor) {
		if (node == null || visitor == null) {
			return;
		}

		traversal(node.left, visitor);
		
		if (visitor.stop) {
			return;
		}
		visitor.visit(node.key, node.value);
		
		traversal(node.right, visitor);
	}

	private static class Node<K, V> {
		K key;
		V value;
		boolean color = RED;
		Node<K, V> left;
		Node<K, V> right;
		Node<K, V> parent;

		public Node(K key, V value, Node<K, V> parent) {
			this.key = key;
			this.value = value;
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
		public Node<K, V> sibling() {
			if (isLeftChild()) {
				return parent.right;
			}
			if (isRightChild()) {
				return parent.left;
			}
			return null;
		}
	}

	// 方法相关
	// --------------------------------------------------------------------------
	// 元素比较
	private int compare(K element1, K element2) {
		if (comparator != null) {
			return comparator.compare(element1, element2);
		}

		return ((Comparable<K>) element1).compareTo(element2);
	}

	// 校验元素是否为null
	private void keyNotNullCheck(K key) {
		if (key == null) {
			throw new IllegalArgumentException("element must not be null");
		}
	}

	// 判断两者是否相等
	private boolean valEquals(V v1, V v2) {
		return v1 == null ? v2 == null : v1.equals(v2);
	}
	
	private Node<K, V> node(K key) {
		Node<K, V> node = root;
		while (node != null) {
			// 比较两者大小
			int cmp = compare(key, node.key);
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

	private void afterPut(Node<K, V> node) {

		Node<K, V> parent = node.parent;

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
		Node<K, V> uncle = parent.sibling();
		// 祖父节点
		Node<K, V> grand = parent.parent;

		if (isRed(uncle)) {
			// 叔父节点是红色
			black(parent);
			black(uncle);
			// 把祖父节点当做是 新添加的节点 - 递归调用
			afterPut(red(grand));
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

	private void afterRemove(Node<K, V> node, Node<K, V> repalcement) {
		// 如果删除的节点是红色
		if (isRed(node)) {
			return;
		}

		// 用以取代node的子节点是红色
		if (isRed(repalcement)) {
			black(repalcement);
			return;
		}

		Node<K, V> parent = node.parent;

		// 删除的是根节点
		if (parent == null) {
			return;
		}

		// 删除的是黑色叶子节点【下溢】
		// 判断被删除的node是左还是右 - 删除操作之后，做此操作，只需判断被删节点是左右节点，可推导此节点
		boolean left = parent.left == null || node.isLeftChild();
		Node<K, V> sibling = left ? parent.right : parent.left;

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

	// ---------------------- 颜色 ----------------------
	// 染色
	private Node<K, V> color(Node<K, V> node, boolean color) {
		if (node == null) {
			return node;
		}
		node.color = color;
		return node;
	}

	// 染红
	private Node<K, V> red(Node<K, V> node) {
		return color(node, RED);
	}

	// 染黑
	private Node<K, V> black(Node<K, V> node) {
		return color(node, BLACK);
	}

	// 节点颜色
	private boolean colorOf(Node<K, V> node) {
		return node == null ? BLACK : node.color;
	}

	// 是否红色
	private boolean isRed(Node<K, V> node) {
		return colorOf(node) == RED;
	}

	// 是否黑色
	private boolean isBlack(Node<K, V> node) {
		return colorOf(node) == BLACK;
	}

	// 旋转
	// --------------------------------------------------------------------------
	// 左旋转
	private void rotateLeft(Node<K, V> grandparent) {
		Node<K, V> parent = grandparent.right;
		Node<K, V> child = parent.left;

		grandparent.right = child;
		parent.left = grandparent;

		afterRotate(grandparent, parent, child);
	}

	// 右旋转
	private void rotateRight(Node<K, V> grandparent) {
		Node<K, V> parent = grandparent.left;
		Node<K, V> child = parent.right;

		grandparent.left = child;
		parent.right = grandparent;

		afterRotate(grandparent, parent, child);
	}

	private void afterRotate(Node<K, V> grandparent, Node<K, V> parent, Node<K, V> child) {
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

	// 前驱节点 - 后继节点
	// --------------------------------------------------------------------------
	// 前驱结点：中序遍历时的前一个节点
	private Node<K, V> predecessor(Node<K, V> node) {
		if (node == null) {
			return null;
		}

		// 前驱节点在左子树当中 .left.right.right.right...
		Node<K, V> p = node.left;
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
	private Node<K, V> successor(Node<K, V> node) {
		if (node == null) {
			return null;
		}

		// 前驱节点在右子树当中 .right.left.left.left...
		Node<K, V> p = node.right;
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
}
