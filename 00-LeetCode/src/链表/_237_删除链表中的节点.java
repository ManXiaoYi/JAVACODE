package 链表;

/**
 * https://leetcode-cn.com/problems/delete-node-in-a-linked-list/
 * 请编写一个函数，使其可以删除某个链表中给定的（非末尾）节点。 传入函数的唯一参数为 要被删除的节点 。
 * 
 * 输入：head = [4,5,1,9], node = 5 
 * 输出：[4,1,9]
 * 解释：给定你链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.
 * 
 * 如何让自己在世界上消失，但又不死？ —— 将自己完全变成另一个人，再杀了那个人就行了。
 */

public class _237_删除链表中的节点 {

	public void deleteNode(ListNode node) {
		// 当前节点值 = 存储下一节点值
		node.val = node.next.val;
		// 当前节点next 指向 下一节点值
		node.next = node.next.next;
	}

}
