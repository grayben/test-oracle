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

    /**
     * Constructs a {@link TestContainer} which invokes the specified {@link SystemUnderTest} and verifies
     * its output using the specified {@link PassiveOracle}.
     * @param systemUnderTest the system under test
     * @param passiveOracle the passive oracle
     */
    public TestContainer(SystemUnderTest<I, O> systemUnderTest, PassiveOracle<I, O> passiveOracle) {
        // ignore the input: a single SUT was given
        this.systemUnderTestProvider = input -> systemUnderTest;

        // ignore the input: a single oracle was given
        this.passiveOracleProvider = input -> passiveOracle;
    }

    /**
     * Constructs a {@link TestContainer} which invokes the specified {@link SystemUnderTest} and verifies
     * its output using the specified {@link ActiveOracle}.
     * @param systemUnderTest the system under test
     * @param activeOracle the active oracle
     */
    public TestContainer(SystemUnderTest<I, O> systemUnderTest, ActiveOracle<I, O> activeOracle) {
        // ignore the input: a single SUT was given
        this.systemUnderTestProvider = input -> systemUnderTest;


        /**
         * Ignore the input: a single oracle was given.
         * <p>
         * Convert the {@link activeOracle} to its equivalent {@link passiveOracle} and then assign it
         */
        this.passiveOracleProvider = input -> Oracles.passiveOracle(activeOracle);
    }

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

    private interface IBeginBuild<I, O> {}

    private interface ISystemUnderTest<I, O> extends IBeginBuild<I, O> {
        IOracle systemUnderTest(SystemUnderTest<I, O> systemUnderTest);
        IOracle systemUnderTestProvider(Function<I, SystemUnderTest<I, O>> systemUnderTestProvider);
    }

    private interface IOracle<I, O> {
        IBuild oracle(PassiveOracle<I, O> passiveOracle);

        IBuild passiveOracleProvider(Function<I, PassiveOracle<I, O>> passiveOracleProvider);

        IBuild oracle(ActiveOracle<I, O> activeOracle);

        IBuild activeOracleProvider(Function<I, ? extends ActiveOracle<I, O>> activeOracleProvider);
    }

    private interface IBuild<I, O> {
        TestContainer<I, O> build();
    }

    public interface TestContainerBuilder<I, O>
            extends ISystemUnderTest<I, O>,
            IOracle<I, O>,
            IBuild<I, O> {}

    public static class Builder<I, O> implements TestContainerBuilder<I,O> {
        private Function<I, SystemUnderTest<I, O>> systemUnderTestProvider;
        private Function<I, PassiveOracle<I, O>> passiveOracleProvider;

        public Builder(){}

        public ISystemUnderTest<I, O> begin(){
            return this;
        }

        @Override
        public TestContainer<I, O> build() {
            return new TestContainer<>(this);
        }

        @Override
        public IOracle systemUnderTest(SystemUnderTest<I, O> systemUnderTest) {
            this.systemUnderTestProvider = input -> systemUnderTest;
            return this;
        }

        @Override
        public IOracle systemUnderTestProvider(Function<I, SystemUnderTest<I, O>> systemUnderTestProvider) {
            this.systemUnderTestProvider = systemUnderTestProvider;
            return this;
        }

        @Override
        public IBuild oracle(PassiveOracle<I, O> passiveOracle) {
            this.passiveOracleProvider = input -> passiveOracle;
            return this;
        }

        @Override
        public IBuild passiveOracleProvider(Function<I, PassiveOracle<I, O>> passiveOracleProvider) {
            this.passiveOracleProvider = passiveOracleProvider;
            return this;
        }

        @Override
        public IBuild oracle(ActiveOracle<I, O> activeOracle) {
            this.passiveOracleProvider = input -> Oracles.passiveOracle(activeOracle);
            return this;
        }

        @Override
        public IBuild activeOracleProvider(Function<I, ? extends ActiveOracle<I, O>> activeOracleProvider) {
            this.passiveOracleProvider = input -> Oracles.passiveOracle(activeOracleProvider.apply(input));
            return this;
        }
    }

    public static void main(){
        Builder<Integer, String> testContainerBuilder = new Builder<>();
        testContainerBuilder.begin().systemUnderTest(null).activeOracleProvider(null).build();
        TestContainer<Integer, String> container = new TestContainer<Integer, String>()
    }
}
