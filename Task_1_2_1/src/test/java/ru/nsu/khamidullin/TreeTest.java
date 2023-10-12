package ru.nsu.khamidullin;

import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TreeTest {
    @Test
    void testGraphBFS() {
        /*         1
         *       /   \
         *      2     2
         *    /   \    \
         *   3     3    3
         */
        Tree<Integer> intTree = new Tree<>(1);
        Tree<Integer> child1 = intTree.addChild(2);
        Tree<Integer> child2 = intTree.addChild(2);
        child1.addChild(3);
        child1.addChild(3);
        child2.addChild(3);

        StringBuilder res = new StringBuilder();

        for (var tree : intTree) {
            res.append(tree);
        }
        assertEquals("122333", res.toString());
    }

    @Test
    void testGraphDFS() {
        /*         1
         *       /   \
         *      2     2
         *    /  \   /  \
         *   3    3  3   3
         */
        Tree<Integer> intTree = new Tree<>(1);
        Tree<Integer> child1 = intTree.addChild(2);
        Tree<Integer> child2 = intTree.addChild(2);
        child1.addChild(3);
        child1.addChild(3);
        child2.addChild(3);
        child2.addChild(3);

        StringBuilder res = new StringBuilder();

        Iterator<Integer> DFS = intTree.iteratorDFS();
        while (DFS.hasNext()) {
            res.append(DFS.next());
        }
        assertEquals("1233233", res.toString());
    }

    @Test
    void testAddChild() {
        /*         1
         *       /   \
         *      2     2
         *    /  \      \
         *   3    3      3
         */
        Tree<Integer> intTree = new Tree<>(1);
        Tree<Integer> intSubTree = new Tree<>(2);

        Tree<Integer> child1 = intTree.addChild(2);
        child1.addChild(3);
        child1.addChild(3);
        intSubTree.addChild(3);

        intTree.addChild(intSubTree);

        StringBuilder res = new StringBuilder();

        for (var tree : intTree) {
            res.append(tree);
        }
        assertEquals("122333", res.toString());
    }


    @Test
    void testDelete() {
        /*         1
         *       /   \
         *      2     2
         *    /  \      \
         *   3    3      3
         */
        Tree<Integer> intTree = new Tree<>(1);
        Tree<Integer> intSubTree = new Tree<>(2);

        Tree<Integer> child1 = intTree.addChild(2);
        child1.addChild(3);
        child1.addChild(3);
        intSubTree.addChild(3);

        intTree.addChild(intSubTree);

        StringBuilder res = new StringBuilder();

        for (var tree : intTree) {
            res.append(tree);
        }
        res.append("|");

        intSubTree.delete();
        for (var tree : intTree) {
            res.append(tree);
        }

        assertEquals("122333|1233", res.toString());
    }

    @Test
    void testDeleteMeFromMyParent() {
        /*         1
         *       /   \
         *      2     2
         *    /  \      \
         *   3    3      3
         */
        Tree<Integer> intTree = new Tree<>(1);
        Tree<Integer> intSubTree = new Tree<>(2);

        Tree<Integer> child1 = intTree.addChild(2);
        child1.addChild(3);
        child1.addChild(3);
        intSubTree.addChild(3);

        intTree.addChild(intSubTree);

        StringBuilder res = new StringBuilder();

        for (var tree : intTree) {
            res.append(tree);
        }
        res.append("|");

        intSubTree.deleteMeFromMyParent();
        for (var value : intTree) {
            res.append(value);
        }

        res.append("|");

        for (var value : intSubTree) {
            res.append(value);
        }

        assertEquals("122333|1233|23", res.toString());
    }

    @Test
    void testCatchConcurrentModificationExceptionDelete() {
        Tree<String> tree = new Tree<>("R1");
        var a = tree.addChild("A");
        a.addChild("B");
        Tree<String> subtree = new Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);

        boolean exception = false;

        try {
            for (var cur : tree) {
                if (cur.equals("R2")) {
                    subtree.delete();
                }
            }
        } catch (ConcurrentModificationException e) {
            exception = true;
        }

        assertTrue(exception);
    }

    @Test
    void testCatchConcurrentModificationExceptionDeleteMeFromMyParent() {
        Tree<String> tree = new Tree<>("R1");
        var a = tree.addChild("A");
        a.addChild("B");
        Tree<String> subtree = new Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);

        boolean exception = false;

        try {
            for (var cur : tree) {
                if (cur.equals("R2")) {
                    subtree.deleteMeFromMyParent();
                }
            }
        } catch (ConcurrentModificationException e) {
            exception = true;
        }

        assertTrue(exception);
    }

    @Test
    void testCatchConcurrentModificationExceptionAddChild() {
        Tree<String> tree = new Tree<>("R1");
        var a = tree.addChild("A");
        a.addChild("B");
        Tree<String> subtree = new Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);

        boolean exception = false;

        try {
            for (var cur : tree) {
                if (cur.equals("R2")) {
                    subtree.addChild("newStr");
                }
            }
        } catch (ConcurrentModificationException e) {
            exception = true;
        }

        assertTrue(exception);
    }
}
