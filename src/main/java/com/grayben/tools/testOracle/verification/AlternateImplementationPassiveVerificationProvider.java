package com.grayben.tools.testOracle.verification;

import java.util.function.Function;

/**
 * Created by beng on 28/01/2016.
 */
public class AlternateImplementationPassiveVerificationProvider<I, O> implements PassiveVerificationProvider<I, O> {

    private final Function<I, O> alternateImplementation;

    public AlternateImplementationPassiveVerificationProvider(Function<I, O> alternateImplementation) {
        this.alternateImplementation = alternateImplementation;
    }

    @Override
    public boolean test(I input, O output) {
        return output == alternateImplementation.apply(input);
    }
}
