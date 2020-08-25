package suanfaTest;

public class RedBlackTree {

    public static void main(String[] args) {

        TreeNode root = new TreeNode(8);
        TreeNode l1 = new TreeNode(4);
        TreeNode r1 = new TreeNode(9);
        TreeNode l2 = new TreeNode(1);
        TreeNode r2 = new TreeNode(6);
        TreeNode l3 = new TreeNode(5);
        TreeNode r3 = new TreeNode(7);


        root.setLeftkid(l1);
        root.setRightkid(r1);

        l1.setLeftkid(l2);
        l1.setRightkid(r2);

        r2.setLeftkid(l3);
        r2.setRightkid(r3);

        root.setParent(null);
        l1.setParent(root);
        r1.setParent(root);
        l2.setParent(l1);
        r2.setParent(l1);
        l3.setParent(r2);
        r3.setParent(r2);

        inOrder(root);
        rorateLeft(root, l1);
        System.out.println(root);
        System.out.println(l1);
        System.out.println(r1);
        System.out.println(l2);
        System.out.println(r2);
        System.out.println(l3);
        System.out.println(r3);


    }

    private static void rorateLeft(TreeNode root, TreeNode p) {

        if (p == null || p.leftkid == null) {
            return;
        }
        TreeNode parent = p.parent;
        TreeNode rightKid = p.rightkid;
        if (parent.leftkid.equals(p)) {
            parent.leftkid = p.rightkid;
        } else {
            parent.rightkid = p.rightkid;
        }
        rightKid.parent = parent;
        p.parent = rightKid;
        p.rightkid = rightKid.leftkid;
        rightKid.leftkid.parent = p;
        rightKid.leftkid = p;
    }

    static class TreeNode {

        private Integer root;

        private TreeNode parent;

        private TreeNode leftkid;

        private TreeNode rightkid;

        private Boolean redFlag;

        public TreeNode(Integer root) {
            this.root = root;
        }

        public TreeNode getParent() {
            return parent;
        }

        public void setParent(TreeNode parent) {
            this.parent = parent;
        }

        public TreeNode getLeftkid() {
            return leftkid;
        }

        public void setLeftkid(TreeNode leftkid) {
            this.leftkid = leftkid;
        }

        public TreeNode getRightkid() {
            return rightkid;
        }

        public void setRightkid(TreeNode rightkid) {
            this.rightkid = rightkid;
        }

        public Boolean getRedFlag() {
            return redFlag;
        }

        public void setRedFlag(Boolean redFlag) {
            this.redFlag = redFlag;
        }

        public Integer getRoot() {
            return root;
        }

        public void setRoot(Integer root) {
            this.root = root;
        }

        @Override
        public String toString() {
            return "TreeNode [数字=" + root +
                    ", 父节点=" + (null != parent ? parent.root : 0) +
                    ", 左节点=" + (null != leftkid ? leftkid.root : 0) +
                    ", 右节点=" + (null != rightkid ? rightkid.root : 0) + "]";
        }
    }

    public static void inOrder(TreeNode treeNode) {
        if (treeNode.leftkid != null) {
            inOrder(treeNode.leftkid);

        }
        System.out.print(treeNode.root + " ");

        if (treeNode.rightkid != null) {
            inOrder(treeNode.rightkid);
        }
    }
}