package com.grayben.tools.testOracle.testContainer;

import com.grayben.tools.testOracle.SystemUnderTest;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by beng on 6/02/2016.
 */
public class TestContainerTest {

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

        PairGenerator<SystemUnderTestOption, Integer, String> pairGenerator
                = functionOption -> {
                    switch (functionOption){
                        case SIMPLE:
                            return new ImmutablePair<>(1, "foobar");
                        case COMPLICATED:
                            return new ImmutablePair<>(2, "chicken soup");
                    }
                    throw new IllegalArgumentException("The option was not recognised");
                };

        TestContainer<Integer, String> oracle = new TestContainer<>(null);

        assertFalse(true);
        assertFalse(true);

    }

    @Test
    public void test_TestReturnsTrue_WhenEnumAdapterDoesMatchUnderlyingSUTFunction() throws Exception {

        PairGenerator<SystemUnderTestOption, Integer, String> pairGenerator
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

        TestContainer<Integer, String> oracle = new TestContainer<>(null);

        assertTrue(false);
        assertTrue(false);

    }

}