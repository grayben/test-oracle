package com.grayben.tools.testOracle.oracle;

import com.google.common.collect.ImmutableMap;
import com.grayben.tools.testOracle.SystemUnderTest;
import com.grayben.tools.testOracle.oracle.input.EnumAdapter;
import com.grayben.tools.testOracle.verification.DiscreteCaseVerificationProvider;
import com.grayben.tools.testOracle.verification.VerificationProvider;
import org.apache.commons.lang3.tuple.Pair;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by beng on 28/01/2016.
 */
public class DiscreteCaseOracle<E extends Enum<E>, I, O>{

    private final Oracle<E, O> delegateOracle;

    private final Class<E> enumClass;

    private final EnumAdapter<E, I> enumAdapter;

    private final Function<E, SystemUnderTest<I, O>> systemUnderTestGenerator;

    private final Function<E, Pair<I, O>> pairGenerator;

    protected DiscreteCaseOracle(Class<E> enumClass,
                                 Function<E, SystemUnderTest<I, O>> systemUnderTestGenerator,
                                 Function<E, Pair<I, O>> pairGenerator) {
        this.enumClass = enumClass;
        this.systemUnderTestGenerator = systemUnderTestGenerator;
        this.pairGenerator = pairGenerator;
        enumAdapter = enumAdapter();
        delegateOracle = new Oracle<E, O>() {
            @Override
            protected SystemUnderTest<E, O> systemUnderTest() {
                return e -> {
                    SystemUnderTest<I, O> systemUnderTest = DiscreteCaseOracle.this.systemUnderTestGenerator.apply(e);
                    I transformedInput = enumAdapter.apply(e);
                    return systemUnderTest.apply(transformedInput);
                };
            }

            @Override
            protected VerificationProvider<E, O> verificationProvider() {
                return new DiscreteCaseVerificationProvider<E, O>() {
                    @Override
                    protected Map<E, O> casePairs() {
                        Map<E, O> map = new HashMap<>();
                        for (E option : EnumSet.allOf(enumClass)){
                            map.put(option, DiscreteCaseOracle.this.pairGenerator.apply(option).getValue());
                        }
                        return ImmutableMap.copyOf(map);
                    }
                };
            }
        };
    }

    private EnumAdapter<E, I> enumAdapter() {
        return new EnumAdapter<E, I>() {
            @Override
            protected EnumMap<E, I> enumMap() {
                Map<E, I> map = new HashMap<>();
                for (E option : EnumSet.allOf(enumClass)) {
                    map.put(option, pairGenerator.apply(option).getKey());
                }
                return new EnumMap<>(map);
            }
        };
    }

    final public boolean validate(E discreteCase){
        return delegateOracle.validate(discreteCase);
    }
}
