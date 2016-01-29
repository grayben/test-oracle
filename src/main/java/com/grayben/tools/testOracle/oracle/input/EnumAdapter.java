package com.grayben.tools.testOracle.oracle.input;

import java.util.EnumMap;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class EnumAdapter<E extends Enum<E>, I> implements InputAdapter<E, I> {

    private final EnumMap<E, I> enumMap;
    protected abstract EnumMap<E, I> enumMap();

    public EnumAdapter() {
        enumMap = enumMap();
    }

    @Override
    final public I apply(E e) {
        return enumMap.get(e);
    }
}
