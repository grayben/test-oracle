package com.grayben.tools.testOracle.adapter;

import java.util.EnumMap;

/**
 * An implementation of {@link Adapter} leveraging the efficiency of an {@link EnumMap}
 * <p>
 * Created by Ben Gray on 28/01/2016.
 * @param <E> input enum type
 * @param <Y> output type
 */
final public class EnumAdapter<E extends Enum<E>, Y> implements Adapter<E, Y> {

    private final EnumMap<E, Y> enumMap;

    public EnumAdapter(EnumMap<E, Y> enumMap) {
        this.enumMap = enumMap;
    }

    /**
     * @param input the input to adapt
     * @return the adapted input
     */
    @Override
    public Y apply(E input) {
        return enumMap.get(input);
    }
}
