package com.grayben.tools.testOracle.testContainer.input;

import java.util.EnumMap;

/**
 * Created by beng on 28/01/2016.
 */
final public class EnumAdapter<E extends Enum<E>, Y> implements Adapter<E, Y> {

    private final EnumMap<E, Y> enumMap;

    public EnumAdapter(EnumMap<E, Y> enumMap) {
        this.enumMap = enumMap;
    }

    @Override
    public Y apply(E e) {
        return enumMap.get(e);
    }
}
