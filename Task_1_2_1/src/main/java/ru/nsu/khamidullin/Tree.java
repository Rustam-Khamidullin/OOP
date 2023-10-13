package ru.nsu.khamidullin;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Implementation of a generic Tree class.
 *
 * @param <T> - the type of the tree's value.
 */
public class Tree<T> implements Iterable<T> {

    private T root;
    private Tree<T> parent;
    private Set<Tree<T>> children;

    private long modificationCount;

    /**
     * Constructor for a Tree.
     *
     * @param root - the value in the root.
     */
    public Tree(T root) {
        this.root = root;
        this.parent = null;
        this.modificationCount = 0;
        this.children = new HashSet<>();
    }

    /**
     * Constructor for a Tree.
     * Connects a new Tree to its parent.
     *
     * @param root   - the value in the root.
     * @param parent - the parent Tree.
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

    /**
     * Add a child to the tree.
     *
     * @param value - the value of the root in the new Tree.
     * @return the new Tree.
     */
    public Tree<T> addChild(T value) {
        Tree<T> child = new Tree<>(value);

        children.add(child);
        child.parent = this;

        incModificationCount();
        return child;
    }

    /**
     * Add a subtree to the tree.
     *
     * @param subTree - the child Tree.
     * @throws CannotAddChildException if subTree is null or already has a parent.
     */
    public void addChild(Tree<T> subTree) throws CannotAddChildException {
        if ((subTree == null) || (subTree.parent != null))
            throw new CannotAddChildException("SubTree is null or parent already exists");

        children.add(subTree);
        subTree.parent = this;

        incModificationCount();
    }

    /**
     * Delete the entire Tree.
     */
    public void delete() {
        deleteMeFromMyParent();

        Set<Tree<T>> copyChildren = new HashSet<>(children);
        for (Tree<T> subTree : copyChildren) {
            subTree.delete();
        }

        children = null;
        root = null;
    }

    /**
     * Delete the connection with the parent.
     */
    public void deleteMeFromMyParent() {
        if (parent != null) {
            parent.children.remove(this);
            parent.incModificationCount();
            parent = null;
        }
    }

    /**
     * Delete the node, but keep its children by attaching them to the parent of the deleted node.
     */
    public void deleteMeAndSaveChildren() {
        if (parent != null) {
            for (var child : children) {
                child.parent = this.parent;
            }
            parent.children.addAll(this.children);
            deleteMeFromMyParent();
            children = null;
            root = null;
        } else {
            throw new CannotAddChildException("There is not new parent.");
        }
    }

    /**
     * Checks if two Trees are equal.
     *
     * @param object - the input Tree.
     * @return true if the Trees are equal, false otherwise.
     */
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

    /**
     * Create a Stream from the elements in the tree.
     *
     * @return a Stream of type T.
     */
    public Stream<T> stream() {
        Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(iterator(), Spliterator.DISTINCT);
        return StreamSupport.stream(spliterator, false);
    }

    /**
     * Implement the Iterable interface.
     *
     * @return an Iterator of type T.
     */
    @Override
    public @NotNull Iterator<T> iterator() {
        return new IteratorBFS<>(this);
    }


    /**
     * Get IteratorBFS.
     *
     * @return an Iterator of type T.
     */
    public Iterator<T> getIteratorBFS() {
        return new IteratorBFS<T>(this);
    }

    /**
     * Get IteratorDFS.
     *
     * @return an Iterator of type T.
     */
    public Iterator<T> getIteratorDFS() {
        return new IteratorDFS<T>(this);
    }

    /**
     * Get the root value of the tree.
     *
     * @return the root value.
     */
    public T getRoot() {
        return root;
    }
    /**
     * Set the root value of the tree.
     *
     * @param root - the new root value.
     */
    public void setRoot(T root) {
        this.root = root;
    }

    /**
     * Get the parent Tree.
     *
     * @return the parent Tree.
     */
    public Tree<T> getParent() {
        return parent;
    }

    /**
     * Get a copy of the children of the tree.
     *
     * @return a set of child Trees.
     */
    public Set<Tree<T>> getChildren() {
        return new HashSet<>(children);
    }

    /**
     * Get the modification count, which tracks the number of modifications to the tree.
     *
     * @return the modification count.
     */
    public long getModificationCount() {
        return modificationCount;
    }

    /**
     * Increment the modification count for this tree and its parent recursively.
     */
    private void incModificationCount() {
        modificationCount++;
        if (parent != null) {
            parent.incModificationCount();
        }
    }
}