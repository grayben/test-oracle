package com.grayben.tools.testOracle.oracle;

import com.grayben.tools.testOracle.oracle.active.AlternateImplementationActiveOracle;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by beng on 29/01/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class AlternateImplementationActiveOracleTest {

    private Function<Integer, String> thePrimaryImplementation;
    private Function<Integer, String> theAlternateImplementation;
    private AlternateImplementationActiveOracle<Integer, String> alternateImplementationActiveOracle;

    @After
    public void tearDown() throws Exception {
        thePrimaryImplementation = null;
        theAlternateImplementation = null;
        alternateImplementationActiveOracle = null;
    }

    @Test
    public void test_TestReturnsTrue_WhenFunctionUnderTestAndAlternateImplementationAreTheSame() throws Exception {
        thePrimaryImplementation = integer -> String.valueOf(2 * integer);
        theAlternateImplementation = thePrimaryImplementation;
        alternateImplementationActiveOracle = new AlternateImplementationActiveOracle<>(theAlternateImplementation);
        Integer input = 4456;
        String actualOutput = thePrimaryImplementation.apply(input);
        assertEquals(alternateImplementationActiveOracle.apply(input), actualOutput);
    }

    @Test
    public void test_TestReturnsFalse_WhenFunctionUnderTestAndAlternateImplementationAreNotTheSame() throws Exception {
        theAlternateImplementation = integer -> String.valueOf(2 * integer);
        thePrimaryImplementation = integer -> theAlternateImplementation.apply(integer).concat(" and then some more");
        alternateImplementationActiveOracle = new AlternateImplementationActiveOracle<>(theAlternateImplementation);
        Integer input = 4456;
        String actualOutput = thePrimaryImplementation.apply(input);
        assertNotEquals(alternateImplementationActiveOracle.apply(input), actualOutput);
    }
}