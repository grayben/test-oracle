package com.grayben.tools.testOracle.oracle;

import java.util.function.BiPredicate;

/**
 * Created by beng on 28/01/2016.
 */
public interface PassiveOracle<I, O> extends BiPredicate<I, O> {
}
