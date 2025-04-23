package io.cockroachdb.demo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

public class RandomData {
    private static final Logger logger = LoggerFactory.getLogger(RandomData.class);

    private static final List<String> countries = new ArrayList<>();

    private static final List<String> currencies = new ArrayList<>();

    static {
        for (Locale locale : Locale.getAvailableLocales()) {
            if (StringUtils.hasLength(locale.getDisplayCountry(Locale.US))) {
                countries.add(locale.getDisplayCountry(Locale.US));
            }
        }

        for (Currency currency : Currency.getAvailableCurrencies()) {
            currencies.add(currency.getCurrencyCode());
        }
    }

    public static BigDecimal randomBigDecimal(double low, double high, int fractions) {
        if (high <= low) {
            throw new IllegalArgumentException("high<=low");
        }
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        return BigDecimal.valueOf(Math.max(low, random.nextDouble() * high))
                .setScale(fractions, RoundingMode.HALF_UP);
    }

    public static <T extends Enum<?>> T selectRandom(Class<T> clazz) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    public static <E> E selectRandom(List<E> collection) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        return collection.get(random.nextInt(collection.size()));
    }

    public static <E> E selectRandom(E[] collection) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        return collection[random.nextInt(collection.length)];
    }

    public static <E> Collection<E> selectRandomUnique(List<E> collection, int count) {
        if (count > collection.size()) {
            throw new IllegalArgumentException("Not enough elements");
        }

        Set<E> uniqueElements = new HashSet<>();
        while (uniqueElements.size() < count) {
            uniqueElements.add(selectRandom(collection));
        }

        return uniqueElements;
    }

    public static int randomInt(int start, int end) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextInt(start, end);
    }

    public static double randomDouble(double start, int end) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextDouble(start, end);
    }

    public static String randomPhoneNumber() {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuilder sb = new StringBuilder()
                .append("(")
                .append(random.nextInt(9) + 1);
        for (int i = 0; i < 2; i++) {
            sb.append(random.nextInt(10));
        }
        sb.append(") ")
                .append(random.nextInt(9) + 1);
        for (int i = 0; i < 2; i++) {
            sb.append(random.nextInt(10));
        }
        sb.append("-");
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static String randomCountry() {
        return selectRandom(countries);
    }

    public static String randomCurrency() {
        return selectRandom(currencies);
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

    public static String randomBytes(int length) {
        byte[] buffer = new byte[length];
        ThreadLocalRandom.current().nextBytes(buffer);
        return toBase64(buffer);
    }

    public static String toBase64(byte[] arr) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(arr);
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

