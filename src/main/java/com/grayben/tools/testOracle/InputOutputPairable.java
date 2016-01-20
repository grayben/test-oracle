package com.grayben.tools.testOracle;

/**
 * Created by beng on 20/01/2016.
 */
public interface InputOutputPairable<I, O> {
    I input();
    O output();
}
