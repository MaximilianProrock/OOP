package ru.vsu.goncharenko.treemap;

import java.awt.*;
import java.util.Iterator;
import java.util.Objects;
import java.util.Stack;

public class RBTree<T extends Comparable<T>> {

    static final boolean RED   = true;
    static final boolean BLACK = false;

    protected static class Node<T extends Comparable<T>> {
        public T value;
        public Node<T> left;
        public Node<T> right;
        public Node<T> parent;
        public boolean color;


        public Node(T value, Node<T> left, Node<T> right, boolean color, Node<T> parent) {
            this.value = value;
            this.left = left;
            this.right = right;
            this.color = color;
            this.parent = parent;
        }

        public Node(T value, Node<T> parent){
            this(value, null, null, BLACK, parent);
        }

        public Node(T value){
            this(value, null);
        }

        public T getValue() {
            return value;
        }

        public Node<T> getLeft() {
            return left;
        }

        public Node<T> getRight() {
            return right;
        }

        public Color getColor(){
            return color == RED ? Color.RED : Color.BLACK;
        }
    }

    Node<T> root = null;
    protected int size = 0;

    public Node<T> getRoot() {
        return root;
    }

    public int size() {
        return size;
    }

    public T add(T value){
        if(root == null){
            setRoot(new Node<>(value));
            size++;
            return null;
        }
        Node<T> node = root;
        while (true){
            int cmp = value.compareTo(node.value);
            if(cmp == 0) {
                T oldValue = node.value;
                node.value = value;
                return oldValue;
            }else if(cmp < 0) {
                if (node.left == null) {
                    setLeft(node, new Node(value));
                    size++;
                    correctAfterAdd(node.left);
                    return null;
                }
                node = node.left;
            }else {
                if (node.right == null) {
                    setRight(node, new Node(value));
                    size++;
                    correctAfterAdd(node.right);
                    return null;
                }
                node = node.right;
            }
        }
    }

    public Node<T> getNode(Node<T> node, T value) {
        if (node == null) {
            return null;
        }
        int cmp = node.getValue().compareTo(value);
        if (cmp == 0) {
            return node;
        } else if (cmp > 0) {
            return getNode(node.getLeft(), value);
        } else {
            return getNode(node.getRight(), value);
        }
    }

    public Node<T> getNode(T value){
        return getNode(root, value);
    }

    public Node<T> getMinNode(Node<T> node) {
        return (node == null || node.getLeft() == null) ? node : getMinNode(node.getLeft());
    }

    public Node<T> getMaxNode(Node<T> node) {
        return (node == null || node.getRight() == null) ? node : getMaxNode(node.getRight());
    }

    public void remove(T value) {
        Node node = (Node) getNode(getRoot(), value);
        if (node == null) {
            // в дереве нет такого значения
            return;
        }

        T oldValue = (T) node.value;

        //если у node 2 потомка, то сведём этот случай к одному из двух: 1) 0 потомков, 2) 1 потомок
        if (node.left != null && node.right!= null) {
            Node nextValueNode = (Node) getMinNode(node.right);
            node.value = nextValueNode.value;
            node = nextValueNode;
        }
        // здесь node имеет не более одного потомка
        Node child = (node.left != null) ? node.left : node.right;
        if (child != null) {
            if (node == root) {
                setRoot(child);
                root.color = BLACK;
            } else if (node.parent.left == node) {
                // child - левый потомок node
                setLeft(node.parent, child);
            } else {
                // child - правый потомок node
                setRight(node.parent, child);
            }
            if (node.color == BLACK) {
                // если удалили черный узел, то нарушилось равновесие по черной высоте
                correctAfterRemove(child);
            }
        } else if (node == root) {
            root = null;
        } else {
            // если удалили черный узел, то нарушилось равновесие по черной высоте
            if (node.color == BLACK) {
                correctAfterRemove(node);
            }
            removeFromParent(node);
        }
        size--;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    private void removeFromParent(Node node) {
        if (node.parent != null) {
            if (node.parent.left == node) {
                node.parent.left = null;
            } else if (node.parent.right == node) {
                node.parent.right = null;
            }
            node.parent = null;
        }
    }

    private void correctAfterAdd(Node<T> node){
        //1 шаг - цвет узла красный
        if (node != null) {
            node.color = RED;
        }
        // шаг 2: Корректировка двух подряд красных узлов (если имеет место быть)
        if (node != null && node != root && colorOf(node.parent) == RED) {
            // случай 1-й "отец" и "дядя" - КРАСНЫЕ
            // покрасить "отца" и "дядю" в ЧЁРНЫЙ, а "дедушку" в КРАСНЫЙ и обработать "дедушку" как вставленный узел
            if (isRed(siblingOf(parentOf(node)))) {
                setColor(parentOf(node), BLACK);
                setColor(siblingOf(parentOf(node)), BLACK);
                setColor(grandparentOf(node), RED);
                correctAfterAdd(grandparentOf(node));
            }

            // случай 2-й "дядя" - ЧЁРНЫЙ, отец - левый потомок деда
            //если узел - левый потомок отца, то один поворот налево,
            //если узел - правый потомок отца, право-левый поворот
            else if (parentOf(node) == leftOf(grandparentOf(node))) {
                if (node == rightOf(parentOf(node))) {
                    leftRotate(node = parentOf(node));
                }
                setColor(parentOf(node), BLACK);
                setColor(grandparentOf(node), RED);
                rightRotate(grandparentOf(node));
            }

            // случай 2-й "дядя" - ЧЁРНЫЙ, отец - правый потомок деда
            //если узел - правый потомок отца, то один поворот налево,
            //если узел - левый потомок отца, право-левый поворот
            else if (parentOf(node) == rightOf(grandparentOf(node))) {
                if (node == leftOf(parentOf(node))) {
                    rightRotate(node = parentOf(node));
                }
                setColor(parentOf(node), BLACK);
                setColor(grandparentOf(node), RED);
                leftRotate(grandparentOf(node));
            }
        }

        // шаг 3: раскрасить корень дерева черным
        setColor(root, BLACK);
    }

    private void correctAfterRemove(Node node) {
        while (node != root && isBlack(node)) {
            if (node == leftOf(parentOf(node))) {
                // Pulled up node is a left child
                Node sibling = rightOf(parentOf(node));
                if (isRed(sibling)) {
                    setColor(sibling, BLACK);
                    setColor(parentOf(node), RED);
                    leftRotate(parentOf(node));
                    sibling = rightOf(parentOf(node));
                }
                if (isBlack(leftOf(sibling)) && isBlack(rightOf(sibling))) {
                    setColor(sibling, RED);
                    node = parentOf(node);
                } else {
                    if (isBlack(rightOf(sibling))) {
                        setColor(leftOf(sibling), BLACK);
                        setColor(sibling, RED);
                        rightRotate(sibling);
                        sibling = rightOf(parentOf(node));
                    }
                    setColor(sibling, colorOf(parentOf(node)));
                    setColor(parentOf(node), BLACK);
                    setColor(rightOf(sibling), BLACK);
                    leftRotate(parentOf(node));
                    node = root;
                }
            } else {
                // pulled up node is a right child
                Node sibling = leftOf(parentOf(node));
                if (isRed(sibling)) {
                    setColor(sibling, BLACK);
                    setColor(parentOf(node), RED);
                    rightRotate(parentOf(node));
                    sibling = leftOf(parentOf(node));
                }
                if (isBlack(leftOf(sibling)) && isBlack(rightOf(sibling))) {
                    setColor(sibling, RED);
                    node = parentOf(node);
                } else {
                    if (isBlack(leftOf(sibling))) {
                        setColor(rightOf(sibling), BLACK);
                        setColor(sibling, RED);
                        leftRotate(sibling);
                        sibling = leftOf(parentOf(node));
                    }
                    setColor(sibling, colorOf(parentOf(node)));
                    setColor(parentOf(node), BLACK);
                    setColor(leftOf(sibling), BLACK);
                    rightRotate(parentOf(node));
                    node = root;
                }
            }
        }
        setColor(node, BLACK);
    }

    protected void leftRotate(Node<T> node){
        if(node.right == null){
            return;
        }

        Node<T> right = node.right;
        setRight(node, right.left);
        if(node.parent == null){
            setRoot(right);
        }else if(node.parent.left == node){
            setLeft(node.parent, right);
        }else {
            setRight(node.parent, right);
        }
        setLeft(right, node);

    }

    protected void rightRotate(Node<T> node){
        if (node.left == null) {
            return;
        }
        Node left = node.left;
        setLeft(node, left.right);
        if (node.parent == null) {
            setRoot(left);
        } else if (node.parent.left == node) {
            setLeft(node.parent, left);
        } else {
            setRight(node.parent, left);
        }
        setRight(left, node);
    }


    private boolean colorOf(Node<T> node) {
        return node == null ? BLACK : node.color;
    }

    private boolean isRed(Node<T> node) {
        return colorOf(node) == RED;
    }

    private boolean isBlack(Node<T> node) {
        return colorOf(node) == BLACK;
    }

    protected void setColor(Node<T> node, boolean color) {
        if (node != null) {
            node.color = color;
        }
    }

    protected void setLeft(Node node, Node left) {
        if (node != null) {
            node.left = left;
            if (left != null) {
                left.parent = node;
            }
        }
    }

    protected void setRight(Node node, Node right) {
        if (node != null) {
            node.right = right;
            if (right != null) {
                right.parent = node;
            }
        }
    }

    protected void setRoot(Node<T> node) {
        root = node;
        if (node != null) {
            node.parent = null;
        }
    }

    private Node leftOf(Node<T> node) {
        return node == null ? null : node.left;
    }

    private Node rightOf(Node<T> node) {
        return node == null ? null : node.right;
    }

    private Node<T> parentOf(Node<T> node) {
        return node == null ? null : node.parent;
    }

    private Node<T> grandparentOf(Node<T> node) {
        return (node == null || node.parent == null) ? null : node.parent.parent;
    }

    private Node<T> siblingOf(Node<T> node) {
        return (node == null || node.parent == null)
                ? null
                : (node == node.parent.left)
                ? node.parent.right : node.parent.left;
    }

    private int getHeight(Node<T> node) {
        return (node == null) ? -1 : Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    public int getHeight() {
        return getHeight(root);
    }

    @Override
    public String toString() {
        int level = 0;
       StringBuilder stringBuilder = new StringBuilder();
       stringBuilder.append(printNode("", root, level));
       return stringBuilder.toString();
    }


   public String printNode(String str, Node<T> node, int level){
        if(node == null){
            return "null";
        }
        String indent = "";
       for (int i = 0; i < level; i++) {
           indent += "-";
       }
       level++;
       str += node.value.toString() + "\n" + indent +  "Left: " + printNode(str, node.left, level) + "\n" + indent + "Right: " + printNode(str, node.right, level) + "\n";
       return str;
   }

    public boolean equals(RBTree<T> rbTree) {
        return size == rbTree.size && equalsTree(root, rbTree.root);
    }

    private boolean equalsTree(Node r, Node t){
        if(r == null && t == null){
            return true;
        }else if((r != null && t == null) || (r==null && t != null)){
            return false;
        }else{
            return t.value.equals(r.value) && (equalsTree(r.left, t.left)) && (equalsTree(r.right, t.right));
        }

    }

    private  <T extends Comparable<T>> Iterable<T> preOrderValues(Node<T> treeNode) {
        return () -> {
            Stack<Node<T>> stack = new Stack<>();
            stack.push(treeNode);

            return new Iterator<T>() {
                @Override
                public boolean hasNext() {
                    return stack.size() > 0;
                }

                @Override
                public T next() {
                    Node<T> node = stack.pop();
                    if (node.getRight() != null) {
                        stack.push(node.getRight());
                    }
                    if (node.getLeft() != null) {
                        stack.push(node.getLeft());
                    }
                    return node.getValue();
                }

            };
        };
    }


    public Iterator<T> iterator(){
        return preOrderValues(root).iterator();
    }

}
