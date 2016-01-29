package com.grayben.tools.testOracle.oracle;

import com.grayben.tools.testOracle.oracle.input.EnumAdapter;
import com.grayben.tools.testOracle.verification.DiscreteCaseVerificationProvider;
import com.grayben.tools.testOracle.verification.VerificationProvider;

import java.util.function.Function;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class DiscreteCaseOracle<E extends Enum<E>, I, O> extends Oracle<E, O> {

    private final EnumAdapter<E, I> enumAdapter;
    private final Function<I, O> underlyingSystemUnderTest;
    private final DiscreteCaseVerificationProvider<I, O> underlyingVerificationProvider;

    protected abstract EnumAdapter<E,I> enumAdapter();
    protected abstract Function<I,O> underlyingSystemUnderTest();
    protected abstract DiscreteCaseVerificationProvider<I, O> discreteCaseVerificationProvider();

    protected DiscreteCaseOracle() {
        underlyingSystemUnderTest = underlyingSystemUnderTest();
        enumAdapter = enumAdapter();
        underlyingVerificationProvider = discreteCaseVerificationProvider();
    }

    @Override
    final protected Function<E, O> systemUnderTest() {
        return enumAdapter.andThen(underlyingSystemUnderTest);
    }

    @Override
    final protected VerificationProvider<E, O> verificationProvider() {
        return (e, o) -> underlyingVerificationProvider.test(enumAdapter.apply(e), o);
    }
}
