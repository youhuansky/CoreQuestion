package MyLeetCode;

/**
 * @ClassName ReverseListTest
 * @Description
 * @Author youhuan
 * @Date 2019/6/4 20:37
 **/
public class ReverseListTest {

    /**
     * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
     * <p>
     * k 是一个正整数，它的值小于或等于链表的长度。
     * <p>
     * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
     * <p>
     * 示例 :
     * <p>
     * 给定这个链表：1->2->3->4->5
     * <p>
     * 当 k = 2 时，应当返回: 2->1->4->3->5
     * <p>
     * 当 k = 3 时，应当返回: 3->2->1->4->5
     * <p>
     * 说明 :
     * <p>
     * 你的算法只能使用常数的额外空间。
     * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
     **/
    public static ListNode reverseKGroup(ListNode head, int k) {
        int num = 0;
        ListNode tmp = head;
        while (null != tmp) {
            num++;
            tmp = tmp.next;
        }
        if (k > num) {
            return head;
        }
        //余数
        int modNum = num % k;
        //商
        int divNum = num / k;

        ListNode[] listNodes = new ListNode[divNum];

        ListNode[] tmpListNodes = new ListNode[k];

        //剩余的
        ListNode restHead = head;
        ListNode tail = null;

        for (int i = 0; i < divNum; i++) {
            listNodes[i] = restHead;

            for (int j = 0; j < k; j++) {
                tmp = restHead;
                restHead = restHead.next;
                tmp.next = null;
                tmpListNodes[k - j - 1] = tmp;
                if (j != 0) {
                    tmpListNodes[k - j - 1].next = tmpListNodes[k - j];
                }
            }
            if (i != 0) {
                tail.next = tmpListNodes[0];
            }

            tail = tmpListNodes[k - 1];
            listNodes[i] = tmpListNodes[0];

        }
        tail.next = restHead;
        return listNodes[0];
    }

    public static void main(String[] args) {

        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        ListNode listNode6 = new ListNode(6);
        ListNode listNode7 = new ListNode(7);
        ListNode listNode8 = new ListNode(8);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        listNode5.next = listNode6;
        listNode6.next = listNode7;
        listNode7.next = listNode8;

        reverseKGroup(listNode1, 3);

    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}