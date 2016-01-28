package com.grayben.tools.testOracle.verification;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class ConstraintVerificationProvider<I, O> implements VerificationProvider<I, O> {

    private final Function<I, Predicate<O>> predicateGenerator;

    public ConstraintVerificationProvider() {
        predicateGenerator = generatePredicate();
    }

    protected abstract Function<I, Predicate<O>> generatePredicate();

    @Override
    public boolean verify(I input, O output) {
        return predicateGenerator.apply(input).test(output);
    }
}
