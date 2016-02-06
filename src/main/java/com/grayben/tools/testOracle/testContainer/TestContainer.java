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
    private final Function<I, SystemUnderTest<I, O>> systemUnderTestProvider;

    /**
     * The test oracle represented as a {@link PassiveOracle}
     */
    private final Function<I, PassiveOracle<I, O>> passiveOracleProvider;

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
        SystemUnderTest<I, O> selectedSystemUnderTest = systemUnderTestProvider.apply(input);
        O actualOutput = selectedSystemUnderTest.apply(input);
        PassiveOracle<I, O> selectedPassiveOracle = passiveOracleProvider.apply(input);
        return selectedPassiveOracle.test(input, actualOutput);
    }

    public interface TestContainerBuildable<I, O> {

        TestContainer<I, O> build();
    }

    public static class Builder<I, O>
            implements TestContainerBuildable<I, O> {

        private final Apprentice apprentice;

        public Builder(){
            this.apprentice = this.new Apprentice();
        }

        public SystemUnderTestSettable<I, O> begin(){
            return this.apprentice;
        }

        public interface SystemUnderTestSettable<I, O>  {
            OracleSettable<I, O> systemUnderTest(SystemUnderTest<I, O> systemUnderTest);
        }

        public interface OracleSettable<I, O> {
            TestContainerBuildable<I, O> oracle(PassiveOracle<I, O> passiveOracle);
            TestContainerBuildable<I, O> oracle(ActiveOracle<I, O> activeOracle);
        }

        @Override
        public TestContainer<I, O> build() {
            return new TestContainer<>(this);
        }

        protected class Apprentice
                implements
                SystemUnderTestSettable<I, O>,
                OracleSettable<I, O>{
            private FunctionBuilder<I, SystemUnderTest<I, O>> systemUnderTestProviderBuilder;

            private FunctionBuilder<I, PassiveOracle<I, O>> passiveOracleProviderBuilder;

            public Apprentice() {
            }

            @Override
            public OracleSettable<I, O> systemUnderTest(SystemUnderTest<I, O> systemUnderTest) {
                this.systemUnderTestProviderBuilder = new FunctionBuilder<>(new ConstantFunction<>(systemUnderTest));
                return this;
            }

            @Override
            public TestContainerBuildable<I, O> oracle(PassiveOracle<I, O> passiveOracle) {
                this.passiveOracleProviderBuilder = new FunctionBuilder<>(new ConstantFunction<>(passiveOracle));
                return Builder.this;
            }

            @Override
            public TestContainerBuildable<I, O> oracle(ActiveOracle<I, O> activeOracle) {
                this.passiveOracleProviderBuilder = new FunctionBuilder<>(new ConstantFunction<>(Oracles.passiveOracle(activeOracle)));
                return Builder.this;
            }
        }
    }
}
