package com.grayben.tools.testOracle.sut.configuration;

/**
 * Created by beng on 18/01/2016.
 */
public interface SUTConfigurationRetrievable<C extends Enum<C>> {
    C getConfig();
}