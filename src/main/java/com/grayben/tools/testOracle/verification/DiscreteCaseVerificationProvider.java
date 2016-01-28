package com.grayben.tools.testOracle.verification;

import com.grayben.tools.testOracle.oracle.input.EnumAdapter;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class DiscreteCaseVerificationProvider<P extends Enum<P>, I, O> implements VerificationProvider<P, O> {

    private final EnumAdapter<P, I> enumAdapter;
    
    private final VerificationProvider<I, O> delegateVerificationSupplier;

    protected abstract EnumAdapter<P, I> enumAdapter();


    protected DiscreteCaseVerificationProvider() {
        super();
        enumAdapter = enumAdapter();
        delegateVerificationSupplier = delegateVerificationSupplier();
    }

    protected abstract VerificationProvider<I, O> delegateVerificationSupplier();

    @Override
    public boolean test(P discreteCase, O output) {
        return delegateVerificationSupplier.test(enumAdapter.apply(discreteCase), output);
    }
}
