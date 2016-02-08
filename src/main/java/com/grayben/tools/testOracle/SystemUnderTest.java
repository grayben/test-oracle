package com.grayben.tools.testOracle;

import com.grayben.tools.math.function.builder.FunctionBuilder;

import java.util.function.Function;

/**
 * Defines a system under test (SUT) as a function.
 * <p>
 * Semantically, any method, or sequence of operations, is a system.
 * Any system can be
 * represented as a function which takes inputs and
 * transforms it into outputs.
 * <p>
 * Any set of inputs can be represented by a single input
 * aggregating the former inputs, just as a set of outputs
 * can be similarly aggregated.
 * <p>
 * Created by Ben Gray on 29/01/2016.
 *
 * @param <I> the type of input
 * @param <O> the type of output
 */
public interface SystemUnderTest<I, O> extends Function<I, O> {

    /**
     * A builder. Intended to provide assistance in incrementally constructing
     * SUTs in future releases.
     *
     * @param <I> the type of input
     * @param <O> the type of output
     */
    class Builder<I, O> extends FunctionBuilder<I, O> {

        /**
         * Construct a builder based upon the specified SUT.
         *
         * @param systemUnderTest the active oracle upon which to base the builder.
         */
        public Builder(SystemUnderTest<I, O> systemUnderTest) {
            super(systemUnderTest);
        }
    }
}
