package com.my.map;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import com.my.printer.BinaryTreeInfo;
import com.my.printer.BinaryTrees;

@SuppressWarnings({ "unchecked", "rawtypes" })

public class HashMap<K, V> implements Map<K, V> {
	private static final boolean RED = false;
	private static final boolean BLACK = true;
	private int size;
	private static final int DEFAULT_CAPACITY = 1 << 4;
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;

	private Node<K, V>[] table;

	public HashMap() {
		table = new Node[DEFAULT_CAPACITY];
	}

	// 暴露方法
	// --------------------------------------------------------------------------
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
		if (size == 0) {
			return;
		}

		size = 0;
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
	}

	@Override
	public V put(K key, V value) {
		resize();

		int index = index(key);
		// 取出index位置的红黑树根节点
		Node<K, V> root = table[index];
		if (root == null) {
			root = createNode(key, value, null);
			table[index] = root;
			size++;
			fixAfterPut(root);
			return null;
		}

		// 添加新的节点到红黑树上面
		Node<K, V> parent = root;
		Node<K, V> node = root;
		int cmp = 0;
		K k1 = key;
		int h1 = hash(k1);
		Node<K, V> result = null;
		boolean searched = false; // 是否已经搜索过这个key
		do {
			parent = node;
			K k2 = node.key;
			int h2 = node.hash;
			if (h1 > h2) {
				cmp = 1;
			} else if (h1 < h2) {
				cmp = -1;
			} else if (Objects.equals(k1, k2)) {
				cmp = 0;
			} else if (k1 != null && k2 != null && k1 instanceof Comparable && k1.getClass() == k2.getClass()
					&& (cmp = ((Comparable) k1).compareTo(k2)) != 0) {
			} else if (searched) { // 已经扫描了
				cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
			} else { // searched == false; 还没有扫描，然后再根据内存地址大小决定左右
				if ((node.left != null && (result = node(node.left, k1)) != null)
						|| (node.right != null && (result = node(node.right, k1)) != null)) {
					// 已经存在这个key
					node = result;
					cmp = 0;
				} else { // 不存在这个key
					searched = true;
					cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
				}
			}

			if (cmp > 0) {
				node = node.right;
			} else if (cmp < 0) {
				node = node.left;
			} else { // 相等
				V oldValue = node.value;
				node.key = key;
				node.value = value;
				node.hash = h1;
				return oldValue;
			}
		} while (node != null);

		// 看看插入到父节点的哪个位置
		Node<K, V> newNode = createNode(key, value, parent);
		if (cmp > 0) {
			parent.right = newNode;
		} else {
			parent.left = newNode;
		}
		size++;

		// 新添加节点之后的处理
		fixAfterPut(newNode);
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

	@Override
	public boolean containsKey(K key) {
		return node(key) != null;
	}

	@Override
	public boolean containsValue(V value) {
		if (size == 0) {
			return false;
		}

		Queue<Node<K, V>> queue = new LinkedList<>();
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null) {
				continue;
			}
			queue.offer(table[i]);

			while (!queue.isEmpty()) {
				Node<K, V> node = queue.poll();

				if (Objects.equals(value, node.value)) {
					return true;
				}

				if (node.left != null) {
					queue.offer(node.left);
				}
				if (node.right != null) {
					queue.offer(node.right);
				}
			}

		}

		return false;
	}

	@Override
	public void traversal(Visitor<K, V> visitor) {
		if (size == 0 || visitor == null) {
			return;
		}

		Queue<Node<K, V>> queue = new LinkedList<>();
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null) {
				continue;
			}
			queue.offer(table[i]);

			while (!queue.isEmpty()) {
				Node<K, V> node = queue.poll();

				if (visitor.visit(node.key, node.value)) {
					return;
				}

				if (node.left != null) {
					queue.offer(node.left);
				}
				if (node.right != null) {
					queue.offer(node.right);
				}
			}

		}

	}

	// 私有方法
	// --------------------------------------------------------------------------
	// 根据key生成对应的索引（在桶数组中的位置）
	private int index(K key) {
		return hash(key) & (table.length - 1);
	}

	// 根据node的key生成对应的索引
	private int index(Node<K, V> node) {
		return node.hash & (table.length - 1);
	}

	private int hash(K key) {
		if (key == null)
			return 0;
		int hash = key.hashCode();
		return hash ^ (hash >>> 16);
	}

	// 根据key查找node
	private Node<K, V> node(K key) {
		Node<K, V> root = table[index(key)];
		return root == null ? null : node(root, key);
	}
	
	private Node<K, V> node(Node<K, V> node, K k1) {
		int h1 = hash(k1);
		// 存储查找结果
		Node<K, V> result = null;
		int cmp = 0;
		while (node != null) {
			K k2 = node.key;
			int h2 = node.hash;
			// 先比较哈希值
			if (h1 > h2) {
				node = node.right;
			} else if (h1 < h2) {
				node = node.left;
			} else if (Objects.equals(k1, k2)) {
				return node;
			} else if (k1 != null && k2 != null 
					&& k1 instanceof Comparable
					&& k1.getClass() == k2.getClass()
					&& (cmp = ((Comparable)k1).compareTo(k2)) != 0) {
				node = cmp > 0 ? node.right : node.left;
			} else if (node.right != null && (result = node(node.right, k1)) != null) { 
				return result;
			} else { // 只能往左边找
				node = node.left;
			}
		}
		return null;
	}
	
	private V remove(Node<K, V> node) {
		if (node == null) {
			return null;
		}

		Node<K, V> willNode = node;

		size--;

		V oldValue = node.value;

		// node是度为2的节点
		if (node.hasTwoChildren()) {
			// 1. 找到后继节点
			Node<K, V> s = successor(node);
			// 2. 用后继节点的值 覆盖 度为2的节点的值
			node.key = s.key;
			node.value = s.value;
			node.hash = s.hash;
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
				table[index(node)] = replacement;
			} else if (node == node.parent.left) {
				node.parent.left = replacement;
			} else {
				node.parent.right = replacement;
			}

			// 删除节点之后的处理
			fixAfterRemove(replacement);
		} else if (node.parent == null) {
			// node是叶子节点 且 为根节点
			table[index(node)] = null;

			// 删除节点之后的处理
			fixAfterRemove(node);
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
			fixAfterRemove(node);
		}

		// 交给子类去处理
		afterRemove(willNode, node);

		return oldValue;
	}

	private void fixAfterPut(Node<K, V> node) {

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
			fixAfterPut(red(grand));
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

	protected void afterRemove(Node<K, V> willNode, Node<K, V> removedNode) {
	}

	private void fixAfterRemove(Node<K, V> node) {
		// 如果删除的节点是红色
		// 或者 用以取代删除节点的子节点是红色
		if (isRed(node)) {
			black(node);
			return;
		}
		
		Node<K, V> parent = node.parent;
		if (parent == null) return;
		
		// 删除的是黑色叶子节点【下溢】
		// 判断被删除的node是左还是右
		boolean left = parent.left == null || node.isLeftChild();
		Node<K, V> sibling = left ? parent.right : parent.left;
		if (left) { // 被删除的节点在左边，兄弟节点在右边
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
				if (parentBlack) {
					fixAfterRemove(parent);
				}
			} else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
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
		} else { // 被删除的节点在右边，兄弟节点在左边
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
				if (parentBlack) {
					fixAfterRemove(parent);
				}
			} else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
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
	
	// 扩容
	private void resize() {
		// 装填因子 <= 0.75
		if (size / table.length <= DEFAULT_LOAD_FACTOR)
			return;

		Node<K, V>[] oldTable = table;
		table = new Node[oldTable.length << 1];

		Queue<Node<K, V>> queue = new LinkedList<>();
		for (int i = 0; i < oldTable.length; i++) {
			if (oldTable[i] == null)
				continue;

			queue.offer(oldTable[i]);
			while (!queue.isEmpty()) {
				Node<K, V> node = queue.poll();
				if (node.left != null) {
					queue.offer(node.left);
				}
				if (node.right != null) {
					queue.offer(node.right);
				}

				// 挪动代码得放到最后面
				moveNode(node);
			}
		}
	}

	// 挪动代码得放到最后面
	private void moveNode(Node<K, V> newNode) {
		// 重置
		newNode.parent = null;
		newNode.left = null;
		newNode.right = null;
		newNode.color = RED;

		int index = index(newNode);
		// 取出index位置的红黑树根节点
		Node<K, V> root = table[index];
		if (root == null) {
			root = newNode;
			table[index] = root;
			fixAfterPut(root);
			return;
		}

		// 添加新的节点到红黑树上面
		Node<K, V> parent = root;
		Node<K, V> node = root;
		int cmp = 0;
		K k1 = newNode.key;
		int h1 = newNode.hash;
		do {
			parent = node;
			K k2 = node.key;
			int h2 = node.hash;
			if (h1 > h2) {
				cmp = 1;
			} else if (h1 < h2) {
				cmp = -1;
			} else if (k1 != null && k2 != null && k1 instanceof Comparable && k1.getClass() == k2.getClass()
					&& (cmp = ((Comparable) k1).compareTo(k2)) != 0) {
			} else {
				cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
			}

			if (cmp > 0) {
				node = node.right;
			} else if (cmp < 0) {
				node = node.left;
			}
		} while (node != null);

		// 看看插入到父节点的哪个位置
		newNode.parent = parent;
		if (cmp > 0) {
			parent.right = newNode;
		} else {
			parent.left = newNode;
		}

		// 新添加节点之后的处理
		fixAfterPut(newNode);
	}

	// Node
	// --------------------------------------------------------------------------
	protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
		return new Node<>(key, value, parent);
	}

	// 创建node
	protected static class Node<K, V> {
		int hash;
		K key;
		V value;
		boolean color = RED;
		Node<K, V> left;
		Node<K, V> right;
		Node<K, V> parent;

		public Node(K key, V value, Node<K, V> parent) {
			this.key = key;
			int hash = key == null ? 0 : key.hashCode();
			this.hash = hash ^ (hash >>> 16);
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

		@Override
		public String toString() {
			return "Node_" + key + "_" + value;
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
			// grandparent/parent/child在同一颗红黑树上，所以谁都可取index
			table[index(grandparent)] = parent;
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

	// 打印相关
	// --------------------------------------------------------------------------
	public void print() {
		if (size == 0) {
			return;
		}

		for (int i = 0; i < table.length; i++) {
			final Node<K, V> root = table[i];
			System.out.println("【index = " + i + "】");

			BinaryTrees.println(new BinaryTreeInfo() {
				@Override
				public Object string(Object node) {
					return node;
				}

				@Override
				public Object root() {
					return root;
				}

				@Override
				public Object right(Object node) {
					return ((Node<K, V>) node).left;
				}

				@Override
				public Object left(Object node) {
					return ((Node<K, V>) node).right;
				}
			});
			System.out.println(
					"--------------------------------------------------------------------------------------------");
		}
	}

}
