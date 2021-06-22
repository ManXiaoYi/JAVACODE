package 链表;

/**
 * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/
 * 存在一个按升序排列的链表，给你这个链表的头节点 head ，
 * 请你删除所有重复的元素，使每个元素 只出现一次 。
 * 返回同样按升序排列的结果链表。
 * 
 * 输入：head = [1,1,2,3,3]
 * 输出：[1,2,3]
 * 
 */

public class _083_删除排序链表中的重复元素 {
	// 	递归方法
    public ListNode deleteDuplicates2(ListNode head) {
    	if (head == null || head.next == null) {
			return head;
		}    	
    	
    	// head.next 指向 去重后的链表
    	head.next = deleteDuplicates2(head.next);
    	
    	// 相等则去重
    	if (head.val == head.next.val) {
			head = head.next;
		}
    	
    	return head;
    }
	
    
	// 	迭代方法
    public ListNode deleteDuplicates(ListNode head) {
    	if (head == null || head.next == null) {
			return head;
		}
    	
    	// 存储头结点
    	ListNode temp = head;
    	
    	// 节点next不为null 循环
    	while (head.next != null) {
			if (head.val == head.next.val) {
				// 当前节点与下一节点的值 相同，删除下一节点
				head.next = head.next.next;
			} else {
				// 当前节点与下一节点的值 不同，指向下一节点
				head = head.next;
			}
		}
    	
    	return temp;
    }
	
}
