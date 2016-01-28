package com.grayben.tools.testOracle.verification;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by beng on 28/01/2016.
 */
public class ConstraintPassiveVerificationProvider<I, O> implements PassiveVerificationProvider<I, O> {

    private final Function<I, Predicate<O>> constraintGenerator;

    public ConstraintPassiveVerificationProvider(Function<I, Predicate<O>> constraintGenerator) {
        this.constraintGenerator = constraintGenerator;
    }

    @Override
    public boolean test(I input, O output) {
        return constraintGenerator.apply(input).test(output);
    }
}
