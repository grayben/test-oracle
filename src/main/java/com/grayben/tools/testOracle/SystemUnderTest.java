package com.grayben.tools.testOracle;

import com.grayben.tools.math.function.builder.FunctionBuilder;

import java.util.function.Function;

/**
 * Created by beng on 29/01/2016.
 */
public interface SystemUnderTest<I, O> extends Function<I, O> {

    class Builder<I, O> extends FunctionBuilder<I, O> {

        public Builder(SystemUnderTest<I, O> systemUnderTest) {
            super(systemUnderTest);
        }
    }
}
