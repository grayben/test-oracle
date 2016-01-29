package com.grayben.tools.testOracle.verification;

import java.util.function.Function;

/**
 * Created by beng on 28/01/2016.
 */
public class AlternateImplementationVerificationProvider<I, O> implements VerificationProvider<I, O> {

    private final Function<I, O> alternateImplementation;

    public AlternateImplementationVerificationProvider(Function<I, O> alternateImplementation) {
        this.alternateImplementation = alternateImplementation;
    }

    @Override
    final public boolean test(I input, O output) {
        return output == alternateImplementation.apply(input);
    }
}
