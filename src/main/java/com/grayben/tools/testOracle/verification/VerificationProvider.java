package com.grayben.tools.testOracle.verification;

/**
 * Created by beng on 28/01/2016.
 */
public interface VerificationProvider<I, O> {

    boolean verify(I input, O output);

}
