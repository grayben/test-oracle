package com.grayben.tools.testOracle.oracle;

import com.grayben.tools.testOracle.oracle.input.InputAdapter;
import com.grayben.tools.testOracle.verification.VerificationProvider;

import java.util.function.Function;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class PassiveOracle<P, I, O>{

    private final Function<I, O> systemUnderTest;

    private final InputAdapter<P, I> inputAdapter;

    private final VerificationProvider<I, O> verificationProvider;

    public PassiveOracle() {
        this.systemUnderTest = systemUnderTest();
        this.inputAdapter = inputAdapter();
        this.verificationProvider = verificationProvider();
    }

    public boolean validate(P parameter) {
        I input = inputAdapter.apply(parameter);
        O actualOutput = systemUnderTest.apply(input);
        return verificationProvider.test(input, actualOutput);
    }

    protected abstract Function<I,O> systemUnderTest();

    protected abstract InputAdapter<P, I> inputAdapter();

    protected abstract VerificationProvider<I,O> verificationProvider();
}
