package ru.nsu.khamidullin;

/**
 * Class Main.
 */
public class Main {
    /**
     * main.
     */
    public static void main(String[] args) {
        var tree = new Tree<>("R1");
        var a = tree.addChild("A");
        a.addChild("B");
        Tree<String> subtree = new Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("R1");
        tree.addChild(subtree);

        for (var cur : tree) {
            System.out.println(cur);
        }
    }
}
