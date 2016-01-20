package com.grayben.tools.testOracle;

import com.grayben.tools.math.parametricEquation.ParametricEquation;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Function;

/**
 * Created by beng on 20/01/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class ParametricTestOracleTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Function<Integer, String> functionMatchingOracleTypeAndRelation;
    private Function<Integer, String> functionMatchingOracleTypeButNotRelation;
    private Function<Integer, Integer> functionNotMatchingOracleType;
    private ParametricEquation<Integer, Integer, String> oracleSutParametricEquation;
    private ParametricTestOracle<Integer, Integer, String> oracleSut;
    //private Function

    @Before
    public void setUp() throws Exception {
        setUpFunctions();
        setUpParametricEquations();
        setUpSut();
    }

    @After
    public void tearDown() throws Exception {
        tearDownFunctions();
        tearDownParametricEquations();
        tearDownSut();
    }
    private void setUpFunctions(){
        this.functionMatchingOracleTypeAndRelation = integer -> String.valueOf(integer);
        this.functionMatchingOracleTypeButNotRelation = integer -> String.valueOf(integer + 1);
        this.functionNotMatchingOracleType = integer -> integer;
    }
    private void tearDownFunctions(){
        this.functionMatchingOracleTypeAndRelation = null;
        this.functionMatchingOracleTypeButNotRelation = null;
        this.functionMatchingOracleTypeButNotRelation = null;
    }
    private void setUpParametricEquations(){
        this.oracleSutParametricEquation = parameter -> {
            Integer input = 2 * parameter;
            String output = functionMatchingOracleTypeAndRelation.apply(input);
            return new ImmutablePair<>(input, output);
        };
    }
    private void tearDownParametricEquations(){
        this.oracleSutParametricEquation = null;
    }
    private void setUpSut(){
        this.oracleSut = new ParametricTestOracle<>(this.oracleSutParametricEquation);
    }

    private void tearDownSut(){
        this.oracleSut = null;
    }

    @Test
    public void test_validateOutputEqualsExpected_DoesNotThrowException_WhenFunctionMapsRelation
            () throws Exception {
        Integer anArbitraryParameter = 56;
        oracleSut.validateOutputEqualsExpected(functionMatchingOracleTypeAndRelation, anArbitraryParameter);
    }

    @Test
    public void test_validateOutputEqualsExpected_ThrowsComparisonFailure_WhenFunctionDoesNotMapRelation
            () throws Exception {
        thrown.expect(ComparisonFailure.class);
        Integer anArbitraryParameter = 56;
        oracleSut.validateOutputEqualsExpected(functionMatchingOracleTypeButNotRelation, anArbitraryParameter);
    }
}