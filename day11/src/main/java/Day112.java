import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Day112 {
    static String[] inputS;

    record Pos(long x, long y) {}

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

        for (int blnk : blank) {
            ArrayList<Character>  bruh = new ArrayList<>();
            for (int i = 0; i < inputS[0].length(); i++) bruh.add('?');
            daMap.set(blnk, new ArrayList<>(bruh));
        }

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
                if (daMap.get(y).get(0) == '?') daMap.get(y).set(blnk, '*');
                else daMap.get(y).set(blnk, '@');
            }
        }

        int gszie = 1000000;
        int yortsize = gszie - 1;

        ArrayList<Pos> littleGs = new ArrayList<>();
        long yoff = 0;
        for (int y = 0; y < daMap.size(); y++) {
            if (daMap.get(y).get(0) == '?' || daMap.get(y).get(0) == '*') yoff += yortsize;
            long xoff = 0;
            for (int x = 0; x < daMap.get(y).size(); x++) {
                char c = daMap.get(y).get(x);
                if (c == '#') littleGs.add(new Pos(xoff + x, yoff + y));
                if (c == '@' || c == '*') xoff += yortsize;
            }
        }

        long r = 0;

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
