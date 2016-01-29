package com.grayben.tools.testOracle.oracle.active;

import java.util.Map;

/**
 * Created by beng on 28/01/2016.
 */
final public class DiscreteCaseActiveOracle<I, O> extends AbstractActiveOracle<I, O> {

    private final Map<I, O> casePairs;

    public DiscreteCaseActiveOracle(Map<I, O> casePairs) {
        this.casePairs = casePairs;
    }

    @Override
    public O apply(I input) {
        if (casePairs.containsKey(input) == false){
            throw new IllegalArgumentException(
                    "The input case given is not covered by this verification provider"
            );
        }
        return casePairs.get(input);
    }
}
