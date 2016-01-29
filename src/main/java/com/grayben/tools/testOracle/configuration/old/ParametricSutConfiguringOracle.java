package com.grayben.tools.testOracle.configuration.old;

/**
 * Created by beng on 27/01/2016.
 */
public abstract class ParametricSutConfiguringOracle<P, SUT, I, O> {}
/*
extends Configuration<P, Pair<SUT, I>, O> {


    private final ParametricOracle<P, Pair<SUT, I>, O> parametricOracle;

    public ParametricSutConfiguringOracle(ParametricEquation<P, Pair<SUT, I>, O> parametricEquation) {
        parametricOracle = new ParametricOracle<>(parametricEquation);
    }

    @Override
    public void validate(Function<Pair<SUT, I>, O> operation, P parameter) {
        this.parametricOracle.validate(operation, parameter);
    }
}
*/