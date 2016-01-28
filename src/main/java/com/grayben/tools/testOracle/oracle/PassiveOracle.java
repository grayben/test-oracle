package com.grayben.tools.testOracle.oracle;

import com.grayben.tools.testOracle.verification.PassiveVerificationProvider;

import java.util.function.Function;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class PassiveOracle<I, O>{

    private final Function<I, O> systemUnderTest;

    private final PassiveVerificationProvider<I, O> passiveVerificationProvider;

    public PassiveOracle() {
        this.systemUnderTest = effectiveSystemUnderTest();
        this.passiveVerificationProvider = verificationProvider();
    }

    public boolean validate(I input) {
        O actualOutput = systemUnderTest.apply(input);
        return passiveVerificationProvider.test(input, actualOutput);
    }

    protected abstract Function<I,O> effectiveSystemUnderTest();

    protected abstract PassiveVerificationProvider<I, O> verificationProvider();
}
