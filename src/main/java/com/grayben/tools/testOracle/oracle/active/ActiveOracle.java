package com.grayben.tools.testOracle.oracle.active;

import com.grayben.tools.testOracle.oracle.passive.PassiveOracle;

import java.util.function.Function;

/**
 * A simple facade for {@link Function} which defines an active test oracle.
 * <p/>
 * Created by Ben Gray on 30/01/2016.
 * @param <I> the input type
 * @param <O> the output type
 */
public interface ActiveOracle<I, O> extends Function<I, O> {

    /**
     * Determine the expected output on a given input.
     * <p>
     * @param input the input
     * @return the expected output for {@code input}
     */
    @Override
    O apply(I input);

    /**
     * Convert this active oracle to a passive oracle.
     * @return the adapted oracle
     */
    PassiveOracle<I, O> adaptToPassiveOracle();
}
