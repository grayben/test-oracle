package com.grayben.tools.testOracle.testContainer;

import com.grayben.tools.testOracle.SystemUnderTest;
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

    final public boolean validate(I input) {
        O actualOutput = systemUnderTest.apply(input);
        return passiveOracle.test(input, actualOutput);
    }
}
