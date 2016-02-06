package com.grayben.tools.testOracle.testContainer;

/**
 * Created by beng on 6/02/2016.
 */
public class TestContainerTest {
    /*

    public enum SystemUnderTestOption {
        SIMPLE(String::valueOf),
        COMPLICATED(integer -> "Number " + String.valueOf(integer));

        private final SystemUnderTest<Integer, String> systemUnderTest;

        public SystemUnderTest<Integer, String> getSystemUnderTest(){
            return this.systemUnderTest;
        }

        SystemUnderTestOption(SystemUnderTest<Integer, String> systemUnderTest) {
            this.systemUnderTest = systemUnderTest;
        }
    }

    @Test
    public void test_TestReturnsFalse_WhenEnumAdapterDoesNotMatchUnderlyingSUTFunction() throws Exception {

        PairGenerator<SystemUnderTestOption, Integer, String> pairGenerator
                = functionOption -> {
                    switch (functionOption){
                        case SIMPLE:
                            return new ImmutablePair<>(1, "foobar");
                        case COMPLICATED:
                            return new ImmutablePair<>(2, "chicken soup");
                    }
                    throw new IllegalArgumentException("The option was not recognised");
                };

        DiscreteCaseTestContainer<SystemUnderTestOption, Integer, String> oracle = new DiscreteCaseTestContainer<>(
                SystemUnderTestOption.class,
                SystemUnderTestOption::getSystemUnderTest,
                pairGenerator
        );

        TestCase.assertFalse(oracle.verify(SystemUnderTestOption.SIMPLE));
        TestCase.assertFalse(oracle.verify(SystemUnderTestOption.COMPLICATED));

    }

    @Test
    public void test_TestReturnsTrue_WhenEnumAdapterDoesMatchUnderlyingSUTFunction() throws Exception {

        PairGenerator<SystemUnderTestOption, Integer, String> pairGenerator
                = functionOption -> {
            Integer input = null;
            switch (functionOption){
                case SIMPLE:
                    input = 1;
                    return new ImmutablePair<>(input, functionOption.getSystemUnderTest().apply(input));
                case COMPLICATED:
                    input = 555;
                    return new ImmutablePair<>(input, functionOption.getSystemUnderTest().apply(input));
            }
            throw new IllegalArgumentException("The option was not recognised");
        };

        DiscreteCaseTestContainer<SystemUnderTestOption, Integer, String> oracle = new DiscreteCaseTestContainer<>(
                SystemUnderTestOption.class,
                SystemUnderTestOption::getSystemUnderTest,
                pairGenerator
        );

        TestCase.assertTrue(oracle.verify(SystemUnderTestOption.SIMPLE));
        TestCase.assertTrue(oracle.verify(SystemUnderTestOption.COMPLICATED));

    }

    */

}