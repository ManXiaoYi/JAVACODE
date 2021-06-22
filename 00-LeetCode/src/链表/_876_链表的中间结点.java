package 链表;

/**
 * https://leetcode-cn.com/problems/middle-of-the-linked-list/
 * 给定一个头结点为 head 的非空单链表，返回链表的中间结点。
 * 如果有两个中间结点，则返回第二个中间结点。
 * 
 * 输入：[1,2,3,4,5,6]
 * 输出：此列表中的结点 4 
 */

public class _876_链表的中间结点 {
	// 方法一：快慢指针
	public ListNode middleNode(ListNode head) {
		ListNode slow = head;
		ListNode fast = head;
		
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}
		
		return slow;
    }
	
	// 方法二：计算长度
	public ListNode middleNode2(ListNode head) {
		ListNode temp = head;
		
		// 计算节点数
		int count = 1;
		while (head.next != null) {
			head = head.next;
			count++;
		}
		
		// 取出中间数
		count = count / 2 + 1;
		while (count > 1) {
			temp = temp.next;
			count--;
		}
		
		return temp;
    }
}
