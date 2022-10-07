package br.com.calcred.api.utils;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.RandomUtils.nextInt;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.apache.commons.lang3.ObjectUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomUtils {

    public static <T> List<T> gerarLista(final Supplier<T> supplier, final int startInclusive, final int endExclusive) {
        return Stream.generate(supplier).limit(nextInt(startInclusive, endExclusive)).collect(toList());
    }

    public static <T> T aleatorio(final T[] array) {

        return ofNullable(array)
            .filter(ObjectUtils::isNotEmpty)
            .map(a -> a[nextInt(0, a.length)])
            .orElse(null);
    }
}