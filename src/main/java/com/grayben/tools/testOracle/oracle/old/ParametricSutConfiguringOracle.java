package com.grayben.tools.testOracle.oracle.old;

import com.grayben.tools.math.parametricEquation.ParametricEquation;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Function;

/**
 * Created by beng on 27/01/2016.
 */
public class ParametricSutConfiguringOracle<P, SUT, I, O> implements Oracle<P, Pair<SUT, I>, O> {

    private final ParametricOracle<P, Pair<SUT, I>, O> parametricOracle;

    public ParametricSutConfiguringOracle(ParametricEquation<P, Pair<SUT, I>, O> parametricEquation) {
        parametricOracle = new ParametricOracle<>(parametricEquation);
    }

    @Override
    public void validate(Function<Pair<SUT, I>, O> operation, P parameter) {
        this.parametricOracle.validate(operation, parameter);
    }
}
