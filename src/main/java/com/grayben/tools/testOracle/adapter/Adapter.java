package com.grayben.tools.testOracle.adapter;

import java.util.function.Function;

/**
 * A simple facade for {@link Function} which can add to code readability.
 *
 * @param <X> input type
 * @param <Y> output type
 */
public interface Adapter<X, Y> extends Function<X, Y> {
}
