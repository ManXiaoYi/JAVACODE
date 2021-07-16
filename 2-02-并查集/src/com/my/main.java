package com.my;

import com.my.tools.Asserts;
import com.my.union.UnionFind_QF;
import com.my.union.UnionFind_QU;

public class main {
	public static void main(String[] args) {
		UnionFind_QU uf = new UnionFind_QU(12);
		uf.union(0, 1);
		uf.union(0, 3);
		uf.union(0, 4);
		uf.union(2, 3);
		uf.union(2, 5);

		uf.union(6, 7);

		uf.union(8, 10);
		uf.union(9, 10);
		uf.union(9, 11);

		Asserts.test(!uf.isSame(2, 7));
		
		uf.union(5, 6);
		
		Asserts.test(uf.isSame(2, 7));
	}
}
