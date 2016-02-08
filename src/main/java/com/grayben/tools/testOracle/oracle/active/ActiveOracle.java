package com.grayben.tools.testOracle.oracle.active;

import com.grayben.tools.math.function.builder.FunctionBuilder;

import java.util.function.Function;

/**
 * Given an input, outputs the expected output of a related system under test.
 * <p>
 * A simple facade for {@link Function} which defines an active test oracle.
 * <p/>
 * Created by Ben Gray on 30/01/2016.
 *
 * @param <I> the type of input to the related system under test
 * @param <O> the type of output from the related system under test
 */
public interface ActiveOracle<I, O> extends Function<I, O> {

    /**
     * A builder. Intended to provide assistance in incrementally constructing
     * active oracles in future releases.
     *
     * @param <I> the type of input to the related system under test
     * @param <O> the type of output from the related system under test
     */
    class Builder<I, O> extends FunctionBuilder<I, O> {

        /**
         * Construct a builder based upon the specified active oracle.
         *
         * @param activeOracle the active oracle upon which to base the builder.
         */
        public Builder(ActiveOracle<I, O> activeOracle) {
            super(activeOracle);
        }
    }

    /**
     * Determine the expected output on a given input.
     * <p>
     * @param input the input
     * @return the expected output
     */
    @Override
    O apply(I input);
}
