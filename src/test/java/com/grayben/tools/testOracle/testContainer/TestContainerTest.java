package com.grayben.tools.testOracle.testContainer;

import com.grayben.tools.testOracle.SystemUnderTest;
import com.grayben.tools.testOracle.oracle.Oracles;
import com.grayben.tools.testOracle.oracle.active.ActiveOracle;
import com.grayben.tools.testOracle.oracle.passive.PassiveOracle;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by beng on 6/02/2016.
 */
public class TestContainerTest {

    public static final int NUM_RANDOM_TRIALS = 1000 * 1000;

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test public void
    test_systemUnderTestThrowsNullPointerException_whenArgumentIsNull
            () throws Exception {
        thrown.equals(NullPointerException.class);

        new TestContainer.Builder<>().begin().systemUnderTest(null);
    }

    @Test public void
    test_oracleWithPassiveOracleThrowsNPE_whenArgumentIsNull
            () throws Exception {
        thrown.equals(NullPointerException.class);

        new TestContainer.Builder<>().begin().systemUnderTest(o -> o).oracle(((PassiveOracle<Object, Object>) null));
    }

    @Test public void
    test_oracleWithActiveOracleThrowsNPE_whenArgumentIsNull
            () throws Exception {
        thrown.equals(NullPointerException.class);

        new TestContainer.Builder<>().begin().systemUnderTest(o -> o).oracle(((ActiveOracle<Object, Object>) null));
    }

    @Test public void
    test_builtTestContainerVerifyReturnsSame_forActiveOracleAndEquivalentPassiveOracleOnRandomisedInputs
            () throws Exception {
        Function<Integer, String> underlyingFunction = Integer::toHexString;
        Function<Integer, String> halfCorrect = input -> {
            if (input % 2 == 0){
                return underlyingFunction.apply(input);
            } else {
                return "The ActiveOracle won't return this text, making this SUT fail on this input.";
            }
        };

        SystemUnderTest<Integer, String> systemUnderTest = halfCorrect::apply;

        ActiveOracle<Integer, String> activeOracle = underlyingFunction::apply;
        PassiveOracle<Integer, String> equivalentPassiveOracle = Oracles.passiveOracle(activeOracle);

        TestContainer<Integer, String> containerWithActive = new TestContainer.Builder<Integer, String>()
                .begin()
                .systemUnderTest(systemUnderTest)
                .oracle(activeOracle)
                .build();

        TestContainer<Integer, String> containerWithPassive = new TestContainer.Builder<Integer, String>()
                .begin()
                .systemUnderTest(systemUnderTest)
                .oracle(equivalentPassiveOracle)
                .build();

        for (int i = 0; i < NUM_RANDOM_TRIALS; i++){
            Integer randomInput = RandomUtils.nextInt(0, Integer.MAX_VALUE) * 2 + RandomUtils.nextInt(0, 2);
            boolean expected = containerWithPassive.verify(randomInput),
                    actual = containerWithActive.verify(randomInput);
            assertEquals(expected, actual);
        }
    }

    @Test public void
    test_verifyReturnsTrueOnRandomisedInput_WhenSystemUnderTestAndActiveOracleAreTheSame
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
            assertTrue(oracle.verify(RandomUtils.nextInt(0, Integer.MAX_VALUE) * 2 + RandomUtils.nextInt(0, 2)));
        }
    }

    @Test public void
    test_verifyReturnsFalseOnRandomisedInput_WhenSystemUnderTestAndActiveOracleAreNonIntersecting
            () throws Exception {

        SystemUnderTest<Integer, String> systemUnderTest = Integer::toHexString;
        ActiveOracle<Integer, String> activeOracle = (t) -> "Hex: " + systemUnderTest.apply(t);

        TestContainer<Integer, String> oracle = new TestContainer.Builder<Integer, String>()
                .begin()
                .systemUnderTest(systemUnderTest)
                .oracle(activeOracle)
                .build();

        for (int i = 0; i < NUM_RANDOM_TRIALS; i++){
            assertFalse(oracle.verify(RandomUtils.nextInt(0, Integer.MAX_VALUE) * 2 + RandomUtils.nextInt(0, 2)));
        }
    }
}