package com.grayben.tools.testOracle.oracle.active;

import java.util.function.Function;

/**
 * Created by beng on 28/01/2016.
 */
final public class AlternateImplementationActiveOracle<I, O> extends AbstractActiveOracle<I, O> {

    private final Function<I, O> alternateImplementation;

    public AlternateImplementationActiveOracle(Function<I, O> alternateImplementation) {
        this.alternateImplementation = alternateImplementation;
    }

    @Override
    public O apply(I input) {
        return alternateImplementation.apply(input);
    }
}
