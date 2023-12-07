import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class Day072 {
    static String[] inputS;

    public static void main(String[] args) throws Throwable {
        try (BufferedReader r = Files.newBufferedReader(Paths.get("input.txt"))) {
            inputS = r.lines().map(String::strip).toArray(String[]::new);
        }

        enum Type {
            FIVE,
            FOUR,
            FULL,
            THREE,
            TWO,
            ONE,
            HIGH
        }

        record Game(String cards, int bid) implements Comparable<Game> {

            Type type() {
                HashMap<Character, Integer> charCnts = new HashMap<>();
                for (char c : cards.toCharArray()) {
                    charCnts.put(c, charCnts.getOrDefault(c, 0) + 1);
                }
                ArrayList<HashMap<Character, Integer>> fuckIt = new ArrayList<>();
                fuckIt.add(charCnts);
                if (charCnts.containsKey('J')) {
                    List<List<Character>> combos = new ArrayList<>(Arrays.asList(new ArrayList<>()));
                    List<List<Character>> combos2 = new ArrayList<>();
                    for (int i = 0; i < charCnts.get('J'); i++) {
                        for (Character character : power) {
                            for (List<Character> combo : combos) {
                                List<Character> ahh = new ArrayList<>(combo);
                                ahh.add(character);
                                combos2.add(ahh);
                            }
                        }
                        combos = combos2;
                        combos2 = new ArrayList<>();
                    }
                    for (List<Character> combo : combos) {
                        HashMap<Character, Integer> charCnts2 = new HashMap<>(charCnts);
                        charCnts2.remove('J');
                        for (char c : combo) {
                            charCnts2.put(c, charCnts2.getOrDefault(c, 0) + 1);
                        }
                        fuckIt.add(charCnts2);
                    }
                }
                return fuckIt.stream().map(Game::yeet).sorted().findFirst().get();
            }

            static Type yeet(HashMap<Character, Integer> charCnts) {
                if (charCnts.size() == 1) {
                    return Type.FIVE;
                } else if (charCnts.size() == 2 && charCnts.entrySet().stream().anyMatch(i -> i.getValue() == 4)) {
                    return Type.FOUR;
                } else if (charCnts.size() == 2 && charCnts.entrySet().stream().anyMatch(i -> i.getValue() == 3)) {
                    return Type.FULL;
                } else if (charCnts.entrySet().stream().anyMatch(i -> i.getValue() == 3)) {
                    return Type.THREE;
                } else if (charCnts.entrySet().stream().filter(i -> i.getValue() == 2).toList().size() == 2) {
                    return Type.TWO;
                } else if (charCnts.entrySet().stream().anyMatch(i -> i.getValue() == 2)) {
                    return Type.ONE;
                } else {
                    return Type.HIGH;
                }
            }

            static List<Character> power = Arrays.asList('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J');

            @Override
            public int compareTo(Game o) {
                int r = -type().compareTo(o.type());
                if (r != 0) return r;
                for (int i = 0; i < cards.length(); i++) {
                    if (power.indexOf(cards.charAt(i)) > power.indexOf(o.cards.charAt(i))) {
                        return -1;
                    } else if (power.indexOf(cards.charAt(i)) < power.indexOf(o.cards.charAt(i))) {
                        return 1;
                    }
                }
                return 0;
            }

        };

        List<Game> games = new ArrayList<>(List.of(inputS).stream().map(s -> new Game(s.split(" ")[0], Integer.parseInt(s.split(" ")[1]))).toList());
        games.sort(Game::compareTo);
        // System.out.println(games);
        // for (var g : games) {
            // System.out.println(g + " " + g.type());
        // }
        int r = 0;
        for (int i = 0; i < games.size(); i++) {
            var g = games.get(i);
            r += (i + 1) * g.bid();
        }
        System.out.println(r);
    }
}
