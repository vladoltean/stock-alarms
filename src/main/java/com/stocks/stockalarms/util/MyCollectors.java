package com.stocks.stockalarms.util;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * By vlad.oltean on 2019-08-27.
 */
public class MyCollectors {

    private MyCollectors() {
    }

    public static <T, K, V> Collector<T, ?, MultiValueMap<K, V>> toMultiValueMap(Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends V> valueMapper) {

        Supplier<LinkedMultiValueMap<K, V>> supplier = LinkedMultiValueMap::new;

        BiConsumer<LinkedMultiValueMap<K, V>, T> accumulator
                = (acc, elem) -> acc.add(keyMapper.apply(elem), valueMapper.apply(elem));

        BinaryOperator<LinkedMultiValueMap<K, V>> combiner = (map1, map2) -> {
            map1.putAll(map2);
            return map1;
        };

        Function<LinkedMultiValueMap<K, V>, MultiValueMap<K, V>> finisher = (lmvm) -> lmvm;

        return new CollectorImpl<>(
                supplier,
                accumulator,
                combiner,
                finisher,
                new HashSet<>()
        );

    }

    /**
     * Simple implementation class for {@code Collector}.
     *
     * @param <T> the type of elements to be collected
     * @param <R> the type of the result
     */
    static class CollectorImpl<T, A, R> implements Collector<T, A, R> {

        private final Supplier<A> supplier;
        private final BiConsumer<A, T> accumulator;
        private final BinaryOperator<A> combiner;
        private final Function<A, R> finisher;
        private final Set<Characteristics> characteristics;

        CollectorImpl(Supplier<A> supplier,
                BiConsumer<A, T> accumulator,
                BinaryOperator<A> combiner,
                Function<A, R> finisher,
                Set<Characteristics> characteristics) {
            this.supplier = supplier;
            this.accumulator = accumulator;
            this.combiner = combiner;
            this.finisher = finisher;
            this.characteristics = characteristics;
        }

        @Override
        public BiConsumer<A, T> accumulator() {
            return accumulator;
        }

        @Override
        public Supplier<A> supplier() {
            return supplier;
        }

        @Override
        public BinaryOperator<A> combiner() {
            return combiner;
        }

        @Override
        public Function<A, R> finisher() {
            return finisher;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return characteristics;
        }
    }

}
