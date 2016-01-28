package com.grayben.tools.testOracle.oracle;

import com.grayben.tools.testOracle.verification.VerificationProvider;

import java.util.function.Function;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class Oracle<P, I, O>{

    private final Function<I, O> systemUnderTest;

    private final Function<P, I> inputSupplier;

    private final VerificationProvider<I, O> verificationProvider;

    public Oracle() {
        this.systemUnderTest = systemUnderTest();
        this.inputSupplier = inputSupplier();
        this.verificationProvider = verificationProvider();
    }

    public boolean validate(P parameter) {
        I input = inputSupplier.apply(parameter);
        O actualOutput = systemUnderTest.apply(input);
        return verificationProvider.test(input, actualOutput);
    }

    protected abstract Function<I,O> systemUnderTest();

    protected abstract Function<P, I> inputSupplier();

    protected abstract VerificationProvider<I,O> verificationProvider();
}
