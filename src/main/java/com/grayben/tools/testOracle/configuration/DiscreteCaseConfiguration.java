package com.grayben.tools.testOracle.configuration;

import com.google.common.collect.ImmutableMap;
import com.grayben.tools.testOracle.SystemUnderTest;
import com.grayben.tools.testOracle.configuration.input.EnumAdapter;
import com.grayben.tools.testOracle.oracle.passive.DiscreteCasePassiveOracle;
import com.grayben.tools.testOracle.oracle.passive.PassiveOracle;
import org.apache.commons.lang3.tuple.Pair;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by beng on 28/01/2016.
 */
public class DiscreteCaseConfiguration<E extends Enum<E>, I, O>{

    private final Configuration<E, O> delegateConfiguration;

    private final Class<E> enumClass;

    private final EnumAdapter<E, I> enumAdapter;

    private final Function<E, SystemUnderTest<I, O>> systemUnderTestGenerator;

    private final Function<E, Pair<I, O>> pairGenerator;

    protected DiscreteCaseConfiguration(Class<E> enumClass,
                                        Function<E, SystemUnderTest<I, O>> systemUnderTestGenerator,
                                        Function<E, Pair<I, O>> pairGenerator) {
        this.enumClass = enumClass;
        this.systemUnderTestGenerator = systemUnderTestGenerator;
        this.pairGenerator = pairGenerator;
        enumAdapter = enumAdapter();
        SystemUnderTest<E, O> systemUnderTest = e -> {
            SystemUnderTest<I, O> systemUnderTest1 = DiscreteCaseConfiguration.this.systemUnderTestGenerator.apply(e);
            I transformedInput = enumAdapter.apply(e);
            return systemUnderTest1.apply(transformedInput);
        };
        PassiveOracle<E, O> passiveOracle = new DiscreteCasePassiveOracle<E, O>() {
            @Override
            protected Map<E, O> casePairs() {
                Map<E, O> map = new HashMap<>();
                for (E option : EnumSet.allOf(enumClass)) {
                    map.put(option, DiscreteCaseConfiguration.this.pairGenerator.apply(option).getValue());
                }
                return ImmutableMap.copyOf(map);
            }
        };
        delegateConfiguration = new Configuration<>(systemUnderTest, passiveOracle);
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
        return delegateConfiguration.validate(discreteCase);
    }
}
