package LeCoTest;

/**
 * 给你一个链表数组，每个链表都已经按升序排列。
 * <p>
 * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
 * <p>
 * 示例 1：
 * <p>
 * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
 * 输出：[1,1,2,3,4,4,5,6]
 * 解释：链表数组如下：
 * [
 * 1->4->5,
 * 1->3->4,
 * 2->6
 * ]
 * 将它们合并到一个有序链表中得到。
 * 1->1->2->3->4->4->5->6
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-k-sorted-lists
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class CombineNodeList {

    static class ListNode {
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

    public static ListNode mergeKLists(ListNode[] lists) {

        Integer[] ints = new Integer[lists.length];
        ListNode head = null;
        ListNode current = null;
        boolean notNullFlag = false;
        if (lists.length == 0) {
            return null;
        }

        for (int i = 0; i < lists.length; i++) {
            if (null != lists[i]) {
                notNullFlag = true;
                break;
            }
        }

        while (notNullFlag) {
            // 往数组中赋值
            for (int i = 0; i < lists.length; i++) {
                if (lists[i] != null) {
                    ints[i] = lists[i].val;
                } else {
                    ints[i] = null;
                }
            }

            Integer minInt = null;
            Integer tmp = null;

            // 取数组中最小值
            for (int i = 0; i < lists.length; i++) {
                tmp = ints[i];
                if (null != tmp && (minInt == null || tmp < minInt)) {
                    minInt = tmp;
                }
            }

            // 确定个数
            int count = 0;
            for (int i = 0; i < lists.length; i++) {
                if (minInt.equals(ints[i])) {
                    count++;
                    if (null != lists[i]) {
                        lists[i] = lists[i].next;
                    }
                }

            }

            // 拼接
            for (int i = 0; i < count; i++) {
                if (head == null) {
                    head = new ListNode(minInt);
                    current = head;
                } else {
                    current.next = new ListNode(minInt);
                    current = current.next;
                }
            }

            // 判断是否有不为null的值
            notNullFlag = false;
            l1:
            for (int i = 0; i < lists.length; i++) {
                if (lists[i] != null) {
                    notNullFlag = true;
                    break l1;
                }
            }
        }
        return head;
    }

    public static void main(String[] args) {

        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(4);
//        ListNode listNode3 = new ListNode(8);

        listNode1.next = listNode2;
//        listNode2.next = listNode3;


        ListNode listNode4 = new ListNode(2);
        ListNode listNode5 = new ListNode(4);
        ListNode listNode6 = new ListNode(5);

        listNode4.next = listNode5;
        listNode5.next = listNode6;

        ListNode listNode7 = new ListNode(1);
        ListNode listNode8 = new ListNode(3);
        ListNode listNode9 = new ListNode(7);
        listNode7.next = listNode8;
        listNode8.next = listNode9;
//        ListNode listNode = mergeKLists(new ListNode[]{listNode1, listNode4, listNode7});
        ListNode listNode = mergeKLists(new ListNode[]{new ListNode(1), new ListNode(0)});

        do {
            System.out.print(listNode.val);
            listNode = listNode.next;
        } while (null != listNode.next);

        System.out.print(listNode.val);
    }
}
