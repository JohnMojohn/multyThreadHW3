import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger length3 = new AtomicInteger();
    public static AtomicInteger length4 = new AtomicInteger();
    public static AtomicInteger length5 = new AtomicInteger();

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void counter(int textLength) {
        if (textLength == 3) {
            length3.getAndIncrement();
        } else if (textLength == 4) {
            length4.getAndIncrement();
        } else if (textLength == 5) {
            length5.getAndIncrement();
        }
    }

    public static boolean sameChar(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != text.charAt(i - 1))
                return false;
        }
        return true;
    }

    public static boolean ascendingChar(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1))
                return false;
        }
        return true;
    }

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Runnable polindrom = () -> {
            for (String text : texts) {
                if (text.equals(new StringBuilder(text).reverse().toString())) {
                    counter(text.length());
                }
            }
        };
        Runnable same = () -> {
            for (String text : texts) {
                if (sameChar(text)) {
                    counter(text.length());
                }
            }
        };
        Runnable ascending = () -> {
            for (String text : texts) {
                if (!sameChar(text) && ascendingChar(text)) {
                    counter(text.length());
                }
            }
        };
        Thread polindromThread = new Thread(polindrom);
        Thread sameThread = new Thread(same);
        Thread ascendingThread = new Thread(ascending);
        polindromThread.start();
        sameThread.start();
        ascendingThread.start();
        polindromThread.join();
        sameThread.join();
        ascendingThread.join();
        System.out.println("Красивых слов с длиной 3: " + length3 + " шт.");
        System.out.println("Красивых слов с длиной 4: " + length4 + " шт.");
        System.out.println("Красивых слов с длиной 5: " + length5 + " шт.");
    }
}