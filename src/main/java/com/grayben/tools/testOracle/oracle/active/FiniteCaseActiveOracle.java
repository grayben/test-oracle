package com.grayben.tools.testOracle.oracle.active;

import java.util.Map;
import java.util.Set;

/**
 * An active oracle capable of computing the expected output of a finite number of input cases.
 * <p>
 * Created by Ben Gray on 28/01/2016.
 * @param <I> the input type
 * @param <O> the output type
 */
final public class FiniteCaseActiveOracle<I, O> implements ActiveOracle<I, O> {

    /**
     * Maps a finite set of input cases to their expected outputs
     */
    private final Map<I, O> casePairs;

    /**
     * Constructs a {@link FiniteCaseActiveOracle} relying in the specified map.
     * @param casePairs
     */
    public FiniteCaseActiveOracle(Map<I, O> casePairs) {
        this.casePairs = casePairs;
    }

    @Override
    public O apply(I input) {
        if (casePairs.containsKey(input) == false){
            throw new IllegalArgumentException(
                    "The input case given is not covered by this oracle"
            );
        }
        return casePairs.get(input);
    }

    public Set<I> getCoveredInputCases(){
        return this.casePairs.keySet();
    }
}
