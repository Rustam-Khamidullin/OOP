package ru.nsu.khamidullin;

import java.util.ConcurrentModificationException;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) {
        Tree<String> tree = new Tree<>("R1");
        var a = tree.addChild("A");
        var b = a.addChild("B");
        Tree<String> subtree = new Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("R1");
        tree.addChild(subtree);

        for (var cur : tree) {
            System.out.println(cur);
        }


        Stream<String> st = tree.stream();
        System.out.println("R1 counter: " + st.filter("R1"::equals).count());

        System.out.println("--------------------------");

        try {
            for (var cur : tree) {

                System.out.println(cur);
                if (cur.equals("R2")) {
                    subtree.delete();
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("ConcurrentModificationException");
        }

        System.out.println("--------------------------");

        for (var cur : tree) {
            System.out.println(cur);
        }
    }
}
