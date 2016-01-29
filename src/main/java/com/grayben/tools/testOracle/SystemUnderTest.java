package com.grayben.tools.testOracle;

import java.util.function.Function;

/**
 * Created by beng on 29/01/2016.
 */
public interface SystemUnderTest<I, O> extends Function<I, O> {
}
