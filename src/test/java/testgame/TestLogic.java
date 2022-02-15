package testgame;

import java.util.HashSet;
import java.util.TreeSet;

public class TestLogic {

    public static void main(String[] args) {
        HashSet<Integer> test = new HashSet<>();
        test.add(5);
        test.add(2);
        test.add(7);
        test.add(3);
        test.add(1);

        TreeSet<Integer> zIndexes = new TreeSet<>(test);

        for (Integer integer : zIndexes) {
            System.out.println(integer);
        }

    }


}
