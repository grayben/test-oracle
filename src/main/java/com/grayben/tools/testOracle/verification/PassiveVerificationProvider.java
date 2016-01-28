package com.grayben.tools.testOracle.verification;

import java.util.function.BiPredicate;

/**
 * Created by beng on 28/01/2016.
 */
public interface PassiveVerificationProvider<I, O> extends BiPredicate<I, O> {
}
