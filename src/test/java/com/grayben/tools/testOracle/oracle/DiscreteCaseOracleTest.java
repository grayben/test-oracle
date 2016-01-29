package com.grayben.tools.testOracle.oracle;

import com.grayben.tools.testOracle.oracle.input.EnumAdapter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static junit.framework.TestCase.*;

/**
 * Created by beng on 29/01/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class DiscreteCaseOracleTest {

    public enum Options {
        SIMPLE, COMPLICATED
    }

    @Test
    public void test_TestReturnsFalse_WhenEnumAdapterDoesNotMatchUnderlyingSUTFunction() throws Exception {

        DiscreteCaseOracle<Options, Integer, String> oracle = new DiscreteCaseOracle<Options, Integer, String>(){

            @Override
            protected EnumAdapter<Options, ImmutablePair<Integer, String>> enumAdapter() {
                return new EnumAdapter<Options, ImmutablePair<Integer, String>>() {
                    @Override
                    protected EnumMap<Options, ImmutablePair<Integer, String>> enumMap() {
                        Map<Options, ImmutablePair<Integer, String>> map = new HashMap<>();
                        map.put(Options.SIMPLE, new ImmutablePair<>(1, "One"));
                        map.put(Options.COMPLICATED, new ImmutablePair<>(345, "Three-hundred and forty-five"));
                        return new EnumMap<>(map);
                    }
                };
            }

            @Override
            protected Function<Options, Function<Integer, String>> underlyingSystemUnderTestFunction() {
                return options -> {
                    //ignore the option

                    return integer -> String.valueOf(integer);
                };
            }
        };

        assertFalse(oracle.validate(Options.SIMPLE));
        assertFalse(oracle.validate(Options.COMPLICATED));

    }

    @Test
    public void test_TestReturnsTrue_WhenEnumAdapterDoesMatchUnderlyingSUTFunction() throws Exception {

        DiscreteCaseOracle<Options, Integer, String> oracle = new DiscreteCaseOracle<Options, Integer, String>(){

            @Override
            protected EnumAdapter<Options, ImmutablePair<Integer, String>> enumAdapter() {
                return new EnumAdapter<Options, ImmutablePair<Integer, String>>() {
                    @Override
                    protected EnumMap<Options, ImmutablePair<Integer, String>> enumMap() {
                        Map<Options, ImmutablePair<Integer, String>> map = new HashMap<>();
                        map.put(Options.SIMPLE, new ImmutablePair<>(1, String.valueOf(1)));
                        map.put(Options.COMPLICATED, new ImmutablePair<>(345, String.valueOf(345)));
                        return new EnumMap<>(map);
                    }
                };
            }

            @Override
            protected Function<Options, Function<Integer, String>> underlyingSystemUnderTestFunction() {
                return options -> {
                    //ignore the option

                    return integer -> String.valueOf(integer);
                };
            }
        };

        assertTrue(oracle.validate(Options.SIMPLE));
        assertTrue(oracle.validate(Options.COMPLICATED));

    }
}