public class Part1 {
    static String[] input;

    public static void main(String[] args) throws Throwable {
        long[][] races = {
            {5383728800l, 33316351289153200l}
        };

        long result = 0;
        for (long[] race : races) {
            long ways2Beat = 0;
            long time = race[0];
            long distance = race[1];
            for (long i = 0; i <= time; i++) {
                long speed = i;
                long traveled = (time - speed) * speed;
                if (traveled > distance) ways2Beat += 1;
            }
            if (result == 0)  result = ways2Beat;
            else result *= ways2Beat;
        }
        System.out.println(result);
    }
}