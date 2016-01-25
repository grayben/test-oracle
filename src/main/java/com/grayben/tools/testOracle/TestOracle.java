package com.grayben.tools.testOracle;

import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Function;

/**
 * Created by beng on 24/01/2016.
 */
public interface TestOracle<SUT, P, I, O> {
    class F1<SUT, I, O> implements Function<Pair<SUT, I>, O> {

        @Override
        public O apply(Pair<SUT, I> sutInputPair) {
            return null;
        }
    }
    void validate(SUT systemUnderTest, Function<I, O> operation, P parameter);
}
