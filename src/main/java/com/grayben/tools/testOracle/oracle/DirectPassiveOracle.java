package com.grayben.tools.testOracle.oracle;

import com.grayben.tools.testOracle.oracle.input.ParametricInputSupplier;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class DirectPassiveOracle<I, O> extends PassiveOracle<I, I, O> {

    @Override
    protected final ParametricInputSupplier<I, I> inputSupplier() {
        return new ParametricInputSupplier<I, I>() {
            @Override
            public I apply(I i) {
                return i;
            }
        };
    }
}
