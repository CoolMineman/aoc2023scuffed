import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

public class Day082 {
    static String[] inputS;

    public static void main(String[] args) throws Throwable {
        try (BufferedReader r = Files.newBufferedReader(Paths.get("input2.txt"))) {
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

        String[] nodes = nodeMap.keySet().stream().filter(k -> k.endsWith("A")).toArray(String[]::new);
        long part2WasCompleteNonsense[] = new long[nodes.length];
        HashMap<String, String> cache = new HashMap<>();

        for (int i = 0; i < nodes.length; i++) {
            while (!nodes[i].endsWith("Z")) {
                nodes[i] = cache.computeIfAbsent(nodes[i], n -> {
                    for (char c : inst.toCharArray()) {
                        if (c == 'L') {
                            n = nodeMap.get(n).left;
                        } else if (c =='R') {
                            n = nodeMap.get(n).right;
                        } else {
                            throw null;
                        }
                    }
                    return n;
                });
                part2WasCompleteNonsense[i] += inst.length();
            }
        }

        System.out.println(Arrays.toString(part2WasCompleteNonsense));

        System.out.println("Use https://www.calculatorsoup.com/calculators/math/lcm.php");
    }

    public static int lcm(int number1, int number2) {
        if (number1 == 0 || number2 == 0) {
            return 0;
        }
        int absNumber1 = Math.abs(number1);
        int absNumber2 = Math.abs(number2);
        int absHigherNumber = Math.max(absNumber1, absNumber2);
        int absLowerNumber = Math.min(absNumber1, absNumber2);
        int lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }
}