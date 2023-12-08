import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class Day08 {
    static String[] inputS;

    public static void main(String[] args) throws Throwable {
        try (BufferedReader r = Files.newBufferedReader(Paths.get("input.txt"))) {
            inputS = r.lines().map(String::strip).toArray(String[]::new);
        }

        String inst = inputS[0];

        class Node {
            String name;
            String left;
            String right;
        }

        HashMap<String, Node> nodeMap = new HashMap<>();

        for (int i = 2; i < inputS.length; i++) {
            String l = inputS[i];
            var a = l.split(" \\= \\(");
            var b = a[1].split(", ");
            var n_name = a[0];
            var left = b[0];
            var right = b[1].split("\\)")[0];
            Node n = new Node();
            n.name = n_name;
            n.left = left;
            n.right = right;
            nodeMap.put(n_name, n);
        }

        int r = 0;

        String node = "AAA";

        while (!node.equals("ZZZ")) {
            for (char c : inst.toCharArray()) {
                if (c == 'L') {
                    node = nodeMap.get(node).left;
                } else if (c =='R') {
                    node = nodeMap.get(node).right;
                } else {
                    throw null;
                }
            }
            r += inst.length();
        }

        System.out.println(r);
    }
}