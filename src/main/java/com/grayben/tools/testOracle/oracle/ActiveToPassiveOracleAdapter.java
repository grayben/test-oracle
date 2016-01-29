package com.grayben.tools.testOracle.oracle;

import com.grayben.tools.testOracle.oracle.active.ActiveOracle;
import com.grayben.tools.testOracle.oracle.passive.PassiveOracle;

/**
 * Created by beng on 30/01/2016.
 */
final public class ActiveToPassiveOracleAdapter<I, O> implements PassiveOracle<I, O> {

    private final ActiveOracle<I, O> activeOracle;

    public ActiveToPassiveOracleAdapter(ActiveOracle<I, O> activeOracle) {
        this.activeOracle = activeOracle;
    }

    @Override
    public boolean test(I input, O output) {
        return activeOracle.apply(input).equals(output);
    }
}
