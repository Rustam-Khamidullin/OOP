package ru.nsu.khamidullin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Class TreeTest
 */
public class TreeTest {
    @Test
    @DisplayName("BFS")
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
    @DisplayName("DFS")
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

        Iterator<Integer> DFS = intTree.getIteratorDFS();
        while (DFS.hasNext()) {
            res.append(DFS.next());
        }
        assertEquals("1233233", res.toString());
    }

    @Test
    @DisplayName("AddChild")
    void testAddChild() {
        /*         1
         *       /   \
         *      2     2
         *    /  \      \
         *   3    3      3
         */
        Tree<Integer> intTree = new Tree<>(1);
        Tree<Integer> intSubTree = new Tree<>(2, intTree);

        Tree<Integer> child1 = intTree.addChild(2);
        child1.addChild(3);
        child1.addChild(3);
        intSubTree.addChild(3);

        StringBuilder res = new StringBuilder();

        for (var tree : intTree) {
            res.append(tree);
        }
        assertEquals("122333", res.toString());
    }


    @Test
    @DisplayName("Delete")
    void testDelete() {
        /*         1
         *       /   \
         *      2     2
         *    /  \      \
         *   3    3      3
         */
        Tree<Integer> intTree = new Tree<>(1);
        Tree<Integer> intSubTree = new Tree<>(2, intTree);

        Tree<Integer> child1 = intTree.addChild(2);
        child1.addChild(3);
        child1.addChild(3);
        intSubTree.addChild(3);

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
    @DisplayName("DeleteMeFromMyParent")
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
    @DisplayName("DeleteMeAndSaveChildren")
    void testDeleteMeAndSaveChildren() {
        /*         1
         *       /   \
         *      2     3
         *    /  \
         *   3    3
         */
        Tree<Integer> intTree = new Tree<>(1);
        Tree<Integer> intSubTree = new Tree<>(2, intTree);

        intTree.addChild(3);
        intSubTree.addChild(3);
        intSubTree.addChild(3);

        intSubTree.deleteMeAndSaveChildren();

        StringBuilder res = new StringBuilder();

        for (var value : intTree) {
            res.append(value);
        }

        assertEquals("1333", res.toString());
    }

    @Test
    @DisplayName("Catch ConcurrentModificationException Delete")
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
    @DisplayName("Catch ConcurrentModificationException DeleteMeFromMyParent")
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
    @DisplayName("Catch ConcurrentModificationException AddChild")
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

    @Test
    @DisplayName("Catch CannotAddChildException AddChild null")
    void testCatchCannotAddChildExceptionAddChildNull() {
        /*         1
         *       /
         *      2
         *    /
         *   3
         */
        Tree<Integer> intTree = new Tree<>(1);
        Tree<Integer> intSubTree = new Tree<>(2, intTree);
        intSubTree.addChild(3);



        boolean exception = false;

        try {
            intTree.addChild((Tree<Integer>) null);
        } catch (CannotAddChildException e) {
            exception = true;
        }

        assertTrue(exception);
    }

    @Test
    @DisplayName("Catch CannotAddChildException AddChild parent already exists")
    void testCatchCannotAddChildExceptionAddChildPAE() {
        /*         1
         *       /
         *      2
         *    /
         *   3
         */
        Tree<Integer> intTree = new Tree<>(1);
        Tree<Integer> intSubTree = new Tree<>(2, intTree);
        Tree<Integer> intNewTree = new Tree<>(3, intSubTree);



        boolean exception = false;

        try {
            intTree.addChild(intNewTree);
        } catch (CannotAddChildException e) {
            exception = true;
        }

        assertTrue(exception);
    }


}