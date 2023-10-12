package ru.nsu.khamidullin;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;



public class Tree<T> implements Iterable {
    /**
     *
     */
    private T root;
    private Tree<T> parent;
    private Set<Tree<T>> children;

    private long modificationCount;

    /*
     *   Tree constructor by value.
     */
    public Tree(T root) {
        /**
         *
         */

        this.root = root;
        this.parent = null;
        this.modificationCount = 0;
        this.children = new HashSet<>();
    }

    /*
    Tree constructor by subtree.
     */
    public Tree(T root, Tree<T> parent) {
        this.root = root;
        this.parent = null;
        this.modificationCount = 0;
        this.children = new HashSet<>();
        if (parent != null) {
            parent.addChild(this);
        }
    }

    public Tree<T> addChild(T value) {
        Tree<T> child = new Tree<>(value);

        children.add(child);
        child.parent = this;

        incModificationCount();
        return child;
    }

    public Tree<T> addChild(Tree<T> subTree) {
        if ((subTree == null) || (subTree.parent != null)) return null;

        children.add(subTree);
        subTree.parent = this;

        incModificationCount();
        return subTree;
    }


    public void delete() {
        deleteMeFromMyParent();

        Set<Tree<T>> copyChildren = new HashSet<>(children);
        for (Tree<T> subTree : copyChildren) {
            subTree.delete();
        }

        children = null;
        root = null;
    }

    public void deleteMeFromMyParent() {
        if (parent != null) {
            parent.children.remove(this);
            parent.incModificationCount();
            parent = null;
        }
    }


    @Override
    public boolean equals(Object object) {
        if ((object == null) || (object.getClass() != getClass())) {
            return false;
        }

        if (object == this) {
            return true;
        }

        var tree = (Tree<?>) object;

        if (!this.root.getClass().equals(tree.root.getClass())) {
            return false;
        }

        if ((this.root == tree.root) && (this.children.size() == tree.children.size())) {
            var usedTreeChildren = new HashSet<>();

            for (var child : this.children) {
                boolean foundEquals = false;

                for (var treeChild : tree.children) {
                    if (!usedTreeChildren.contains(treeChild) && child.equals(treeChild)) {
                        foundEquals = true;
                        usedTreeChildren.add(treeChild);
                        break;
                    }
                }

                if (!foundEquals) return false;
            }
        }

        return true;
    }

    public Stream<T> stream() {
        Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(iterator(), Spliterator.DISTINCT);
        return StreamSupport.stream(spliterator, false);
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return new BFSIterator();
    }

    public Iterator<T> iteratorDFS() {
        return new DFSIterator();
    }

    public Iterator<T> iteratorBFS() {
        return new BFSIterator();
    }

    private class DFSIterator implements Iterator<T> {
        Stack<Tree<T>> stack;
        long expectedModificationCount;

        public DFSIterator() {
            this.stack = new Stack<>();
            stack.push(Tree.this);
            this.expectedModificationCount = modificationCount;
        }

        @Override
        public boolean hasNext() {
            if (expectedModificationCount != modificationCount) {
                throw new ConcurrentModificationException();
            }
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Tree<T> curTree = stack.pop();
            stack.addAll(curTree.children);
            return curTree.root;
        }
    }

    private class BFSIterator implements Iterator<T> {
        Queue<Tree<T>> queue;
        long expectedModificationCount;

        public BFSIterator() {
            this.queue = new LinkedList<>();
            queue.add(Tree.this);
            this.expectedModificationCount = modificationCount;
        }

        @Override
        public boolean hasNext() {
            if (expectedModificationCount != modificationCount) {
                throw new ConcurrentModificationException();
            }
            return !queue.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Tree<T> curTree = queue.poll();
            queue.addAll(curTree.children);
            return curTree.root;
        }
    }

    public T getRoot() {
        return root;
    }

    public void setRoot(T root) {
        this.root = root;
    }

    public Tree<T> getParent() {
        return parent;
    }

    public Set<Tree<T>> getChildren() {
        return new HashSet<>(children);
    }

    private void incModificationCount() {
        modificationCount++;
        if (parent != null) {
            parent.incModificationCount();
        }
    }
}
