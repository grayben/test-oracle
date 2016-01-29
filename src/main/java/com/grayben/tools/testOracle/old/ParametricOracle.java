package com.grayben.tools.testOracle.old;

/**
 * Created by beng on 20/01/2016.
 */
abstract class ParametricOracle<P, I, O> {}
        /*

        extends TestContainer<P, O> {
    private final ParametricEquation<P, I, O> parametricEquation;

    public ParametricOracle(ParametricEquation<P, I, O> parametricEquation) {
        this.parametricEquation = parametricEquation;
    }

    //@Override
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

        */