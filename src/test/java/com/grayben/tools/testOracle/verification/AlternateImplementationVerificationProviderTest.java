package com.grayben.tools.testOracle.verification;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Function;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by beng on 29/01/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class AlternateImplementationVerificationProviderTest {

    private Function<Integer, String> thePrimaryImplementation;
    private Function<Integer, String> theAlternateImplementation;
    private AlternateImplementationVerificationProvider<Integer, String> alternateImplementationVerificationProvider;

    @After
    public void tearDown() throws Exception {
        thePrimaryImplementation = null;
        theAlternateImplementation = null;
        alternateImplementationVerificationProvider = null;
    }

    @Test
    public void test_TestReturnsTrue_WhenFunctionUnderTestAndAlternateImplementationAreTheSame() throws Exception {
        thePrimaryImplementation = integer -> String.valueOf(2 * integer);
        theAlternateImplementation = thePrimaryImplementation;
        alternateImplementationVerificationProvider = new AlternateImplementationVerificationProvider<>(theAlternateImplementation);
        Integer input = 4456;
        String actualOutput = thePrimaryImplementation.apply(input);
        assertTrue(alternateImplementationVerificationProvider.test(input, actualOutput));
    }

    @Test
    public void test_TestReturnsFalse_WhenFunctionUnderTestAndAlternateImplementationAreNotTheSame() throws Exception {
        theAlternateImplementation = integer -> String.valueOf(2 * integer);
        thePrimaryImplementation = integer -> theAlternateImplementation.apply(integer).concat(" and then some more");
        alternateImplementationVerificationProvider = new AlternateImplementationVerificationProvider<>(theAlternateImplementation);
        Integer input = 4456;
        String actualOutput = thePrimaryImplementation.apply(input);
        assertFalse(alternateImplementationVerificationProvider.test(input, actualOutput));
    }
}