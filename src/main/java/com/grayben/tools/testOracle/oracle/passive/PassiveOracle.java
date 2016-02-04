package com.grayben.tools.testOracle.oracle.passive;

import com.grayben.tools.math.function.builder.BiPredicateBuilder;

import java.util.function.BiPredicate;

/**
 * Created by beng on 28/01/2016.
 */
public interface PassiveOracle<I, O> extends BiPredicate<I, O> {

    class Builder<I, O> extends BiPredicateBuilder<I, O> {
        public Builder(PassiveOracle<I, O> function) {
            super(function);
        }
    }

    @Override
    boolean test(I input, O actualOutput);
}
