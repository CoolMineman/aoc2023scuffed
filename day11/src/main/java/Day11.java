import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Day11 {
    static String[] inputS;

    record Pos(int x, int y) {}

    public static void main(String[] args) throws Throwable {
        try (BufferedReader r = Files.newBufferedReader(Paths.get("input.txt"))) {
            inputS = r.lines().map(String::strip).toArray(String[]::new);
        }

        ArrayList<ArrayList<Character>> daMap = new ArrayList<>();
        for (String l : inputS) {
            ArrayList<Character> yeet = new ArrayList<>();
            for (char c : l.toCharArray())
                yeet.add(c);
            daMap.add(yeet);
        }

        ArrayList<Integer> blank = new ArrayList<>();

        for (int y = 0; y < daMap.size(); y++) {
            var l = daMap.get(y);
            if (!l.contains('#')) {
                blank.add(y);
            }
        }

        int offset = 0;
        for (int blnk : blank) {
            daMap.add(blnk + offset + 1, new ArrayList<>(daMap.get(blnk + offset)));
            offset += 1;
        }

        offset = 0;
        blank.clear();

        for (int x = 0; x < inputS[0].length(); x++) {
            boolean isEmpty = true;
            for (int y = 0; y < daMap.size(); y++) {
                if (daMap.get(y).get(x) == '#') isEmpty = false;
            }
            if (isEmpty) blank.add(x);
        }

        for (int blnk : blank) {
            for (int y = 0; y < daMap.size(); y++) {
                daMap.get(y).add(blnk + offset + 1, '.');
            }
            offset += 1;
        }

        ArrayList<Pos> littleGs = new ArrayList<>();
        for (int y = 0; y < daMap.size(); y++) {
            for (int x = 0; x < daMap.get(y).size(); x++) {
                if (daMap.get(y).get(x) == '#') littleGs.add(new Pos(x, y));
            }
        }

        int r = 0;

        for (int i=0; i <= littleGs.size() - 2; i++) {
            for (int j=i+1; j <= littleGs.size() - 1; j++) {
                var a = littleGs.get(i);
                var b = littleGs.get(j);
                r += Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
            }
        }

        

        for (var l : daMap) {
            for (var c : l) {
                System.out.print(c);
            }
            System.out.println();
        }

        System.out.println(r);

    }
}
