package com.grayben.tools.testOracle.oracle;

import com.grayben.tools.testOracle.verification.VerificationProvider;

import java.util.function.Function;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class PassiveOracle<I, O>{

    private final Function<I, O> systemUnderTest;

    private final VerificationProvider<I, O> verificationProvider;

    public PassiveOracle() {
        this.systemUnderTest = effectiveSystemUnderTest();
        this.verificationProvider = verificationProvider();
    }

    public boolean validate(I input) {
        O actualOutput = systemUnderTest.apply(input);
        return verificationProvider.test(input, actualOutput);
    }

    protected abstract Function<I,O> effectiveSystemUnderTest();

    protected abstract VerificationProvider<I, O> verificationProvider();
}
