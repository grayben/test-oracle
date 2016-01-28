package com.grayben.tools.testOracle.oracle;

import com.grayben.tools.testOracle.verification.VerificationProvider;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class AbstractOracle<I, O>{


    private Supplier<I> inputSupplier;


    public AbstractOracle(VerificationProvider<I, O> verificationProvider) {
        this.systemUnderTest = systemUnderTest();
        this.verificationProvider = verificationProvider;
    }

    protected abstract Function<I,O> systemUnderTest();

    private final Function<I, O> systemUnderTest;

    public boolean validate() {
        I input = inputSupplier.get();
        O actualOutput = systemUnderTest.apply(input);
        return verificationProvider.verify(input, actualOutput);
    }

    private final VerificationProvider<I, O> verificationProvider;
}
