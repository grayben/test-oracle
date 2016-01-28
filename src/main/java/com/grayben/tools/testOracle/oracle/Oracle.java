package com.grayben.tools.testOracle.oracle;

import java.util.function.Function;

/**
 * Created by beng on 24/01/2016.
 */
public interface Oracle<I, O> {
    boolean validate(Function<I, O> operation, I input);
}
