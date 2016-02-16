package com.grayben.tools.testOracle.testContainer;

import com.grayben.tools.math.function.ConstantFunction;
import com.grayben.tools.math.function.builder.FunctionBuilder;
import com.grayben.tools.testOracle.SystemUnderTest;
import com.grayben.tools.testOracle.oracle.Oracles;
import com.grayben.tools.testOracle.oracle.active.ActiveOracle;
import com.grayben.tools.testOracle.oracle.passive.PassiveOracle;

import java.util.function.Function;

/**
 * A class responsible for invoking the {@link SystemUnderTest} and verifying the output using a test oracle.
 * <p>
 * Created by Ben Gray on 28/01/2016.
 * @see ActiveOracle
 * @see PassiveOracle
 * @param <I> the input type
 * @param <O> the output type
 */
public class TestContainer<I, O> {

    /**
     * The system under test
     */
    private final Function<I, SystemUnderTest<? super I, ? extends O>> systemUnderTestProvider;

    /**
     * The test oracle represented as a {@link PassiveOracle}
     */
    private final Function<I, PassiveOracle<? super I, ? super O>> passiveOracleProvider;

    /**
     * Use the specified builder to construct this test container.
     * @param builder the completed builder upon which to base construction
     */
    protected TestContainer(Builder<I, O> builder){
        this.passiveOracleProvider = builder.apprentice.passiveOracleProviderBuilder.build();
        this.systemUnderTestProvider = builder.apprentice.systemUnderTestProviderBuilder.build();
    }

    /**
     * For the specified input, verify the system under test on the specified input.
     * @param input the input to the {@link SystemUnderTest} encapsulated by this {@link TestContainer}
     * @return true if and only if the system under test is verified on the specified input
     */
    final public boolean verify(I input) {
        SystemUnderTest<? super I, ? extends O> selectedSystemUnderTest = systemUnderTestProvider.apply(input);
        O actualOutput = selectedSystemUnderTest.apply(input);
        PassiveOracle<? super I, ? super O> selectedPassiveOracle = passiveOracleProvider.apply(input);
        return selectedPassiveOracle.test(input, actualOutput);
    }

    /**
     * Defines methods which build a {@link TestContainer}
     * @param <I> the input type
     * @param <O> the output type
     */
    public interface TestContainerBuildable<I, O> {

        /**
         * @return a completed test container
         */
        TestContainer<I, O> build();
    }

    /**
     * A builder implementing the builder design pattern.
     *
     * @param <I> the input type
     * @param <O> the output type
     */
    public static class Builder<I, O>
            implements TestContainerBuildable<I, O> {

        /**
         * This builder's apprentice.
         */
        private final Apprentice apprentice;

        /**
         * Construct a builder.
         */
        public Builder(){
            this.apprentice = this.new Apprentice();
        }

        /**
         * Used to begin planning of the target {@link TestContainer}.
         * <p>
         * Used to hack around problems developing this builder with generic types; may be
         * removed in future releases if/when these problems can be overcome in a more elegant fashion.
         * @return the next builder context
         */
        public SystemUnderTestSettable<I, O> begin(){
            return this.apprentice;
        }

        /**
         * Defines a context in which the system under test of the target {@link TestContainer}
         * may be specified.
         *
         * @param <I> the input type
         * @param <O> the output type
         */
        public interface SystemUnderTestSettable<I, O>  {

            /**
             * Plan the target {@link TestContainer} with the specified {@link SystemUnderTest}.
             *
             * @param systemUnderTest the system under test for use by the target {@link TestContainer}.
             * @return the next builder context
             */
            OracleSettable<I, O> systemUnderTest(SystemUnderTest<? super I, ? extends O> systemUnderTest);
        }

        /**
         * Defines a context in which the test oracle of the target {@link TestContainer}
         * may be specified.
         *
         * @param <I> the input type
         * @param <O> the output type
         */
        public interface OracleSettable<I, O> {

            /**
             * Plan the target {@link TestContainer} with the specified {@link PassiveOracle}.
             *
             * @param passiveOracle the passive oracle for use by the target {@link TestContainer}
             * @return the next builder context
             */
            TestContainerBuildable<I, O> oracle(PassiveOracle<? super I, ? super O> passiveOracle);

            /**
             * Plan the target {@link TestContainer} with the specified {@link ActiveOracle}.
             *
             * @param activeOracle the active oracle for use by the target {@link TestContainer}
             * @return the next builder context
             */
            TestContainerBuildable<I, O> oracle(ActiveOracle<? super I, ? extends O> activeOracle);
        }

        @Override
        public TestContainer<I, O> build() {
            return new TestContainer<>(this);
        }

        /**
         * A sub-builder of sorts. Exists as another class which encapsulates
         * certain builder methods to hack around some problems with implementing a generic
         * builder in Java.
         * <p>
         * May be removed in a later release if/when a more elegant solution is found.
         */
        protected class Apprentice
                implements
                SystemUnderTestSettable<I, O>,
                OracleSettable<I, O>{

            /**
             * The {@link FunctionBuilder} to use to provide the {@link SystemUnderTest} for
             * use by the target {@link TestContainer}.
             * <p>
             * The FunctionBuilder -> SystemUnderTest layer of indirection is used here
             * to allow later implementations to add flexibility to this builder. However, as at
             * this version, this indirection is not utilised.
             */
            private FunctionBuilder<I, SystemUnderTest<? super I, ? extends O>> systemUnderTestProviderBuilder;

            /**
             * The {@link FunctionBuilder} to use to provide the {@link PassiveOracle} for
             * use by the target {@link TestContainer}.
             * <p>
             * The FunctionBuilder -> {@link PassiveOracle} layer of indirection is used here
             * to allow later implementations to add flexibility to this builder. However, as at
             * this version, this indirection is not utilised.
             */
            private FunctionBuilder<I, PassiveOracle<? super I, ? super O>> passiveOracleProviderBuilder;

            public Apprentice() {
            }

            @Override
            public OracleSettable<I, O> systemUnderTest(SystemUnderTest<? super I, ? extends O> systemUnderTest) {
                this.systemUnderTestProviderBuilder = new FunctionBuilder<>(new ConstantFunction<>(systemUnderTest));
                return this;
            }

            @Override
            public TestContainerBuildable<I, O> oracle(PassiveOracle<? super I, ? super O> passiveOracle) {
                this.passiveOracleProviderBuilder = new FunctionBuilder<>(new ConstantFunction<>(passiveOracle));
                return Builder.this;
            }

            @Override
            public TestContainerBuildable<I, O> oracle(ActiveOracle<? super I, ? extends O> activeOracle) {
                this.passiveOracleProviderBuilder = new FunctionBuilder<>(new ConstantFunction<>(Oracles.passiveOracle(activeOracle)));
                return Builder.this;
            }
        }
    }
}
