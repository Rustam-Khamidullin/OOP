package ru.nsu.khamidullin;

import java.util.stream.Stream;

/**
 * Class Main.
 */
public class Main {
    /**
     * main.
     */
    public static void main(String[] args) {
        var tree = new Tree<>(1);
        var subTree = new Tree<>(2, tree);
        subTree.addChild(3);
        subTree.addChild(4);
        subTree.addChild(5);
        subTree.addChild(6);

        tree.addChild(4);

        tree.setRoot(7);

        var stream = tree.stream();

        var counter = stream.filter(x -> x >= 4).count();

        System.out.println(counter + " elems greater then 4.");

        for (var elem : tree) {
            System.out.print(elem);
        }
    }
}
