package com.grayben.tools.testOracle.oracle;

import com.grayben.tools.testOracle.oracle.input.EnumAdapter;
import com.grayben.tools.testOracle.verification.ActiveVerificationProvider;

import java.util.function.Function;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class DiscreteCaseOracle<E extends Enum<E>, I, O> extends ActiveOracle<E, O> {

    private final Function<I, O> underlyingSystemUnderTest;

    private final EnumAdapter<E, I> enumAdapter;

    protected abstract EnumAdapter<E,I> enumAdapter();

    protected abstract Function<I,O> underlyingSystemUnderTest();

    protected DiscreteCaseOracle() {
        underlyingSystemUnderTest = underlyingSystemUnderTest();
        enumAdapter = enumAdapter();
    }

    @Override
    protected Function<E, O> systemUnderTest() {
        return enumAdapter.andThen(underlyingSystemUnderTest);
    }

    @Override
    final protected ActiveVerificationProvider<E, O> verificationProvider() {
        return discreteCaseVerificationProvider();
    }

    protected abstract ActiveVerificationProvider<E, O> discreteCaseVerificationProvider();
}
