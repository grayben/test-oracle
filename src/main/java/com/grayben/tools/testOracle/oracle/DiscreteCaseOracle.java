package com.grayben.tools.testOracle.oracle;

import com.google.common.collect.ImmutableMap;
import com.grayben.tools.testOracle.oracle.input.EnumAdapter;
import com.grayben.tools.testOracle.verification.DiscreteCaseVerificationProvider;
import com.grayben.tools.testOracle.verification.VerificationProvider;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class DiscreteCaseOracle<E extends Enum<E>, I, O> extends Oracle<E, O> {

    private final EnumAdapter<E, I> enumAdapter;
    private final Function<E, Function<I, O>> underlyingSystemUnderTestFunction;

    protected abstract EnumAdapter<E, I> enumAdapter();
    protected abstract Function<E, Function<I,O>> underlyingSystemUnderTestFunction();

    protected DiscreteCaseOracle() {
        underlyingSystemUnderTestFunction = underlyingSystemUnderTestFunction();
        enumAdapter = enumAdapter();
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
        return discreteCaseVerificationProvider();
    }

    protected abstract DiscreteCaseVerificationProvider<E, O> discreteCaseVerificationProvider();

    public static class ConcreteDiscreteCaseOracle
            extends DiscreteCaseOracle<ConcreteDiscreteCaseOracle.Option, Integer, String>{
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
        protected EnumAdapter<Option, Integer> enumAdapter() {
            return new EnumAdapter<Option, Integer>() {
                @Override
                protected EnumMap<Option, Integer> enumMap() {
                    Map<Option, Integer> map = new HashMap<>();
                    // TODO: fix low cohesion: enum adapter and verification provider
                    // TODO: both declaring different sides of a relation
                    map.put(Option.SIMPLE, 1);
                    return new EnumMap<>(map);
                }
            };
        }

        @Override
        protected DiscreteCaseVerificationProvider<Option, String> discreteCaseVerificationProvider(){
            return new DiscreteCaseVerificationProvider<Option, String>() {
                @Override
                protected Map<Option, String> casePairs() {
                    Map<Option, String> map = new HashMap<>();
                    Integer theRightSimpleInput = 1;
                    //TODO: fix low cohesion: enum adapter and verification provider
                    //TODO: both declaring different sides of a relation
                    map.put(Option.SIMPLE, String.valueOf(2 * theRightSimpleInput));
                    return ImmutableMap.copyOf(map);
                }
            };
        }

        private Function<Option, Pair<Integer, String>> pairGenerator(){
            return new Function<Option, Pair<Integer, String>>() {
                @Override
                public Pair<Integer, String> apply(Option option) {
                    return null;
                }
            };
        }

        protected Pair<Integer, String> getPair(Option option){

            switch (option) {
                case SIMPLE:
                    return new ImmutablePair<>(1, "One");
            }
            throw new IllegalArgumentException("The option was not recognised");
        }

        public enum Option {
            SIMPLE
        }

    }
}
