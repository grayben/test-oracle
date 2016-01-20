package com.grayben.tools.testOracle;

import com.grayben.tools.math.parametricEquation.ParametricEquation;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by beng on 20/01/2016.
 */
public class ParametricTestOracle<P, I, O> {
    private final ParametricEquation<P, I, O> parametricEquation;

    public ParametricTestOracle(ParametricEquation<P, I, O> parametricEquation) {
        this.parametricEquation = parametricEquation;
    }

    public void validateOutputEqualsExpected(Function<I, O> operation, P parameter){
        Pair<I, O> oraclePair = getInputAndExpectedOutput(parameter);
        I input = oraclePair.getLeft();
        O expectedOutput = oraclePair.getRight();
        O actualOut = operation.apply(input);

        String notEqualMessage = "Based on the parameter " + parameter.toString() + ", this oracle " +
                "generated the input variable " + input.toString() + ".\n" +
                "This oracle determined that the expected output is " + expectedOutput.toString() + ". " +
                "However, the actual function output was " + actualOut.toString() + ".";

        assertEquals(notEqualMessage, expectedOutput, actualOut);
    }

    public void validateOutputNotEqualsExpected(Function<I, O> operation, P parameter){
        Pair<I, O> oraclePair = getInputAndExpectedOutput(parameter);
        I input = oraclePair.getLeft();
        O expectedOutput = oraclePair.getRight();
        O actualOut = operation.apply(input);

        String equalMessage = "Based on the parameter " + parameter.toString() + ", this oracle " +
                "generated the input variable " + input.toString() + ".\n" +
                "This oracle determined that the expected output is " + expectedOutput.toString() + ". " +
                "However, the actual function output was also " + actualOut.toString() + ".";

        assertNotEquals(equalMessage, expectedOutput, actualOut);
    }

    private Pair<I, O> getInputAndExpectedOutput(P parameter) {
        return parametricEquation.apply(parameter);
    }
}
