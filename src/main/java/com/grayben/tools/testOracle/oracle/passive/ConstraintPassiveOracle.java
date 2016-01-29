package com.grayben.tools.testOracle.oracle.passive;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by beng on 28/01/2016.
 */
final public class ConstraintPassiveOracle<I, O> implements PassiveOracle<I, O> {

    private final Function<I, Predicate<O>> constraintGenerator;

    public ConstraintPassiveOracle(Function<I, Predicate<O>> constraintGenerator) {
        this.constraintGenerator = constraintGenerator;
    }

    @Override
    public boolean test(I input, O output) {
        return constraintGenerator.apply(input).test(output);
    }
}
