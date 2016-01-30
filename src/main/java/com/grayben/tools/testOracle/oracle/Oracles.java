package com.grayben.tools.testOracle.oracle;

import com.grayben.tools.testOracle.oracle.active.ActiveOracle;
import com.grayben.tools.testOracle.oracle.passive.PassiveOracle;

/**
 * Created by Ben Gray on 30/01/2016.
 */
public class Oracles {

    private Oracles(){}

    /**
     *
     * @param activeOracle
     * @param <I>
     * @param <O>
     * @return
     */
    public static <I, O> PassiveOracle<I, O> passiveOracle(ActiveOracle<I, O> activeOracle){
        return (input, actualOutput) -> actualOutput.equals(activeOracle.apply(input));
    }
}
