package com.grayben.tools.testOracle.oracle.active;

import java.util.function.Function;

/**
 * Created by beng on 30/01/2016.
 */
public interface ActiveOracle<I, O> extends Function<I, O> {

    @Override
    O apply(I input);
}
