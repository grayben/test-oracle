package com.grayben.tools.testOracle.verification;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Function;

import static org.junit.Assert.assertTrue;

/**
 * Created by beng on 29/01/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class AlternateImplementationVerificationProviderTest {

    private Function<Integer, String> theFunction;
    private AlternateImplementationVerificationProvider<Integer, String> alternateImplementationVerificationProvider;

    @After
    public void tearDown() throws Exception {
        theFunction = null;
        alternateImplementationVerificationProvider = null;
    }

    @Test
    public void test_TestReturnsTrue_WhenFunctionUnderTestAndAlternateImplementationAreTheSame() throws Exception {
        theFunction = integer -> String.valueOf(2 * integer);
        alternateImplementationVerificationProvider = new AlternateImplementationVerificationProvider<>(theFunction);
        Integer input = 4456;
        String actualOutput = theFunction.apply(input);
        assertTrue(alternateImplementationVerificationProvider.test(input, actualOutput));
    }
}