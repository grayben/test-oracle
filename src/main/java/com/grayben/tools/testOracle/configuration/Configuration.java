package com.grayben.tools.testOracle.configuration;

import com.grayben.tools.testOracle.SystemUnderTest;
import com.grayben.tools.testOracle.verification.VerificationProvider;

/**
 * Created by beng on 28/01/2016.
 */
public class Configuration<I, O>{

    private final SystemUnderTest<I, O> systemUnderTest;

    private final VerificationProvider<I, O> verificationProvider;

    public Configuration(SystemUnderTest<I, O> systemUnderTest, VerificationProvider<I, O> verificationProvider) {
        this.systemUnderTest = systemUnderTest;
        this.verificationProvider = verificationProvider;
    }

    final public boolean validate(I input) {
        O actualOutput = systemUnderTest.apply(input);
        return verificationProvider.test(input, actualOutput);
    }
}
