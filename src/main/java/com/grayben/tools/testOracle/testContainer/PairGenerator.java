package com.grayben.tools.testOracle.testContainer;

import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Function;

/**
 * A facade for the {@link Function} interface whereby the output type is a pair of generic types.
 * <p>
 * This interface is defined to increase the readability of code using the functionality provided.
 * <p>
 * Created by Ben Gray on 30/01/2016.
 * @param <P>
 * @param <X>
 * @param <Y>
 */
interface PairGenerator<P, X, Y> extends Function<P, Pair<X, Y>> {
}
