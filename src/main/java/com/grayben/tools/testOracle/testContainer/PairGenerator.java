package com.grayben.tools.testOracle.testContainer;

import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Function;

/**
 * Created by beng on 30/01/2016.
 */
public interface PairGenerator<P, X, Y> extends Function<P, Pair<X, Y>> {
}
