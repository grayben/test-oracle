package com.grayben.tools.testOracle.oracle.passive;

import java.util.function.BiPredicate;

/**
 * Created by beng on 28/01/2016.
 */
public interface PassiveOracle<I, O> extends BiPredicate<I, O> {

    @Override
    boolean test(I input, O actualOutput);
}
