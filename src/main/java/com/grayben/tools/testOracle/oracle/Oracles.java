package com.grayben.tools.testOracle.oracle;

import com.grayben.tools.testOracle.oracle.active.ActiveOracle;
import com.grayben.tools.testOracle.oracle.passive.PassiveOracle;

/**
 * A utility class containing static methods with which to manipulate test oracles defined in
 * {@link com.grayben.tools.testOracle.oracle}.
 * <p>
 * This class may not be instantiated.
 * <p>
 * Created by Ben Gray on 30/01/2016.
 */
public class Oracles {

    private Oracles(){}

    /**
     * Converts the specified {@link ActiveOracle} into an equivalent {@link PassiveOracle}.
     * @param activeOracle the oracle to convert
     * @param <I> the input type
     * @param <O> the output type
     * @return a {@link PassiveOracle} equivalent to the specified {@link ActiveOracle}
     */
    public static <I, O> PassiveOracle<I, O> passiveOracle(ActiveOracle<I, O> activeOracle){
        return (input, actualOutput) -> actualOutput.equals(activeOracle.apply(input));
    }
}
