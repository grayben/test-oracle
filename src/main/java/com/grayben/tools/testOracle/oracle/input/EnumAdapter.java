package com.grayben.tools.testOracle.oracle.input;

import java.util.EnumMap;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class EnumAdapter<E extends Enum<E>, I> extends InputAdapter<E, I> {

    private final EnumMap<E, I> enumMap;

    public EnumAdapter() {
        enumMap = enumMap();
    }

    protected abstract EnumMap<E, I> enumMap();

    @Override
    public I apply(E e) {
        return enumMap.get(e);
    }
}
