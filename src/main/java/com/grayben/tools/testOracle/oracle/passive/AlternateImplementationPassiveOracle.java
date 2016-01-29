package com.grayben.tools.testOracle.oracle.passive;

import java.util.function.Function;

/**
 * Created by beng on 28/01/2016.
 */
final public class AlternateImplementationPassiveOracle<I, O> implements PassiveOracle<I, O> {

    private final Function<I, O> alternateImplementation;

    public AlternateImplementationPassiveOracle(Function<I, O> alternateImplementation) {
        this.alternateImplementation = alternateImplementation;
    }

    @Override
    public boolean test(I input, O output) {
        return output.equals(alternateImplementation.apply(input));
    }
}
