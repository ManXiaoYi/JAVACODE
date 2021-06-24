package 二叉树;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/binary-tree-inorder-traversal/
 * 给定一个二叉树的根节点 root ，返回它的 中序 遍历。
 *
 * 输入：root = [1,null,2,3]
 * 输出：[1,3,2]
 */

public class _94_二叉树的中序遍历 {
	// 递归
    public List<Integer> inorderTraversal(TreeNode root) {
    	List<Integer> res = new ArrayList<>();

		// 遍历 整棵树
    	inorder(root, res);
    	
    	return res;
    }

    public void inorder(TreeNode root, List<Integer> res) {
		if (root == null) {
			return;
		}

		// 访问 左子树
		inorder(root.left, res);
		// 访问 根节点
		res.add(root.val);
		// 访问 右子树
		inorder(root.right, res);
	}
}
