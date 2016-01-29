package com.grayben.tools.testOracle.oracle;

import com.grayben.tools.testOracle.oracle.input.EnumAdapter;
import com.grayben.tools.testOracle.verification.VerificationProvider;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Function;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class DiscreteCaseOracle<E extends Enum<E>, I, O> extends Oracle<E, O> {

    private final EnumAdapter<E, Pair<I, O>> enumAdapter;
    private final Function<E, Function<I, O>> underlyingSystemUnderTestFunction;

    protected abstract EnumAdapter<E,Pair<I, O>> enumAdapter();
    protected abstract Function<E, Function<I,O>> underlyingSystemUnderTestFunction();

    protected DiscreteCaseOracle() {
        underlyingSystemUnderTestFunction = underlyingSystemUnderTestFunction();
        enumAdapter = enumAdapter();
    }

    @Override
    final protected Function<E, O> systemUnderTest() {
        return e -> underlyingSystemUnderTestFunction.apply(e).apply(enumAdapter.apply(e).getKey());
    }

    @Override
    final protected VerificationProvider<E, O> verificationProvider() {
        return (e, o) -> enumAdapter.apply(e).getRight().equals(o);
    }
}
