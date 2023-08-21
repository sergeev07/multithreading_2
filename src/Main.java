import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {

        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {

                String route = generateRoute("RLF", 100);
                int frequency = (int) route.chars().filter(c -> c == 'R').count();

                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(frequency)) {
                        sizeToFreq.put(frequency, sizeToFreq.get(frequency) + 1);
                    } else {
                        sizeToFreq.put(frequency, 1);
                    }
                }
            }).start();
        }

        Map.Entry<Integer, Integer> max = sizeToFreq.entrySet().stream().max(Map.Entry.comparingByValue()).get();

        System.out.printf("Самое частое количество повторений %d (встретилось %d раз)%n", max.getKey(), max.getValue());

        System.out.println("Другие размеры:");

        sizeToFreq.entrySet()
                .stream()
                .forEach(e -> System.out.printf("- %d (%d раз)%n", e.getKey(), e.getValue()));
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
