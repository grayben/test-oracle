package com.grayben.tools.testOracle.testContainer;

import com.grayben.tools.testOracle.SystemUnderTest;
import com.grayben.tools.testOracle.oracle.Oracles;
import com.grayben.tools.testOracle.oracle.active.ActiveOracle;
import com.grayben.tools.testOracle.oracle.passive.PassiveOracle;

/**
 * Created by beng on 28/01/2016.
 */
public class TestContainer<I, O>{

    private final SystemUnderTest<I, O> systemUnderTest;

    private final PassiveOracle<I, O> passiveOracle;

    public TestContainer(SystemUnderTest<I, O> systemUnderTest, PassiveOracle<I, O> passiveOracle) {
        this.systemUnderTest = systemUnderTest;
        this.passiveOracle = passiveOracle;
    }

    public TestContainer(SystemUnderTest<I, O> systemUnderTest, ActiveOracle<I, O> activeOracle) {
        this.systemUnderTest = systemUnderTest;
        this.passiveOracle = Oracles.passiveOracle(activeOracle);
    }

    final public boolean verify(I input) {
        O actualOutput = systemUnderTest.apply(input);
        return passiveOracle.test(input, actualOutput);
    }
}
