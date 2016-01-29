package com.grayben.tools.testOracle.verification;

import java.util.function.BiPredicate;

/**
 * Created by beng on 28/01/2016.
 */
public interface VerificationProvider<I, O> extends BiPredicate<I, O> {
}
