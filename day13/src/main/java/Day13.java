import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day13 {
    static String[] inputS;

    public static void main(String[] args) throws Throwable {
        try (BufferedReader r = Files.newBufferedReader(Paths.get("input.txt")))  {
            inputS = r.lines().map(String::strip).toArray(String[]::new);
        }

        long r = 0;

        ArrayList<String> pattern = new ArrayList<>();
        for (var s : inputS) {
            if (s.isEmpty()) {
                var e = doProblem(pattern);
                System.out.println(e);
                r += e;
                pattern.clear();
            } else {
                pattern.add(s);
            }
        }
        System.out.println(r);
    }

    static long doProblem(List<String> pattern) {
        for (int i = 0; i < pattern.get(0).length() - 1; i++) {
            boolean reflective = true;
            loop:
            for (int j = 0; j < pattern.get(0).length(); j++) {
                int left = i - j;
                int right = i + 1 + j;
                if (left < 0 || right >= pattern.get(0).length()) {
                    break;
                }
                for (int k = 0; k < pattern.size(); k++) {
                    if (pattern.get(k).charAt(left) != pattern.get(k).charAt(right)) {
                        reflective = false;
                        break loop;
                    }
                }
            }
            if (reflective) {
                return i + 1;
            }
        }
        for (int i = 0; i < pattern.size() - 1; i++) {
            boolean reflective = true;
            loop:
            for (int j = 0; j < pattern.size(); j++) {
                int top = i - j;
                int bottom = i + 1 + j;
                if (top < 0 || bottom >= pattern.size()) {
                    break;
                }
                if (!pattern.get(top).equals(pattern.get(bottom))) {
                    reflective = false;
                    break loop;
                }
            }
            if (reflective) {
                return (i + 1) * 100;
            }
        }
        throw null;
    }




}
