package adt;

import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @author Ooi Choon Chong
 * @param <T>
 */
public class BinarySearchTree<T> implements BinarySearchTreeInterface<T> {

    private Node<T> root;
    private Comparator<? super T> comparator;

    private static class Node<T> {

        T data;
        Node<T> left, right;

        Node(T data) {
            this.data = data;
            this.left = this.right = null;
        }
    }

    public BinarySearchTree() {
        root = null;
    }

    public BinarySearchTree(Comparator<? super T> comparator) {
        this.root = null;
        this.comparator = comparator;
    }

    @Override
    public boolean insert(T element) {
        if (element == null) {
            return false;
        }
        root = insertRecursive(root, element);
        return true;
    }

    private Node<T> insertRecursive(Node<T> node, T element) {
        if (node == null) {
            return new Node<>(element);
        }

        if (comparator.compare(element, node.data) < 0) {
            node.left = insertRecursive(node.left, element);
        } else if (comparator.compare(element, node.data) > 0) {
            node.right = insertRecursive(node.right, element);
        }

        return node;
    }

    @Override
    public boolean delete(T element) {
        if (element == null || isEmpty()) {
            return false;
        }
        root = deleteRecursive(root, element);
        return true;
    }

    private Node<T> deleteRecursive(Node<T> node, T element) {
        if (node == null) {
            return null;
        }

        if (comparator.compare(element, node.data) < 0) {
            node.left = deleteRecursive(node.left, element);
        } else if (comparator.compare(element, node.data) > 0) {
            node.right = deleteRecursive(node.right, element);
        } else {
            // Node to be deleted found
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }

            // Node with two children
            node.data = findMin(node.right).data;
            node.right = deleteRecursive(node.right, node.data);
        }
        return node;
    }

    @Override
    public boolean contains(T element) {
        return containsRecursive(root, element);
    }

    private boolean containsRecursive(Node<T> node, T element) {
        if (node == null) {
            return false;
        }
        if (comparator.compare(element, node.data) < 0) {
            return containsRecursive(node.left, element);
        } else if (comparator.compare(element, node.data) > 0) {
            return containsRecursive(node.right, element);
        } else {
            return true;
        }
    }

    @Override
    public T findMin() {
        if (isEmpty()) {
            return null;
        }
        return findMin(root).data;
    }

    private Node<T> findMin(Node<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    @Override
    public T findMax() {
        if (isEmpty()) {
            return null;
        }
        return findMax(root).data;
    }

    private Node<T> findMax(Node<T> node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public Iterator<T> iterator() {
        return new InOrderIterator();
    }

    private class InOrderIterator implements Iterator<T> {

        private final StackInterface<Node<T>> stack = new LinkedStack<>();

        public InOrderIterator() {
            pushLeft(root);
        }

        private void pushLeft(Node<T> node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            Node<T> node = stack.pop();
            pushLeft(node.right);
            return node.data;
        }
    }
}
