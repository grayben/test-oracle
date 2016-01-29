package com.grayben.tools.testOracle.verification;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by beng on 28/01/2016.
 */
public class ConstraintVerificationProvider<I, O> implements VerificationProvider<I, O> {

    private final Function<I, Predicate<O>> constraintGenerator;

    public ConstraintVerificationProvider(Function<I, Predicate<O>> constraintGenerator) {
        this.constraintGenerator = constraintGenerator;
    }

    @Override
    final public boolean test(I input, O output) {
        return constraintGenerator.apply(input).test(output);
    }
}
