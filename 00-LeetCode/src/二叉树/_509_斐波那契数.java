package 二叉树;

/**
 * https://leetcode-cn.com/problems/fibonacci-number/
 * 斐波那契数，通常用 F(n) 表示，形成的序列称为 斐波那契数列 。
 * 该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。
 * 
 * F(0) = 0，F(1) = 1
 * F(n) = F(n - 1) + F(n - 2)，其中 n > 1
 * 给你 n ，请计算 F(n) 。
 */

public class _509_斐波那契数 {
	
    public int fib(int n) {
    	if (n <= 1) {
			return n;
		}
    	
    	int first = 0;
    	int second = 1;
    	while (n-- > 1) {
			second = first + second;
			first = second = first;
		}
    	return second;
    }
	
	// 递归
	public int fib1(int n) {
		if (n <= 1) {
			return n;
		}			
		return  fib1(n - 1) + fib1(n - 2);
    }
}
