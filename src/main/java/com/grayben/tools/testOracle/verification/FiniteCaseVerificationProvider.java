package com.grayben.tools.testOracle.verification;

import com.grayben.tools.testOracle.oracle.input.InputAdapter;

import java.util.function.BiPredicate;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class FiniteCaseVerificationProvider<P, I, O> implements BiPredicate<P, O> {

    protected abstract InputAdapter<P, I> inputAdapter();

    protected abstract

    @Override
    public boolean test(P p, O o) {
        return inputAdapter().andThen();
    }
}
