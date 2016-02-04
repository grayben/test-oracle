package com.grayben.tools.testOracle.testContainer;

import com.grayben.tools.math.function.ConstantFunction;
import com.grayben.tools.math.function.builder.FunctionBuilder;
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

    protected TestContainer(Builder.TheRealBuilder<I, O> builder){
        this.passiveOracleProvider = builder.passiveOracleProviderBuilder.build();
        this.systemUnderTestProvider = builder.systemUnderTestProviderBuilder.build();
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

        public Builder(){}

        public SystemUnderTestSettable<I, O> begin(){
            return new TheRealBuilder<>();
        }

        public interface SystemUnderTestSettable<I, O> extends SystemUnderTestAndOracleSettable<I, O> {

            OracleSettable<I, O> systemUnderTest(SystemUnderTest<I, O> systemUnderTest);
            OracleSettable<I, O> systemUnderTestProvider(Function<I, SystemUnderTest<I, O>> systemUnderTestProvider);
        }

        public interface OracleSettable<I, O> {

            TestContainerBuildable<I, O> oracle(PassiveOracle<I, O> passiveOracle);
            TestContainerBuildable<I, O> passiveOracleProvider(Function<I, PassiveOracle<I, O>> passiveOracleProvider);
            TestContainerBuildable<I, O> oracle(ActiveOracle<I, O> activeOracle);
            TestContainerBuildable<I, O> activeOracleProvider(Function<I, ActiveOracle<I, O>> activeOracleProvider);
        }

        public interface SystemUnderTestAndOracleSettable<I, O> {

            TestContainerBuildable<I, O> systemUnderTestAndPassiveOracleProvider(
                    ParametricEquation<
                            I,
                            SystemUnderTest<I, O>,
                            PassiveOracle<I, O>
                            > parametricEquation);

            TestContainerBuildable<I, O> systemUnderTestAndActiveOracleProvider(
                    ParametricEquation<
                            I,
                            SystemUnderTest<I, O>,
                            ActiveOracle<I, O>
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

            private FunctionBuilder<I, SystemUnderTest<I, O>> systemUnderTestProviderBuilder;
            private FunctionBuilder<I, PassiveOracle<I, O>> passiveOracleProviderBuilder;

            public TheRealBuilder() {
            }

            @Override
            public OracleSettable<I, O> systemUnderTest(SystemUnderTest<I, O> systemUnderTest) {
                this.systemUnderTestProviderBuilder = new FunctionBuilder<>(new ConstantFunction<>(systemUnderTest));
                return this;
            }

            @Override
            public OracleSettable<I, O> systemUnderTestProvider(Function<I, SystemUnderTest<I, O>> systemUnderTestProvider) {
                this.systemUnderTestProviderBuilder = new FunctionBuilder<>(systemUnderTestProvider);
                return this;
            }

            @Override
            public TestContainerBuildable<I, O> oracle(PassiveOracle<I, O> passiveOracle) {
                this.passiveOracleProviderBuilder = new FunctionBuilder<>(new ConstantFunction<>(passiveOracle));
                return this;
            }

            @Override
            public TestContainerBuildable<I, O> passiveOracleProvider(Function<I, PassiveOracle<I, O>> passiveOracleProvider) {
                this.passiveOracleProviderBuilder = new FunctionBuilder<>(passiveOracleProvider);
                return this;
            }

            @Override
            public TestContainerBuildable<I, O> oracle(ActiveOracle<I, O> activeOracle) {
                this.passiveOracleProviderBuilder = new FunctionBuilder<>(new ConstantFunction<>(Oracles.passiveOracle(activeOracle)));
                return this;
            }

            @Override
            public TestContainerBuildable<I, O> activeOracleProvider(Function<I, ActiveOracle<I, O>> activeOracleProvider) {
                this.passiveOracleProviderBuilder = new FunctionBuilder<>(input -> Oracles.passiveOracle(activeOracleProvider.apply(input)));
                return this;
            }

            @Override
            public TestContainer<I, O> build() {
                return new TestContainer<>(this);
            }

            @Override
            public TestContainerBuildable<I, O> systemUnderTestAndPassiveOracleProvider(ParametricEquation<I, SystemUnderTest<I, O>, PassiveOracle<I, O>> parametricEquation) {
                this.systemUnderTestProviderBuilder = new FunctionBuilder<>(input -> parametricEquation.apply(input).getLeft());
                this.passiveOracleProviderBuilder = new FunctionBuilder<>(input -> parametricEquation.apply(input).getRight());
                return this;
            }

            @Override
            public TestContainerBuildable<I, O> systemUnderTestAndActiveOracleProvider(ParametricEquation<I, SystemUnderTest<I, O>, ActiveOracle<I, O>> parametricEquation) {
                this.systemUnderTestProviderBuilder = new FunctionBuilder<>(input -> parametricEquation.apply(input).getLeft());
                this.passiveOracleProviderBuilder = new FunctionBuilder<>(input -> Oracles.passiveOracle(parametricEquation.apply(input).getRight()));
                return this;
            }
        }
    }
}
