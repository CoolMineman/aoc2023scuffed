import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day14 {
    static char[][] inputS;

    public static void main(String[] args) throws Throwable {
        try (BufferedReader r = Files.newBufferedReader(Paths.get("input.txt"))) {
            inputS = r.lines().map(String::strip).map(String::toCharArray).toArray(char[][]::new);
        }

        for (int i = 0; i < inputS.length; i++) {
            for (int j = 0; j < inputS[i].length; j++) {
                if (inputS[i][j] == 'O') {
                    slideUp(inputS, i, j);
                }
            }
        }

        for (char[] e : inputS) {
            System.out.println(new String(e));
        }

        long r = 0;
        for (int i = 0; i < inputS.length; i++) {
            for (int j = 0; j < inputS[i].length; j++) {
                if (inputS[i][j] == 'O') {
                    r += inputS.length - i;
                }
            }
        }
        System.out.println(r);
    }

    static void slideUp(char[][] in, int y, int x) {
        while (y != 0 && inputS[y - 1][x] == '.') {
            inputS[y - 1][x] = 'O';
            inputS[y][x] = '.';
            y -= 1;
        }
    }
}
