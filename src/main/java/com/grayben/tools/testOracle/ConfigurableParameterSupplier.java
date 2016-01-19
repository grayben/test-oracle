package com.grayben.tools.testOracle;

import java.util.function.Function;

/**
 * Created by beng on 19/01/2016.
 */
public abstract class ConfigurableParameterSupplier<T extends Enum<T>, P> {

    private final Function<T, P> parameterFunction;

    public ConfigurableParameterSupplier(){
        this.parameterFunction = parameterFunction();
    }

    protected abstract Function<T, P> parameterFunction();

    public final P supply(T option){
        return this.parameterFunction.apply(option);
    }

    //TODO: remove example concrete class
    private static class ConcreteConfigurableParameterSupplier
            extends ConfigurableParameterSupplier<ConcreteConfigurableParameterSupplier.Option, Integer> {

        public enum Option {
            SIMPLE,
            COMPLICATED
        }

        @Override
        protected Function<Option, Integer> parameterFunction() {
            return new Function<Option, Integer>() {
                public Integer apply(Option option) {
                    Integer toReturn = null;
                    switch (option){
                        case SIMPLE:
                            toReturn = 1;
                            break;
                        case COMPLICATED:
                            toReturn = 2;
                            break;
                    }
                    return toReturn;
                }
            };
        }
    }
}