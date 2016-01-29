package com.grayben.tools.testOracle.oracle;

import java.util.Map;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class DiscreteCasePassiveOracle<I, O> implements PassiveOracle<I, O> {

    private final Map<I, O> casePairs;

    protected DiscreteCasePassiveOracle() {
        super();
        casePairs = casePairs();
    }

    protected abstract Map<I,O> casePairs();

    @Override
    final public boolean test(I input, O output) {
        if (casePairs.containsKey(input) == false){
            throw new IllegalArgumentException(
                    "The input case given is not covered by this verification provider"
            );
        }
        if (casePairs.get(input).equals(output))
            return true;
        else
            return false;
    }
}
