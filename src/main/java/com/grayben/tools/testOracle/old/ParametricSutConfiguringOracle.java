package com.grayben.tools.testOracle.old;

/**
 * Created by beng on 27/01/2016.
 */
abstract class ParametricSutConfiguringOracle<P, SUT, I, O> {}
/*
extends TestContainer<P, Pair<SUT, I>, O> {


    private final ParametricOracle<P, Pair<SUT, I>, O> parametricOracle;

    public ParametricSutConfiguringOracle(ParametricEquation<P, Pair<SUT, I>, O> parametricEquation) {
        parametricOracle = new ParametricOracle<>(parametricEquation);
    }

    @Override
    public void verify(Function<Pair<SUT, I>, O> operation, P parameter) {
        this.parametricOracle.verify(operation, parameter);
    }
}
*/