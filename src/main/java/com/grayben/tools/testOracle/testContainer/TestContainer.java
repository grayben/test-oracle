package com.grayben.tools.testOracle.testContainer;

import com.grayben.tools.math.function.ConstantFunction;
import com.grayben.tools.math.function.parametric.ParametricEquation;
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
public class TestContainer<I, O>{

    /**
     * The system under test
     */
    private final Function<? super I, ? extends SystemUnderTest<? super I, ? extends O>> systemUnderTestProvider;

    /**
     * The test oracle represented as a {@link PassiveOracle}
     */
    private final Function<? super I, ? extends PassiveOracle<? super I, ? super O>> passiveOracleProvider;

    protected TestContainer(Builder<I, O> builder){
        this.passiveOracleProvider = builder.passiveOracleProvider;
        this.systemUnderTestProvider = builder.systemUnderTestProvider;
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

    public static class Builder<I, O> {

        private Function<? super I, ? extends SystemUnderTest<? super I, ? extends O>> systemUnderTestProvider;
        private Function<? super I, ? extends PassiveOracle<? super I, ? super O>> passiveOracleProvider;

        public Builder(){}

        public SystemUnderTestSettable<I, O> begin(){
            return new TheRealBuilder<>(this);
        }

        public interface SystemUnderTestSettable<I, O> extends SystemUnderTestAndOracleSettable<I, O> {
            OracleSettable<I, O> systemUnderTest(SystemUnderTest<? super I, ? extends O> systemUnderTest);
            OracleSettable<I, O> systemUnderTestProvider(Function<? super I, ? extends SystemUnderTest<? super I, ? extends O>> systemUnderTestProvider);
        }

        public interface OracleSettable<I, O> {
            TestContainerBuildable<I, O> oracle(PassiveOracle<? super I, ? super O> passiveOracle);

            TestContainerBuildable<I, O> passiveOracleProvider(Function<? super I, ? extends PassiveOracle<? super I, ? super O>> passiveOracleProvider);

            TestContainerBuildable<I, O> oracle(ActiveOracle<? super I, ? extends O> activeOracle);

            TestContainerBuildable<I, O> activeOracleProvider(Function<? super I, ? extends ActiveOracle<? super I, ? extends O>> activeOracleProvider);
        }

        public interface SystemUnderTestAndOracleSettable<I, O> {
            TestContainerBuildable<I, O> systemUnderTestAndPassiveOracleProvider(
                    ParametricEquation<
                            ? super I,
                            ? extends SystemUnderTest<? super I, ? extends O>,
                            ? extends PassiveOracle<? super I, ? super O>
                    > parametricEquation);

            TestContainerBuildable<I, O> systemUnderTestAndActiveOracleProvider(
                    ParametricEquation<
                            ? super I,
                            ? extends SystemUnderTest<? super I, ? extends O>,
                            ? extends ActiveOracle<? super I, ? extends O>
                    > parametricEquation);
        }

        public interface TestContainerBuildable<I, O> {
            TestContainer<I, O> build();
        }

        protected static class TheRealBuilder<I, O>
                implements
                SystemUnderTestSettable<I, O>,
                OracleSettable<I, O>,
                TestContainerBuildable<I, O> {

            private final Builder<I, O> builder;

            public TheRealBuilder(Builder<I, O> builder) {
                this.builder = builder;
            }

            @Override
            public OracleSettable<I, O> systemUnderTest(SystemUnderTest<? super I, ? extends O> systemUnderTest) {
                this.builder.systemUnderTestProvider = new ConstantFunction<>(systemUnderTest);
                return this;
            }

            @Override
            public OracleSettable<I, O> systemUnderTestProvider(Function<? super I, ? extends SystemUnderTest<? super I, ? extends O>> systemUnderTestProvider) {
                this.builder.systemUnderTestProvider = systemUnderTestProvider;
                return this;
            }

            @Override
            public TestContainerBuildable<I, O> oracle(PassiveOracle<? super I, ? super O> passiveOracle) {
                this.builder.passiveOracleProvider = new ConstantFunction<>(passiveOracle);
                return this;
            }

            @Override
            public TestContainerBuildable<I, O> passiveOracleProvider(Function<? super I, ? extends PassiveOracle<? super I, ? super O>> passiveOracleProvider) {
                this.builder.passiveOracleProvider = passiveOracleProvider;
                return this;
            }

            @Override
            public TestContainerBuildable<I, O> oracle(ActiveOracle<? super I, ? extends O> activeOracle) {
                this.builder.passiveOracleProvider = new ConstantFunction<>(Oracles.passiveOracle(activeOracle));
                return this;
            }

            @Override
            public TestContainerBuildable<I, O> activeOracleProvider(Function<? super I, ? extends ActiveOracle<? super I, ? extends O>> activeOracleProvider) {
                this.builder.passiveOracleProvider = input -> Oracles.passiveOracle(activeOracleProvider.apply(input));
                return this;
            }

            @Override
            public TestContainer<I, O> build() {
                return new TestContainer<>(this.builder);
            }

            @Override
            public TestContainerBuildable<I, O> systemUnderTestAndPassiveOracleProvider(ParametricEquation<? super I, ? extends SystemUnderTest<? super I, ? extends O>, ? extends PassiveOracle<? super I, ? super O>> parametricEquation) {
                this.builder.systemUnderTestProvider = input -> parametricEquation.apply(input).getLeft();
                this.builder.passiveOracleProvider = input -> parametricEquation.apply(input).getRight();
                return this;
            }

            @Override
            public TestContainerBuildable<I, O> systemUnderTestAndActiveOracleProvider(ParametricEquation<? super I, ? extends SystemUnderTest<? super I, ? extends O>, ? extends ActiveOracle<? super I, ? extends O>> parametricEquation) {
                this.builder.systemUnderTestProvider = input -> parametricEquation.apply(input).getLeft();
                this.builder.passiveOracleProvider = input -> Oracles.passiveOracle(parametricEquation.apply(input).getRight());
                return this;
            }
        }
    }
}
