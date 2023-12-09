import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day092 {
    static String[] inputS;

    public static void main(String[] args) throws Throwable {
        try (BufferedReader r = Files.newBufferedReader(Paths.get("input.txt"))) {
            inputS = r.lines().map(String::strip).toArray(String[]::new);
        }
        int r = 0;
        for (String l : inputS) {
            r += puzzle(new ArrayList<>(Arrays.stream(l.split(" ")).map(Integer::parseInt).toList()));
        }
        System.out.println(r);
    }

    private static int puzzle(List<Integer> input) {
        List<List<Integer>> lists = new ArrayList<>();
        List<Integer> a = input;
        while (!a.stream().allMatch(i -> i == 0)) {
            lists.add(a);
            a = nextRow(a);
        }
        if (lists.isEmpty()) return 0;
        for (int i = lists.size() - 2; i >= 0; i--) {
            lists.get(i).add(getLast(lists.get(i)) + getLast(lists.get(i + 1)));
        }
        for (int i = lists.size() - 2; i >= 0; i--) {
            lists.get(i).add(0, getFirst(lists.get(i)) - getFirst(lists.get(i + 1)));
        }
        // int r = 0;
        // for (var l : lists) {
        //     System.out.println(l);
        //     r += getLast(l);
        // }
        return getFirst(lists.get(0));
    }

    private static int getFirst(List<Integer> l) {
        return l.get(0);
    }

    private static int  getLast(List<Integer> l) {
        return l.get(l.size() - 1);
    }

    private static List<Integer> nextRow(List<Integer> input) {
        ArrayList<Integer> next_row = new ArrayList<>();
        for (int i = 0; i < input.size() - 1; i++) {
            next_row.add(input.get(i + 1) - input.get(i));
        }
        return next_row;
    } 
}