package com.grayben.tools.testOracle.old;

/**
 * Created by beng on 24/01/2016.
 */
abstract class DirectFunctionOracle<I, O> {}
/*
extends TestContainer<I, O> {

    private final ParametricOracle<I, I, O> parametricOracle;

    public DirectFunctionOracle(Function<I, O> directFunction) {
        this.parametricOracle = new ParametricOracle<>(i -> {
            Function<I, I> identity = Function.identity();
            return new ImmutablePair<>(identity.apply(i), directFunction.apply(i));
        });
    }

    public void verify(Function<I, O> operation, I parameter) {
        this.parametricOracle.verify(operation, parameter);
    }
}
*/