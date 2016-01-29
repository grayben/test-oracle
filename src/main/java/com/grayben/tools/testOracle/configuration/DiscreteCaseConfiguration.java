package com.grayben.tools.testOracle.configuration;

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

    public DiscreteCaseConfiguration(Class<E> enumClass,
                                        Function<E, SystemUnderTest<I, O>> systemUnderTestGenerator,
                                        Function<E, Pair<I, O>> pairGenerator) {
        EnumAdapter<E, I> enumAdapter = enumAdapter(enumClass, pairGenerator);
        PassiveOracle<E, O> passiveOracle = passiveOracle(enumClass, pairGenerator);
        SystemUnderTest<E, O> systemUnderTest = systemUnderTest(enumAdapter, systemUnderTestGenerator);
        delegateConfiguration = new Configuration<>(systemUnderTest, passiveOracle);
    }

    private EnumAdapter<E, I> enumAdapter(Class<E> enumClass, Function<E, Pair<I, O>> pairGenerator) {
        Map<E, I> map = new HashMap<>();
        for (E option : EnumSet.allOf(enumClass)) {
            map.put(option, pairGenerator.apply(option).getKey());
        }
        EnumMap<E, I> enumMap = new EnumMap<>(map);
        return new EnumAdapter<>(enumMap);
    }

    private SystemUnderTest<E, O> systemUnderTest(EnumAdapter<E, I> enumAdapter, Function<E, SystemUnderTest<I, O>> systemUnderTestGenerator) {
        return enumInput -> {
            SystemUnderTest<I, O> systemUnderTest = systemUnderTestGenerator.apply(enumInput);
            I transformedInput = enumAdapter.apply(enumInput);
            return systemUnderTest.apply(transformedInput);
        };
    }

    private PassiveOracle<E, O> passiveOracle(Class<E> enumClass, Function<E, Pair<I, O>> pairGenerator) {
        Map<E, O> casePairs = new HashMap<>();
        for (E option : EnumSet.allOf(enumClass)) {
            casePairs.put(option, pairGenerator.apply(option).getValue());
        }
        return new DiscreteCasePassiveOracle<>(casePairs);
    }

    final public boolean validate(E discreteCase){
        return delegateConfiguration.validate(discreteCase);
    }
}
