package com.grayben.tools.testOracle.testContainer;

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
    private final Function<I, SystemUnderTest<I, O>> systemUnderTestProvider;

    /**
     * The test oracle represented as a {@link PassiveOracle}
     */
    private final Function<I, PassiveOracle<I, O>> passiveOracleProvider;

    private TestContainer(Builder<I, O> builder){
        this.passiveOracleProvider = builder.passiveOracleProvider;
        this.systemUnderTestProvider = builder.systemUnderTestProvider;
    }

    /**
     * For the specified input, verify the system under test on the specified input.
     * @param input the input to the {@link SystemUnderTest} encapsulated by this {@link TestContainer}
     * @return true if and only if the system under test is verified on the specified input
     */
    final public boolean verify(I input) {
        SystemUnderTest<I, O> selectedSystemUnderTest = systemUnderTestProvider.apply(input);
        O actualOutput = selectedSystemUnderTest.apply(input);
        PassiveOracle<I, O> selectedPassiveOracle = passiveOracleProvider.apply(input);
        return selectedPassiveOracle.test(input, actualOutput);
    }

    public static class Builder<I, O> {

        private Function<I, SystemUnderTest<I, O>> systemUnderTestProvider;
        private Function<I, PassiveOracle<I, O>> passiveOracleProvider;

        public Builder(){}

        public SystemUnderTestSettable<I, O> begin(){
            return new TheRealBuilder<>(this); 
        }

        public interface SystemUnderTestSettable<I, O> {
            OracleSettable<I, O> systemUnderTest(SystemUnderTest<I, O> systemUnderTest);
            OracleSettable<I, O> systemUnderTestProvider(Function<I, SystemUnderTest<I, O>> systemUnderTestProvider);
        }

        public interface OracleSettable<I, O> {
            TestContainerBuildable<I, O> oracle(PassiveOracle<I, O> passiveOracle);

            TestContainerBuildable<I, O> passiveOracleProvider(Function<I, PassiveOracle<I, O>> passiveOracleProvider);

            TestContainerBuildable<I, O> oracle(ActiveOracle<I, O> activeOracle);

            TestContainerBuildable<I, O> activeOracleProvider(Function<I, ? extends ActiveOracle<I, O>> activeOracleProvider);
        }

        public interface TestContainerBuildable<I, O> {
            TestContainer<I, O> build();
        }

        private static class TheRealBuilder<I, O>
                implements
                SystemUnderTestSettable<I, O>,
                OracleSettable<I, O>,
                TestContainerBuildable<I, O> {

            private final Builder<I, O> builder;

            public TheRealBuilder(Builder<I, O> builder) {
                this.builder = builder;
            }

            @Override
            public OracleSettable<I, O> systemUnderTest(SystemUnderTest<I, O> systemUnderTest) {
                this.builder.systemUnderTestProvider = input -> systemUnderTest;
                return this;
            }

            @Override
            public OracleSettable<I, O> systemUnderTestProvider(Function<I, SystemUnderTest<I, O>> systemUnderTestProvider) {
                this.builder.systemUnderTestProvider = systemUnderTestProvider;
                return this;
            }

            @Override
            public TestContainerBuildable<I, O> oracle(PassiveOracle<I, O> passiveOracle) {
                this.builder.passiveOracleProvider = input -> passiveOracle;
                return this;
            }

            @Override
            public TestContainerBuildable<I, O> passiveOracleProvider(Function<I, PassiveOracle<I, O>> passiveOracleProvider) {
                this.builder.passiveOracleProvider = passiveOracleProvider;
                return this;
            }

            @Override
            public TestContainerBuildable<I, O> oracle(ActiveOracle<I, O> activeOracle) {
                this.builder.passiveOracleProvider = input -> Oracles.passiveOracle(activeOracle);
                return this;
            }

            @Override
            public TestContainerBuildable<I, O> activeOracleProvider(Function<I, ? extends ActiveOracle<I, O>> activeOracleProvider) {
                this.builder.passiveOracleProvider = input -> Oracles.passiveOracle(activeOracleProvider.apply(input));
                return this;
            }

            @Override
            public TestContainer<I, O> build() {
                return new TestContainer<>(this.builder);
            }
        }


    }
}
