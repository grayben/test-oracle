package com.grayben.tools.testOracle.oracle.old;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.function.Function;

/**
 * Created by beng on 24/01/2016.
 */
public class DirectFunctionOracle<I, O> implements Oracle<I, I, O> {

    private final ParametricOracle<I, I, O> parametricOracle;

    public DirectFunctionOracle(Function<I, O> directFunction) {
        this.parametricOracle = new ParametricOracle<>(i -> {
            Function<I, I> identity = Function.identity();
            return new ImmutablePair<>(identity.apply(i), directFunction.apply(i));
        });
    }

    @Override
    public void validate(Function<I, O> operation, I parameter) {
        this.parametricOracle.validate(operation, parameter);
    }
}
