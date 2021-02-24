package MyLeetCode;



import java.util.*;


/**
 * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
 *
 * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
 * 说明:
 *
 * 所有节点的值都是唯一的。
 * p、q 为不同节点且均存在于给定的二叉树中。
 */
public class LowestCommonAncestorTest2 {

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(6);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(8);
        TreeNode node4 = new TreeNode(0);
        TreeNode node5 = new TreeNode(4);
        TreeNode node6 = new TreeNode(7);
        TreeNode node7 = new TreeNode(9);
        TreeNode node8 = new TreeNode(3);
        TreeNode node9 = new TreeNode(5);
        node1.left = node2;
        node1.right = node3;

        node2.left = node4;
        node2.right = node5;

        node3.left = node6;
        node3.right = node7;

        node5.left = node8;
        node5.right = node9;

        TreeNode treeNode = lowestCommonAncestor(node1, node2, node5);

        System.out.println(treeNode.val);

//        Stack<TreeNode> treeNodeStack = new Stack<>();
//
//        treeNodeStack.push(node1);
//        treeNodeStack.push(node2);
//        treeNodeStack.push(node3);
//        treeNodeStack.push(node4);
////        System.out.println(treeNodeStack);
//        for (TreeNode  treeNode : treeNodeStack) {
//            System.out.print(treeNode.val);
//        }
    }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 遍历树，得到两个节点的trace，逆序比trace
        // 二分查找，找到一个链路，再比较两个链路的大小
        List<Integer> integers1 = genTrace(root, p.val);
        List<Integer> integers2 = genTrace(root, q.val);
        LinkedHashSet<Integer> integerMap = new LinkedHashSet<>();
        Collections.reverse(integers2);
        integers1.forEach(data -> integerMap.add(data));

        for (int i = 0; i < integers2.size(); i++) {
            if (integerMap.contains(integers2.get(i))) {
                return new TreeNode(integers2.get(i));
            }
        }
        integers1.forEach(System.out::print);
        System.out.println();
        integers2.forEach(System.out::print);
        return null;

    }

    public static List<Integer> genTrace(TreeNode root, int goal) {
        List<Integer> result = new ArrayList();
        Stack<TreeNode> treeNodeStack = new Stack<>();
        TreeNode tmpNode = root;
        Integer tmpInt = root.val;
        treeNodeStack.push(root);
        while (true) {

            if (tmpNode.val == goal) {
                treeNodeStack.push(tmpNode);
                break;
            } else {
                if (null != tmpNode.left && tmpNode.left.val != tmpInt) {
                    tmpNode = tmpNode.left;
                    treeNodeStack.push(tmpNode);
                    tmpInt = tmpNode.val;
                } else if (null != tmpNode.right && tmpNode.right.val != tmpInt) {
                    tmpNode = tmpNode.right;
                    treeNodeStack.push(tmpNode);
                    tmpInt = tmpNode.val;
                } else {
                    if (treeNodeStack.size() != 0) {
                        tmpNode = treeNodeStack.pop();
                    }
                    tmpInt = tmpNode.val;
                }
            }

//            if (treeNodeStack.size() != 0) {
//                tmpNode = treeNodeStack.pop();
//            }
            for (TreeNode  treeNode : treeNodeStack) {
                System.out.print(treeNode.val);
            }
        }

        while (treeNodeStack.size() != 0) {
            result.add(treeNodeStack.pop().val);
        }
        return result;
    }
}
