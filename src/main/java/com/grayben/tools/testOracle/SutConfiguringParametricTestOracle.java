package com.grayben.tools.testOracle;

import com.grayben.tools.math.parametricEquation.ParametricEquation;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Created by beng on 27/01/2016.
 */
public class SutConfiguringParametricTestOracle<P, SUT, I, O> extends ParametricTestOracle<P, Pair<SUT, I>, O> {

    public SutConfiguringParametricTestOracle(ParametricEquation<P, Pair<SUT, I>, O> parametricEquation) {
        super(parametricEquation);
    }
}
