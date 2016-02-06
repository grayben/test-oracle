package com.grayben.tools.testOracle.testContainer;

import com.grayben.tools.testOracle.SystemUnderTest;
import com.grayben.tools.testOracle.oracle.active.ActiveOracle;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.*;

/**
 * Created by beng on 6/02/2016.
 */
public class TestContainerTest {

    public static final int NUM_RANDOM_TRIALS = 1000 * 1000;

    @Test
    public void test_verifyReturnsTrueOnRandomisedInput_WhenSystemUnderTestAndActiveOracleAreTheSame
            () throws Exception {

        Function<Integer, String> underlyingFunction = Integer::toHexString;

        SystemUnderTest<Integer, String> systemUnderTest = underlyingFunction::apply;
        ActiveOracle<Integer, String> activeOracle = underlyingFunction::apply;

        TestContainer<Integer, String> oracle = new TestContainer.Builder<Integer, String>()
                .begin()
                .systemUnderTest(systemUnderTest)
                .oracle(activeOracle)
                .build();

        for (int i = 0; i < NUM_RANDOM_TRIALS; i++){
            assertTrue(oracle.verify(RandomUtils.nextInt(0, Integer.MAX_VALUE) * 2 + RandomUtils.nextInt(0, 1)));
        }

    }

    @Test
    public void test_verifyReturnsFalseOnRandomisedInput_WhenSystemUnderTestAndActiveOracleAreNonIntersecting
            () throws Exception {

        SystemUnderTest<Integer, String> systemUnderTest = Integer::toHexString;
        ActiveOracle<Integer, String> activeOracle = (t) -> "Hex: " + systemUnderTest.apply(t);

        TestContainer<Integer, String> oracle = new TestContainer.Builder<Integer, String>()
                .begin()
                .systemUnderTest(systemUnderTest)
                .oracle(activeOracle)
                .build();

        for (int i = 0; i < NUM_RANDOM_TRIALS; i++){
            assertFalse(oracle.verify(RandomUtils.nextInt(0, Integer.MAX_VALUE) * 2 + RandomUtils.nextInt(0, 1)));
        }

    }

}