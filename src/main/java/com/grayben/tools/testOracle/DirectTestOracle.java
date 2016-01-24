package com.grayben.tools.testOracle;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.function.Function;

/**
 * Created by beng on 24/01/2016.
 */
public class DirectTestOracle<I, O> extends ParametricTestOracle<I, I, O>{

    public DirectTestOracle(Function<I, O> directEquation) {
        super(i -> {
            Function<I, I> identity = Function.identity();
            return new ImmutablePair<>(identity.apply(i), directEquation.apply(i));
        });
    }
}
