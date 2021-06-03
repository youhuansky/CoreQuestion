package MyLeetCode;

/**
 * 输入一个链表，输出该链表中倒数第k个节点。为了符合大多数人的习惯，本题从1开始计数，即链表的尾节点是倒数第1个节点。
 *
 * 例如，一个链表有 6 个节点，从头节点开始，它们的值依次是 1、2、3、4、5、6。这个链表的倒数第 3 个节点是值为 4 的节点。
 *
 *  
 *
 * 示例：
 *
 * 给定一个链表: 1->2->3->4->5, 和 k = 2.
 *
 * 返回链表 4->5.

 */

public class ReverseLinkedList {


    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        ListNode kthFromEnd = getKthFromEnd(listNode1, 0);
        System.out.println(kthFromEnd.val);
    }

    public static ListNode getKthFromEnd(ListNode head, int k) {
        ListNode currentNode = head;
        ListNode resultNode = null;
        if (k ==0) {
            resultNode = head;
        }
        int num = 1;
        while(null != currentNode) {
            if (null != resultNode) {
                resultNode = resultNode.next;
            }
            currentNode = currentNode.next;
            if(num == k) {
                resultNode = head;
            }

            num ++;
        }
        return resultNode;
    }
}



