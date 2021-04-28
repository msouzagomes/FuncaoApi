package br.com.calcred.api.utils;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.RandomUtils.nextInt;

import org.apache.commons.lang3.ObjectUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomUtils {

    public static <T> T aleatorio(final T[] array) {

        return ofNullable(array)
            .filter(ObjectUtils::isNotEmpty)
            .map(a -> a[nextInt(0, a.length)])
            .orElse(null);
    }
}