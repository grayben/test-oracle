package com.grayben.tools.testOracle;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.function.Function;

/**
 * Created by beng on 24/01/2016.
 */
public class DirectTestOracle<I, O> implements TestOracle<I, I, O> {

    private final ParametricTestOracle<I, I, O> parametricTestOracle;

    public DirectTestOracle(Function<I, O> directEquation) {
        this.parametricTestOracle = new ParametricTestOracle<>(i -> {
            Function<I, I> identity = Function.identity();
            return new ImmutablePair<>(identity.apply(i), directEquation.apply(i));
        });
    }

    @Override
    public void validate(Function<I, O> operation, I parameter) {
        this.parametricTestOracle.validate(operation, parameter);
    }
}
