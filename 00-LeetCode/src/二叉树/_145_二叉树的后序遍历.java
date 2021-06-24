package 二叉树;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/binary-tree-postorder-traversal/
 * 给定一个二叉树，返回它的 后序 遍历。
 *
 * 输入: [1,null,2,3]  
 * 输出: [3,2,1]
 */

public class _145_二叉树的后序遍历 {
	// 递归
    public List<Integer> postorderTraversal(TreeNode root) {
    	List<Integer> res = new ArrayList<>();

		// 遍历 整棵树
    	postorder(root, res);
    	
    	return res;
    }
    
    public void postorder(TreeNode root, List<Integer> res) {
		if (root == null) {
			return;
		}

		// 访问 左子树
		postorder(root.left, res);
		// 访问 右子树
		postorder(root.right, res);
		// 访问 根节点
		res.add(root.val);
	}
}
