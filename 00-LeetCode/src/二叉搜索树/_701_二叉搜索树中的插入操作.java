package 二叉搜索树;

/**
 * https://leetcode-cn.com/problems/insert-into-a-binary-search-tree/
 * 给定二叉搜索树（BST）的根节点和要插入树中的值，将值插入二叉搜索树。
 * 返回插入后二叉搜索树的根节点。
 * 输入数据 保证 ，新值和原始二叉搜索树中的任意节点值都不同。
 */

public class _701_二叉搜索树中的插入操作 {
	// 递归
    public TreeNode insertIntoBST3(TreeNode root, int val) {
    	if (root == null) {
			return new TreeNode(val);
		}
    
    	if (val < root.val) {
    		// 节点在左子树
			root.left = insertIntoBST3(root.left, val);
		} else {
			// 节点在右子树
			root.right = insertIntoBST3(root.right, val);
		}
    	
    	return root;
    }
    
    
	// 迭代2
    public TreeNode insertIntoBST2(TreeNode root, int val) {
		// 添加位置是第一个节点
    	if (root == null) {
			return new TreeNode(val) ;
		}

		// 添加位置不是第一个节点 - 找到父节点
    	TreeNode node = root;
    	TreeNode parent = root;
    	
    	while (node != null) {
			parent = node;
			
			node = val < node.val ? node.left : node.right;
		}

    	// 插入元素
    	if (val < parent.val) {
			parent.left = new TreeNode(val);
		} else {
			parent.right = new TreeNode(val);
		}
    	
    	return root;
    }
    
	// 迭代1
    public TreeNode insertIntoBST(TreeNode root, int val) {
		// 添加位置是第一个节点
    	if (root == null) {
			TreeNode newNode = new TreeNode(val, null, null);
			root = newNode;
			return root;
		}

		// 添加位置不是第一个节点 - 找到父节点
    	TreeNode node = root;
    	TreeNode parent = root;
    	int result = 0;
    	
    	while (node != null) {
			result = val - node.val;
			
			parent = node;
			
			if (result > 0) {
				node = node.right;
			} else if (result < 0) {
				node = node.left;
			} else { // 相等 元素覆盖
				node.val = val;
				return node;
			}
		}
    	
    	// 插入元素
    	TreeNode newNode = new TreeNode(val);
    	if (result > 0) {
			parent.right = newNode;
		} else {
			parent.left = newNode;
		}
    	
    	return root;
    }
}
