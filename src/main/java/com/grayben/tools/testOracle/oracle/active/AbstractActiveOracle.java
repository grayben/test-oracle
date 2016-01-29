package com.grayben.tools.testOracle.oracle.active;

import com.grayben.tools.testOracle.oracle.passive.PassiveOracle;

/**
 * Created by beng on 30/01/2016.
 */
public abstract class AbstractActiveOracle<I, O> implements ActiveOracle<I, O> {

    @Override
    final public PassiveOracle<I, O> adaptToPassiveOracle() {
        return (input, actualOutput) -> actualOutput.equals(apply(input));
    }
}
