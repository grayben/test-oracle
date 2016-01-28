package com.grayben.tools.testOracle.oracle;

import com.grayben.tools.testOracle.oracle.input.EnumAdapter;
import com.grayben.tools.testOracle.verification.PassiveVerificationProvider;

import java.util.function.Function;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class DiscreteCaseOracle<E extends Enum<E>, I, O> extends PassiveOracle<E, O> {

    private final Function<I, O> underlyingSystemUnderTest;

    private final EnumAdapter<E, I> enumAdapter;

    protected DiscreteCaseOracle() {
        underlyingSystemUnderTest = underlyingSystemUnderTest();
        enumAdapter = enumAdapter();
    }

    protected abstract Function<I,O> underlyingSystemUnderTest();

    @Override
    protected Function<E, O> effectiveSystemUnderTest() {
        return enumAdapter.andThen(underlyingSystemUnderTest);
    }

    protected abstract EnumAdapter<E,I> enumAdapter();

    @Override
    final protected PassiveVerificationProvider<E, O> verificationProvider() {
        return discreteCaseVerificationProvider();
    }

    protected abstract PassiveVerificationProvider<E, O> discreteCaseVerificationProvider();
}
