package ru.nsu.khamidullin;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Class IteratorBFS is an iterator for breadth-first traversal of a Tree.
 *
 * @param <T> - the type of values in the Tree.
 */
public class IteratorBFS<T> implements Iterator<T> {
    Tree<T> tree;
    Queue<Tree<T>> queue;
    long expectedModificationCount;

    /**
     * Constructor for IteratorBFS.
     *
     * @param tree - the Tree to traverse.
     */
    public IteratorBFS(Tree<T> tree) {
        this.queue = new LinkedList<>();
        this.tree = tree;
        queue.add(tree);
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
        return !queue.isEmpty();
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

        Tree<T> curTree = queue.poll();
        queue.addAll(curTree.getChildren());
        return curTree.getRoot();
    }
}