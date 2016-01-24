package com.grayben.tools.testOracle;

import java.util.function.Function;

/**
 * Created by beng on 24/01/2016.
 */
public interface TestOracle<P, I, O> {
    void validate(Function<I, O> operation, P parameter);
}
