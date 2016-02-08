package com.grayben.tools.testOracle.oracle.active;

import java.util.function.Function;

/**
 * An active oracle that uses an alternative implementation to determine the expected output.
 * <p>
 * Ideally, the alternative implementation may be a previous version of the system under test which itself has
 * been well tested, or a simplified implementation which is only a partial oracle (only determines the output
 * over a restricted input domain).
 * <p>
 * Created by Ben Gray on 28/01/2016.
 * @see com.grayben.tools.testOracle.SystemUnderTest
 * @param <I> the input type
 * @param <O> the output type
 */
final public class AlternateImplementationActiveOracle<I, O> implements ActiveOracle<I, O> {

    /**
     * The alternate implementation with which to determine the expected output of a given input
     */
    private final Function<I, O> alternateImplementation;

    /**
     * Constructs an {@link AlternateImplementationActiveOracle}
     * which utilises the specified alternate implementation
     * of the target system under test.
     * @param alternateImplementation the alternate implementation of the target system under test
     * @see com.grayben.tools.testOracle.SystemUnderTest
     */
    public AlternateImplementationActiveOracle(Function<I, O> alternateImplementation) {
        this.alternateImplementation = alternateImplementation;
    }

    @Override
    public O apply(I input) {
        return alternateImplementation.apply(input);
    }
}
