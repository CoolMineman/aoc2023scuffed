import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Day04 {
    static String[] input;

    public static void main(String[] args) throws Throwable {
        try (BufferedReader r = Files.newBufferedReader(Paths.get("input.txt"))) {
            input = r.lines().collect(Collectors.toList()).toArray(new String[0]);
        }
        // part1();
        part2();
    }

    public static void part1() throws Throwable {
        int score = 0;
        for (String line : input) {
            int localScore = 0;
            String[] ahh = line.replace("  ", " ").replace("  ", " ").split(" ");
            boolean winning = false;
            HashSet<Integer> win = new HashSet<>();
            for (int i = 2; i < ahh.length; i++) {
                String s = ahh[i];
                if (s.isEmpty()) continue;
                if (s.equals("|")) {
                    winning = true;
                } else {
                    Integer dascore = Integer.parseInt(s);
                    if (winning) {
                        if (win.contains(dascore)) {
                            if (localScore == 0) localScore = 1;
                            else localScore *= 2;
                        }
                    } else {
                        win.add(dascore);
                    }
                }
            }
            score += localScore;
        }
        System.out.println(score);
    }


    public static void part2() throws Throwable {
        int score = 0;
        for (int i = 0; i < input.length; i++) {
            score += copies(i);
            score += 1;
        }
        System.out.println(score);
    }

    static HashMap<Integer, Integer> i_can_optimize_too = new HashMap<>();
    public static int copies(int index) {
        if (i_can_optimize_too.containsKey(index)) return i_can_optimize_too.get(index);
        String line = input[index];
        int localScore = 0;
        String[] ahh = line.replace("  ", " ").replace("  ", " ").split(" ");
        boolean winning = false;
        HashSet<Integer> win = new HashSet<>();
        for (int i = 2; i < ahh.length; i++) {
            String s = ahh[i];
            if (s.isEmpty()) continue;
            if (s.equals("|")) {
                winning = true;
            } else {
                Integer dascore = Integer.parseInt(s);
                if (winning) {
                    if (win.contains(dascore)) {
                        localScore += 1;
                    }
                } else {
                    win.add(dascore);
                }
            }
        }
        int r = localScore;
        for (int i = 0; i < localScore; i++) {
            r += copies(index + 1 + i);
        }
        i_can_optimize_too.put(index, r);
        return r;
    }
}