package com.grayben.tools.testOracle;

import com.grayben.tools.function.orderedPair.OrderedPair;
import com.grayben.tools.function.orderedPair.OrderedPairCalculable;

/**
 * Created by beng on 19/01/2016.
 */
public abstract class ConfigurableOrderedPairSupplier<E extends Enum<E>, P, I, O> {

    private final ConfigurableParameterSupplier<E, P> configurableParameterSupplier;

    private final OrderedPairCalculable<P, I, O> orderedPairCalculable;

    public ConfigurableOrderedPairSupplier(){
        this.configurableParameterSupplier = configurableParameterSupplier();
        this.orderedPairCalculable = orderedPairCalculable();
    }

    protected abstract ConfigurableParameterSupplier<E,P> configurableParameterSupplier();

    protected abstract OrderedPairCalculable<P,I,O> orderedPairCalculable();

    public final OrderedPair supply(E option){
        return this.orderedPairCalculable.apply(this.configurableParameterSupplier.supply(option));
    }
}
