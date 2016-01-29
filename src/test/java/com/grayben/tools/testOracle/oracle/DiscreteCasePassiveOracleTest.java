package com.grayben.tools.testOracle.oracle;

import com.grayben.tools.testOracle.oracle.passive.DiscreteCasePassiveOracle;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by beng on 29/01/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class DiscreteCasePassiveOracleTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    DiscreteCasePassiveOracle<Integer, String> discreteCaseVerificationProvider;

    @After
    public void tearDown() throws Exception {
        discreteCaseVerificationProvider = null;
    }

    @Test
    public void test_TestThrowsIllegalArgumentException_WhenCasePairsReturnsEmptyMap() throws Exception {
        Map<Integer, String> casePairs = new HashMap<>();
        discreteCaseVerificationProvider = new DiscreteCasePassiveOracle<>(casePairs);
        thrown.expect(IllegalArgumentException.class);
        discreteCaseVerificationProvider.test(new Integer(1), "One");
    }

    @Test
    public void test_TestReturnsTrue_WhenInputEntryInMapReturnedByCasePairs() throws Exception {
        Map<Integer, String> casePairs = new HashMap<>();
        casePairs.put(1, "One");
        casePairs.put(2, "Two");
        discreteCaseVerificationProvider = new DiscreteCasePassiveOracle<>(casePairs);
        Map.Entry<Integer, String> entry = casePairs.entrySet().iterator().next();
        boolean returned = discreteCaseVerificationProvider.test(entry.getKey(), entry.getValue());
        assertTrue(returned);
    }

    @Test
    public void test_TestReturnsFalse_WhenInputValueNotMappedByCasePairs() throws Exception {
        Map<Integer, String> casePairs = new HashMap<>();
        casePairs.put(1, "One");
        casePairs.put(2, "Two");
        discreteCaseVerificationProvider = new DiscreteCasePassiveOracle<>(casePairs);
        Map.Entry<Integer, String> entry = new AbstractMap.SimpleImmutableEntry<>(2, "Fourteen");
        if (casePairs.containsKey(entry.getKey()) == false){
            throw new IllegalStateException("The entry.key must be found in the verification provider casePairs");
        }
        if (casePairs.get(entry.getKey()).equals(entry.getValue())){
            throw new IllegalStateException(
                    "The entry.key must not casePairs to entry.value in the verification provider casePairs"
            );
        }

        boolean returned = discreteCaseVerificationProvider.test(entry.getKey(), entry.getValue());
        assertFalse(returned);
    }

}