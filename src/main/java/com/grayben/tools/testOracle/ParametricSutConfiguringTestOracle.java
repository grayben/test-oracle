package com.grayben.tools.testOracle;

import com.grayben.tools.math.parametricEquation.ParametricEquation;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Function;

/**
 * Created by beng on 27/01/2016.
 */
public class ParametricSutConfiguringTestOracle<P, SUT, I, O> implements TestOracle<P, Pair<SUT, I>, O> {

    private final ParametricTestOracle<P, Pair<SUT, I>, O> parametricTestOracle;

    public ParametricSutConfiguringTestOracle(ParametricEquation<P, Pair<SUT, I>, O> parametricEquation) {
        parametricTestOracle = new ParametricTestOracle<>(parametricEquation);
    }

    @Override
    public void validate(Function<Pair<SUT, I>, O> operation, P parameter) {
        this.parametricTestOracle.validate(operation, parameter);
    }
}
