package com.grayben.tools.testOracle;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Created by beng on 20/01/2016.
 */
public class InputOutputPair<I, O> implements InputOutputPairable<I, O> {

    private final Pair<I, O> pair;

    public InputOutputPair(I input, O output) {
        this.pair = new ImmutablePair<I, O>(input, output);
    }

    public I input() {
        return pair.getLeft();
    }

    public O output() {
        return pair.getRight();
    }
}
