package com.grayben.tools.testOracle.verification;

import java.util.Map;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class DiscreteCaseVerificationProvider<I, O> implements VerificationProvider<I, O> {

    private final Map<I, O> casePairs;

    protected DiscreteCaseVerificationProvider() {
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
