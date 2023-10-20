package ru.nsu.khamidullin;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Class IteratorDFS is an iterator for depth-first traversal of a Tree.
 *
 * @param <T> - the type of values in the Tree.
 */
public class IteratorDfs<T> implements Iterator<T> {
    private final Tree<T> tree;
    private final Stack<Tree<T>> stack;
    private final long expectedModificationCount;

    /**
     * Constructor for IteratorDFS.
     *
     * @param tree - the Tree to traverse.
     */
    public IteratorDfs(Tree<T> tree) {
        this.tree = tree;
        this.stack = new Stack<>();
        stack.push(tree);
        this.expectedModificationCount = tree.getModificationCount();
    }

    /**
     * Check if there are more elements to iterate over.
     *
     * @return true if there are more elements, false otherwise.
     * @throws ConcurrentModificationException if the Tree is modified during iteration.
     */
    @Override
    public boolean hasNext() {
        if (expectedModificationCount != tree.getModificationCount()) {
            throw new ConcurrentModificationException();
        }
        return !stack.isEmpty();
    }

    /**
     * Get the next element in the traversal.
     *
     * @return the next element.
     * @throws NoSuchElementException if there are no more elements.
     */
    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        Tree<T> curTree = stack.pop();
        stack.addAll(curTree.getChildren());
        return curTree.getRoot();
    }
}