package com.my.union;

public class UnionFind_QU extends UnionFind {

	public UnionFind_QU(int capacity) {
		super(capacity);
	}

	/**
	 * 通过parent链表不断地往上找，直到找到根节点
	 */
	@Override
	public int find(int v) {
		rangeCheck(v);
		
		// v和其父节点不相等，循环至 v和v的父节点相等
		while (v != parents[v]) {
			// 往上找
			v = parents[v];
		}
		
		// v为根节点
		return v;
	}

	/**
	 * 将v1的根节点 嫁接到 v2的根节点上
	 */
	@Override
	public void union(int v1, int v2) {
		int p1 = find(v1);
		int p2 = find(v2);
		if (p1 == p2) {
			return;
		}
		
		// v1的根节点p1，p1新的根节点是p2
		parents[p1] = p2;
	}

}
