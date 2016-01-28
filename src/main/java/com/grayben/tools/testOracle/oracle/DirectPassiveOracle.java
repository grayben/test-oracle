package com.grayben.tools.testOracle.oracle;

import com.grayben.tools.testOracle.oracle.input.InputAdapter;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class DirectPassiveOracle<I, O> extends PassiveOracle<I, I, O> {

    @Override
    protected final InputAdapter<I, I> inputAdapter() {
        return new InputAdapter<I, I>() {
            @Override
            public I apply(I i) {
                return i;
            }
        };
    }
}
