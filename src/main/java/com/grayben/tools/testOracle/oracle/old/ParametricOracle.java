package com.grayben.tools.testOracle.oracle.old;

import com.grayben.tools.math.parametricEquation.ParametricEquation;
import com.grayben.tools.testOracle.oracle.Oracle;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;

/**
 * Created by beng on 20/01/2016.
 */
public class ParametricOracle<P, I, O> implements Oracle<P, I, O> {
    private final ParametricEquation<P, I, O> parametricEquation;

    public ParametricOracle(ParametricEquation<P, I, O> parametricEquation) {
        this.parametricEquation = parametricEquation;
    }

    @Override
    public void validate(Function<I, O> operation, P parameter){
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

    private Pair<I, O> getInputAndExpectedOutput(P parameter) {
        if (parameter == null) {
            throw new NullPointerException(
                    "Argument 'parameter' is null"
            );
        }
        return parametricEquation.apply(parameter);
    }
}
