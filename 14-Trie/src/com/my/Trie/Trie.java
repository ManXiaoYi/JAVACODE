package com.my.Trie;

import java.util.HashMap;

public class Trie<V> {
	private int size;
	private Node<V> root;

	// 暴露方法
	// --------------------------------------------------------------------------
	public int size() {
		return size;
	};

	public boolean isEmpty() {
		return size == 0;
	};

	public void clear() {
		size = 0;
		root = null;
	};

	public V get(String key) {
		Node<V> node = node(key);

		// node存在 & 是单词
		return (node != null && node.word) ? node.value : null;
	}

	public boolean contains(String key) {
		Node<V> node = node(key);

		return node != null && node.word;
	};

	public V add(String key, V value) {
		keyCheck(key);

		// 创建根节点
		if (root == null) {
			root = new Node<>(null);
		}

		Node<V> node = root;

		int length = key.length();
		for (int i = 0; i < length; i++) {
			char c = key.charAt(i);

			boolean emptyChildren = node.children == null;
			// 获取子节点
			Node<V> childNode = emptyChildren ? null : node.children.get(c);

			if (childNode == null) {
				childNode = new Node<>(node);
				childNode.character = c;
				node.children = emptyChildren ? new HashMap<>() : node.children;
				node.children.put(c, childNode);
			}
			node = childNode;
		}

		// 单词已经存在
		if (node.word) {
			V oldValue = node.value;
			// 覆盖
			node.value = value;
			return oldValue;
		}

		// 新增加一个单词
		node.word = true;
		node.value = value;
		size++;
		return null;
	};

	public V remove(String key) {
		// 找到最后一个节点
		Node<V> node = node(key);

		// 如果不是单词结尾，不用做任何操作
		if (node == null || !node.word) {
			return null;
		}

		size--;

		V oldValue = node.value;

		// 如果还有子节点
		if (node.children != null && !node.children.isEmpty()) {
			node.word = false;
			node.value = null;
			return oldValue;
		}

		// 如果没有子节点
		Node<V> parent = null;
		while ((parent = node.parent) != null) {
			parent.children.remove(node.character);

			if (parent.word || !parent.children.isEmpty()) {
				break;
			}
			node = parent;
		}

		return oldValue;
	};

	public boolean startsWith(String prefix) {
		return node(prefix) != null;
	};

	// 方法
	// --------------------------------------------------------------------------
	private void keyCheck(String key) {
		if (key == null || key.length() == 0) {
			throw new IllegalArgumentException("key must not be empty");
		}
	}

	private Node<V> node(String key) {
		keyCheck(key);

		Node<V> node = root;

		int length = key.length();
		for (int i = 0; i < length; i++) {
			// node、node的子节点、node子节点的值 为null返回
			if (node == null || node.children == null || node.children.isEmpty()) {
				return null;
			}

			char c = key.charAt(i);
			// 向下传递
			node = node.children.get(c);
		}

		return node;
	}

	// node相关
	// --------------------------------------------------------------------------
	private static class Node<V> {
		// 父节点
		Node<V> parent;
		// 子节点
		HashMap<Character, Node<V>> children;
		
		// 存储对应的字符
		Character character;
		
		// 是否为单词的结尾（是否为一个完整的单词）
		boolean word;
		// word为true的时候存值
		V value;

		public Node(Node<V> parent) {
			this.parent = parent;
		}
	}

}
