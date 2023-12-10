import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day102 {
    static String[] inputS;
    static Pos start;

    record Pos(int x, int y) {

    }

    public static void main(String[] args) throws Throwable {
        try (BufferedReader r = Files.newBufferedReader(Paths.get("input.txt"))) {
            inputS = r.lines().map(String::strip).toArray(String[]::new);
        }

        for (int y = 0; y < inputS.length; y++) {
            var l = inputS[y];
            for (int x = 0; x < l.length(); x++) {
                if (l.charAt(x) == 'S') {
                    start = new Pos(x, y);
                }
            }
        }

        HashMap<Pos, Integer> distance = new HashMap<>();

        ArrayList<Pos> nodesA = new ArrayList<>();
        ArrayList<Pos> nodesB = new ArrayList<>();
        nodesA.add(start);
        int curdistance = 0;

        while (!nodesA.isEmpty()) {
            for (Pos p : nodesA) {
                if (distance.containsKey(p)) continue;
                distance.put(p, curdistance);
                char pipe = getPipe(p);
                List<Pos> offsets;
                switch (pipe) {
                    case 'S':
                    //Hardcoded lol
                        offsets = Arrays.asList(new Pos(0,1), new Pos(-1, 0)); 
                        break;
                    case '|':
                        offsets = Arrays.asList(new Pos(0,1), new Pos(0, -1));
                        break;
                    case '-':
                        offsets = Arrays.asList(new Pos(1,0), new Pos(-1,0));
                        break;
                    case 'L':
                        offsets = Arrays.asList(new Pos(0,-1), new Pos(1, 0));
                        break;
                    case 'J':
                        offsets = Arrays.asList(new Pos(0,-1), new Pos(-1, 0));
                        break;
                    case '7':
                        offsets = Arrays.asList(new Pos(0,1), new Pos(-1, 0));
                        break;
                    case 'F':
                        offsets = Arrays.asList(new Pos(0,1), new Pos(1, 0));
                        break;
                    default:
                        offsets = Arrays.asList();
                        break;
                }
                for (Pos off : offsets) {
                    nodesB.add(new Pos(p.x + off.x, p.y + off.y));
                }
            }
            nodesA = nodesB;
            nodesB = new ArrayList<>();
            curdistance += 1;
        }

        int highest = 0;
        for (var e : distance.entrySet()) {
            // System.out.println(e.getKey());
            // System.out.println(e.getValue());
            highest = Math.max(highest, e.getValue());
        }

        int[][] bruh = new int[inputS[0].length() * 5][inputS.length * 5];

        for (Pos p : distance.keySet()) {
            String pattern;
            switch (getPipe(p)) {
            case 'S':
            //Hardcoded lol
                pattern = """
                        \s    G
                        XXXX G
                           X G
                        XX X G
                         X X G
                        """;
                break;
            case '|':
                pattern = """
                        \sX X G
                         X X G
                         X X G
                         X X G
                         X X G
                        """;
                break;
            case '-':
                pattern = """
                        \s    G
                        XXXXXG
                             G
                        XXXXXG
                             G
                        """;
                break;
            case 'L':
                pattern = """
                        \sX X G
                         X XXG
                         X   G
                         XXXXG
                              G
                        """;
                break;
            case 'J':
                pattern = """
                        \sX X G
                        XX X G
                           X G
                        XXXX G
                             G
                        """;
                break;
            case '7':
                pattern = """
                        \s    G
                        XXXX G
                           X G
                        XX X G
                         X X G
                        """;
                break;
            case 'F':
                pattern = """
                       \s    G
                        XXXXG
                        X   G
                        X XXG
                        X X G
                        """;
                break;
            default:
                pattern = "   \n   \n   \n";
                break;
            }
            var ls = pattern.split("\n");
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    try {
                    bruh[p.x * 5  + i][p.y * 5 + j] = ls[j].charAt(i) == 'X' ? 1 : 0;
                    } catch (Exception e) {
                        System.out.println(pattern);
                        throw e;
                    }
                }
            }
        }

        for (int x = 0; x < bruh.length; x++) {
            floodFill(bruh, new Pos(x, 0));
            floodFill(bruh, new Pos(x, bruh[0].length - 1));
        }
        for (int y = 0; y < bruh[0].length; y++) {
            floodFill(bruh, new Pos(0, y));
            floodFill(bruh, new Pos(bruh.length - 1, y));
        }

        int r = 0;
        for (int y = 0; y < inputS.length; y++) {
            var l = inputS[y];
            for (int x = 0; x < l.length(); x++) {
                if (distance.containsKey(new Pos(x, y))) continue;
                if (bruh[x * 5 + 1][y * 5 + 1] == 0) r += 1;
            }
        }

        System.out.println("P2");
        System.out.println(bruh.length + " " + bruh[0].length);
        for (int y = 0; y < bruh[0].length; y++) {
            for (int x = 0; x < bruh.length; x++) {
                System.out.print(bruh[x][y]); System.out.print(" ");
            }
            System.out.println();
        }

        System.err.println("Enclosed " + r);
    }

    public static void floodFill(int[][] bruh, Pos p) {
        ArrayDeque<Pos> toFill = new ArrayDeque<>();
        toFill.add(p);
        while (!toFill.isEmpty()) {
            p = toFill.pop();
            int r = get(bruh, p);
            if (r == 0) {
                bruh[p.x][p.y] = 2;
                toFill.add(new Pos(p.x + 1, p.y + 1));
                toFill.add(new Pos(p.x + 1, p.y));
                toFill.add(new Pos(p.x + 1, p.y - 1));
                toFill.add(new Pos(p.x, p.y - 1));
                toFill.add(new Pos(p.x - 1, p.y - 1));
                toFill.add(new Pos(p.x - 1, p.y));
                toFill.add(new Pos(p.x - 1, p.y + 1));
                toFill.add(new Pos(p.x, p.y + 1));
            }
        }
        
    }

    List<Pos> offsets(char c) {
        List<Pos> offsets;
        switch (c) {
                    case 'S':
                    //Hardcoded lol
                        offsets = Arrays.asList(new Pos(0,1), new Pos(-1, 0)); 
                        break;
                    case '|':
                        offsets = Arrays.asList(new Pos(0,1), new Pos(0, -1));
                        break;
                    case '-':
                        offsets = Arrays.asList(new Pos(1,0), new Pos(-1,0));
                        break;
                    case 'L':
                        offsets = Arrays.asList(new Pos(0,-1), new Pos(1, 0));
                        break;
                    case 'J':
                        offsets = Arrays.asList(new Pos(0,-1), new Pos(-1, 0));
                        break;
                    case '7':
                        offsets = Arrays.asList(new Pos(0,1), new Pos(-1, 0));
                        break;
                    case 'F':
                        offsets = Arrays.asList(new Pos(0,1), new Pos(1, 0));
                        break;
                    default:
                        offsets = Arrays.asList();
                        break;
                }
        return offsets;
    }

    static int get(int[][] bruh, Pos p) {
        int x = p.x;
        int y = p.y;
        if (x >= bruh.length || x < 0) return 4;
        if (y >= bruh[0].length | y < 0) return 4;        
        return bruh[x][y];
    }

    //INVERTED Y!
    public static char getPipe(Pos p) {
        int x = p.x;
        int y = p.y;
        if (y >= inputS.length | y < 0) return '.';
        var l = inputS[y];
        if (x >= l.length() || x < 0) return '.';
        return l.charAt(x);
    }
}
