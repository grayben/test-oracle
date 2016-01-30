package com.grayben.tools.testOracle.testContainer;

import com.grayben.tools.testOracle.SystemUnderTest;
import com.grayben.tools.testOracle.oracle.Oracles;
import com.grayben.tools.testOracle.oracle.active.ActiveOracle;
import com.grayben.tools.testOracle.oracle.passive.PassiveOracle;

/**
 * A class responsible for invoking the {@link SystemUnderTest} and verifying the output using a test oracle.
 * <p>
 * Created by Ben Gray on 28/01/2016.
 * @see ActiveOracle
 * @see PassiveOracle
 * @param <I> the input type
 * @param <O> the output type
 */
public class TestContainer<I, O>{

    /**
     * The system under test
     */
    private final SystemUnderTest<I, O> systemUnderTest;

    /**
     * The test oracle represented as a {@link PassiveOracle}
     */
    private final PassiveOracle<I, O> passiveOracle;

    /**
     * Constructs a {@link TestContainer} which invokes the specified {@link SystemUnderTest} and verifies
     * its output using the specified {@link PassiveOracle}.
     * @param systemUnderTest the system under test
     * @param passiveOracle the passive oracle
     */
    public TestContainer(SystemUnderTest<I, O> systemUnderTest, PassiveOracle<I, O> passiveOracle) {
        this.systemUnderTest = systemUnderTest;
        this.passiveOracle = passiveOracle;
    }

    /**
     * Constructs a {@link TestContainer} which invokes the specified {@link SystemUnderTest} and verifies
     * its output using the specified {@link ActiveOracle}.
     * @param systemUnderTest the system under test
     * @param activeOracle the active oracle
     */
    public TestContainer(SystemUnderTest<I, O> systemUnderTest, ActiveOracle<I, O> activeOracle) {
        this.systemUnderTest = systemUnderTest;
        /**
         * Convert the {@link activeOracle} to its equivalent {@link passiveOracle} and then assign it
         */
        this.passiveOracle = Oracles.passiveOracle(activeOracle);
    }

    /**
     * For the specified input, verify the system under test on the specified input.
     * @param input the input to the {@link SystemUnderTest} encapsulated by this {@link TestContainer}
     * @return true if and only if the system under test is verified on the specified input
     */
    final public boolean verify(I input) {
        O actualOutput = systemUnderTest.apply(input);
        return passiveOracle.test(input, actualOutput);
    }
}
