import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Day12 {
    static String[] inputS;

    public static void main(String[] args) throws Throwable {
        try (BufferedReader r = Files.newBufferedReader(Paths.get("input.txt"))) {
            inputS = r.lines().map(String::strip).toArray(String[]::new);
        }

        int r = 0;

        for (String rec : inputS) {
            var ah = rec.split(" ");
            Integer[] lengths = Arrays.stream(ah[1].split(",")).map(Integer::parseInt).toArray(Integer[]::new);
            var springs = ah[0];
            var permutations = new ArrayList<StringBuilder>();
            permutations.add(new StringBuilder());
            for (char c : springs.toCharArray()) {
                if (c == '.' || c == '#') {
                    for (StringBuilder sb : permutations) sb.append(c);
                } else if (c == '?') {
                    var permutations2 = new ArrayList<StringBuilder>();
                    for (StringBuilder sb : permutations) {
                        var sb2 = new StringBuilder(sb);
                        sb2.append('.');
                        permutations2.add(sb2);
                        sb2 = new StringBuilder(sb);
                        sb2.append('#');
                        permutations2.add(sb2);
                    }
                    permutations = permutations2;
                }
            }
            int localR = 0;
            perms:
            for (var sb : permutations) {
                var ls = new ArrayList<>(Arrays.asList(lengths));
                int gsize = 0;
                for (char c : sb.toString().toCharArray()) {
                    if (c == '#') {
                        gsize += 1;
                    } else if (gsize > 0) {
                        if (ls.isEmpty() || ls.get(0) != gsize) {
                            continue perms;
                        }
                        ls.remove(0);
                        gsize = 0;
                    }
                }
                if (gsize > 0) {
                    if (ls.isEmpty() || ls.get(0) != gsize) {
                        continue perms;
                    }
                    ls.remove(0);
                    gsize = 0;
                }
                if (ls.isEmpty()) {
                    localR += 1;
                }
            }
            r += localR;
            System.out.println(localR);
        }
        System.out.println(r);
    }
}
