package 二叉树;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/invert-binary-tree/ 
 * 翻转一棵二叉树。
 * 
 * 谷歌：我们90％的工程师使用您编写的软件(Homebrew)， 
 * 但是您却无法在面试时在白板上写出翻转二叉树这道题，这太糟糕了。
 */

public class _226_翻转二叉树 {
	// 前序遍历 - 迭代
	public TreeNode invertTreeTwo(TreeNode root) {
		if (root == null) {
			return null;
		}
		
		// 借用栈的结构
		Stack<TreeNode> stack = new Stack<>();
		// 1. 将root入栈
		stack.push(root);
		
		while (!stack.isEmpty()) {
			// 弹出栈顶节点
			TreeNode node = stack.pop();
			
			// 左右互换
			TreeNode tmp = node.left;
			node.left = node.right;
			node.right = tmp;
			
			// node.right入栈
			if (node.right != null) {
				stack.push(node.right);
			}
			
			// node.left入栈栈
			if (node.left != null) {
				stack.push(node.left);
			}
		}
				
		return root;
	}
	
	// 中序遍历 - 迭代
	public TreeNode invertTree2Two(TreeNode root) {
		if (root == null) {
			return null;
		}
		
		Stack<TreeNode> stack = new Stack<>();
		
		TreeNode node = root;
		
		while (node != null) {
			stack.push(node);
			
			node = node.left;	
		}
		
		return root;
	}
	
	// 后序遍历 - 迭代
	public TreeNode invertTree3Two(TreeNode root) {
		
		return root;
	}

	// 前序遍历 - 递归
	public TreeNode invertTree(TreeNode root) {
		if (root == null) {
			return null;
		}

		TreeNode tmp = root.left;
		root.left = root.right;
		root.right = tmp;

		invertTree(root.left);
		invertTree(root.right);

		return root;
	}

	// 中序遍历- 递归
	public TreeNode invertTree2(TreeNode root) {
		if (root == null) {
			return null;
		}

		invertTree(root.left);

		TreeNode tmp = root.left;
		root.left = root.right;
		root.right = tmp;

		invertTree(root.left);

		return root;
	}

	// 后序遍历- 递归
	public TreeNode invertTree3(TreeNode root) {
		if (root == null) {
			return null;
		}

		invertTree(root.left);
		invertTree(root.right);

		TreeNode tmp = root.left;
		root.left = root.right;
		root.right = tmp;

		return root;
	}
	
	// 层序遍历
	public TreeNode invertTree4(TreeNode root) {
		if (root == null) {
			return null;
		}

		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		
		while (!queue.isEmpty()) {
			TreeNode node = queue.poll();
			
			TreeNode tmp = node.left;
			node.left = node.right;
			node.right = tmp;
			
			if (node.left != null) {
				queue.offer(node.left);
			}
			
			if (node.right != null) {
				queue.offer(node.right);
			}
			
		}

		return root;
	}
}
