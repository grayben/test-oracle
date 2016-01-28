package com.grayben.tools.testOracle.oracle;

import java.util.function.Function;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class CompleteDomainPassiveOracle<I, O> extends PassiveOracle<I, I, O> {

    @Override
    protected final Function<I, I> inputSupplier() {
        return Function.identity();
    }
}
