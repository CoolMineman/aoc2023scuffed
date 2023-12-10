import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day10 {
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
        System.out.println("Highest: " + highest);
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
