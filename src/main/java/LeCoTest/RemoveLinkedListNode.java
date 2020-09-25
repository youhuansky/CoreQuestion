package LeCoTest;

/**
 * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
 * <p>
 * 示例：
 * <p>
 * 给定一个链表: 1->2->3->4->5, 和 n = 2.
 * <p>
 * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class RemoveLinkedListNode {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {

        // 循环了几次
        Integer cursor = 1;
        // 当前循环到的节点
        ListNode current;
        // 当前循环到的节点的前n个
        ListNode tmp = null;
        // 当前循环到的节点的前n+1个
        ListNode tmp2 = null;
        // 头节点
        ListNode headNode = head;
        if (head.next == null) {
            return null;
        }

        while (null != head.next) {

            if (n != 1) {
                if (cursor.equals(n - 1)) {
                    tmp = headNode;
                }

                if (cursor > n - 1) {
                    tmp = tmp.next;
                }
            }
            if (cursor.equals(n)) {
                tmp2 = headNode;
            }

            if (cursor > n) {
                tmp2 = tmp2.next;
            }

            head = head.next;
            cursor++;
        }

        // 循环完一遍将倒数n 删除
        if (cursor.equals(n)) {
            return tmp.next;
        } else if (n == 1) {
            tmp2.next = null;
        } else {
            tmp2.next = tmp.next;
        }


        return headNode;
    }


    public static void main(String[] args) {

        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        ListNode listNode6 = new ListNode(6);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        listNode5.next = listNode6;
        ListNode listNode = removeNthFromEnd(listNode1, 1);


        do {
            System.out.print(listNode.val);
            listNode = listNode.next;
        } while (null != listNode.next);

        System.out.print(listNode.val);
    }
}
