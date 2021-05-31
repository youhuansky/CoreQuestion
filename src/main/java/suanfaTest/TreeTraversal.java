package suanfaTest;

import java.util.Stack;

public class TreeTraversal {


    static class Node {
        int data;   //节点值
        Node leftChild;  //左孩子
        Node rightChild; //右孩子

        public Node(int data) {
            this.data = data;
        }
    }


    public static void inOrder(Node root) {
        if (root == null) {
            System.out.println("空树");
            return;
        }
        Node tmp = root;
        Stack<Node> s = new Stack<>();
        while (tmp != null || !s.empty()) {
            //1.将根节点入栈
            //2.将所有左孩子入栈
            while (tmp != null) {
                s.push(tmp);
                tmp = tmp.leftChild;
            }
            //3.访问栈顶元素
            tmp = s.pop();
            System.out.print(tmp.data + " ");
            //4.如果栈顶元素存在右孩子，则将右孩子赋值给tmp，也就是将右孩子入栈
            if (tmp.rightChild != null) {
                tmp = tmp.rightChild;
            }
            //否则，将tmp置为null，表示下次要访问的是栈顶元素
            else {
                tmp = null;
            }
        }
        System.out.println();
    }

    public static void preOrder(Node root) {

        if (null == root) {
            return;
        }

        // 先序遍历
        Node tmp = root;
        Stack<Node> s = new Stack<>();
        while (true) {
            while (null != tmp) {
                s.push(tmp);
                System.out.print(tmp.data);
                tmp = tmp.leftChild;
            }
            if (s.empty()) {
                break;
            }
            tmp = s.pop();
            if (null != tmp.rightChild) {
                tmp = tmp.rightChild;
            } else {
                tmp = null;
            }
        }
    }


    public static void postOrder(Node root) {
        if (null == root) {
            return;
        }
        Node tmp = root;
        Stack<Node> s = new Stack<>();
        while (true) {
            while (tmp != null) {
                s.push(tmp);
                tmp = tmp.leftChild;
            }
            if (s.empty()) {
                break;
            }
            tmp = s.peek();
            if (null != tmp.rightChild) {
                tmp = tmp.rightChild;
            } else {
                s.pop();
                System.out.print(tmp.data);
                tmp = null;
            }
        }
    }

    public static void preOrderRight(Node Root) {
        if (Root == null) {
            System.out.println("空树");
            return;
        }
        Node tmp = Root;
        Stack<Node> s = new Stack<Node>();
        s.push(tmp);  //根节点入栈
        while (!s.empty()) {
            //1.访问根节点
            Node p = s.pop();
            System.out.print(p.data + " ");
            //2.如果根节点存在右孩子，则将右孩子入栈
            if (p.rightChild != null) {
                s.push(p.rightChild);
            }
            //3.如果根节点存在左孩子，则将左孩子入栈
            if (p.leftChild != null) {
                s.push(p.leftChild);
            }
        }
        System.out.println();
    }

    public static void postOrderRight(Node Root) {
        if (Root == null) {
            System.out.println("空树");
            return;
        }
        Node tmp = Root;  //当前节点
        Node prev = null; //上一次访问的节点
        Stack<Node> s = new Stack<Node>();
        while (tmp != null || !s.empty()) {
            //1.将根节点及其左孩子入栈
            while (tmp != null) {
                s.push(tmp);
                tmp = tmp.leftChild;
            }

            if (!s.empty()) {
                //2.获取栈顶元素值
                tmp = s.peek();
                //3.没有右孩子，或者右孩子已经被访问过
                if (tmp.rightChild == null || tmp.rightChild == prev) {
                    //则可以访问栈顶元素
                    tmp = s.pop();
                    System.out.print(tmp.data + " ");
                    //标记上一次访问的节点
                    prev = tmp;
                    tmp = null;
                }
                //4.存在没有被访问的右孩子
                else {
                    tmp = tmp.rightChild;
                }
            }
        }
        System.out.println();
    }


    public static void preOrder2(Node root) {
        if (null != root) {
            Stack<Node> stack = new Stack<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                root = stack.pop();
                System.out.print(root.data);
                if (null != root.rightChild) {
                    stack.push(root.rightChild);
                }
                if (null != root.leftChild) {
                    stack.push(root.leftChild);
                }
            }
        }
    }

    public static void postOrder2(Node root) {
        if (null != root) {
            Stack<Node> stack = new Stack<>();
            Stack<Node> stack2 = new Stack<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                root = stack.pop();
                stack2.push(root);
                if (null != root.leftChild) {
                    stack.push(root.leftChild);
                }
                if (null != root.rightChild) {
                    stack.push(root.rightChild);
                }
            }
            while (!stack2.isEmpty()) {
                System.out.print(stack2.pop().data);
            }
        }
    }


    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        Node node8 = new Node(8);
        Node node9 = new Node(9);
        Node node10 = new Node(10);
        node1.leftChild = node2;
        node1.rightChild = node3;

        node2.leftChild = node4;
        node2.rightChild = node5;

        node3.leftChild = node6;
        node3.rightChild = node7;

        node4.leftChild = node8;

        node6.leftChild = node9;
        node7.leftChild = node10;
//        inOrder(node1);
//        preOrder(node1);
//        preOrderRight(node1);
//        postOrderRight(node1);
//        preOrder2(node1);
//        System.out.println();
//        preOrderRight(node1);
        postOrder2(node1);
        System.out.println();
        postOrderRight(node1);
    }
}
