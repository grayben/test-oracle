package com.grayben.tools.testOracle.oracle;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Function;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by beng on 29/01/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class DiscreteCaseOracleTest {

    public enum FunctionOption {
        SIMPLE(String::valueOf),
        COMPLICATED(integer -> "Number " + String.valueOf(integer));

        private final Function<Integer, String> function;

        public Function<Integer, String> getFunction(){
            return this.function;
        }

        FunctionOption(Function<Integer, String> function) {
            this.function = function;
        }
    }

    @Test
    public void test_TestReturnsFalse_WhenEnumAdapterDoesNotMatchUnderlyingSUTFunction() throws Exception {

        DiscreteCaseOracle<FunctionOption, Integer, String> oracle = new DiscreteCaseOracle<FunctionOption, Integer, String>(FunctionOption.class){

            @Override
            protected Function<FunctionOption, Function<Integer, String>> systemUnderTestGenerator() {
                return FunctionOption::getFunction;
            }

            @Override
            protected Function<FunctionOption, Pair<Integer, String>> pairGenerator() {
                return functionOption -> {
                    switch (functionOption){
                        case SIMPLE:
                            return new ImmutablePair<>(1, "foobar");
                        case COMPLICATED:
                            return new ImmutablePair<>(2, "chicken soup");
                    }
                    throw new IllegalArgumentException("The option was not recognised");
                };
            }
        };

        assertFalse(oracle.validate(FunctionOption.SIMPLE));
        assertFalse(oracle.validate(FunctionOption.COMPLICATED));

    }

    @Test
    public void test_TestReturnsTrue_WhenEnumAdapterDoesMatchUnderlyingSUTFunction() throws Exception {

        DiscreteCaseOracle<FunctionOption, Integer, String> oracle = new DiscreteCaseOracle<FunctionOption, Integer, String>(FunctionOption.class){

            @Override
            protected Function<FunctionOption, Function<Integer, String>> systemUnderTestGenerator() {
                return FunctionOption::getFunction;
            }

            @Override
            protected Function<FunctionOption, Pair<Integer, String>> pairGenerator() {
                return functionOption -> {
                    Integer input = null;
                    switch (functionOption){
                        case SIMPLE:
                            input = 1;
                            return new ImmutablePair<>(input, functionOption.getFunction().apply(input));
                        case COMPLICATED:
                            input = 555;
                            return new ImmutablePair<>(input, functionOption.getFunction().apply(input));
                    }
                    throw new IllegalArgumentException("The option was not recognised");
                };
            }
        };

        assertTrue(oracle.validate(FunctionOption.SIMPLE));
        assertTrue(oracle.validate(FunctionOption.COMPLICATED));

    }
}