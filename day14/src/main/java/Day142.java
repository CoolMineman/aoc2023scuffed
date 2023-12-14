import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day142 {
    static char[][] inputS;

    public static void main(String[] args) throws Throwable {
        try (BufferedReader r = Files.newBufferedReader(Paths.get("input.txt"))) {
            inputS = r.lines().map(String::strip).map(String::toCharArray).toArray(char[][]::new);
        }

        for (int i = 0;; i++) {
            System.out.println("Cycle " + i);
            System.out.println(score(inputS));
            cycle();
        }
        
        // Find the pattern by hand lmao
    }

    static void cycle() {
        for (int i = 0; i < inputS.length; i++) {
            for (int j = 0; j < inputS[i].length; j++) {
                if (inputS[i][j] == 'O') {
                    slideUp(i, j);
                }
            }
        }
        // for (char[] e : inputS) {
        //     System.out.println(new String(e));
        // }
        // System.out.println();
        for (int j = 0; j < inputS[0].length; j++) {
            for (int i = 0; i < inputS.length; i++) {
                if (inputS[i][j] == 'O') {
                    slideLeft(i, j);
                }
            }
        }
        // for (char[] e : inputS) {
        //     System.out.println(new String(e));
        // }
        // System.out.println();
        for (int i = inputS.length - 1; i >= 0; i--) {
            for (int j = 0; j < inputS[i].length; j++) {
                if (inputS[i][j] == 'O') {
                    slideDown(i, j);
                }
            }
        }
        // for (char[] e : inputS) {
        //     System.out.println(new String(e));
        // }
        // System.out.println();
        for (int j = inputS[0].length - 1; j >= 0; j--) {
            for (int i = 0; i < inputS.length; i++) {
                if (inputS[i][j] == 'O') {
                    slideRight(i, j);
                }
            }
        }
        // for (char[] e : inputS) {
        //     System.out.println(new String(e));
        // }
        // System.out.println();
    }

    static long score(char[][] inputS) {
        long r = 0;
        for (int i = 0; i < inputS.length; i++) {
            for (int j = 0; j < inputS[i].length; j++) {
                if (inputS[i][j] == 'O') {
                    r += inputS.length - i;
                }
            }
        }
        return r;
    }

    static void slideUp(int y, int x) {
        while (y != 0 && inputS[y - 1][x] == '.') {
            inputS[y - 1][x] = 'O';
            inputS[y][x] = '.';
            y -= 1;
        }
    }

    static void slideDown(int y, int x) {
        while (y != inputS.length - 1 && inputS[y + 1][x] == '.') {
            inputS[y + 1][x] = 'O';
            inputS[y][x] = '.';
            y += 1;
        }
    }

    static void slideLeft(int y, int x) {
        while (x != 0 && inputS[y][x - 1] == '.') {
            inputS[y][x - 1] = 'O';
            inputS[y][x] = '.';
            x -= 1;
        }
    }

    static void slideRight(int y, int x) {
        while (x != inputS[y].length - 1 && inputS[y][x + 1] == '.') {
            inputS[y][x + 1] = 'O';
            inputS[y][x] = '.';
            x += 1;
        }
    }
}
