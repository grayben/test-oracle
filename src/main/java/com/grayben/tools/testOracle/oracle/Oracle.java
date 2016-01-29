package com.grayben.tools.testOracle.oracle;

import com.grayben.tools.testOracle.SystemUnderTest;
import com.grayben.tools.testOracle.verification.VerificationProvider;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class Oracle<I, O>{

    private final SystemUnderTest<I, O> systemUnderTest;

    private final VerificationProvider<I, O> verificationProvider;

    public Oracle() {
        this.systemUnderTest = systemUnderTest();
        this.verificationProvider = verificationProvider();
    }

    final public boolean validate(I input) {
        O actualOutput = systemUnderTest.apply(input);
        return verificationProvider.test(input, actualOutput);
    }

    protected abstract SystemUnderTest<I, O> systemUnderTest();

    protected abstract VerificationProvider<I, O> verificationProvider();
}
