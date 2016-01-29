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
public abstract class DiscreteCaseOracle<E extends Enum<E>, I, O> extends Oracle<E, O> {

    private final Class<E> enumClass;

    private final EnumAdapter<E, I> enumAdapter = new EnumAdapter<E, I>() {
        @Override
        protected EnumMap<E, I> enumMap() {
            Map<E, I> map = new HashMap<>();
            for (E option : EnumSet.allOf(enumClass)) {
                map.put(option, pairGenerator.apply(option).getKey());
            }
            return new EnumMap<>(map);
        }
    };

    private final Function<E, Function<I, O>> underlyingSystemUnderTestFunction = underlyingSystemUnderTestFunction();

    private final Function<E, Pair<I, O>> pairGenerator = pairGenerator();

    protected DiscreteCaseOracle(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    final protected Function<E, O> systemUnderTest() {
        return new Function<E, O>() {
            @Override
            public O apply(E e) {
                Function<I, O> underlyingSystemUnderTest = underlyingSystemUnderTestFunction.apply(e);
                I transformedInput = enumAdapter.apply(e);
                O actualOutput = underlyingSystemUnderTest.apply(transformedInput);
                return actualOutput;
            }
        };
    }

    @Override
    final protected VerificationProvider<E, O> verificationProvider() {
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

    protected abstract Function<E, Function<I,O>> underlyingSystemUnderTestFunction();

    protected abstract Function<E, Pair<I, O>> pairGenerator();














    public static class ConcreteDiscreteCaseOracle
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

        //TODO: fix low cohesion: enum adapter and verification provider both declaring different sides of a relation

        @Override
        protected Function<Option, Pair<Integer, String>> pairGenerator(){
            return new Function<Option, Pair<Integer, String>>() {
                @Override
                public Pair<Integer, String> apply(Option option) {
                    switch (option) {
                        case SIMPLE:
                            return new ImmutablePair<>(1, "2");
                    }
                    throw new IllegalArgumentException("Did not recognise the Option given");
                }
            };
        }

        public enum Option {
            SIMPLE
        }

    }
}
