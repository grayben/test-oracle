package com.grayben.tools.testOracle.verification;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by beng on 29/01/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class DiscreteCaseVerificationProviderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    DiscreteCaseVerificationProvider<Integer, String> discreteCaseVerificationProvider;

    @After
    public void tearDown() throws Exception {
        discreteCaseVerificationProvider = null;
    }

    @Test
    public void test_TestThrowsIllegalArgumentException_WhenCasePairsReturnsEmptyMap() throws Exception {
        discreteCaseVerificationProvider = new DiscreteCaseVerificationProvider<Integer, String>() {
            @Override
            protected Map<Integer, String> casePairs() {
                return new HashMap<>();
            }
        };
        thrown.expect(IllegalArgumentException.class);
        discreteCaseVerificationProvider.test(new Integer(1), "One");
    }

}