package com.grayben.tools.testOracle.verification;

import com.grayben.tools.testOracle.oracle.input.EnumAdapter;

import java.util.function.BiPredicate;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class FiniteCaseVerificationProvider<P extends Enum<P>, I, O> implements VerificationProvider<P, O> {

    private final EnumAdapter<P, I> enumAdapter;
    
    private final BiPredicate<I, O> delegatePredicate;

    protected abstract EnumAdapter<P, I> enumAdapter();


    protected FiniteCaseVerificationProvider() {
        super();
        enumAdapter = enumAdapter();
        delegatePredicate = delegatePredicate();
    }

    protected abstract BiPredicate<I, O> delegatePredicate();

    @Override
    public boolean test(P parameter, O output) {
        return delegatePredicate.test(enumAdapter.apply(parameter), output);
    }
}
