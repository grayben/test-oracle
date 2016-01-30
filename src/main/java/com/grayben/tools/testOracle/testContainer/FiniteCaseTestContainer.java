package com.grayben.tools.testOracle.testContainer;

import com.grayben.tools.testOracle.SystemUnderTest;
import com.grayben.tools.testOracle.oracle.active.FiniteCaseActiveOracle;

import java.util.Set;

/**
 * Created by Ben Gray on 30/01/2016.
 */
public class FiniteCaseTestContainer<I, O> extends TestContainer<I, O> {

    private final FiniteCaseActiveOracle<I, O> finiteCaseActiveOracle;

    public final Set<I> getCoveredInputCases(){
        return finiteCaseActiveOracle.getCoveredInputCases();
    }

    public FiniteCaseTestContainer(SystemUnderTest<I, O> systemUnderTest,
                                   FiniteCaseActiveOracle<I, O> finiteCaseActiveOracle) {
        super(systemUnderTest, finiteCaseActiveOracle);
        this.finiteCaseActiveOracle = finiteCaseActiveOracle;
    }
}
