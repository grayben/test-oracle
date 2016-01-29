package com.grayben.tools.testOracle.oracle.input;

import java.util.EnumMap;

/**
 * Created by beng on 28/01/2016.
 */
public abstract class EnumAdapter<E extends Enum<E>, Y> implements Adapter<E, Y> {

    private final EnumMap<E, Y> enumMap;
    protected abstract EnumMap<E, Y> enumMap();

    public EnumAdapter() {
        enumMap = enumMap();
    }

    @Override
    final public Y apply(E e) {
        return enumMap.get(e);
    }
}
