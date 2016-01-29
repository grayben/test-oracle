package com.grayben.tools.testOracle.oracle;

import com.google.common.collect.ImmutableMap;
import com.grayben.tools.testOracle.oracle.input.EnumAdapter;
import com.grayben.tools.testOracle.verification.DiscreteCaseVerificationProvider;
import com.grayben.tools.testOracle.verification.VerificationProvider;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class DiscreteCaseOracle<E extends Enum<E>, I, O>{

    private final Oracle<E, O> delegateOracle;

    private final Class<E> enumClass;

    private final EnumAdapter<E, I> enumAdapter;

    private final EnumAdapter<E, I> enumAdapter() {
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

    private final Function<E, Function<I, O>> underlyingSystemUnderTestFunction = underlyingSystemUnderTestFunction();

    private final Function<E, Pair<I, O>> pairGenerator = pairGenerator();

    protected DiscreteCaseOracle(Class<E> enumClass) {
        this.enumClass = enumClass;
        enumAdapter = enumAdapter();
        delegateOracle = new Oracle<E, O>() {
            @Override
            protected Function<E, O> systemUnderTest() {
                return e -> {
                    Function<I, O> underlyingSystemUnderTest = underlyingSystemUnderTestFunction.apply(e);
                    I transformedInput = enumAdapter.apply(e);
                    return underlyingSystemUnderTest.apply(transformedInput);
                };
            }

            @Override
            protected VerificationProvider<E, O> verificationProvider() {
                return new DiscreteCaseVerificationProvider<E, O>() {
                    @Override
                    protected Map<E, O> casePairs() {
                        Map<E, O> map = new HashMap<>();
                        for (E option : EnumSet.allOf(enumClass)){
                            map.put(option, pairGenerator.apply(option).getValue());
                        }
                        return ImmutableMap.copyOf(map);
                    }
                };
            }
        };
    }

    final public boolean validate(E discreteCase){
        return delegateOracle.validate(discreteCase);
    }

    protected abstract Function<E, Function<I,O>> underlyingSystemUnderTestFunction();

    protected abstract Function<E, Pair<I, O>> pairGenerator();














    private static class ConcreteDiscreteCaseOracle
            extends DiscreteCaseOracle<ConcreteDiscreteCaseOracle.Option, Integer, String>{

        protected ConcreteDiscreteCaseOracle() {
            super(ConcreteDiscreteCaseOracle.Option.class);
        }

        @Override
        protected Function<Option, Function<Integer, String>> underlyingSystemUnderTestFunction() {
            return options -> {
                switch (options){
                    case SIMPLE:
                        return integer -> String.valueOf(2 * integer);
                }
                throw new IllegalArgumentException("The input option was not recognised");
            };
        }

        @Override
        protected Function<Option, Pair<Integer, String>> pairGenerator(){
            return option -> {
                switch (option) {
                    case SIMPLE:
                        return new ImmutablePair<>(1, "2");
                }
                throw new IllegalArgumentException("Did not recognise the Option given");
            };
        }

        public enum Option {
            SIMPLE
        }

    }
}
