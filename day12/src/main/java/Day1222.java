import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Day1222 {
    static String[] inputS;

    record DaState(int indexValid, long groupSize) {}

    public static void main(String[] args) throws Throwable {
        try (BufferedReader r = Files.newBufferedReader(Paths.get("input.txt"))) {
            inputS = r.lines().map(String::strip).toArray(String[]::new);
        }

        long r = 0;

        

        for (String rec : inputS) {
            var ah = rec.split(" ");
            Integer[] lengths2 = Arrays.stream(ah[1].split(",")).map(Integer::parseInt).toArray(Integer[]::new);
            var yeet = new ArrayList<Integer>();
            yeet.addAll(Arrays.asList(lengths2));
            yeet.addAll(Arrays.asList(lengths2));
            yeet.addAll(Arrays.asList(lengths2));
            yeet.addAll(Arrays.asList(lengths2));
            yeet.addAll(Arrays.asList(lengths2));
            lengths2 = yeet.toArray(new Integer[0]);
            var lengths = new ArrayList<>(Arrays.asList(lengths2));
            var springs = ah[0] + "?" + ah[0] + "?" + ah[0] + "?" + ah[0] + "?" + ah[0];

            var states = new HashMap<DaState, Long>();
            states.put(new DaState(0, 0), 1l);
            int pos = 0;
            for (char c : springs.toCharArray()) {
                if (c == '.') {
                    states = hashify(states.entrySet().stream().filter(e -> e.getKey().groupSize == 0 || (lengths.size() > e.getKey().indexValid && lengths.get(e.getKey().indexValid) == e.getKey().groupSize)).map(e -> new AbstractMap.SimpleImmutableEntry<>(new DaState(e.getKey().groupSize == 0 ? e.getKey().indexValid : e.getKey().indexValid + 1, 0), e.getValue())));
                } else if (c == '#') {
                    states = hashify(states.entrySet().stream().map(e -> new AbstractMap.SimpleImmutableEntry<>(new DaState(e.getKey().indexValid, e.getKey().groupSize + 1), e.getValue())));
                } else if (c == '?') {
                    states = hashify(Stream.concat(states.entrySet().stream().map(e -> new AbstractMap.SimpleImmutableEntry<>(new DaState(e.getKey().indexValid, e.getKey().groupSize + 1), e.getValue())),
                        states.entrySet().stream().filter(e -> e.getKey().groupSize == 0 || (lengths.size() > e.getKey().indexValid && lengths.get(e.getKey().indexValid) == e.getKey().groupSize)).map(e -> new AbstractMap.SimpleImmutableEntry<>(new DaState(e.getKey().groupSize == 0 ? e.getKey().indexValid : e.getKey().indexValid + 1, 0), e.getValue()))
                    ));
                }
                pos += 1;
                // System.out.println(pos);
                // System.out.println(states);
            }
            states = hashify(states.entrySet().stream().filter(e -> e.getKey().groupSize == 0 || (lengths.size() > e.getKey().indexValid && lengths.get(e.getKey().indexValid) == e.getKey().groupSize)).map(e -> new AbstractMap.SimpleImmutableEntry<>(new DaState(e.getKey().groupSize == 0 ? e.getKey().indexValid : e.getKey().indexValid + 1, 0), e.getValue())));
            long localR = 0;
            for (var s : states.entrySet()) {
                if (s.getKey().indexValid == lengths.size() && s.getKey().groupSize == 0) localR += s.getValue();
            }
            System.out.println(localR);
            r += localR;
        }
        System.out.println(r);
    }

    static HashMap<DaState, Long> hashify(Stream<Entry<DaState, Long>> stream) {
        HashMap<DaState, Long> r = new HashMap<>();
        for (var e : (Iterable<Entry<DaState, Long>>) () -> stream.iterator())  {
            r.put(e.getKey(), r.getOrDefault(e.getKey(), 0l) + e.getValue());
        }
        return r;
    }
}
