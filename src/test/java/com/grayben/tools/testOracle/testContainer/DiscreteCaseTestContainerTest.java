package com.grayben.tools.testOracle.testContainer;

import com.grayben.tools.testOracle.SystemUnderTest;
import junit.framework.TestCase;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Function;

/**
 * Created by beng on 29/01/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class DiscreteCaseTestContainerTest {

    public enum SystemUnderTestOption {
        SIMPLE(String::valueOf),
        COMPLICATED(integer -> "Number " + String.valueOf(integer));

        private final SystemUnderTest<Integer, String> systemUnderTest;

        public SystemUnderTest<Integer, String> getSystemUnderTest(){
            return this.systemUnderTest;
        }

        SystemUnderTestOption(SystemUnderTest<Integer, String> systemUnderTest) {
            this.systemUnderTest = systemUnderTest;
        }
    }

    @Test
    public void test_TestReturnsFalse_WhenEnumAdapterDoesNotMatchUnderlyingSUTFunction() throws Exception {

        Function<SystemUnderTestOption, Pair<Integer, String>> pairGenerator
                = functionOption -> {
                    switch (functionOption){
                        case SIMPLE:
                            return new ImmutablePair<>(1, "foobar");
                        case COMPLICATED:
                            return new ImmutablePair<>(2, "chicken soup");
                    }
                    throw new IllegalArgumentException("The option was not recognised");
                };

        DiscreteCaseTestContainer<SystemUnderTestOption, Integer, String> oracle = new DiscreteCaseTestContainer<>(
                SystemUnderTestOption.class,
                SystemUnderTestOption::getSystemUnderTest,
                pairGenerator
        );

        TestCase.assertFalse(oracle.validate(SystemUnderTestOption.SIMPLE));
        TestCase.assertFalse(oracle.validate(SystemUnderTestOption.COMPLICATED));

    }

    @Test
    public void test_TestReturnsTrue_WhenEnumAdapterDoesMatchUnderlyingSUTFunction() throws Exception {

        Function<SystemUnderTestOption, Pair<Integer, String>> pairGenerator
                = functionOption -> {
            Integer input = null;
            switch (functionOption){
                case SIMPLE:
                    input = 1;
                    return new ImmutablePair<>(input, functionOption.getSystemUnderTest().apply(input));
                case COMPLICATED:
                    input = 555;
                    return new ImmutablePair<>(input, functionOption.getSystemUnderTest().apply(input));
            }
            throw new IllegalArgumentException("The option was not recognised");
        };

        DiscreteCaseTestContainer<SystemUnderTestOption, Integer, String> oracle = new DiscreteCaseTestContainer<>(
                SystemUnderTestOption.class,
                SystemUnderTestOption::getSystemUnderTest,
                pairGenerator
        );

        TestCase.assertTrue(oracle.validate(SystemUnderTestOption.SIMPLE));
        TestCase.assertTrue(oracle.validate(SystemUnderTestOption.COMPLICATED));

    }
}