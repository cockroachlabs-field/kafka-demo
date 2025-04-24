package io.cockroachdb.demo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.util.StringUtils;

public class RandomData {
    private static final List<String> countries = new ArrayList<>();

    static {
        for (Locale locale : Locale.getAvailableLocales()) {
            if (StringUtils.hasLength(locale.getDisplayCountry(Locale.US))) {
                countries.add(locale.getDisplayCountry(Locale.US));
            }
        }
    }

    public static <E> E selectRandom(List<E> collection) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        return collection.get(random.nextInt(collection.size()));
    }

    public static String randomCountry() {
        return selectRandom(countries);
    }

    public static String randomZipCode() {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static String randomEmail(String firstName, String lastName) {
        return (firstName.toLowerCase()
                + "."
                + lastName.toLowerCase()
                + "_" + ThreadLocalRandom.current().nextInt()
                + "@cockroachdb.io")
                .replace(' ', '.');
    }

    private static final char[] VOWELS = "aeiou".toCharArray();

    private static final char[] CONSONANTS = "bcdfghjklmnpqrstvwxyz".toCharArray();

    public static String randomWord(int length) {
        StringBuilder sb = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < length; i++) {
            if (random.nextBoolean()) {
                sb.append(VOWELS[random.nextInt(VOWELS.length)]);
            } else {
                sb.append(CONSONANTS[random.nextInt(CONSONANTS.length)]);
            }
        }
        return sb.toString();
    }
}

