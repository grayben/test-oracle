package com.grayben.tools.testOracle.oracle;

import com.grayben.tools.testOracle.verification.ActiveVerificationProvider;

import java.util.function.Function;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class ActiveOracle<I, O>{

    private final Function<I, O> systemUnderTest;

    private final ActiveVerificationProvider<I, O> activeVerificationProvider;

    public ActiveOracle() {
        this.systemUnderTest = systemUnderTest();
        this.activeVerificationProvider = verificationProvider();
    }

    public boolean validate(I input) {
        O actualOutput = systemUnderTest.apply(input);
        return activeVerificationProvider.apply(input).equals(actualOutput);
    }

    protected abstract Function<I,O> systemUnderTest();

    protected abstract ActiveVerificationProvider<I, O> verificationProvider();
}
