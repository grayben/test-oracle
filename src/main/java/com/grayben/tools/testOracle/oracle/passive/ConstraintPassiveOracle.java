package com.grayben.tools.testOracle.oracle.passive;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A passive oracle that applies a constraint, based on the input, to a specified output in order to verify it.
 * <p>
 * The set of constraints relied upon may vary with the input itself.
 * Therefore, the set of input to constraint mappings
 * accordingly, the set of constraints is not stored as a Collection (which is finite),
 * but instead represented as a function that maps any arbitrary input to a constraint
 * to be applied to the output to be tested.
 * <p>
 * Created by Ben Gray on 28/01/2016.
 * @see Function
 * @param <I> the input type
 * @param <O> the output type
 */
final public class ConstraintPassiveOracle<I, O> implements PassiveOracle<I, O> {

    /**
     * The possibly infinite set input to constraint mappings
     */
    private final Function<I, Predicate<O>> constraintGenerator;

    /**
     * Constructs a {@link ConstraintPassiveOracle} relying upon the specified constraint generator.
     * <p>
     * @param constraintGenerator the constraint generator upon which to rely
     */
    public ConstraintPassiveOracle(Function<I, Predicate<O>> constraintGenerator) {
        this.constraintGenerator = constraintGenerator;
    }

    @Override
    public boolean test(I input, O output) {
        return constraintGenerator.apply(input).test(output);
    }
}
