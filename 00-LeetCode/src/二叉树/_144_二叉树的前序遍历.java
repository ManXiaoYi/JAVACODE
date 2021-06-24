package 二叉树;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/binary-tree-preorder-traversal/
 * 给你二叉树的根节点 root ，返回它节点值的 前序 遍历。
 *
 * 输入：root = [1,null,2,3]
 * 输出：[1,2,3]
 */

public class _144_二叉树的前序遍历 {	
	// 迭代 - 利用栈实现
	public List<Integer> preorderTraversal2(TreeNode root) {
		List<Integer> res = new ArrayList<>();
		
		if (root == null) {
			return res;
		}
		
		Stack<TreeNode> stack = new Stack<>();
		stack.push(root);
		
		while (!stack.isEmpty()) {
			TreeNode node = stack.pop();
			
			res.add(node.val);
			
			if (node.right != null) {
				stack.push(node.right);
			}
			
			if (node.left != null) {
				stack.push(node.left);
			}
		}
		
		return res;
	}
	
	
	// 递归
	public List<Integer> preorderTraversal(TreeNode root) {
		List<Integer> res = new ArrayList<>();
	
		// 遍历 整棵树
		preorder(root, res);
		
		return res;
	}
	
	public void preorder(TreeNode root, List<Integer> res) {
		if (root == null) {
			return;
		}
		
		// 访问 根节点
		res.add(root.val);
		// 访问 左子树
		preorder(root.left, res);
		// 访问 右子树
		preorder(root.right, res);
	}
}
