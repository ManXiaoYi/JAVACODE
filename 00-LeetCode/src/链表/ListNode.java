package 链表;

/**
 * 链表相关题目
 * 
 * 1、指针指向 与 节点赋值（237 - 删除节点） 
 * 2、递归方法（206 - 反转链表） 
 * 3、迭代方法（206 - 反转链表） 
 * 4、快慢指针（141 - 环形链表）
 * 5、虚拟头节点（203 - 移除元素）
 * 
 * 
 * 
 * 
 * 
 */

public class ListNode {
	int val;
	ListNode next;

	ListNode() {
	}

	ListNode(int val) {
		this.val = val;
	}

	ListNode(int val, ListNode next) {
		this.val = val;
		this.next = next;
	}
}